/**
 * 
 */
package org.packet.packetsimulation.core.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;
import org.packet.packetsimulationGeneration.core.domain.Record;

/**
 * @author jcx
 *
 */
@Entity 
@Table(name = "SEGMENT") 
public class Segment extends KoalaAbstractEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3217299244567448789L;

	@ManyToOne
	@JoinColumn(name = "RECORD_ID")
	private Record record;
	
	@Column(name = "CONTENT",length=5000)
	private String content;
	
	@Column(name = "SEGMARK")
	private String segMark;

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSegMark() {
		return segMark;
	}

	public void setSegMark(String segMark) {
		this.segMark = segMark;
	}

	@Override
	public String[] businessKeys() {
		return null;
	}
}
