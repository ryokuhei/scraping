package com.mycompany.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "madoka_magika")
public class MadokaMagikaEntity implements DeterminationData, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "estimated_setting")
	private int estimated_setting;

	@Column(name = "probability_of_direct_hit_art")
	private int directHitArt;

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(
		name = "direct_hits",
		joinColumns = @JoinColumn(name = "determination_data_id")
    )
	@Column(name="game")
	private List<Integer> startOfDirectHitArt;

	@Column(name = "probability_of_direct_hit_art")
	private int probabilityOfDirectHitArt;

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
