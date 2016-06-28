package org.packet.packetsimulation.core.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;
import org.openkoala.koala.commons.domain.KoalaBaseEntity;

@Entity 
@Table(name = "DICT_TYPE")
public class DictType extends KoalaAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7421925841232785214L;

//	@Column(name = "DICT_ID")
//	private long dictId;
//	
//	public long getDictId() {
//		return dictId;
//	}
//
//	public void setDictId(long dictId) {
//		this.dictId = dictId;
//	}


	@Column(name = "UP_DICT_ID")
	private long upDictId;
	
	@Column(name = "DICT_NAME")
	private String dictName;
	
	@Column(name = "DICT_CLASSIFY")
	private long dictClassify;
	
	@Column(name = "CREATE_USER_ID")
	private long createUserId;
	
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "MEND_USER_ID")
	private long mendUserId;
	
	@Column(name = "MEND_TIME")
	private Date mendTime;

	@Column(name = "REMARK")
	private String remark;
	
	// 获取对应的字典数据项
     @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	 @JoinColumn(name="DICT_ID")
	 private List<DictItem> dictItemSet;

     
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public long getUpDictId() {
		return upDictId;
	}

	public void setUpDictId(long upDictId) {
		this.upDictId = upDictId;
	}

	public long getDictClassify() {
		return dictClassify;
	}

	public void setDictClassify(long dictClassify) {
		this.dictClassify = dictClassify;
	}

	public long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getMendUserId() {
		return mendUserId;
	}

	public void setMendUserId(long mendUserId) {
		this.mendUserId = mendUserId;
	}

	public Date getMendTime() {
		return mendTime;
	}

	public void setMendTime(Date mendTime) {
		this.mendTime = mendTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<DictItem> getDictItemSet() {
		return dictItemSet;
	}


	public void setDictItemSet(List<DictItem> dictItemSet) {
		this.dictItemSet = dictItemSet;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	   /**
     * 重写删除
     * 将该类型所有子类全部删除
     */
	@Override
    public void remove() {
		//this.
        getRepository().remove(this);
    }

}
