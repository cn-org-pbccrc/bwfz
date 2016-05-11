package org.packet.packetsimulation.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.dayatang.domain.ValueObject;

/**
 * @author Charles
 *
 */

@Embeddable
public class BatchRule  implements ValueObject{
	
	private static final long serialVersionUID = -6335897849644090133L;
	
	/**
	 * 字段英文名
	 */
	@Column(name = "EN_NAME")
	private String enName;
	
	/**
	 * 字段中文名
	 */
	@Column(name = "CN_NAME")
	private String cnName;
	
	/**
	 * 字段xpath
	 */
	@Column(name = "XPATH")
	private String xpath;
	
	/**
	 * 规则类型 0-自增 1-数据字典 2-自定义
	 */
	@Column(name = "RULE_TYPE") 
	private Integer ruleType; 
	
	/**
	 * 规则属性
	 */
	@Column(name = "RULE_PROPERTIES",length=5000) 
	private String ruleProperties;
	
	/**
	 * 该规则是否启用
	 */
	@Column(name = "IN_USE")
	private Boolean inUse = false;
	

	public Boolean getInUse() {
		return inUse;
	}

	public void setInUse(Boolean inUse) {
		this.inUse = inUse;
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

}
