package org.packet.packetsimulation.facade.dto;

import java.util.Date;

import java.io.Serializable;

public class TaskDTO implements Serializable {

	private Long id;

	private int version;
			
	private String setTime;
			
	private String sendChannel;
				
	private Date taskCreatedTime;
	
	private Date taskCreatedTimeEnd;
			
	private String taskStatus;
				
	private String packetNum;
			
	private String taskCreator;
					
	private String taskName;
		
	private Integer taskFrom;	
	
	private Long missionId;
	
	public void setSetTime(String setTime) { 
		this.setTime = setTime;
	}

	public String getSetTime() {
		return this.setTime;
	}

	public void setSendChannel(String sendChannel) { 
		this.sendChannel = sendChannel;
	}

	public String getSendChannel() {
		return this.sendChannel;
	}
		
			
	
	public void setTaskCreatedTime(Date taskCreatedTime) { 
		this.taskCreatedTime = taskCreatedTime;
	}

	public Date getTaskCreatedTime() {
		return this.taskCreatedTime;
	}
		
		public void setTaskCreatedTimeEnd(Date taskCreatedTimeEnd) { 
		this.taskCreatedTimeEnd = taskCreatedTimeEnd;
	}

	public Date getTaskCreatedTimeEnd() {
		return this.taskCreatedTimeEnd;
	}
			
	
	public void setTaskStatus(String taskStatus) { 
		this.taskStatus = taskStatus;
	}

	public String getTaskStatus() {
		return this.taskStatus;
	}
		
			
	
	public void setPacketNum(String packetNum) { 
		this.packetNum = packetNum;
	}

	public String getPacketNum() {
		return this.packetNum;
	}
		
			
	
	public void setTaskCreator(String taskCreator) { 
		this.taskCreator = taskCreator;
	}

	public String getTaskCreator() {
		return this.taskCreator;
	}
		
			
	
	public void setTaskName(String taskName) { 
		this.taskName = taskName;
	}

	public String getTaskName() {
		return this.taskName;
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

    public Integer getTaskFrom() {
		return taskFrom;
	}

	public void setTaskFrom(Integer taskFrom) {
		this.taskFrom = taskFrom;
	}

	public Long getMissionId() {
		return missionId;
	}

	public void setMissionId(Long missionId) {
		this.missionId = missionId;
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
		TaskDTO other = (TaskDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}