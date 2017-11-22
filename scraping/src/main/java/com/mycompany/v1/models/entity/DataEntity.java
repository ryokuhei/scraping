package com.mycompany.v1.models.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "machine_data")
public class DataEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "machine_number")
	private int machineNumber;

	@Column(name = "machine_name")
	private String machineName;

	@Column(name = "date")
	private LocalDate date;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "machine_data_details_id")
	private DataDetailEntity dataDetail;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "determination_data_id", referencedColumnName="id")
//	private Object determinationData;
//	@Transient
	private MadokaMagikaEntity determinationData;

	public DataEntity() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMachineNumber() {
		return machineNumber;
	}

	public void setMachineNumber(int machineNumber) {
		this.machineNumber = machineNumber;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public DataDetailEntity getDataDetail() {
		return dataDetail;
	}

	public void setDataDetail(DataDetailEntity dataDetail) {
		this.dataDetail = dataDetail;
	}

	public Object getDeterminationData() {
		return determinationData;
	}

	public void setDeterminationData(Object determinationData) {
		this.determinationData = (MadokaMagikaEntity)determinationData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
