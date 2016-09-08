package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;

import org.packet.packetsimulationGeneration.core.domain.RecordType;

public class RecordDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4884293242028561460L;

	private Long id;

	private int version;

	private RecordType recordType;
	
	private String recordTypeStr;

	private String content;

	private String recordName;

	private String createdBy;

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

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
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
		RecordDTO other = (RecordDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}