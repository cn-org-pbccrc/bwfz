package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;

public class QuantityRuleDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7611099340376005788L;

	/**
	 * 字段名称
	 */
	private String cnName;
	
	/**
	 * 所属段段标
	 */
	private String segMark;
	
	/**
	 * 标识符
	 */
	private String itemId;
	
	/**
	 * 数据项长度
	 */
	private Integer itemLength;
	
	/**
	 * 数据项类型
	 */
	private Integer itemType;
	
	/**
	 * 规则类型 0-自增 1-数据字典 2-自定义
	 */
	private Integer ruleType; 
	
	/**
	 * 规则属性
	 */
	private String ruleProperties;
	
	/**
	 * 是否启用
	 */
	private Boolean inUse = false;


	public Boolean getInUse() {
		return inUse;
	}

	public void setInUse(Boolean inUse) {
		this.inUse = inUse;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public String getRuleProperties() {
		return ruleProperties;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public void setRuleProperties(String ruleProperties) {
		this.ruleProperties = ruleProperties;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getSegMark() {
		return segMark;
	}

	public void setSegMark(String segMark) {
		this.segMark = segMark;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getItemLength() {
		return itemLength;
	}

	public void setItemLength(Integer itemLength) {
		this.itemLength = itemLength;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	

}