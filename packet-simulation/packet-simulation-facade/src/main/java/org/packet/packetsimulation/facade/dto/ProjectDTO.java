package org.packet.packetsimulation.facade.dto;

import java.util.Date;

import java.io.Serializable;

public class ProjectDTO implements Serializable {

	private Long id;

	private int version;

			
		private String projectCode;
		
				
		private String projectManager;
		
        private String projectManagerName;		
				
		private Date projectstartDate;
		
		private Date projectstartDateEnd;
				
		private String projectRemark;
		
				
		private Date projectendDate;
		
		private Date projectendDateEnd;
				
		private String projectName;
		
			
	
	public void setProjectCode(String projectCode) { 
		this.projectCode = projectCode;
	}

	public String getProjectCode() {
		return this.projectCode;
	}
		
	public String getProjectManagerName() {
		return projectManagerName;
	}
	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}		
	
	public void setProjectManager(String projectManager) { 
		this.projectManager = projectManager;
	}

	public String getProjectManager() {
		return this.projectManager;
	}
		
			
	
	public void setProjectstartDate(Date projectstartDate) { 
		this.projectstartDate = projectstartDate;
	}

	public Date getProjectstartDate() {
		return this.projectstartDate;
	}
		
		public void setProjectstartDateEnd(Date projectstartDateEnd) { 
		this.projectstartDateEnd = projectstartDateEnd;
	}

	public Date getProjectstartDateEnd() {
		return this.projectstartDateEnd;
	}
			
	
	public void setProjectRemark(String projectRemark) { 
		this.projectRemark = projectRemark;
	}

	public String getProjectRemark() {
		return this.projectRemark;
	}
		
			
	
	public void setProjectendDate(Date projectendDate) { 
		this.projectendDate = projectendDate;
	}

	public Date getProjectendDate() {
		return this.projectendDate;
	}
		
		public void setProjectendDateEnd(Date projectendDateEnd) { 
		this.projectendDateEnd = projectendDateEnd;
	}

	public Date getProjectendDateEnd() {
		return this.projectendDateEnd;
	}
			
	
	public void setProjectName(String projectName) { 
		this.projectName = projectName;
	}

	public String getProjectName() {
		return this.projectName;
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
		ProjectDTO other = (ProjectDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}