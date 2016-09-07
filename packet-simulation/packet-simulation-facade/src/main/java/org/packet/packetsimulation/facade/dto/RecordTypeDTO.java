package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.packet.packetsimulationGeneration.core.domain.RecordItem;
import org.packet.packetsimulationGeneration.facade.dto.RecordItemDTO;

public class RecordTypeDTO implements Serializable {

	private static final long serialVersionUID = -3725891089203311177L;

	private Long id;

	private int version;

	private String transform;

	private String recordTemp;

	private String createdBy;

	private String code;

	private String recordType;
	
	private String fileType;

	/**
	 * 数据项
	 */
	private List<RecordItemDTO> headerItems = new ArrayList<RecordItemDTO>();

	public List<RecordItemDTO> getHeaderItems() {
		return headerItems;
	}

	public void setHeaderItems(List<RecordItemDTO> headerItems) {
		this.headerItems = headerItems;
	}

	public void setTransform(String transform) {
		this.transform = transform;
	}

	public String getTransform() {
		return this.transform;
	}

	public void setRecordTemp(String recordTemp) {
		this.recordTemp = recordTemp;
	}

	public String getRecordTemp() {
		return this.recordTemp;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getRecordType() {
		return this.recordType;
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

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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
		RecordTypeDTO other = (RecordTypeDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}