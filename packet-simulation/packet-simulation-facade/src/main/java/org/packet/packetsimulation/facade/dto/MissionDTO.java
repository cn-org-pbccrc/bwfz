package org.packet.packetsimulation.facade.dto;

import java.util.Date;
import java.io.Serializable;

public class MissionDTO implements Serializable {

	private Long id;

	private int version;

			
		private Date startDate;
		
		private Date startDateEnd;
				
		private String status;
		
				
		private String description;
		
				
		private String name;
		
				
		private Date endDate;
		
		private Date endDateEnd;
				
		private String director;
		
		private String directorName;
		
		private Long projectId;
		
		private String taskCreator;
		
		private Date taskCreatedTime;
		
		private Boolean disabled;
	
	public Date getTaskCreatedTime() {
			return taskCreatedTime;
		}

		public void setTaskCreatedTime(Date taskCreatedTime) {
			this.taskCreatedTime = taskCreatedTime;
		}

	public String getDirectorName() {
			return directorName;
		}

		public void setDirectorName(String directorName) {
			this.directorName = directorName;
		}

	public Long getProjectId() {
			return projectId;
		}

		public void setProjectId(Long projectId) {
			this.projectId = projectId;
		}

		public String getTaskCreator() {
			return taskCreator;
		}

		public void setTaskCreator(String taskCreator) {
			this.taskCreator = taskCreator;
		}

	public void setStartDate(Date startDate) { 
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}
		
		public void setStartDateEnd(Date startDateEnd) { 
		this.startDateEnd = startDateEnd;
	}

	public Date getStartDateEnd() {
		return this.startDateEnd;
	}
			
	
	public void setStatus(String status) { 
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
		
			
	
	public void setDescription(String description) { 
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
		
			
	
	public void setName(String name) { 
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
		
			
	
	public void setEndDate(Date endDate) { 
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}
		
		public void setEndDateEnd(Date endDateEnd) { 
		this.endDateEnd = endDateEnd;
	}

	public Date getEndDateEnd() {
		return this.endDateEnd;
	}
			
	
	public void setDirector(String director) { 
		this.director = director;
	}

	public String getDirector() {
		return this.director;
	}
		
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

    public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MissionDTO other = (MissionDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}