package com.mycompany.controllers.ranking;

import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.resource.StringResourceStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.models.Csv;
import com.mycompany.models.HttpClient;
import com.mycompany.models.ImageAnalysis;
import com.mycompany.models.entity.DataEntity;
import com.mycompany.models.entity.RankingDataEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.WebPage;

public class Ranking extends WebPage {
	private static final long serialVersionUID = 1L;

	public Ranking(final PageParameters parameters) {
		super(parameters);

  		ImageAnalysis imageAnalysis = new ImageAnalysis();
		HttpClient client = new HttpClient();

		List<DataEntity> rankingDataList = client.getRankingData();
		List<DataEntity> dataList = new ArrayList<DataEntity>();
		for(DataEntity data: rankingDataList) {

			RankingDataEntity rankingData = data.getRankingData();
			data = client.getData(data.getMachineNo());
			data.setRankingData(rankingData);

			if(data.getTodayDataDetail().getResultImagePath() != null) {
    	        int earnedMedals = imageAnalysis.calculateEarnedMedals(data.getTodayDataDetail().getResultImagePath());
    	        data.getTodayDataDetail().setEarnedMedals(earnedMedals);
			}
			System.out.print("EarnedMedals :");
			System.out.println(data.getTodayDataDetail().getEarnedMedals());

			dataList.add(data);
		}
		
		writeCsv(dataList);
		setJson(dataList);
    }

    protected void writeCsv(List<DataEntity> dataList) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.now();
		String today = localDate.format(formatter);

		Csv csv = new Csv();
        String csvName = today + "_" + "ranking" + ".csv";
		for(DataEntity data: dataList) {
		    csv.writeOfRankingData(data, csvName);
		    csv.writeOfData(data, csvName);
		    csv.writeOfDataDetail(data, csvName);
			if(data.getDeterminationData() != null) {
        		csv.writeOfDeterminationData(data.getDeterminationData(), csvName);
    		}
			csv.writeOfEnter(csvName);
	    
		}
	}

	@Override
	protected void configureResponse(WebResponse response) {
		response.setContentType("application/json");
		super.configureResponse(response);
	}
	
	protected void setJson(List<DataEntity> dataList) {
		Gson gson = new GsonBuilder().create();
		getRequestCycle().scheduleRequestHandlerAfterCurrent(
				new ResourceStreamRequestHandler(
						new StringResourceStream(gson.toJson(dataList))));
	}
}
