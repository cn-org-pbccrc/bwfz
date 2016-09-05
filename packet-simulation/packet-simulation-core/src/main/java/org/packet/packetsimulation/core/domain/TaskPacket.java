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
import org.springframework.format.annotation.DateTimeFormat;

@Entity 
@Table(name = "TASK_PACKET")
public class TaskPacket extends KoalaAbstractEntity{
	
	private static final long serialVersionUID = -8192000377774622162L;
	/**
	 * 任务
	 */
	@ManyToOne
	@JoinColumn(name = "TASK_ID")
	private Task task;	
	
	/**
	 * 文件来源  内部/外部
	 */
	@Column(name = "PACKETFROM")
	private Integer packetFrom;
	
	/**
	 * 文件名称
	 */
	@Column(name = "SELECTED_PACKET_NAME")
	private String selectedPacketName;
	
	/**
	 * 文件名前缀
	 */
	@Column(name = "FRONT_POSITION")
	private String frontPosition;
	
	/**
	 * 文件序列号0~9999
	 */
	@Column(name = "PACKET_NUMBER")
	private Integer packetNumber;
	
	/**
	 * 文件格式版本号
	 */
	@Column(name = "SELECTED_FILE_VERSION")
	private String selectedFileVersion;
	

	/**
	 * 数据提供机构代码
	 */
	@Column(name = "SELECTED_ORIG_SENDER")
	private String selectedOrigSender;
	
	/**
	 * 发送时间
	 */
	//@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "SELECTED_ORIG_SEND_DATE")
	private Date selectedOrigSendDate;
	
	/**
	 * 数据类型
	 */
	@Column(name = "SELECTED_DATATYPE")
	private Integer selectedDataType;
	
	/**
	 * 业务类型
	 */
	@Column(name = "SELECTED_BIZTYPE")
	private String selectedBizType;

	/**
	 * 记录类型
	 */
	@Column(name = "SELECTED_RECORDTYPE")
	private String selectedRecordType;
	
	/**
	 * 加压
	 */
	@Column(name = "COMPRESSION")
	private Integer compression;
	
	/**
	 * 加密
	 */
	@Column(name = "ENCRYPTION")
	private Integer encryption;
	
	/**
	 * 排序字段
	 */
	@Column(name = "SERIAL_NUMBER")
	private Integer serialNumber;
	
	public Integer getSerialNumber() {
		return serialNumber;
	}
	
	/**
	 * 发送状态 0-失败 1-已发送 2-已反馈
	 */
	@Column(name = "SEND_STATE")
	private Integer sendState;
	
	/**
	 * 接收反馈时间
	 */
	//@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "RECEIVE_DATE")
	private Date receiveDate;
	
	/**
     * 创建时间
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE")
    private Date createDate = new Date();
	
    /**
	 * 创建者
	 */
	@Column(name = "CREATEDBY")
	private String createdBy;
	
	public String getFrontPosition() {
		return frontPosition;
	}

	public Integer getPacketNumber() {
		return packetNumber;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setFrontPosition(String frontPosition) {
		this.frontPosition = frontPosition;
	}

	public void setPacketNumber(Integer packetNumber) {
		this.packetNumber = packetNumber;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Integer getPacketFrom() {
		return packetFrom;
	}

	public void setPacketFrom(Integer packetFrom) {
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

	public Integer getSelectedDataType() {
		return selectedDataType;
	}

	public void setSelectedDataType(Integer selectedDataType) {
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

	public Integer getCompression() {
		return compression;
	}

	public void setCompression(Integer compression) {
		this.compression = compression;
	}

	public Integer getEncryption() {
		return encryption;
	}

	public void setEncryption(Integer encryption) {
		this.encryption = encryption;
	}

	public Integer getSendState() {
		return sendState;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setSendState(Integer sendState) {
		this.sendState = sendState;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getSelectedBizType() {
		return selectedBizType;
	}

	public void setSelectedBizType(String selectedBizType) {
		this.selectedBizType = selectedBizType;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
}
