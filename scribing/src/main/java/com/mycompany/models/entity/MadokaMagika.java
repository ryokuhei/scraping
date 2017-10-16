package com.mycompany.models.entity;

import java.util.List;

public class MadokaMagika implements DeterminationData {
	
	private int directHitArt;
	private List<Integer> startOfDirectHitArt;
	private int probabilityOfDirectHitArt;

	public MadokaMagika() {
	}

	public int getDirectHitArt() {
		return directHitArt;
	}
	public void setDirectHitArt(int directHitArt) {
		this.directHitArt = directHitArt;
	}
	public List<Integer> getStartOfDirectHitArt() {
		return startOfDirectHitArt;
	}
	public void setStartOfDirectHitArt(List<Integer> startOfDirectHitArt) {
		this.startOfDirectHitArt = startOfDirectHitArt;
	}
	public int getProbabilityOfDirectHitArt() {
		return probabilityOfDirectHitArt;
	}
	public void setProbabilityOfDirectHitArt(int probabilityOfDirectHitArt) {
		this.probabilityOfDirectHitArt = probabilityOfDirectHitArt;
	}
	
}
