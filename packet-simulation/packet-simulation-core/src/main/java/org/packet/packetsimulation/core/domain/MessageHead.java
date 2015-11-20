package org.packet.packetsimulation.core.domain;

import java.util.Date;

public class MessageHead {
	private int id;
	
	private String xml;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}


	
}
