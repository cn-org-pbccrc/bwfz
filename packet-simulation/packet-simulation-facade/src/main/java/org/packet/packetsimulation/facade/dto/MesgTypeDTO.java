package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;

public class MesgTypeDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7991090069957988125L;

	private Long id;

	private int version;

	private Integer sort;

	private String mesgType;
	
	//private String mesgTypeStr;
	
	private String code;

	private String bizType;
	
	private String countTag;
	
	private String xml;
	
	private String createdBy;
	
	private String transform;

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getTransform() {
		return transform;
	}

	public void setTransform(String transform) {
		this.transform = transform;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

//	public String getMesgTypeStr() {
//		return mesgTypeStr;
//	}
//
//	public void setMesgTypeStr(String mesgTypeStr) {
//		this.mesgTypeStr = mesgTypeStr;
//	}

	public String getCountTag() {
		return countTag;
	}

	public void setCountTag(String countTag) {
		this.countTag = countTag;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return this.sort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMesgType(String mesgType) {
		this.mesgType = mesgType;
	}

	public String getMesgType() {
		return this.mesgType;
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
		MesgTypeDTO other = (MesgTypeDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}