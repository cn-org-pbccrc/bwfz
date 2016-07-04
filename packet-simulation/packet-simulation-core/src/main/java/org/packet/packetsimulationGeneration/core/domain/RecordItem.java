package org.packet.packetsimulationGeneration.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.dayatang.domain.ValueObject;

/**
 * 数据项定义
 * @author charles
 *
 */
@Embeddable
public class RecordItem implements ValueObject{

	private static final long serialVersionUID = 6822453358648226776L;
	
	/**
	 * 标志符
	 */
	@Column(name = "ITEM_ID")
	private String itemId;
	
	/**
	 * 数据项名称
	 */
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	/**
	 * 数据项类型
	 */
	@Column(name = "ITEM_TYPE")
	private String itemType;
	
	/**
	 * 数据项长度
	 */
	@Column(name = "ITEM_LENGTH")
	private Integer itemLength;
	
	/**
	 * 数据项位置
	 */
	@Column(name = "ITEM_LOCATION")
	private String itemLocation;
	
	/**
	 * 描述
	 */
	@Column(name = "ITEM_DESC")
	private String itemDesc;
	
	/**
	 * 状态
	 */
	@Column(name = "STATE") 
	private String state;

	public String getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public Integer getItemLength() {
		return itemLength;
	}

	public String getItemLocation() {
		return itemLocation;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public String getState() {
		return state;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setItemLength(Integer itemLength) {
		this.itemLength = itemLength;
	}

	public void setItemLocation(String itemLocation) {
		this.itemLocation = itemLocation;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
