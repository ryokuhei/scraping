package com.mycompany.models.entity;

public class DataEntity {
	
	private int machineNo;
	private String machineName;
	private DataDetailEntity todayDataDetail;
	private DataDetailEntity yesterdayDataDetail;
	private RankingDataEntity rankingData;
	private Object determinationData;

	public DataEntity() {
	}

	public int getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(int machineNo) {
		this.machineNo = machineNo;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public DataDetailEntity getTodayDataDetail() {
		return todayDataDetail;
	}

	public void setTodayDataDetail(DataDetailEntity todayDataDetail) {
		this.todayDataDetail = todayDataDetail;
	}

	public DataDetailEntity getYesterdayDataDetail() {
		return yesterdayDataDetail;
	}

	public void setYesterdayDataDetail(DataDetailEntity yesterdayDataDetail) {
		this.yesterdayDataDetail = yesterdayDataDetail;
	}

	public RankingDataEntity getRankingData() {
		return rankingData;
	}

	public void setRankingData(RankingDataEntity rankingData) {
		this.rankingData = rankingData;
	}

	public Object getDeterminationData() {
		return determinationData;
	}

	public void setDeterminationData(Object determinationData) {
		this.determinationData = determinationData;
	}

}
