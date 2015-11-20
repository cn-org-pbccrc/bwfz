package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;
import java.util.Date;


public class TaskPacketDTO implements Serializable {

	private Long id;
	

	private int version;
	
	
	private Long taskId;

			
		private String encryption;
		
				
		private String selectedPacketName;
		
		private String packetFrom;
		
		private String selectedFileVersion;
		
		private String selectedOrigSender;
		
		private Date selectedOrigSendDate;
		
		private String selectedDataType;

		private String selectedRecordType;
				
		private String compression;
		
			
	
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

	public void setEncryption(String encryption) { 
		this.encryption = encryption;
	}

	public String getEncryption() {
		return this.encryption;
	}
		
			
	
	public void setSelectedPacketName(String selectedPacketName) { 
		this.selectedPacketName = selectedPacketName;
	}

	public String getSelectedPacketName() {
		return this.selectedPacketName;
	}
		
			
	
	public void setCompression(String compression) { 
		this.compression = compression;
	}

	public String getCompression() {
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
}