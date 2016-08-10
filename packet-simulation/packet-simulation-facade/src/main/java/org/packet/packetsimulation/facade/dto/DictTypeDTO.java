package org.packet.packetsimulation.facade.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.io.Serializable;

import org.packet.packetsimulation.core.domain.DictItem;

public class DictTypeDTO implements Serializable {

	private Long id;

	private int version;

			
		private Date createTime;
		
		//private Date createTimeEnd;
								
		private long upDictId;
		
				
		private String remark;
		
								
		private long dictClassify;
		
				
		private Date mendTime;
		
		//private Date mendTimeEnd;
								
		private Long mendUserId;
		
				
		private String dictName;
		
								
		private Long createUserId;
		
		// 每个数据字典类型对应多个数据字典项，用list保存是为了允许重复
		private List<DictItemDTO> dictItemSet;
			
	// 在数据字典类型的dto中，按照类似tree的结构保存字典类型，顶端的类型dto id设为-1，由于dto不入数据库，因此利用该顶端可以将
   // 所有的数据字典类型组织起来
	private Set<DictTypeDTO> childTypesSet;
	
	
	// 用来保存父节点
	//private DictTypeDTO parentType;
	
	public void setCreateTime(Date createTime) { 
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}
		
//		public void setCreateTimeEnd(Date createTimeEnd) { 
//		this.createTimeEnd = createTimeEnd;
//	}
//
//	public Date getCreateTimeEnd() {
//		return this.createTimeEnd;
//	}
							
	
	public void setUpDictId(Long upDictId) { 
		this.upDictId = upDictId;
	}

	public Long getUpDictId() {
		return this.upDictId;
	}
		
			
	
	public void setRemark(String remark) { 
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}
		
							
	
	public void setDictClassify(Long dictClassify) { 
		this.dictClassify = dictClassify;
	}

	public Long getDictClassify() {
		return this.dictClassify;
	}
		
			
	
	public void setMendTime(Date mendTime) { 
		this.mendTime = mendTime;
	}

	public Date getMendTime() {
		return this.mendTime;
	}
		
//		public void setMendTimeEnd(Date mendTimeEnd) { 
//		this.mendTimeEnd = mendTimeEnd;
//	}
//
//	public Date getMendTimeEnd() {
//		return this.mendTimeEnd;
//	}
							
	
	public void setMendUserId(Long mendUserId) { 
		this.mendUserId = mendUserId;
	}

	public Long getMendUserId() {
		return this.mendUserId;
	}
		
			
	
	public void setDictName(String dictName) { 
		this.dictName = dictName;
	}

	public String getDictName() {
		return this.dictName;
	}
		
							
	
	public void setCreateUserId(Long createUserId) { 
		this.createUserId = createUserId;
	}

	public Long getCreateUserId() {
		return this.createUserId;
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
		DictTypeDTO other = (DictTypeDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<DictItemDTO> getDictItemSet() {
		return dictItemSet;
	}

	public void setDictItemSet(List<DictItemDTO> dictItemSet) {
		this.dictItemSet = dictItemSet;
	}

	public Set<DictTypeDTO> getChildTypesSet() {
		return childTypesSet;
	}

	public void setChildTypesSet(Set<DictTypeDTO> childTypesSet) {
		this.childTypesSet = childTypesSet;
	}

//	public DictTypeDTO getParentType() {
//		return parentType;
//	}
//
//	public void setParentType(DictTypeDTO parentType) {
//		this.parentType = parentType;
//	}
}