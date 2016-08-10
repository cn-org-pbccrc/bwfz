package org.packet.packetsimulation.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

@Entity 
@Table(name = "Config")
public class Config extends KoalaAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2678785437422360453L;

	@Column(name = "NAME")
	private String name;
	
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "PARAM")
	private String param;
	
	
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getParam() {
		return param;
	}



	public void setParam(String param) {
		this.param = param;
	}

}
