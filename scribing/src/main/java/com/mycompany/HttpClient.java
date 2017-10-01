package com.mycompany;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpClient {
	
	private String domin;	
	private Document document;
	
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
		this.domin = properties.getProperty("domin");
	}
	
	public List<Data> getRankingData() {

		String url = domin + Path.Rank.getPath();
		

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
	
	
	public List<DataDetail> getData(int machineNo) {
		try {
		    Thread.sleep((long)(Math.random() * 5000));
		}catch(InterruptedException e) {
			e.printStackTrace();
		}

		String url = domin + Path.View.getPath() + machineNo;
		
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
				    } else if(size == 8) {
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


	public List<DataDetail> getData2(int machineNo) {

		String url = domin + Path.View.getPath() + machineNo;
		
		List<DataDetail> dataDetailList = new ArrayList<DataDetail>();
		try {
			document = Jsoup.connect(url).get();
			Elements elements = document.select(".data tbody");

			int i = 0;

         		DataDetail dataDetail = new DataDetail();
				String day = elements.select("td").get(i++).text();
				    String bb = elements.select("td").get(i++).text();
				    String rb = elements.select("td").get(i++).text();
				    String art = elements.select("td").get(i++).text();
				    String bonusProbability = elements.select("td").get(i++).text();
				    String totalBonus = elements.select("td").get(i++).text();
				    String totalGame = elements.select("td").get(i++).text();
				    String totalArt = elements.select("td").get(i++).text();
				    String endGame = elements.select("td").get(i++).text();
				    String maxMedal = elements.select("td").get(i++).text();

				    try {
				        dataDetail.setBb(Integer.parseInt(bb.replaceAll("[^0-9]", "")));
				        dataDetail.setRb(Integer.parseInt(rb.replaceAll("[^0-9]", "")));
				        dataDetail.setArt(Integer.parseInt(art.replaceAll("[^0-9]", "")));
				        dataDetail.setBonusProbability(Integer.parseInt(bonusProbability.replaceAll("[^0-9]", "")));
				        dataDetail.setTotalBonus(Integer.parseInt(totalBonus.replaceAll("[^0-9]", "")));
				        dataDetail.setTotalGame(Integer.parseInt(totalGame.replaceAll("[^0-9]", "")));
				        dataDetail.setTotalArt(Integer.parseInt(totalArt.replaceAll("[^0-9]", "")));
				        dataDetail.setEndGame(Integer.parseInt(endGame.replaceAll("[^0-9]", "")));
				        dataDetail.setMaxMedal(Integer.parseInt(maxMedal.replaceAll("[^0-9]", "")));
	    			} catch(NumberFormatException e) {
		    			e.printStackTrace();
			    	}
			        dataDetailList.add(0, dataDetail);

         		dataDetail = new DataDetail();
				day = elements.select("td").get(i++).text();
				bb = elements.select("td").get(i++).text();
				rb = elements.select("td").get(i++).text();
				art = elements.select("td").get(i++).text();
				bonusProbability = elements.select("td").get(i++).text();
				totalBonus = elements.select("td").get(i++).text();
				totalGame = elements.select("td").get(i++).text();
				totalArt = elements.select("td").get(i++).text();
				endGame = elements.select("td").get(i++).text();
				maxMedal = elements.select("td").get(i++).text();

				    try {
				        dataDetail.setBb(Integer.parseInt(bb.replaceAll("[^0-9]", "")));
				        dataDetail.setRb(Integer.parseInt(rb.replaceAll("[^0-9]", "")));
				        dataDetail.setArt(Integer.parseInt(art.replaceAll("[^0-9]", "")));
				        dataDetail.setBonusProbability(Integer.parseInt(bonusProbability.replaceAll("[^0-9]", "")));
				        dataDetail.setTotalBonus(Integer.parseInt(totalBonus.replaceAll("[^0-9]", "")));
				        dataDetail.setTotalGame(Integer.parseInt(totalGame.replaceAll("[^0-9]", "")));
				        dataDetail.setTotalArt(Integer.parseInt(totalArt.replaceAll("[^0-9]", "")));
				        dataDetail.setEndGame(Integer.parseInt(endGame.replaceAll("[^0-9]", "")));
				        dataDetail.setMaxMedal(Integer.parseInt(maxMedal.replaceAll("[^0-9]", "")));
	    			} catch(NumberFormatException e) {
		    			e.printStackTrace();
			    	}
	
			        dataDetailList.add(1, dataDetail);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataDetailList;
	}

	

}
