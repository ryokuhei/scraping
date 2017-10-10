package com.mycompany;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.mycompany.model.Csv;
import com.mycompany.model.HttpClient;
import com.mycompany.model.ImageAnalysis;
import com.mycompany.model.entity.Data;
import com.mycompany.model.entity.DataDetail;

import org.apache.wicket.markup.html.basic.Label;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	boolean flg = true;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.now();
		String today = localDate.format(formatter);

  		ImageAnalysis imageAnalysis = new ImageAnalysis();
		HttpClient client = new HttpClient();

		List<Data> dataList = client.getRankingData();
		for(Data data: dataList) {
			List<DataDetail> dataDetailList = client.getData(data.getMachineNo(), data.getMachineName());
			if(dataDetailList.size() >= 2) {
			    data.setTodayDataDetail(dataDetailList.get(0));
		    	data.setYesterdayDataDetail(dataDetailList.get(1));
			}

			if(data.getTodayDataDetail().getResultImagePath() != null) {
    	        int earnedMedals = imageAnalysis.calculateEarnedMedals(data.getTodayDataDetail().getResultImagePath());
    	        data.getTodayDataDetail().setEarnedMedals(earnedMedals);
			}
			System.out.print("EarnedMedals :");
			System.out.println(data.getTodayDataDetail().getEarnedMedals());
		}
		
		Csv csv = new Csv();
		csv.write(dataList, today);
    }
}
