package com.mycompany.v1.controllers.input.view;

import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.resource.StringResourceStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.v1.models.Csv;
import com.mycompany.v1.models.HttpClient;
import com.mycompany.v1.models.ImageAnalysis;
import com.mycompany.v1.models.dao.MachineDataDao;
import com.mycompany.v1.models.entity.DataEntity;

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

		if(data.getDataDetail().getResultImageFile() != null) {
    	       int earnedMedals = imageAnalysis.calculateEarnedMedals(data.getDataDetail().getResultImageFile());
    	       data.getDataDetail().setEarnedMedals(earnedMedals);
		}
		System.out.print("EarnedMedals :");
		System.out.println(data.getDataDetail().getEarnedMedals());
		
		MachineDataDao dao = new MachineDataDao();
		int id = dao.isExsist(data);
		if(id != -1) {
			data.setId(id);
			dao.update(data);
		} else {
		    dao.create(data);
		}
		writeCsv(data);
		setJson(data);
    }

    protected void writeCsv(DataEntity data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.now();
		String today = localDate.format(formatter);

		Csv csv = new Csv();
        String csvName = today + "_" + "view_" + data.getMachineNumber() + "_" + data.getMachineName() + ".csv";

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
