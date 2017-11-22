package com.mycompany.v1.controllers.input.ranking;

import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.resource.StringResourceStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.v1.models.Csv;
import com.mycompany.v1.models.HttpClient;
import com.mycompany.v1.models.dao.RankingsDao;
import com.mycompany.v1.models.entity.RankingEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.wicket.markup.html.WebPage;

public class Ranking extends WebPage {
	private static final long serialVersionUID = 1L;

	public Ranking(final PageParameters parameters) {
		super(parameters);

		HttpClient client = new HttpClient();

		List<RankingEntity> rankingList = client.getRankingData();
		
		for(RankingEntity ranking: rankingList) {
    		RankingsDao dao = new RankingsDao();
	    	dao.create(ranking);
		}
		writeCsv(rankingList);
		setJson(rankingList);
    }

    protected void writeCsv(List<RankingEntity> dataList) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.now();
		String today = localDate.format(formatter);

		Csv csv = new Csv();
        String csvName = today + "_" + "ranking" + ".csv";
		for(RankingEntity data: dataList) {
		    csv.writeOfRankingData(data, csvName);
			csv.writeOfEnter(csvName);
		}
	}

	@Override
	protected void configureResponse(WebResponse response) {
		response.setContentType("application/json");
		super.configureResponse(response);
	}
	
	protected void setJson(List<RankingEntity> dataList) {
		Gson gson = new GsonBuilder().create();
		getRequestCycle().scheduleRequestHandlerAfterCurrent(
				new ResourceStreamRequestHandler(
						new StringResourceStream(gson.toJson(dataList))));
	}
}
