package org.packet.packetsimulationGeneration.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.dayatang.domain.ValueObject;
import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

/**
 * 段定义
 * @author charles
 *
 */
@Entity 
@Table(name = "RECORD_SEGMENT") 
public class RecordSegment  extends KoalaAbstractEntity{

	
	private static final long serialVersionUID = -308955181754747584L;

	@ManyToOne
	@JoinColumn(name = "RECORDTYPE_ID")
	private RecordType recordType;
	
	/**
	 * 段名称
	 */
	@Column(name = "SEG_NAME")
	private String segName;
	
	/**
	 * 段标
	 */
	@Column(name = "SEG_MARK")
	private String segMark;
	
	/**
	 * 描述
	 */
	@Column(name = "SEG_DESC")
	private String segDesc;
	
	/**
	 * 出现次数
	 */
	@Column(name = "APPEAR_TIMES") 
	private String appearTimes; 
	
	/**
	 * 状态
	 */
	@Column(name = "STATE") 
	private String state;
	
	/**
	 * 段长度
	 */
	@Column(name = "SEG_LENGTH")
	private Integer segLength;
	
	/**
	 * 数据项
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "RECORD_ITEM", joinColumns = @JoinColumn(name = "SEG_ID"))
	@OrderColumn(name = "ORDER_COLUMN")
	private List<RecordItem> recordItems = new ArrayList<RecordItem>();

	public String getSegName() {
		return segName;
	}

	public String getSegMark() {
		return segMark;
	}


	public String getAppearTimes() {
		return appearTimes;
	}

	public String getState() {
		return state;
	}

	public Integer getSegLength() {
		return segLength;
	}

	public List<RecordItem> getRecordItems() {
		return recordItems;
	}

	public void setSegName(String segName) {
		this.segName = segName;
	}

	public void setSegMark(String segMark) {
		this.segMark = segMark;
	}


	public void setAppearTimes(String appearTimes) {
		this.appearTimes = appearTimes;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setSegLength(Integer segLength) {
		this.segLength = segLength;
	}

	public void setRecordItems(List<RecordItem> recordItems) {
		this.recordItems = recordItems;
	}

	public RecordType getRecordType() {
		return recordType;
	}

	public String getSegDesc() {
		return segDesc;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public void setSegDesc(String segDesc) {
		this.segDesc = segDesc;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { "id"};
	}
	
}
