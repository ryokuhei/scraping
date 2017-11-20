package com.mycompany;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import com.mycompany.controllers.homePage.HomePage;
import com.mycompany.controllers.ranking.Ranking;
import com.mycompany.controllers.sort.Sort;
import com.mycompany.controllers.special.Special;
import com.mycompany.controllers.test.TestHibernate;
import com.mycompany.controllers.test.TestJson;
import com.mycompany.controllers.view.View;

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
	public void init()
	{
		super.init();
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");

		mountPage("/ranking", Ranking.class);
		mountPage("/view", View.class);
		mountPage("/sort", Sort.class);
		mountPage("/special", Special.class);
		mountPage("/testJson", TestJson.class);
		mountPage("/test", TestHibernate.class);

		// add your configuration here
	}
}
