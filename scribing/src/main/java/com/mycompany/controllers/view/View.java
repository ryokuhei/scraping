package com.mycompany.controllers.view;

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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.wicket.markup.html.WebPage;

public class View extends WebPage {
	private static final long serialVersionUID = 1L;

	public View(final PageParameters parameters) {
		super(parameters);
		
		int machineNo = parameters.get(0).toInt();

  		ImageAnalysis imageAnalysis = new ImageAnalysis();
		HttpClient client = new HttpClient();
		
		DataEntity data = client.getData(machineNo);

		if(data.getTodayDataDetail().getResultImagePath() != null) {
    	       int earnedMedals = imageAnalysis.calculateEarnedMedals(data.getTodayDataDetail().getResultImagePath());
    	       data.getTodayDataDetail().setEarnedMedals(earnedMedals);
		}
		System.out.print("EarnedMedals :");
		System.out.println(data.getTodayDataDetail().getEarnedMedals());
		
		writeCsv(data);
		setJson(data);
    }

    protected void writeCsv(DataEntity data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.now();
		String today = localDate.format(formatter);

		Csv csv = new Csv();
        String csvName = today + "_" + "view_" + data.getMachineNo() + "_" + data.getMachineName() + ".csv";

		csv.writeOfData(data, csvName);
		csv.writeOfDataDetail(data, csvName);
		if(data.getDeterminationData() != null) {
    		csv.writeOfDeterminationData(data.getDeterminationData(), csvName);
		}
		csv.writeOfEnter(csvName);
	}

	@Override
	protected void configureResponse(WebResponse response) {
		response.setContentType("application/json");
		super.configureResponse(response);
	}
	
	protected void setJson(DataEntity data) {
		Gson gson = new GsonBuilder().create();
		getRequestCycle().scheduleRequestHandlerAfterCurrent(
				new ResourceStreamRequestHandler(
						new StringResourceStream(gson.toJson(data))
						)
				);
	}
}
