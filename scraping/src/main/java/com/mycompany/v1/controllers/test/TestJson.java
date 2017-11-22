package com.mycompany.v1.controllers.test;

import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.resource.StringResourceStream;
import org.apache.wicket.util.string.StringValue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.WebPage;

public class TestJson extends WebPage {
	private static final long serialVersionUID = 1L;

	public TestJson(final PageParameters parameters) {
		super(parameters);
		
		Map<String, String> map = new HashMap<String,String>();
		map.put("test", "test");
		
		Gson gson = new GsonBuilder().create();
		getRequestCycle().scheduleRequestHandlerAfterCurrent(
				new ResourceStreamRequestHandler(
						new StringResourceStream(gson.toJson(map))));
	}
	
	@Override
	protected void configureResponse(WebResponse response) {
		response.setContentType("application/json");
		super.configureResponse(response);
	}
}
