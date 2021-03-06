package com.mycompany.v1.controllers.test;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.mycompany.v1.models.Token;
import com.mycompany.v1.models.entity.MySession;


public class Test extends WebPage {
	
	private IModel<String> tokenModel = new Model<String>("");
	public Test() {
		super();
		
		MySession session = (MySession)getSession();
		Boolean result = false;

        System.out.println("modle : " + tokenModel.getObject());
    	System.out.println("session" + session.getToken());
		
		if(tokenModel.getObject().equals(session.getToken())) {
			result = true;
		}
		
		String token = Token.createToken(); 
		tokenModel.setObject(token);
		session.setToken(token);
		
		Form form = new Form("form");
		super.add(form);
		
		Button button = new Button("button", new Model("submit")) {
			@Override
			public void onSubmit() {
				System.out.println("clicked");
				setResponsePage(new NextTest(tokenModel));
			}
		};
		form.add(button);
		
//		HiddenField<String> hidden = new HiddenField<String>("token", tokenModel);
		TextField<String> hidden = new TextField("token", tokenModel);
		form.add(hidden);
		Label label = new Label("test", "token:" + result);
		form.add(label);
		
	}
}
