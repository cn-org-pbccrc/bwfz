package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;

public class SubmissionDTO implements Serializable {

	private Long id;

	private int version;

			
		private String createdBy;
		
				
		private String recordNum;
		
				
		private String Name;
		
		private String content;
		
			
	public void setCreatedBy(String createdBy) { 
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}
		
			
	
	public void setRecordNum(String recordNum) { 
		this.recordNum = recordNum;
	}

	public String getRecordNum() {
		return this.recordNum;
	}
		
			
	
	public void setName(String Name) { 
		this.Name = Name;
	}

	public String getName() {
		return this.Name;
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

    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
		SubmissionDTO other = (SubmissionDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}