package com.tvazteca.reactiveScheduler.models;

public class Cuenta {
	String token;
	String uri;
	String uri_token;
	
	public Cuenta() {
		
	}
	
	public Cuenta(String token, String uri, String uri_token) {
		super();
		this.token = token;
		this.uri = uri;
		this.uri_token = uri_token;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUri_token() {
		return uri_token;
	}
	public void setUri_token(String uri_token) {
		this.uri_token = uri_token;
	}
	
	
}
