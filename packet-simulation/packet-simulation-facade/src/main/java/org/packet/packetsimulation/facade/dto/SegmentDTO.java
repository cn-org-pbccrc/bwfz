package org.packet.packetsimulation.facade.dto;

import java.util.Date;

import javax.persistence.Column;

import org.packet.packetsimulationGeneration.core.domain.Record;

import java.io.Serializable;

public class SegmentDTO implements Serializable {

	private Long id;

	private int version;

	private Long recordId;
	
	private String content;
	
	private String segMark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
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
	
}