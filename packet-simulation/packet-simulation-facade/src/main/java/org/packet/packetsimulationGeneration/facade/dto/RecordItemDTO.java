package org.packet.packetsimulationGeneration.facade.dto;

import java.io.Serializable;

public class RecordItemDTO implements Serializable{

	private static final long serialVersionUID = 355086288094682957L;
	
	/**
	 * 标志符
	 */
	private String itemId;
	
	/**
	 * 数据项名称
	 */
	private String itemName;
	
	/**
	 * 数据项类型
	 */
	private String itemType;
	
	/**
	 * 数据项长度
	 */
	private Integer itemLength;
	
	/**
	 * 数据项位置
	 */
	private String itemLocation;
	
	/**
	 * 描述
	 */
	private String itemDesc;
	
	/**
	 * 状态
	 */
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
