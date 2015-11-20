package org.packet.packetsimulation.core.domain;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;
import org.openkoala.security.org.core.domain.EmployeeUser;

@Entity 
@Table(name = "PROJECTS") 
public class Project extends KoalaAbstractEntity {

	private static final long serialVersionUID = -4748565962108778092L;
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="MANAGER_NAME")
	private EmployeeUser employeeUser; 

	@Column(name = "PROJECT_NAME") 
	private String projectName; 

	@Column(name = "PROJECT_CODE") 
	private String projectCode; 
	
	@Column(name = "PROJECT_MANAGER")
	@Transient
	private String projectManager; 

	@Temporal(value = TemporalType.DATE) 
	@Column(name = "PROJECT_START_DATE") 
	private Date projectstartDate; 
	
	@Temporal(value = TemporalType.DATE) 
	@Column(name = "PROJECT_END_DATE") 
	private Date projectendDate; 

	@Column(name = "PROJECT_REMARK") 
	private String projectRemark; 
	
	
	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getProjectCode() {
		return projectCode;
	}


	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}


	public String getProjectManager() {
		return projectManager;
	}


	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}


	public Date getProjectstartDate() {
		return projectstartDate;
	}


	public void setProjectstartDate(Date projectstartDate) {
		this.projectstartDate = projectstartDate;
	}


	public Date getProjectendDate() {
		return projectendDate;
	}


	public void setProjectendDate(Date projectendDate) {
		this.projectendDate = projectendDate;
	}


	public String getProjectRemark() {
		return projectRemark;
	}


	public void setProjectRemark(String projectRemark) {
		this.projectRemark = projectRemark;
	}

	public EmployeeUser getEmployeeUser() { 
		return employeeUser; 
	}
			
	public void setEmployeeUser(EmployeeUser employeeUser) { 
		this.employeeUser = employeeUser; 
	} 

	@Override
	public String[] businessKeys() {
		
		return new String[] { "projectName", "projectCode" };
	}
	
}
