package com.mycompany.v1.models.entity;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class MySession extends WebSession {
	
	private static final long serialVersionUID = 6169771008525331019L;

	private String token;

	public MySession(Request request) {
		super(request);
		
	}
	
	public String getToken() {
		return this.token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

}
