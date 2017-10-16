package com.mycompany.models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

public class ImageAnalysis {
	
	private BufferedImage imageData;
	private int referenceValue;
	private int referenceX;
	private int referenceY;
	private double pointOfOnePixel;

	public ImageAnalysis() {
		
		Properties properties = new Properties();

		String documentRoot = System.getProperty("user.dir");
		documentRoot += "/src/main/webapp/WEB-INF/";
		String filePath = documentRoot + "dataImage.properties";
		
		try {
			InputStream inputStream = new FileInputStream(filePath);
			properties.load(inputStream);

    		this.referenceValue = Integer.parseInt(properties.getProperty("referenceY"));
	    	this.referenceX = Integer.parseInt(properties.getProperty("referenceX"));
		    this.referenceY = Integer.parseInt(properties.getProperty("referenceY"));
		    this.pointOfOnePixel = Double.parseDouble(properties.getProperty("pointOfOnePixel"));
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}

	}
	
	public int calculateEarnedMedals(File imagePath) {

		try {
			this.imageData = ImageIO.read(imagePath);
		} catch(IOException e) {
			e.printStackTrace();
		}

		int width = this.imageData.getWidth(), height = this.imageData.getHeight();
		
		int targetRgb = this.imageData.getRGB(this.referenceX, this.referenceY);
		
		Map<String, Integer> coordinateMap = new HashMap<String,Integer>();

		outside: for(int x = width - 1; x > 0; x--) {
			for(int y = 0; y < height; y++) {
				int rgb = this.imageData.getRGB(x, y);
				if(targetRgb == rgb) {
					coordinateMap.put("x", x);
					coordinateMap.put("y", y);
					
					break outside;
				}
			}
		}
		
		int earnedMedals = referenceValue - coordinateMap.get("y");
		earnedMedals = (int) (earnedMedals * pointOfOnePixel); 

		return earnedMedals;
		
	}
}
