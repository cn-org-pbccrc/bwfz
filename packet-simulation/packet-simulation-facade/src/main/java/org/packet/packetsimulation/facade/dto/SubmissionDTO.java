package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;

import org.packet.packetsimulationGeneration.core.domain.RecordType;

public class SubmissionDTO implements Serializable {

	private Long id;

	private int version;

			
		private String createdBy;
		
				
		private Long recordNum;
		
				
		private String name;
		
		private String content;
		
		private RecordType recordType;
		
		private String recordTypeStr;
		
			
	public void setCreatedBy(String createdBy) { 
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}
		
			
	
	public void setRecordNum(Long recordNum) { 
		this.recordNum = recordNum;
	}

	public Long getRecordNum() {
		return this.recordNum;
	}
		
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public String getRecordTypeStr() {
		return recordTypeStr;
	}

	public void setRecordTypeStr(String recordTypeStr) {
		this.recordTypeStr = recordTypeStr;
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