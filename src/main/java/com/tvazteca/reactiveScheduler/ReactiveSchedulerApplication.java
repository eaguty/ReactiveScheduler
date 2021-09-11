package com.tvazteca.reactiveScheduler;


import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.tvazteca.reactiveScheduler.models.repositories.ViewsRepository;

@SpringBootApplication
public class ReactiveSchedulerApplication implements CommandLineRunner {
	
	
	@Autowired
	private ViewsRepository dao;
	
	
	/*@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	*/
	private static final Logger log= LoggerFactory.getLogger(ReactiveSchedulerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReactiveSchedulerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		WebClient client = WebClient.create();
		
		
		
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
				+ "                \"2021-06-29T10:00:00Z\",\n"
				+ "                \"2021-06-30T10:59:59Z\"\n"
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
		

		WebClient.ResponseSpec responseSpec = client.post()
				    .uri(new URI("https://metrics.mdstrm.com/outbound/v1/metric/api/"))
				    .header("X-api-token", "TOKEN")
				    .accept(MediaType.APPLICATION_JSON)
				    .contentType(MediaType.APPLICATION_JSON)
				    .body(BodyInserters.fromValue(parameters))
				    .retrieve();
				    
			
			String responseBody = responseSpec.bodyToMono(String.class).block();
			System.out.println(responseBody);
		
		
		
			    
		//String responseBody = responseSpec.bodyToMono(String.class).block();
		
		
		
		
		
		//mongoTemplate.dropCollection("producto").subscribe();
		/*Flux<ViewsDocument> views = dao.findAll();
		views.subscribe(view -> log.info("ID: "+view.getContent_id()+" Fue visto "+view.getViews()+" veces en la fecha "+view.getFecha()));
		*/
		/*Flux.just(new Producto("TV", 456.89),
				new Producto("TV1", 456.89),
				new Producto("TV2", 456.89),
				new Producto("TV3", 456.89),
				new Producto("TV4", 456.89),
				new Producto("TV5", 456.89),
				new Producto("TV6", 456.89),
				new Producto("TV7", 456.89),
				new Producto("TV8", 456.89),
				new Producto("TV9", 456.89),
				new Producto("TV10", 456.89),
				new Producto("TV11", 456.89),
				new Producto("TV12", 456.89),
				new Producto("Apple", 789.654))
		.flatMap(producto -> {
			producto.setCreateAt(new Date());
			return dao.save(producto);
			})
		.subscribe(producto -> log.info("Insert: "+ producto.getNombre() + " Precio:"));
		
		*/
	}
	
	/*public static Flux<ViewsDocument> saveMetricsByDate(){
		log.info("***************************");
		log.info("Inicio de saveMetricsByDate");
		log.info("***************************");
		return component.saveMetricsByDate();
	}*/
	

}
