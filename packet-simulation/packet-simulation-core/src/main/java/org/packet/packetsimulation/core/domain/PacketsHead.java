package org.packet.packetsimulation.core.domain;

import java.util.Date;

public class PacketsHead {

	private String fileVersion;
	private String origSender;
	private String origSendDate;
	private String recordType;
	private String dataType;
	public String getFileVersion() {
		return fileVersion;
	}
	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}
	public String getOrigSender() {
		return origSender;
	}
	public void setOrigSender(String origSender) {
		this.origSender = origSender;
	}
	public String getOrigSendDate() {
		return origSendDate;
	}
	public void setOrigSendDate(String origSendDate) {
		this.origSendDate = origSendDate;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	
	
}
