package com.mycompany.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.mycompany.model.entity.Data;
import com.mycompany.model.entity.DataDetail;

public class HttpClient {
	
	private String domin;	
	private String shopNumber;	
	private Document document;
	private String outputPath;
	
	public HttpClient() {
		
		Properties properties = new Properties();
		String strpass = System.getProperty("user.dir");
		strpass += "/src/main/webapp/WEB-INF/";
		strpass += "domin.properties";

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
	
	public List<Data> getRankingData() {

		String url = this.domin + "h/" + this.shopNumber + "/" + Path.Rank.getPath();

		List<Data> dataList = new ArrayList<Data>();
		try {
			document = Jsoup.connect(url).get();
			Elements elements = document.select(".item .text");
			
			boolean isRanking = false;
			
			for(Element element: elements) {

         		Data data = new Data();
				String rank = element.select(".rank").text();
				String no   = element.select(".unit_no").text();
				String name   = element.select(".name").text();
				String point   = element.select(".point").text();
				
				try {
				data.setRank(Integer.parseInt(rank.replaceAll("[^0-9]", "")));
				data.setMachineNo(Integer.parseInt(no.replaceAll("[^0-9]", "")));
				data.setMachineName(name);
				data.setPoint(Integer.parseInt(point.replaceAll("[^0-9]", "")));
				} catch(NumberFormatException e) {
					e.printStackTrace();
				}
				
				if(data.getRank() == 1) {
					isRanking = !isRanking;
				}
				if(isRanking) {
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
	
	
	public List<DataDetail> getData(int machineNo, String machineName) {
		try {
		    Thread.sleep((long)(Math.random() * 5000));
		}catch(InterruptedException e) {
			e.printStackTrace();
		}

		String url = this.domin + "h/" + this.shopNumber + "/" + Path.View.getPath() + machineNo;
		
		List<DataDetail> dataDetailList = new ArrayList<DataDetail>();
		try {
			document = Jsoup.connect(url).get();
			Elements tbody = document.select(".data tbody");
			Elements trList = tbody.select("tr");
			
			for(Element tr : trList) {

    			int i = 0;
				int size = tr.select("td").size();
				
				System.out.println("No.: " + machineNo);
				System.out.println("size: " + size);
				
				String day = tr.select("td").get(i++).text();
         		DataDetail dataDetail = new DataDetail();

				try {
				    if(size == 10) {
				    	this.getDataDetailAt10(tr, i);
				    } else if(size == 8) {
				    	this.getDataDetailAt8(tr, i);
	    			} else {
	    				System.err.println("size" + size);
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

			        dataDetailList.add(0, dataDetail);
				} else if(day.equals("1日前")) {
			        dataDetailList.add(1, dataDetail);
				} else {
					System.out.println(day);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataDetailList;
	}

	private DataDetail getDataDetailAt10(Element tr, int i) {

		DataDetail dataDetail = new DataDetail();
    	String bb = tr.select("td").get(i++).text();
	    if(bb != null) {
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

	private DataDetail getDataDetailAt8(Element tr, int i) {

		DataDetail dataDetail = new DataDetail();
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
}
