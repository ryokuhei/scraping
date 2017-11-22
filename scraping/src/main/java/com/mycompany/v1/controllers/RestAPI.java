package com.mycompany.v1.controllers;

import org.wicketstuff.rest.contenthandling.json.objserialdeserial.GsonObjectSerialDeserial;
import org.wicketstuff.rest.contenthandling.json.webserialdeserial.JsonWebSerialDeserial;
import org.wicketstuff.rest.resource.AbstractRestResource;

import com.mycompany.v1.models.dao.TokenDao;

public class RestAPI extends AbstractRestResource<JsonWebSerialDeserial> {

	private static final long serialVersionUID = 5687215650477457589L;

	public RestAPI() {
		super(new JsonWebSerialDeserial(new GsonObjectSerialDeserial()));
	}
	
	@SuppressWarnings("unused")
	private Boolean cheackToken(String token) {
		Boolean result = false;

    	TokenDao dao = new TokenDao();
    	result = dao.checkToken(token);

    	if(!result) {
    	    this.setResponseStatusCode(401);
    	}
		
		return result;
	}

}
