package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;

public class BatchRuleDTO implements Serializable {

	private static final long serialVersionUID = -6907640189779048533L;
	
	/**
	 * 字段名称
	 */
	private String enName;
	
	/**
	 * 字段名称
	 */
	private String cnName;
	
	/**
	 * 字段xpath
	 */
	private String xpath;
	
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

	public String getXpath() {
		return xpath;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public String getRuleProperties() {
		return ruleProperties;
	}


	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public void setRuleProperties(String ruleProperties) {
		this.ruleProperties = ruleProperties;
	}

	public String getEnName() {
		return enName;
	}

	public String getCnName() {
		return cnName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	

}