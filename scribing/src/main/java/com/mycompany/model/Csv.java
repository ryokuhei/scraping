package com.mycompany.model;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import com.mycompany.model.entity.Data;
import com.mycompany.model.entity.DataDetail;

public class Csv {

	private String outputPath;
	
	public Csv() {
		Properties properties = new Properties();
		String strpass = System.getProperty("user.dir");
		strpass = System.getProperty("user.dir");
		strpass += "/src/main/webapp/WEB-INF/";
		strpass += "dataImage.properties";

		try {
			InputStream istream = new FileInputStream(strpass);
			properties.load(istream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.outputPath = properties.getProperty("outputPath");
	

	}
	
	public void write(List<Data> dataList, String date) {
		try {
			FileWriter fw = new FileWriter(outputPath + "result_"+ date + ".csv", true);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

			
			for(Data data: dataList) {
    			pw.print("rank of " + data.getRank() + ", ");
    			pw.print("no. " + data.getMachineNo() + ", ");
    			pw.print(data.getPoint() + " medal, ");
    			pw.print(data.getMachineName());
    			pw.println();

    			pw.print("Date,　");
    			pw.print("BB,　");
    			pw.print("RB,　");
    			pw.print("ART,　");
    			pw.print("BB probability,　");
    			pw.print("Synthesis probability,　");
    			pw.print("Total game,　");
    			pw.print("End game,");
    			pw.print("Total ART, ");
    			pw.print("MaxMedal, ");
    			pw.print("EarnedMedals, ");
    			pw.print("ResultImagePath　");
    			pw.println();

    			DataDetail todayDataDetail = data.getTodayDataDetail();
    			pw.print("today, ");
    			pw.print(todayDataDetail.getBb()+ ", ");
    			pw.print(todayDataDetail.getRb()+ ", ");
    			pw.print(todayDataDetail.getArt()+ ", ");
    			pw.print("1/" + todayDataDetail.getBonusProbability()+ ", ");
    			pw.print("1/" + todayDataDetail.getTotalBonus()+ ", ");
    			pw.print(todayDataDetail.getTotalGame()+ ", ");
    			pw.print(todayDataDetail.getEndGame()+ ", ");
    			pw.print(todayDataDetail.getTotalArt()+ ", ");
    			pw.print(todayDataDetail.getMaxMedal()+ ", ");
    			pw.print(todayDataDetail.getEarnedMedals() + "medals, ");
    			pw.print(todayDataDetail.getResultImagePath());


    			pw.println();
    			DataDetail yesterdayDataDetail = data.getYesterdayDataDetail();
    			pw.print("yesterday, ");
    			pw.print(yesterdayDataDetail.getBb()+ ", ");
    			pw.print(yesterdayDataDetail.getRb()+ ", ");
    			pw.print(yesterdayDataDetail.getArt()+ ", ");
    			pw.print("1/" + yesterdayDataDetail.getBonusProbability()+ ", ");
    			pw.print("1/" + yesterdayDataDetail.getTotalBonus()+ ", ");
    			pw.print(yesterdayDataDetail.getTotalGame()+ ", ");
    			pw.print(yesterdayDataDetail.getEndGame()+ ", ");
    			pw.print(yesterdayDataDetail.getTotalArt()+ ", ");
    			pw.print(yesterdayDataDetail.getMaxMedal()+ " ");

    			pw.println();

    			pw.println();
			}
			
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
