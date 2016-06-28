package org.packet.packetsimulation.core.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

@Entity 
@Table(name = "DICT_ITEM")
public class DictItem extends KoalaAbstractEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2271747116081628318L;

//	@Column(name = "DICT_ITEM_ID")
//	private long dictItemId;
	
//	public long getDictItemId() {
//		return dictItemId;
//	}
//
//	public void setDictItemId(long dictItemId) {
//		this.dictItemId = dictItemId;
//	}

	@Column(name = "DICT_ID")
	private long dictId;
	
	@Column(name = "DICT_ITEM_CODE")
	private String dictItemCode;
	
	@Column(name = "DICT_ITEM_NAME")
	private String dictItemName;
	
	@Column(name = "ITEM_SORT")
	private int itemSort;
	
	@Column(name = "DEL_FLAG")
	private String delFlag;

	@Column(name = "REMARK")
	private String remark;
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getDictId() {
		return dictId;
	}

	public void setDictId(long dictId) {
		this.dictId = dictId;
	}

	public String getDictItemCode() {
		return dictItemCode;
	}

	public void setDictItemCode(String dictItemCode) {
		this.dictItemCode = dictItemCode;
	}

	public String getDictItemName() {
		return dictItemName;
	}

	public void setDictItemName(String dictItemName) {
		this.dictItemName = dictItemName;
	}

	public int getItemSort() {
		return itemSort;
	}

	public void setItemSort(int itemSort) {
		this.itemSort = itemSort;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public void remove(){
		 // 将del_flag设置为1即为删除
		this.setDelFlag("1");
		super.save();
	}
	
	@Override
	 public void save() {
		if(this.getDelFlag()==null){
			this.setDelFlag("0");
		}
		super.save();
    }
}
