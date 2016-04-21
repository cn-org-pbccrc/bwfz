package org.packet.packetsimulation.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

/**
 * @author Charles
 *
 */

@Entity 
@Table(name = "BATCH_RULE") 
public class BatchRule extends KoalaAbstractEntity{
	
	private static final long serialVersionUID = -6335897849644090133L;
	
	/**
	 * 字段xpath
	 */
	@Column(name = "XPATH",length=4)
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
	 * 记录模板类型
	 */
	@Column(name = "MESGTYPE_CODE") 
	private String mesgTypeCode;
	
	/**
	 * 创建者
	 */
	@Column(name = "CREATED_BY") 
	private String createdBy;

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

	public String getMesgTypeCode() {
		return mesgTypeCode;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setMesgTypeCode(String mesgTypeCode) {
		this.mesgTypeCode = mesgTypeCode;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { "mesgType"};
	}
}

