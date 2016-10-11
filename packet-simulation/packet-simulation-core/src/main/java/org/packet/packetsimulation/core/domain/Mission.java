package org.packet.packetsimulation.core.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;
import org.openkoala.security.core.domain.User;
import org.openkoala.security.org.core.domain.EmployeeUser;

@Entity 
@Table(name = "MISSION")
@NamedQueries({
    @NamedQuery(name = "Mission.findAllMissionsByUserAccount", query = "SELECT _mission FROM Mission _mission, Actor _actor WHERE _mission.employeeUser.id = _actor.id AND _mission.disabled = :disabled AND _actor.userAccount = :userAccount")
})
public class Mission extends KoalaAbstractEntity{
	
	private static final long serialVersionUID = -4757105962018778456L;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DIRECTOR")
	@Transient
	private String director;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "PROJECT_ID")
	private Project project;
	
	@Column(name = "TASK_CREATOR")
	private String taskCreator;
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = "TASK_CREATEDTIME")
	private Date taskCreatedTime;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "DIRECTOR_NAME")
	private EmployeeUser employeeUser;
	
	@Column(name = "DISABLED")
    private boolean disabled;

	public EmployeeUser getEmployeeUser() {
		return employeeUser;
	}

	public void setEmployeeUser(EmployeeUser employeeUser) {
		this.employeeUser = employeeUser;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getTaskCreator() {
		return taskCreator;
	}

	public void setTaskCreator(String taskCreator) {
		this.taskCreator = taskCreator;
	}

	public Date getTaskCreatedTime() {
		return taskCreatedTime;
	}

	public void setTaskCreatedTime(Date taskCreatedTime) {
		this.taskCreatedTime = taskCreatedTime;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public static String findMissionNameBy(Long missionId){
		return get(Mission.class, missionId).getName();
	}
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
