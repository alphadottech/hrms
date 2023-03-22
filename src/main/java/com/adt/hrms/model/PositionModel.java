   package com.adt.hrms.model;


import java.time.LocalDateTime;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(catalog = "EmployeeDB", schema = "employee_schema", name="Position")
public class PositionModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
//	@Column(name = "tech_id")
	private Integer techId;
	
//	@Column(name = "position_open_date")
	private LocalDateTime positionOpenDate;
	
//	@Column(name = "position_close_date")
	private LocalDateTime positionCloseDate;
	
//	@Column(name = "status")
	private String status;
	
//	@Column(name = "Experience_in_year")
	private Double experienceInYear;
	
//	@Column(name = "remote")
	private Boolean remote;
	
//	@Column(name = "position_type")
	private String positionType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTechId() {
		return techId;
	}

	public void setTechId(Integer techId) {
		this.techId = techId;
	}

	public LocalDateTime getPositionOpenDate() {
		return positionOpenDate;
	}

	public void setPositionOpenDate(LocalDateTime positionOpenDate) {
		this.positionOpenDate = positionOpenDate;
	}

	public LocalDateTime getPositionCloseDate() {
		return positionCloseDate;
	}

	public void setPositionCloseDate(LocalDateTime positionCloseDate) {
		this.positionCloseDate = positionCloseDate;
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

	public PositionModel(Integer id, Integer techId, LocalDateTime positionOpenDate, LocalDateTime positionCloseDate,
			String status, double experienceInYear, boolean remote, String positionType) {
		super();
		this.id = id;
		this.techId = techId;
		this.positionOpenDate = positionOpenDate;
		this.positionCloseDate = positionCloseDate;
		this.status = status;
		this.experienceInYear = experienceInYear;
		this.remote = remote;
		this.positionType = positionType;
	}

	public PositionModel() {
		super();
		
	}

	@Override
	public String toString() {
		return "PositionModel [id=" + id + ", techId=" + techId + ", positionOpenDate=" + positionOpenDate
				+ ", positionCloseDate=" + positionCloseDate + ", status=" + status + ", experienceInYear="
				+ experienceInYear + ", remote=" + remote + ", positionType=" + positionType + "]";
	}
	
	
	
	
}
