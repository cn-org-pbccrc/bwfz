package org.packet.packetsimulation.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;
import org.springframework.format.annotation.DateTimeFormat;

@Entity 
@Table(name = "Mesg_Batch")
public class MesgBatch extends KoalaAbstractEntity{
	
	private static final long serialVersionUID = 2790533878962293456L;
	
	@Column(name = "PACKET_NAME")
	private String packetName;
	
	@Column(name = "FILE_VERSION")
	private String fileVersion;

	@Column(name = "ORIG_SENDER")
	private String origSender;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "ORIG_SEND_DATE")
	private Date origSendDate;
	
	@Column(name = "DATATYPE")
	private String dataType;
	
	@OneToOne
    @JoinColumn(name = "MESGTYPE_ID")
	private MesgType mesgType;
	
	@Column(name = "XML",length=5000)
	private String xml;
	
	@Column(name = "MESGNUM")
	private Integer mesgNum;
	
	@Column(name = "RESERVE")
	private String reserve;
	
	@Column(name = "FRONT_POSITION")
	private String frontPosition;
	
	@Column(name = "SERIAL_NUMBER")
	private Integer serialNumber;
	
	@Column(name = "CREATEDBY")
	private String createdBy;
	
	@Column(name = "START")
	private Integer start;
	
	@Column(name = "PACKET_NUM")
	private Integer packetNum;

	public String getPacketName() {
		return packetName;
	}

	public void setPacketName(String packetName) {
		this.packetName = packetName;
	}

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

	public Date getOrigSendDate() {
		return origSendDate;
	}

	public void setOrigSendDate(Date origSendDate) {
		this.origSendDate = origSendDate;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public MesgType getMesgType() {
		return mesgType;
	}

	public void setMesgType(MesgType mesgType) {
		this.mesgType = mesgType;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Integer getMesgNum() {
		return mesgNum;
	}

	public void setMesgNum(Integer mesgNum) {
		this.mesgNum = mesgNum;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getFrontPosition() {
		return frontPosition;
	}

	public void setFrontPosition(String frontPosition) {
		this.frontPosition = frontPosition;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
