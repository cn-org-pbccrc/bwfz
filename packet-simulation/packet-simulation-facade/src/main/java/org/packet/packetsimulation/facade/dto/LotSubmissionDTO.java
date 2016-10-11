package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;

public class LotSubmissionDTO implements Serializable {

	private Long id;

	private int version;

			
		private Integer encryption;
		
				
		private String name;
		
				
		private Integer serialNumber;
		
				
		private Integer compression;
		
		private String frontPosition;
		
		private Integer fileNumber;
		
		private Long lotId;
		
		private Integer submissionFrom;
	
	public void setEncryption(Integer encryption) { 
		this.encryption = encryption;
	}

	public Integer getEncryption() {
		return this.encryption;
	}
		
			
	
	public void setName(String name) { 
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
		
			
	
	public void setSerialNumber(Integer serialNumber) { 
		this.serialNumber = serialNumber;
	}

	public Integer getSerialNumber() {
		return this.serialNumber;
	}			
	
	public void setCompression(Integer compression) { 
		this.compression = compression;
	}

	public Integer getCompression() {
		return this.compression;
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

    public String getFrontPosition() {
		return frontPosition;
	}

	public void setFrontPosition(String frontPosition) {
		this.frontPosition = frontPosition;
	}

	public Integer getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(Integer fileNumber) {
		this.fileNumber = fileNumber;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public Integer getSubmissionFrom() {
		return submissionFrom;
	}

	public void setSubmissionFrom(Integer submissionFrom) {
		this.submissionFrom = submissionFrom;
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
		LotSubmissionDTO other = (LotSubmissionDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}