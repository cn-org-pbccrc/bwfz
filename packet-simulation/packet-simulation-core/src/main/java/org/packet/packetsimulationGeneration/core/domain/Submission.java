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
 * @author jcx
 *
 */
@Entity 
@Table(name = "SUBMISSION") 
public class Submission extends KoalaAbstractEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1875299243827454177L;

//	@OneToOne
//    @JoinColumn(name = "RECORDTYPE_ID")
//	private RecordType recordType;
	
	@Column(name = "CONTENT",length=5000)
	private String content;
	
	@Column(name = "NAME")
	private String Name;
	
	@Column(name = "CREATE_BY")
	private String createdBy;
	
	@Column(name = "RECORD_NUM")
	private String recordNum;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}

	@Override
	public String[] businessKeys() {
		return null;
	}
}
