package com.mycompany.models;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import com.mycompany.models.entity.DataDetailEntity;
import com.mycompany.models.entity.DataEntity;
import com.mycompany.models.entity.MadokaMagikaEntity;
import com.mycompany.models.entity.RankingEntity;


public class Csv {

	private String outputPath;
	
	public Csv() {
		this.setOutputPath();
	}
	public void writeOfRankingData(RankingEntity data, String csvName) {
		try {
			FileWriter fw = new FileWriter(outputPath + csvName, true);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

    		pw.print("rank of " + data.getRank() + ", ");
    		pw.print("machineNo. " + data.getMachineNo() + ", ");
    		pw.print(data.getPoint() + " medal, ");
    		pw.println();

			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeOfData(DataEntity data, String csvName) {
		try {
			FileWriter fw = new FileWriter(outputPath + csvName, true);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

    		pw.print("no. " + data.getMachineNumber() + ", ");
    		pw.print(data.getMachineName());
    		pw.println();

			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeOfDataDetail(DataEntity data, String csvName) {
		try {
			FileWriter fw = new FileWriter(outputPath + csvName, true);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

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

    		DataDetailEntity todayDataDetail = data.getDataDetail();
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
    		pw.print(todayDataDetail.getResultImageFile());

    		pw.println();

//    		DataDetailEntity yesterdayDataDetail = data.getYesterdayDataDetail();
//    		pw.print("yesterday, ");
//    		pw.print(yesterdayDataDetail.getBb()+ ", ");
//    		pw.print(yesterdayDataDetail.getRb()+ ", ");
//    		pw.print(yesterdayDataDetail.getArt()+ ", ");
//    		pw.print("1/" + yesterdayDataDetail.getBonusProbability()+ ", ");
//    		pw.print("1/" + yesterdayDataDetail.getTotalBonus()+ ", ");
//    		pw.print(yesterdayDataDetail.getTotalGame()+ ", ");
//    		pw.print(yesterdayDataDetail.getEndGame()+ ", ");
//    		pw.print(yesterdayDataDetail.getTotalArt()+ ", ");
//    		pw.print(yesterdayDataDetail.getMaxMedal()+ " ");
//
//    		pw.println();
			
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	private void setOutputPath() {
		Properties properties = new Properties();
		String strpass = System.getProperty("user.dir");
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

	public void writeOfDeterminationData(Object determinationData, String csvName) {
		try {
			FileWriter fw = new FileWriter(outputPath + csvName, true);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

			if(determinationData instanceof MadokaMagikaEntity) {
    		    MadokaMagikaEntity madoka = (MadokaMagikaEntity)determinationData;

    		    pw.print("DirectHitArt.");
    		    pw.println();
    		    pw.print("count: " + madoka.getDirectHitArt() + ", ");
    		    pw.print("start: " + madoka.getStartOfDirectHitArt() + ", ");
    		    pw.print("probablity: 1/" + madoka.getProbabilityOfDirectHitArt() + ", ");
    		    pw.println();
			}

			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeOfEnter(String csvName) {
		try {
			FileWriter fw = new FileWriter(outputPath + csvName, true);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

   		    pw.println();

			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
