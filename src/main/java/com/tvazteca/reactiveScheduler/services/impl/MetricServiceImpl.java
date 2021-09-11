package com.tvazteca.reactiveScheduler.services.impl;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tvazteca.reactiveScheduler.models.Cuenta;
import com.tvazteca.reactiveScheduler.models.documents.ViewsDocument;
import com.tvazteca.reactiveScheduler.models.repositories.ViewsRepository;
import com.tvazteca.reactiveScheduler.services.MetricService;


@Service("serviceMetric")
public class MetricServiceImpl implements MetricService{
	
	@Autowired
	private ViewsRepository dao;
	
	static RestTemplate restTemplate = new RestTemplate();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MetricServiceImpl.class);
	private static final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final String appConfigPath = rootPath + "app.properties";
	private static final Properties appProps = new Properties();
	
	
	
	@Override
	public List<ViewsDocument> getMetricByDate(String cuenta, String fecha) {
		LOGGER.info("****************** GET METRIC SERVICE ******************");
		List<ViewsDocument> views = new ArrayList<>();
		Cuenta credentiales = getCredentials(cuenta);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("X-api-token", credentiales.getToken());
		String parameters = "{\n"
				+ "    \"name\": \"stream_per\",\n"
				+ "    \"dimension\": [\n"
				+ "        \"content_id\"\n"
				+ "    ],\n"
				+ "    \"filter\": [\n"
				+ "        {\n"
				+ "            \"name\": \"date\",\n"
				+ "            \"op\": [\n"
				+ "                \">=\",\n"
				+ "                \"<=\"\n"
				+ "            ],\n"
				+ "            \"value\": [\n"
				+ "                \""+fecha+"T10:00:00Z\",\n"
				+ "                \""+fecha+"T10:59:59Z\"\n"
				+ "            ]\n"
				+ "        },\n"
				+ "        {\n"
				+ "            \"name\": \"content_type\",\n"
				+ "            \"value\": [\n"
				+ "                \"vod\"\n"
				+ "            ]\n"
				+ "        }\n"
				+ "    ]\n"
				+ "}";
		HttpEntity<String> entity = new HttpEntity<>(parameters, headers);
		LOGGER.info(entity.getHeaders().toString());
		LOGGER.info(entity.getBody());
		String url=credentiales.getUri();

		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
		
		if (response.hasBody()) {
		    LOGGER.info("Request Successful");
		    //LOGGER.info(response.getBody());
		    
		    JSONObject root = new JSONObject(response.getBody());
		    JSONArray jsonObj = root.getJSONArray("data");


		    for(int i=0; i<jsonObj.length(); i++) {
		    	JSONObject JView = jsonObj.getJSONObject(i);
		    	ViewsDocument view =  setViewsDocument(JView, cuenta, fecha);
		    	LOGGER.info(view.toString());
		    	dao.save(view);
		    	views.add(view);
		    }
		} else {
			LOGGER.info("Request Failed");
			LOGGER.info(response.getStatusCode().toString());
		}
		
		return views;
	}
	
	public static ViewsDocument setViewsDocument(JSONObject view, String cuenta, String fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha1 = new Date();
		try {
			fecha1 = sdf.parse(fecha);
		} catch (Exception e) {
			
		}
		
		ViewsDocument viewDocument = new ViewsDocument();
		viewDocument.setContent_id(view.getString("content_id"));
		viewDocument.setCuenta(cuenta);
		viewDocument.setViews(view.getInt("start"));
		viewDocument.setFecha(fecha1);
		return viewDocument;
		
	}
	
	public static Cuenta getCredentials(String cuenta) {
		
		try {
			appProps.load(new FileInputStream(appConfigPath));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Cuenta credentials = new Cuenta();
		String token= appProps.getProperty("app.mediastream."+cuenta+".token");
		String uri= appProps.getProperty("app.mediastream.api.metric");
		credentials.setToken(token);
		credentials.setUri(uri);
		credentials.setUri_token(uri+"?token="+token);
		return credentials;
	}
	

}
