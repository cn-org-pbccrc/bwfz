package org.packet.packetsimulation.facade.dto;

import java.util.Date;
import java.io.Serializable;

public class ThreeStandardDTO implements Serializable {

	private Long id;

	private int version;

			
		private String organizationCode;
		
				
		private String credentialNumber;
		
				
		private String createdBy;
		
				
		private String customerCode;
		
				
		private String name;
		
				
		private String credentialType;
		
				
		private Date createdDate;
		
		private Date createdDateEnd;
			
	
	public void setOrganizationCode(String organizationCode) { 
		this.organizationCode = organizationCode;
	}

	public String getOrganizationCode() {
		return this.organizationCode;
	}
		
			
	
	public void setCredentialNumber(String credentialNumber) { 
		this.credentialNumber = credentialNumber;
	}

	public String getCredentialNumber() {
		return this.credentialNumber;
	}
		
			
	
	public void setCreatedBy(String createdBy) { 
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}
		
			
	
	public void setCustomerCode(String customerCode) { 
		this.customerCode = customerCode;
	}

	public String getCustomerCode() {
		return this.customerCode;
	}
		
			
	
	public void setName(String name) { 
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
		
			
	
	public void setCredentialType(String credentialType) { 
		this.credentialType = credentialType;
	}

	public String getCredentialType() {
		return this.credentialType;
	}
		
			
	
	public void setCreatedDate(Date createdDate) { 
		this.createdDate = createdDate;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}
		
		public void setCreatedDateEnd(Date createdDateEnd) { 
		this.createdDateEnd = createdDateEnd;
	}

	public Date getCreatedDateEnd() {
		return this.createdDateEnd;
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
		ThreeStandardDTO other = (ThreeStandardDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}