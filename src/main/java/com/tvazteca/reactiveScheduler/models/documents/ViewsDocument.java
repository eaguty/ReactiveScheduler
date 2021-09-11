package com.tvazteca.reactiveScheduler.models.documents;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="views")
public class ViewsDocument {
	
	@Id
	private String id;
	
	String content_id;
	Date fecha;
	int views;
	String cuenta;
	
	public ViewsDocument() {
		
	}
	
	

	public ViewsDocument(String id, String content_id, Date fecha, int views, String cuenta) {
		this.id = id;
		this.content_id = content_id;
		this.fecha = fecha;
		this.views = views;
		this.cuenta = cuenta;
	}



	public String get_id() {
		return id;
	}



	public void set_id(String id) {
		this.id = id;
	}



	public String getContent_id() {
		return content_id;
	}

	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	
	
	
	
	
}