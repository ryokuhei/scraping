package com.mycompany;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Csv {
	
	public Csv() {
	}
	
	public void write(List<Data> dataList) {
		try {
			FileWriter fw = new FileWriter("c:\\Temp\\result.csv", true);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

			
			for(Data data: dataList) {
    			pw.print("rank of " + data.getRank() + " ");
    			pw.print("no. " + data.getMachineNo() + " ");
    			pw.print(data.getPoint() + " medal ");
    			pw.print(data.getMachineName());
    			pw.println();

    			pw.print("Date　");
    			pw.print("BB　");
    			pw.print("RB　");
    			pw.print("ART　");
    			pw.print("BB probability　");
    			pw.print("Synthesis probability　");
    			pw.print("Total game　");
    			pw.print("End game　");
    			pw.print("Total ART　");
    			pw.print("MaxMedal　");
    			pw.println();

    			DataDetail todayDataDetail = data.getTodayDataDetail();
    			pw.print("today  ");
    			pw.print(todayDataDetail.getBb()+ " ");
    			pw.print(todayDataDetail.getRb()+ " ");
    			pw.print(todayDataDetail.getArt()+ " ");
    			pw.print("1/" + todayDataDetail.getBonusProbability()+ " ");
    			pw.print("1/" + todayDataDetail.getTotalBonus()+ " ");
    			pw.print(todayDataDetail.getTotalGame()+ " ");
    			pw.print(todayDataDetail.getEndGame()+ " ");
    			pw.print(todayDataDetail.getTotalArt()+ " ");
    			pw.print(todayDataDetail.getMaxMedal()+ " ");

    			pw.println();
    			DataDetail yesterdayDataDetail = data.getYesterdayDataDetail();
    			pw.print("yesterday ");
    			pw.print(yesterdayDataDetail.getBb()+ " ");
    			pw.print(yesterdayDataDetail.getRb()+ " ");
    			pw.print(yesterdayDataDetail.getArt()+ " ");
    			pw.print("1/" + yesterdayDataDetail.getBonusProbability()+ " ");
    			pw.print("1/" + yesterdayDataDetail.getTotalBonus()+ " ");
    			pw.print(yesterdayDataDetail.getTotalGame()+ " ");
    			pw.print(yesterdayDataDetail.getEndGame()+ " ");
    			pw.print(yesterdayDataDetail.getTotalArt()+ " ");
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
