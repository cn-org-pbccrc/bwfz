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

	@OneToOne
    @JoinColumn(name = "RECORDTYPE_ID")
	private RecordType recordType;
	
	@Column(name = "CONTENT",length=5000)
	private String content;
	
	@Column(name = "RECORD_NAME")
	private String recordName;
	
	@Column(name = "CREATE_BY")
	private String createdBy;
	

	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createBy) {
		this.createdBy = createBy;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	@Override
	public String[] businessKeys() {
		return null;
	}
}
