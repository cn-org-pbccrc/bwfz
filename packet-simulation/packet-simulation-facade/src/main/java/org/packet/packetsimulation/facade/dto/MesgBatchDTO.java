package org.packet.packetsimulation.facade.dto;

import java.util.Date;
import java.io.Serializable;

public class MesgBatchDTO implements Serializable {

	private Long id;

	private int version;

			
		private Integer mesgNum;
		
				
		private String dataType;
		
				
		private String createdBy;
		
				
		private String origSender;
		
				
		private String packetName;
		
				
		private String fileVersion;
		
				
		private Date origSendDate;
		
		private Date origSendDateEnd;
		
		private String xml;
		
		private Long mesgType;
		
		private String mesgTypeStr;
		
		private String reserve;
		
		private Integer start;
		
		private Integer packetNum;
			
	public void setMesgNum(Integer mesgNum) { 
		this.mesgNum = mesgNum;
	}

	public Integer getMesgNum() {
		return this.mesgNum;
	}	
	
	public void setDataType(String dataType) { 
		this.dataType = dataType;
	}

	public String getDataType() {
		return this.dataType;
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
		
			
	
	public void setPacketName(String packetName) { 
		this.packetName = packetName;
	}

	public String getPacketName() {
		return this.packetName;
	}
		
			
	
	public void setFileVersion(String fileVersion) { 
		this.fileVersion = fileVersion;
	}

	public String getFileVersion() {
		return this.fileVersion;
	}
		
			
	
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

    public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Long getMesgType() {
		return mesgType;
	}

	public void setMesgType(Long mesgType) {
		this.mesgType = mesgType;
	}

	public String getMesgTypeStr() {
		return mesgTypeStr;
	}

	public void setMesgTypeStr(String mesgTypeStr) {
		this.mesgTypeStr = mesgTypeStr;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getPacketNum() {
		return packetNum;
	}

	public void setPacketNum(Integer packetNum) {
		this.packetNum = packetNum;
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
		MesgBatchDTO other = (MesgBatchDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}