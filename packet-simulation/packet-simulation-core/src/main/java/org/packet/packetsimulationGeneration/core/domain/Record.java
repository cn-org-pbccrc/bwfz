/**
 * 
 */
package org.packet.packetsimulationGeneration.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

/**
 * @author charles
 *
 */
@Entity 
@Table(name = "RECORD") 
public class Record extends KoalaAbstractEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5947299243827448177L;

//	@ManyToOne
//	@JoinColumn(name = "RECORDFILE_ID")
//	private RecordFile recordFile;
//	
	@OneToOne
    @JoinColumn(name = "RECORDTYPE_ID")
	private RecordType recordType;
	
	@Column(name = "CONTENT",length=5000)
	private String content;
	
	@Column(name = "REMARK")
	private String remark;

	
	@Column(name = "CREATE_BY")
	private String createBy;
	
	@Column(name = "MESG_FROM")
	private Integer mesgFrom;

//	public RecordFile getRecordFile() {
//		return recordFile;
//	}
//
	public RecordType getRecordType() {
		return recordType;
	}

//	public void setRecordFile(RecordFile recordFile) {
//		this.recordFile = recordFile;
//	}
//
	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getMesgFrom() {
		return mesgFrom;
	}

	public void setMesgFrom(Integer mesgFrom) {
		this.mesgFrom = mesgFrom;
	}

	@Override
	public String[] businessKeys() {
		return null;
	}
}
