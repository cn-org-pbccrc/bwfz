package org.packet.packetsimulation.facade.dto;

import java.util.Date;

import java.io.Serializable;

public class LotDTO implements Serializable {

	private Long id;

	private int version;

			
		private String setTime;
		
				
		private String lotCreator;
		
				
		private String sendChannel;
		
				
		private Date lotCreatedTime;
		
		private Date lotCreatedTimeEnd;
				
		private String lotName;
		
				
		private String submissionNum;
		
				
		private Integer type;
		
				
		private String lotStatus;
		
			
	
	public void setSetTime(String setTime) { 
		this.setTime = setTime;
	}

	public String getSetTime() {
		return this.setTime;
	}
		
			
	
	public void setLotCreator(String lotCreator) { 
		this.lotCreator = lotCreator;
	}

	public String getLotCreator() {
		return this.lotCreator;
	}
		
			
	
	public void setSendChannel(String sendChannel) { 
		this.sendChannel = sendChannel;
	}

	public String getSendChannel() {
		return this.sendChannel;
	}
		
			
	
	public void setLotCreatedTime(Date lotCreatedTime) { 
		this.lotCreatedTime = lotCreatedTime;
	}

	public Date getLotCreatedTime() {
		return this.lotCreatedTime;
	}
		
		public void setLotCreatedTimeEnd(Date lotCreatedTimeEnd) { 
		this.lotCreatedTimeEnd = lotCreatedTimeEnd;
	}

	public Date getLotCreatedTimeEnd() {
		return this.lotCreatedTimeEnd;
	}
			
	
	public void setLotName(String lotName) { 
		this.lotName = lotName;
	}

	public String getLotName() {
		return this.lotName;
	}
		
			
	
	public void setSubmissionNum(String submissionNum) { 
		this.submissionNum = submissionNum;
	}

	public String getSubmissionNum() {
		return this.submissionNum;
	}
		
			
	
	public void setType(Integer type) { 
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}
		
			
	
	public void setLotStatus(String lotStatus) { 
		this.lotStatus = lotStatus;
	}

	public String getLotStatus() {
		return this.lotStatus;
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
		LotDTO other = (LotDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}