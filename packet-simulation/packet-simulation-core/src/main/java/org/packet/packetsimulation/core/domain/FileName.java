package org.packet.packetsimulation.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

@Entity 
@Table(name = "FILE_NAME")
public class FileName extends KoalaAbstractEntity{
	
	private static final long serialVersionUID = 6156533216962292999L;
	
	@Column(name = "FRONT_POSITION")
	private String frontPosition;
	
	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;
	
	public FileName(){
		
	}
	
	public FileName(String frontPosition, String serialNumber) {
		this.frontPosition = frontPosition;
		this.serialNumber = serialNumber;
	}

	public String getFrontPosition() {
		return frontPosition;
	}

	public void setFrontPosition(String frontPosition) {
		this.frontPosition = frontPosition;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public static FileName getByFrontPosition(String frontPosition) {
        return getRepository()
                .createCriteriaQuery(FileName.class)
                .eq("frontPosition", frontPosition)
                .singleResult();
    }
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
}
