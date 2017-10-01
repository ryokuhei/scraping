package com.mycompany;

public class Data {
	
	private int rank;
	private int machineNo;
	private String machineName;
	private int point;
	private DataDetail todayDataDetail;
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	private DataDetail yesterdayDataDetail;
	
	public Data() {
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
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

	public DataDetail getTodayDataDetail() {
		return todayDataDetail;
	}

	public void setTodayDataDetail(DataDetail todayDataDetail) {
		this.todayDataDetail = todayDataDetail;
	}

	public DataDetail getYesterdayDataDetail() {
		return yesterdayDataDetail;
	}

	public void setYesterdayDataDetail(DataDetail yesterdayDataDetail) {
		this.yesterdayDataDetail = yesterdayDataDetail;
	}
}
