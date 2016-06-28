package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;

public class DictItemDTO implements Serializable {

	private Long id;

	private int version;

							
		private Long dictId;
		
				
		private String remark;
		
								
		private Integer itemSort;
		
				
		private String dictItemName;
		
				
		private String dictItemCode;
		
				
		private String delFlag;
		
							
	
	public void setDictId(Long dictId) { 
		this.dictId = dictId;
	}

	public Long getDictId() {
		return this.dictId;
	}
		
			
	
	public void setRemark(String remark) { 
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}
		
							
	
	public void setItemSort(Integer itemSort) { 
		this.itemSort = itemSort;
	}

	public Integer getItemSort() {
		return this.itemSort;
	}
		
			
	
	public void setDictItemName(String dictItemName) { 
		this.dictItemName = dictItemName;
	}

	public String getDictItemName() {
		return this.dictItemName;
	}
		
			
	
	public void setDictItemCode(String dictItemCode) { 
		this.dictItemCode = dictItemCode;
	}

	public String getDictItemCode() {
		return this.dictItemCode;
	}
		
			
	
	public void setDelFlag(String delFlag) { 
		this.delFlag = delFlag;
	}

	public String getDelFlag() {
		return this.delFlag;
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
		DictItemDTO other = (DictItemDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}