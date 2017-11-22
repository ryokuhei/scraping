package com.mycompany;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.cycle.RequestCycleListenerCollection;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import com.mycompany.v1.controllers.homePage.HomePage;
import com.mycompany.v1.controllers.input.ranking.Ranking;
import com.mycompany.v1.controllers.input.sort.Sort;
import com.mycompany.v1.controllers.input.special.Special;
import com.mycompany.v1.controllers.input.view.View;
import com.mycompany.v1.controllers.test.Test;
import com.mycompany.v1.controllers.test.TestHibernate;
import com.mycompany.v1.controllers.test.TestJson;
import com.mycompany.v1.models.entity.MySession;


/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see com.mycompany.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
//		return TestHibernate.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");

		mountPage("/ranking", Ranking.class);
		mountPage("/view", View.class);
		mountPage("/sort", Sort.class);
		mountPage("/special", Special.class);

		mountResource("/search", new ResourceReference("restReference") {

			private static final long serialVersionUID = 1L;

			private com.mycompany.v1.controllers.output.view.View apis  = new com.mycompany.v1.controllers.output.view.View();

			@Override
			public IResource getResource() {
				return apis;
			}
		});

		mountPage("/testJson", TestJson.class);
//		mountPage("/test", Test.class);
		mountResource("/test", new ResourceReference("restReference") {

			private static final long serialVersionUID = 1L;

			private TestHibernate apis  = new TestHibernate();

			@Override
			public IResource getResource() {
				return apis;
			}
		});


		// add your configuration here
		
//		RequestCycleListenerCollection lis = new RequestCycleListenerCollection() {
//			private static final long serialVersionUID = -8209538500669793318L;
//
//			@Override
//			public void onEndRequest(RequestCycle request) {
//				Response res = request.getResponse();
//				System.out.println("startResponse");
//				System.out.println(res);
//				System.out.println("endResponse");
//			}
//			@Override
//			public void onBeginRequest(RequestCycle request) {
//				Request req = request.getRequest();
//				System.out.println("startRequest");
//				System.out.println(req);
//				System.out.println("endRequest");
//			}
//		};
//		getRequestCycleListeners().add(lis);
	}
	
	@Override
	public Session newSession(Request req, Response res) {
		return new MySession(req);
	}
	
}
