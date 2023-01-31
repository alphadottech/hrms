package com.adt.hrms.ui;


public class PositionDateConverter {
	
	private int uiid;
	
	private int techid;
	
	private String positionopendate;
	
	private String positionclosedate;
	
	private String status;
	
	private double experienceInYear;
	
	private boolean remote;
	
	private String positionType;

	public int getUiid() {
		return uiid;
	}

	public void setUiid(int uiid) {
		this.uiid = uiid;
	}

	public int getTechid() {
		return techid;
	}

	public void setTechid(int techid) {
		this.techid = techid;
	}

	public String getPositionopendate() {
		return positionopendate;
	}

	public void setPositionopendate(String positionopendate) {
		this.positionopendate = positionopendate;
	}

	public String getPositionclosedate() {
		return positionclosedate;
	}

	public void setPositionclosedate(String positionclosedate) {
		this.positionclosedate = positionclosedate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getExperienceInYear() {
		return experienceInYear;
	}

	public void setExperienceInYear(double experienceInYear) {
		this.experienceInYear = experienceInYear;
	}

	public boolean isRemote() {
		return remote;
	}

	public void setRemote(boolean remote) {
		this.remote = remote;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public PositionDateConverter(int uiid, int techid, String positionopendate, String positionclosedate, String status,
			double experienceInYear, boolean remote, String positionType) {
		super();
		this.uiid = uiid;
		this.techid = techid;
		this.positionopendate = positionopendate;
		this.positionclosedate = positionclosedate;
		this.status = status;
		this.experienceInYear = experienceInYear;
		this.remote = remote;
		this.positionType = positionType;
	}

	public PositionDateConverter() {
		super();
		
	}

	@Override
	public String toString() {
		return "PositionDateConverter [uiid=" + uiid + ", techid=" + techid + ", positionopendate=" + positionopendate
				+ ", positionclosedate=" + positionclosedate + ", status=" + status + ", experienceInYear="
				+ experienceInYear + ", remote=" + remote + ", positionType=" + positionType + "]";
	}
	
	
	
	

	
	
	

}
