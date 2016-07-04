package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.packet.packetsimulationGeneration.facade.dto.RecordItemDTO;

public class RecordSegmentDTO implements Serializable {

	private Long id;

	private int version;

	private RecordTypeDTO recordTypeDTO;
	
	private String segMark;

	private String segDesc;

	private String state;

	private String segName;

	private Integer segLength;

	private String appearTimes;
	
	/**
	 * 数据项
	 */
	private List<RecordItemDTO> recordItems = new ArrayList<RecordItemDTO>();

	public void setSegMark(String segMark) {
		this.segMark = segMark;
	}

	public String getSegMark() {
		return this.segMark;
	}

	public void setSegDesc(String segDesc) {
		this.segDesc = segDesc;
	}

	public String getSegDesc() {
		return this.segDesc;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return this.state;
	}

	public void setSegName(String segName) {
		this.segName = segName;
	}

	public String getSegName() {
		return this.segName;
	}

	public void setSegLength(Integer segLength) {
		this.segLength = segLength;
	}

	public Integer getSegLength() {
		return this.segLength;
	}

	public void setAppearTimes(String appearTimes) {
		this.appearTimes = appearTimes;
	}

	public String getAppearTimes() {
		return this.appearTimes;
	}

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

	public RecordTypeDTO getRecordTypeDTO() {
		return recordTypeDTO;
	}

	public void setRecordTypeDTO(RecordTypeDTO recordTypeDTO) {
		this.recordTypeDTO = recordTypeDTO;
	}

	public List<RecordItemDTO> getRecordItems() {
		return recordItems;
	}

	public void setRecordItems(List<RecordItemDTO> recordItems) {
		this.recordItems = recordItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecordSegmentDTO other = (RecordSegmentDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}