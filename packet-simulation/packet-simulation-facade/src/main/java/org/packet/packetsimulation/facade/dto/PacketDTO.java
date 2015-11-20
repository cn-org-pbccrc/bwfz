package org.packet.packetsimulation.facade.dto;

import java.util.Date;
import java.io.Serializable;

public class PacketDTO implements Serializable {

	private Long id;

	private int version;

	private String packId;

	private String createdBy;

	private String origSender;
	
	private String packetName;

	//private Date createdAt;

	//private Date createdAtEnd;

	private Date origSendDate;

	private Date origSendDateEnd;
	
	private String fileVersion;
	
	private String dataType;
	
	private String recordType;

	private String mesgNum;
	
	private String reserve;

	public String getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getMesgNum() {
		return mesgNum;
	}

	public void setMesgNum(String mesgNum) {
		this.mesgNum = mesgNum;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setOrigSender(String origSender) {
		this.origSender = origSender;
	}

	public String getOrigSender() {
		return this.origSender;
	}

//	public void setCreatedAt(Date createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public Date getCreatedAt() {
//		return this.createdAt;
//	}
//
//	public void setCreatedAtEnd(Date createdAtEnd) {
//		this.createdAtEnd = createdAtEnd;
//	}
//
//	public Date getCreatedAtEnd() {
//		return this.createdAtEnd;
//	}

	public void setOrigSendDate(Date origSendDate) {
		this.origSendDate = origSendDate;
	}

	public Date getOrigSendDate() {
		return this.origSendDate;
	}

	public void setOrigSendDateEnd(Date origSendDateEnd) {
		this.origSendDateEnd = origSendDateEnd;
	}

	public Date getOrigSendDateEnd() {
		return this.origSendDateEnd;
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

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
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
		PacketDTO other = (PacketDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getPacketName() {
		return packetName;
	}

	public void setPacketName(String packetName) {
		this.packetName = packetName;
	}
}