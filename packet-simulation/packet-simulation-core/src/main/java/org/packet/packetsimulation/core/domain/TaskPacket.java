package org.packet.packetsimulation.core.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;
import org.openkoala.security.org.core.domain.EmployeeUser;

@Entity 
@Table(name = "TASK_PACKET")
public class TaskPacket extends KoalaAbstractEntity{
	
	private static final long serialVersionUID = -8192000377774622162L;
	
	@ManyToOne
	@JoinColumn(name = "TASK_ID")
	private Task task;	
	
	@Column(name = "PACKETFROM")
	private String packetFrom;
	
	@Column(name = "SELECTED_PACKET_NAME")
	private String selectedPacketName;
	
	@Column(name = "SELECTED_FILE_VERSION")
	private String selectedFileVersion;
	
	@Column(name = "SELECTED_ORIG_SENDER")
	private String selectedOrigSender;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "SELECTED_ORIG_SEND_DATE")
	private Date selectedOrigSendDate;
	
	@Column(name = "SELECTED_DATATYPE")
	private String selectedDataType;

	@Column(name = "SELECTED_RECORDTYPE")
	private String selectedRecordType;
	
	@Column(name = "COMPRESSION")
	private String compression;
	
	@Column(name = "ENCRYPTION")
	private String encryption;
	
	public String getPacketFrom() {
		return packetFrom;
	}

	public void setPacketFrom(String packetFrom) {
		this.packetFrom = packetFrom;
	}

	public String getSelectedFileVersion() {
		return selectedFileVersion;
	}

	public void setSelectedFileVersion(String selectedFileVersion) {
		this.selectedFileVersion = selectedFileVersion;
	}

	public String getSelectedOrigSender() {
		return selectedOrigSender;
	}

	public void setSelectedOrigSender(String selectedOrigSender) {
		this.selectedOrigSender = selectedOrigSender;
	}

	public Date getSelectedOrigSendDate() {
		return selectedOrigSendDate;
	}

	public void setSelectedOrigSendDate(Date selectedOrigSendDate) {
		this.selectedOrigSendDate = selectedOrigSendDate;
	}

	public String getSelectedDataType() {
		return selectedDataType;
	}

	public void setSelectedDataType(String selectedDataType) {
		this.selectedDataType = selectedDataType;
	}

	public String getSelectedRecordType() {
		return selectedRecordType;
	}

	public void setSelectedRecordType(String selectedRecordType) {
		this.selectedRecordType = selectedRecordType;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getSelectedPacketName() {
		return selectedPacketName;
	}

	public void setSelectedPacketName(String selectedPacketName) {
		this.selectedPacketName = selectedPacketName;
	}

	public String getCompression() {
		return compression;
	}

	public void setCompression(String compression) {
		this.compression = compression;
	}

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
}
