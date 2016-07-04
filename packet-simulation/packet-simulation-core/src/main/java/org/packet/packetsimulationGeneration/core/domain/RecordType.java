package org.packet.packetsimulationGeneration.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

/**
 * 一代报文类型定义
 * @author charles
 *
 */
@Entity 
@Table(name = "Record_Type") 
public class RecordType extends KoalaAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2078237222176724905L;

	/**
	 * 记录类型中文名
	 */
	@Column(name = "RECORD_TYPE") 
	private String recordType; 
	
	/**
	 * 记录类型代码（4位数字）
	 */
	@Column(name = "CODE",length=4)
	private String code;
	
	/**
	 * 记录基础模板
	 */
	@Column(name = "RECORD_TEMP",length=15000) 
	private String recordTemp;
	
	/**
	 * 创建者
	 */
	@Column(name = "CREATED_BY") 
	private String createdBy;
	
	/**
	 * 记录转换配置
	 */
	@Column(name = "TRANSFORM",length=5000) 
	private String transform;
	
//	/**
//	 * 段
//	 */
//	@ElementCollection(fetch = FetchType.EAGER)
//	@CollectionTable(name = "RECORD_SEGMENT", joinColumns = @JoinColumn(name = "RECORD_ID"))
//	@OrderColumn(name = "ORDER_COLUMN")
//	private List<RecordSegment> recordSegments = new ArrayList<RecordSegment>();
	
	public String getRecordType() {
		return recordType;
	}

	public String getCode() {
		return code;
	}

	public String getRecordTemp() {
		return recordTemp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getTransform() {
		return transform;
	}

//	public List<RecordSegment> getRecordSegments() {
//		return recordSegments;
//	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setRecordTemp(String recordTemp) {
		this.recordTemp = recordTemp;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setTransform(String transform) {
		this.transform = transform;
	}

//	public void setRecordSegments(List<RecordSegment> recordSegments) {
//		this.recordSegments = recordSegments;
//	}

	@Override
	public String[] businessKeys() {
		return new String[] { "recordType"};
	}

}
