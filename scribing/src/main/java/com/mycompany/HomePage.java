package com.mycompany;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	boolean flg = true;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		

		if(flg) {
			
		    HttpClient client = new HttpClient();
		    List<Data> dataList = client.getRankingData();
		    for(Data data: dataList) {
		    	List<DataDetail> dataDetailList = client.getData(data.getMachineNo());
		    	if(dataDetailList.size() >= 2) {
		    	    data.setTodayDataDetail(dataDetailList.get(0));
		        	data.setYesterdayDataDetail(dataDetailList.get(1));
		    	}
		    }
		    
		    Csv csv = new Csv();
		    csv.write(dataList);
		}
		flg = false;

		// TODO Add your page's components here

    }
}
