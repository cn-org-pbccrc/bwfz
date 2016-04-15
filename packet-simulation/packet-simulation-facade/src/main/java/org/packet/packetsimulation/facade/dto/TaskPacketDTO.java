package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;
import java.util.Date;

import org.packet.packetsimulation.core.domain.Task;


public class TaskPacketDTO implements Serializable {

	private Long id;
	
	private int version;
	
	private Task task;
	
	private Long taskId;

	private Integer encryption;
		
	private String selectedPacketName;
	
	private Integer packetFrom;
	
	private String selectedFileVersion;
	
	private String selectedOrigSender;
	
	private Date selectedOrigSendDate;
	
	private Date selectedOrigSendDateEnd;
	
	private Integer selectedDataType;

	private String selectedRecordType;
			
	private Integer compression;
	
	private Integer serialNumber;
	
	/**
	 * 文件名前缀
	 */
	private String frontPosition;
	
	/**
	 * 文件序列号0~9999
	 */
	private Integer packetNumber;
	
	/**
	 * 发送状态 0-失败 1-已发送 2-已反馈
	 */
	private Integer sendState;
	
	/**
	 * 接收反馈时间
	 */
	private Date receiveDate;
	
    /**
	 * 创建者
	 */
	private String createdBy;
		
	public Integer getSendState() {
		return sendState;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setSendState(Integer sendState) {
		this.sendState = sendState;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getSerialNumber() {
			return serialNumber;
		}

		public void setSerialNumber(Integer serialNumber) {
			this.serialNumber = serialNumber;
		}

	public Date getSelectedOrigSendDateEnd() {
			return selectedOrigSendDateEnd;
		}

		public void setSelectedOrigSendDateEnd(Date selectedOrigSendDateEnd) {
			this.selectedOrigSendDateEnd = selectedOrigSendDateEnd;
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

	public void setEncryption(Integer encryption) { 
		this.encryption = encryption;
	}

	public Integer getEncryption() {
		return encryption;
	}
		
			
	
	public void setSelectedPacketName(String selectedPacketName) { 
		this.selectedPacketName = selectedPacketName;
	}

	public String getSelectedPacketName() {
		return this.selectedPacketName;
	}
		
			
	
	public void setCompression(Integer compression) { 
		this.compression = compression;
	}

	public Integer getCompression() {
		return compression;
	}
		
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
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
		TaskPacketDTO other = (TaskPacketDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

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
}