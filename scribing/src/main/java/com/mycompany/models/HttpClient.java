package com.mycompany.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mycompany.models.entity.DataDetailEntity;
import com.mycompany.models.entity.DataEntity;
import com.mycompany.models.entity.MadokaMagika;
import com.mycompany.models.entity.RankingDataEntity;

public class HttpClient {
	
	private String domin;	
	private String shopNumber;	
	private Document document;
	private String outputPath;
	
	public HttpClient() {
		
		Properties properties = new Properties();
		String documentRoot = System.getProperty("user.dir");
		documentRoot += "/src/main/webapp/WEB-INF/";
		String strpass = documentRoot + "domin.properties";

		try {
			InputStream istream = new FileInputStream(strpass);
			properties.load(istream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.domin      = properties.getProperty("domin");
		this.shopNumber = properties.getProperty("shopNumber");

		strpass = documentRoot + "dataImage.properties";

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

	private void setResponseDocument(String url) throws IOException {

    	try {
	        Thread.sleep((long)(Math.random() * 5000));
	    }catch(InterruptedException e) {
		   	e.printStackTrace();
	    }
    	
    	System.out.println("url: " + url);

		this.document = Jsoup.connect(url).get();
	}

	
	public List<DataEntity> getRankingData() {

		String url = this.domin + "h/" + this.shopNumber + "/" + Path.Rank.getPath();

		List<DataEntity> dataList = new ArrayList<DataEntity>();
		try {
			setResponseDocument(url);
			Elements elements = document.select(".item .text");
			
			boolean isRanking = false;
			
			for(Element element: elements) {

         		DataEntity data = new DataEntity();
         		RankingDataEntity rankingData = new RankingDataEntity();

				String rank = element.select(".rank").text();
				String no   = element.select(".unit_no").text();
				String name   = element.select(".name").text();
				String point   = element.select(".point").text();
				
				try {
				    rankingData.setRank(Integer.parseInt(rank.replaceAll("[^0-9]", "")));
				    data.setMachineNo(Integer.parseInt(no.replaceAll("[^0-9]", "")));
				    data.setMachineName(name);
				    rankingData.setPoint(Integer.parseInt(point.replaceAll("[^0-9]", "")));
				} catch(NumberFormatException e) {
					e.printStackTrace();
				}
				
				if(rankingData.getRank() == 1) {
					isRanking = !isRanking;
				}
				if(isRanking) {
					data.setRankingData(rankingData);
				    dataList.add(data);
				} else {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	private String getMachineName() {
		
		String machineName = "";

		Elements item = document.select(".item");
		Elements name = item.select(".name");
		machineName = name.get(0).text();

		return machineName;
	}
	
	public DataEntity getData(int machineNo) {
		
		DataEntity data = new DataEntity();
		data.setMachineNo(machineNo);

		String url = this.domin + "h/" + this.shopNumber + "/" + Path.View.getPath() + machineNo;
		
		try {
			setResponseDocument(url);

			String machineName = getMachineName();
			data.setMachineName(machineName);
			
			
			Elements tbody = document.select(".data tbody");
			Elements trList = tbody.select("tr");
			
			for(Element tr : trList) {

    			int i = 0;
				int size = tr.select("td").size();
				
				System.out.println("No.: " + machineNo);
				System.out.println("size: " + size);
				
				String day = tr.select("td").get(i++).text();
         		DataDetailEntity dataDetail = new DataDetailEntity();

				try {
				    if(size == 10) {
				    	dataDetail = this.getDataDetailAt10(tr, i);
				    } else if(size == 8) {
				    	dataDetail = this.getDataDetailAt8(tr, i);
	    			} else {
	    				System.out.println("size: " + size);
	    				System.out.println(tr);
	    			}

	    		} catch(NumberFormatException e) {
		    	    e.printStackTrace();
			    } catch(IndexOutOfBoundsException e) {
			    	e.printStackTrace();
			    	System.out.println(tr);
			    }

				if(day.equals("本日")) {
			        File imagePath = this.getResultImage(document, machineNo, machineName);
			        dataDetail.setResultImagePath(imagePath);
			        data.setTodayDataDetail(dataDetail);
				} else if(day.equals("1日前")) {
			        data.setYesterdayDataDetail(dataDetail);
				} else {
					System.out.println(day);
					break;
				}
			}
			String checkMachineName = this.getMachineName("2013110004");

			if(checkMachineName.equals(data.getMachineName())) {

    			System.out.println("!!!!!!! OK !!!!!!!!");
				int totalGame = data.getTodayDataDetail().getTotalGame();
				int totalArt  = data.getTodayDataDetail().getTotalArt();
			    data.setDeterminationData(this.getDirectHitArt(totalGame - totalArt));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	private DataDetailEntity getDataDetailAt10(Element tr, int i) {

		DataDetailEntity dataDetail = new DataDetailEntity();
    	String bb = tr.select("td").get(i++).text();
	    if(bb != null && bb != "") {
	       dataDetail.setBb(Integer.parseInt(bb.replaceAll("[^0-9]", "")));
	    } 
	    String rb = tr.select("td").get(i++).text();
	    if(rb != null) {
	        dataDetail.setRb(Integer.parseInt(rb.replaceAll("[^0-9]", "")));
	    }
		String art = tr.select("td").get(i++).text();
	    if(art != null && !(art.equals("&nbsp;")) && !(art.equals(" ")) && !(art.equals("")) && !(art.isEmpty())) {
	   	    dataDetail.setArt(Integer.parseInt(art.replaceAll("[^0-9]", "")));
	    }
	    String bonusProbability = tr.select("td").get(i++).text();
	    bonusProbability  = bonusProbability.replaceFirst("1", "");
	    if(bonusProbability != null) {
		    dataDetail.setBonusProbability(Integer.parseInt(bonusProbability.replaceAll("[^0-9]", "")));
	    }
		String totalBonus = tr.select("td").get(i++).text();
		totalBonus  = totalBonus.replaceFirst("1", "");
		if(totalBonus != null) {
		    dataDetail.setTotalBonus(Integer.parseInt(totalBonus.replaceAll("[^0-9]", "")));
		}
	    String totalGame = tr.select("td").get(i++).text();
		if(totalGame != null) {
		    dataDetail.setTotalGame(Integer.parseInt(totalGame.replaceAll("[^0-9]", "")));
		}
		String totalArt = tr.select("td").get(i++).text();

		if(totalArt != null && !(totalArt.equals("&nbsp;")) && !(totalArt.equals(" ")) && !(totalArt.equals("")) && !(totalArt.isEmpty())) {
	        dataDetail.setTotalArt(Integer.parseInt(totalArt.replaceAll("[^0-9]", "")));
		}
		String endGame = tr.select("td").get(i++).text();
		if(endGame != null) {
		    dataDetail.setEndGame(Integer.parseInt(endGame.replaceAll("[^0-9]", "")));
		}
	    String maxMedal = tr.select("td").get(i++).text();
		if(maxMedal != null) {
		    dataDetail.setMaxMedal(Integer.parseInt(maxMedal.replaceAll("[^0-9]", "")));
		}
		
	    return dataDetail;
	}

	private DataDetailEntity getDataDetailAt8(Element tr, int i) {

		DataDetailEntity dataDetail = new DataDetailEntity();
    	String bb = tr.select("td").get(i++).text();
		if(bb != null) {
		    dataDetail.setBb(Integer.parseInt(bb.replaceAll("[^0-9]", "")));
		} 
		String rb = tr.select("td").get(i++).text();
		if(rb != null) {
		    dataDetail.setRb(Integer.parseInt(rb.replaceAll("[^0-9]", "")));
		}
		String bonusProbability = tr.select("td").get(i++).text();
		bonusProbability  = bonusProbability.replaceFirst("1", "");
		if(bonusProbability != null) {
	        dataDetail.setBonusProbability(Integer.parseInt(bonusProbability.replaceAll("[^0-9]", "")));
		}
		String totalBonus = tr.select("td").get(i++).text();
		totalBonus  = totalBonus.replaceFirst("1", "");
		if(totalBonus != null) {
		    dataDetail.setTotalBonus(Integer.parseInt(totalBonus.replaceAll("[^0-9]", "")));
		}
	    String totalGame = tr.select("td").get(i++).text();
		if(totalGame != null) {
		    dataDetail.setTotalGame(Integer.parseInt(totalGame.replaceAll("[^0-9]", "")));
		}
		String endGame = tr.select("td").get(i++).text();
		if(endGame != null) {
		    dataDetail.setEndGame(Integer.parseInt(endGame.replaceAll("[^0-9]", "")));
		}
	    String maxMedal = tr.select("td").get(i++).text();
		if(maxMedal != null) {
		    dataDetail.setMaxMedal(Integer.parseInt(maxMedal.replaceAll("[^0-9]", "")));
		}
		return dataDetail;
	}
	public File getResultImage(Document document, int machineNumber, String machineName) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate localDate = LocalDate.now();
		String date = localDate.format(formatter);
		
		Elements div = document.select("#tab-graph-today");
		Elements images = div.select("img");
		Element image = images.get(0);
		String imagePath = image.attr("src");
		
		if(!imagePath.contains("http")) {
			imagePath = this.domin + imagePath;
		}
		
		System.out.println("imageUrl: " + imagePath);
		
		File file = new File(this.outputPath + date);
		if(!file.exists()) {
			file.mkdirs();
		}
		String imageName = date + "_" + machineNumber + "_" + machineName + ".png";
		file = new File(file, imageName);
		
		try {
		    URL url = new URL(imagePath);
		    URLConnection connection = url.openConnection();
		    InputStream input = connection.getInputStream();
		    
		    FileOutputStream output = new FileOutputStream(file, false);
		    int binary;
		    while((binary = input.read()) != -1) {
		    	output.write(binary);
		    }
			output.close();
			input.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		
		return file;
	}


	public File getResultImage2(int machineNumber, String machineName, String date) {
		
		File file = new File(this.outputPath + date);
		if(!file.exists()) {
			file.mkdirs();
		}
		String imageName = date + "_" + machineNumber + "_" + machineName + ".png";
		file = new File(file, imageName);
		
		URL url;
		try {
			url = new URL(this.domin + "h/slp/"
	             	+ this.shopNumber + "/" 
		            + machineNumber + "/0/"
		            + imageName);
			
		    URLConnection connection = url.openConnection();
		    InputStream input = connection.getInputStream();
		    
		    FileOutputStream output = new FileOutputStream(file, false);
		    int binary;
		    while((binary = input.read()) != -1) {
		    	output.write(binary);
		    }
			output.close();
			input.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		
		return file;
	}
	
	public List<Integer> getMachineNumbersAtSort(int sortNo) {
		List<Integer> machineNumbers = new ArrayList<Integer>();

		String url = this.domin + "h/" + this.shopNumber + "/" + Path.Sort.getPath() + sortNo + "/1-20";
		
		try {
			setResponseDocument(url);

			Elements sort = document.select(".sort");
			Elements unitNo = sort.select(".unit_no");
			
			int size = unitNo.size();
			System.out.println("size: " + size);
			for(Element li : unitNo) {

				Elements a = li.select("a");
				int machineNo = Integer.parseInt(a.get(0).text());
				System.out.println("No.: " + machineNo);

				machineNumbers.add(machineNo);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return machineNumbers;
	}
	
	private Object getDirectHitArt(int normalGame) {

		MadokaMagika madoka = new MadokaMagika();
		
		Elements tbody = document.select(".history tbody");
		Elements trList = tbody.select("tr");
		
		
		int directHitArt = 0;
		List<Integer> startList = new ArrayList<Integer>();
		for(Element tr: trList) {
			
			int isArt = 0;
            isArt = tr.select(".art").size();

			if(isArt != 0) {
				int start = Integer.parseInt(tr.select(".start").get(0).text());
				System.out.println("StartOfDirectHitArt: " + start);
				
				if(start >= 50) {
					directHitArt++;
					startList.add(start);
				}
			}
		}
		
		madoka.setDirectHitArt(directHitArt);
		madoka.setStartOfDirectHitArt(startList);

		
		if(normalGame > 0 && directHitArt > 0) {
		    madoka.setProbabilityOfDirectHitArt( normalGame / directHitArt );
		}

		System.out.println("!!!!!!!!!!!!");
		System.out.println(normalGame);
		System.out.println(directHitArt);
		System.out.println(madoka.getProbabilityOfDirectHitArt());
		System.out.println("!!!!!!!!!!!!");
	
		
		Object determinationData = madoka;

		return determinationData;
	}
	
	private String getMachineName(String sortNo) {

		String machineName = "";

		Properties properties = new Properties();
		String documentRoot = System.getProperty("user.dir");
		documentRoot += "/src/main/webapp/WEB-INF/";
		String strpass = documentRoot + "sortOfMachine.properties";

		try {
			InputStream istream = new FileInputStream(strpass);

			InputStreamReader isr = new InputStreamReader(istream, "UTF-8");
			properties.load(isr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		machineName = properties.getProperty(sortNo);
		return machineName;
	}

}
