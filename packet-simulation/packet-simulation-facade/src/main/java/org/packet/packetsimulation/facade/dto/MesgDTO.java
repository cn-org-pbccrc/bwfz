package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;

public class MesgDTO implements Serializable {

	private Long id;

	private int version;

	private String content;

	private Long packetId;

	private String nodeValues;

	private Long mesgType;

	private String mesgTypeStr;

	//private String mesgPriority;

	//private String mesgDirection;

	//private String reserve;

	private String mesgId;
	
	private String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMesgId() {
		return mesgId;
	}

	public void setMesgId(String mesgId) {
		this.mesgId = mesgId;
	}

//	public String getMesgPriority() {
//		return mesgPriority;
//	}
//
//	public void setMesgPriority(String mesgPriority) {
//		this.mesgPriority = mesgPriority;
//	}
//
//	public String getMesgDirection() {
//		return mesgDirection;
//	}
//
//	public void setMesgDirection(String mesgDirection) {
//		this.mesgDirection = mesgDirection;
//	}
//
//	public String getReserve() {
//		return reserve;
//	}
//
//	public void setReserve(String reserve) {
//		this.reserve = reserve;
//	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMesgTypeStr() {
		return mesgTypeStr;
	}

	public void setMesgTypeStr(String mesgTypeStr) {
		this.mesgTypeStr = mesgTypeStr;
	}

	public String getContent() {
		return this.content;
	}

	public Long getMesgType() {
		return mesgType;
	}

	public void setMesgType(Long mesgType) {
		this.mesgType = mesgType;
	}

	public Long getPacketId() {
		return packetId;
	}

	public void setPacketId(Long packetId) {
		this.packetId = packetId;
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

	public String getNodeValues() {
		return nodeValues;
	}

	public void setNodeValues(String nodeValues) {
		this.nodeValues = nodeValues;
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
		MesgDTO other = (MesgDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}