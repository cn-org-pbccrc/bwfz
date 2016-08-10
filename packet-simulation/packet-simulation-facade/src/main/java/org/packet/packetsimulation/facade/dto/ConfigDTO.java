package org.packet.packetsimulation.facade.dto;

import java.io.Serializable;

public class ConfigDTO implements Serializable {

	private Long id;

	private int version;

			
		private String param;
		
				
		private String name;
		
				
		private String type;
		
			
	
	public void setParam(String param) { 
		this.param = param;
	}

	public String getParam() {
		return this.param;
	}
		
			
	
	public void setName(String name) { 
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
		
			
	
	public void setType(String type) { 
		this.type = type;
	}

	public String getType() {
		return this.type;
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
		ConfigDTO other = (ConfigDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}