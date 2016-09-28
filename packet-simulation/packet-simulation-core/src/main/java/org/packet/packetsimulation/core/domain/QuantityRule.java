package org.packet.packetsimulation.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.dayatang.domain.ValueObject;

/**
 * @author jcx
 *
 */

@Embeddable
public class QuantityRule  implements ValueObject{
	
	private static final long serialVersionUID = -654497849648421933L;
	
	/**
	 * 字段中文名
	 */
	@Column(name = "CN_NAME")
	private String cnName;
	
	/**
	 * 所属段段标
	 */
	@Column(name = "SEG_MARK")
	private String segMark;
	
	/**
	 * 标识符
	 */
	@Column(name = "ITEM_ID")
	private String itemId;
	
	/**
	 * 数据项长度
	 */
	@Column(name = "ITEM_LENGTH")
	private Integer itemLength;
	
	/**
	 * 数据项类型
	 */
	@Column(name = "ITEM_TYPE")
	private Integer itemType;
	
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

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
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

