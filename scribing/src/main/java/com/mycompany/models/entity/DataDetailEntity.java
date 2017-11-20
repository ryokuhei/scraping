package com.mycompany.models.entity;

import java.io.File;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "machine_data_details")
public class DataDetailEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "bb")
	private int bb;
	@Column(name = "rb")
	private int rb;
	@Column(name = "art")
	private int art;
	@Column(name = "bonus_probability")
	private int bonusProbability;
	@Column(name = "total_bonus")
	private int totalBonus;
	@Column(name = "total_game")
	private int totalGame;
	@Column(name = "total_art")
	private int totalArt;
	@Column(name = "end_game")
	private int endGame;
	@Column(name = "max_medal")
	private int maxMedal;
	@Column(name = "earned_medal")
	private int earnedMedals; 
	@Column(name = "result_image_path")
	private String resultImagePath;
	@Transient
	private File resultImageFile;

	public DataDetailEntity() {
	}

	public int getBb() {
		return bb;
	}
	public void setBb(int bb) {
		this.bb = bb;
	}
	public int getRb() {
		return rb;
	}
	public void setRb(int rb) {
		this.rb = rb;
	}
	public int getArt() {
		return art;
	}
	public void setArt(int art) {
		this.art = art;
	}
	public int getBonusProbability() {
		return bonusProbability;
	}
	public void setBonusProbability(int bonusProbability) {
		this.bonusProbability = bonusProbability;
	}
	public int getTotalBonus() {
		return totalBonus;
	}
	public void setTotalBonus(int totalBonus) {
		this.totalBonus = totalBonus;
	}
	public int getTotalGame() {
		return totalGame;
	}
	public void setTotalGame(int totalGame) {
		this.totalGame = totalGame;
	}
	public int getTotalArt() {
		return totalArt;
	}
	public void setTotalArt(int totalArt) {
		this.totalArt = totalArt;
	}
	public int getEndGame() {
		return endGame;
	}
	public void setEndGame(int endGame) {
		this.endGame = endGame;
	}
	public int getMaxMedal() {
		return maxMedal;
	}
	
	public void setMaxMedal(int maxMedal) {
		this.maxMedal = maxMedal;
	}
	public File getResultImageFile() {
		return resultImageFile;
	}

	public void setResultImageFile(File resultImageFile) {
		this.resultImageFile = resultImageFile;
	}

	public int getEarnedMedals() {
		return earnedMedals;
	}

	public void setEarnedMedals(int earnedMedals) {
		this.earnedMedals = earnedMedals;
	}
	
	@PrePersist
	@PreUpdate
	public void preCreateAndUpdate() {
		String path = this.resultImageFile.getPath();
		this.resultImagePath = path;
	}

}
