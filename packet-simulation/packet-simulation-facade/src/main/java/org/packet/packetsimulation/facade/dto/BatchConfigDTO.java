package org.packet.packetsimulation.facade.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

import org.openkoala.gqc.facade.dto.PreQueryConditionDTO;
import org.packet.packetsimulation.core.domain.MesgType;

public class BatchConfigDTO implements Serializable {

	private static final long serialVersionUID = -5720499292951007981L;

	private Long id;

	private int version;

	private String createdBy;
	
	private Date createDate;
	
	private Date createDateEnd;
	
	private MesgTypeDTO mesgTypeDTO;
	
	/**
	 * 静态查询条件。
	 */
	private List<BatchRuleDTO> BatchRules = new ArrayList<BatchRuleDTO>();
			
	
	public List<BatchRuleDTO> getBatchRules() {
		return BatchRules;
	}

	public void setBatchRules(List<BatchRuleDTO> batchRules) {
		BatchRules = batchRules;
	}

	public MesgTypeDTO getMesgTypeDTO() {
		return mesgTypeDTO;
	}

	public void setMesgTypeDTO(MesgTypeDTO mesgTypeDTO) {
		this.mesgTypeDTO = mesgTypeDTO;
	}

	public void setCreatedBy(String createdBy) { 
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}
		
	public void setCreateDate(Date createDate) { 
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}
		
		public void setCreateDateEnd(Date createDateEnd) { 
		this.createDateEnd = createDateEnd;
	}

	public Date getCreateDateEnd() {
		return this.createDateEnd;
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
		BatchConfigDTO other = (BatchConfigDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}