/**
 * 
 */
package org.packet.packetsimulation.core.domain;

import java.util.Date;
//import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;
import org.openkoala.security.core.PacketNameIsExistedException;
import org.openkoala.security.core.UserAccountIsExistedException;
import org.openkoala.security.core.domain.User;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author keji
 *
 */
@Entity 
@Table(name = "PACKET")
@NamedQueries({
	@NamedQuery(name = "getMesgCountOfPacket", query = "select count(o) from Mesg o where o.packet = :Packet") })
public class Packet extends KoalaAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6156533878962292627L;
	
	@Column(name = "PACKET_NAME")
	private String packetName;
	
	@Column(name = "FILE_VERSION")
	private String fileVersion;

	@Column(name = "PACKID")
	private String packId;

	@Column(name = "ORIG_SENDER")
	private String origSender;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "ORIG_SEND_DATE")
	private Date origSendDate;
	
//	@Temporal(TemporalType.DATE)
//	@Column(name = "CREATEDAT")
//	private Date createdAt;
	
	@Column(name = "CREATEDBY")
	private String createdBy;
	
//	private Set<Mesg> mesgs = new HashSet<Mesg>();
	@Column(name = "DATATYPE")
	private String dataType;

	@Column(name = "RECORDTYPE")
	private String recordType;
	
	@Column(name = "MESGNUM")
	private String mesgNum;
	
	@Column(name = "RESERVE")
	private String reserve;
	
	@Column(name = "FRONT_POSITION")
	private String frontPosition;
	
	@Column(name = "SERIAL_NUMBER")
	private Integer serialNumber;
//	public Date getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(Date createdAt) {
//		this.createdAt = createdAt;
//	}
	
	public Packet() {
	}
	  
    public Packet(String packetName) {
        //checkUserAccount(userAccount);
        isExistPacketName(packetName);
        this.packetName = packetName;
    }
    
//    public static Packet getByFrontPosition(String frontPosition) {
//        return getRepository()
//                .createCriteriaQuery(FileName.class)
//                .eq("frontPosition", frontPosition)
//                .singleResult();
//    }

	public String getFrontPosition() {
		return frontPosition;
	}

	public void setFrontPosition(String frontPosition) {
		this.frontPosition = frontPosition;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPacketName() {
		return packetName;
	}


	public void setPacketName(String packetName) {
		this.packetName = packetName;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRecordType() {
		return recordType;
	}


	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
//	public Set<Mesg> getMesgs() {
//		return mesgs;
//	}
//
//
//	public void setMesgs(Set<Mesg> mesgs) {
//		this.mesgs = mesgs;
//	}


//	@Override
//	public String[] businessKeys() {
//		return new String[] { "mesgId" };
//	}

	public String getPackId() {
		return packId;
	}


	public void setPackId(String packId) {
		this.packId = packId;
	}


	public String getOrigSender() {
		return origSender;
	}


	public void setOrigSender(String origSender) {
		this.origSender = origSender;
	}


	public Date getOrigSendDate() {
		return origSendDate;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setOrigSendDate(Date origSendDate) {
		this.origSendDate = origSendDate;
	}

	public String getFileVersion() {
		return fileVersion;
	}


	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}


	public String getDataType() {
		return dataType;
	}


	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	public String getMesgNum() {
		return mesgNum;
	}


	public void setMesgNum(String mesgNum) {
		this.mesgNum = mesgNum;
	}


	public String getReserve() {
		return reserve;
	}


	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	private void isExistPacketName(String packetName) {
		if (getByPacketName(packetName) != null) {
			throw new PacketNameIsExistedException("packet packetName is existed.");
	    }
	}
	
	public boolean verify(String packetName) {
		if (getByPacketName(packetName) != null) {
			return true;
	    }
		return false;
	}
	
    public static Packet getByPacketName(String packetName) {
        return getRepository()
                .createCriteriaQuery(Packet.class)
                .eq("packetName", packetName)
                .singleResult();
    }
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Long getMesgCountOfPacket(Packet packet) {
		return getRepository().createNamedQuery("getMesgCountOfPacket").addParameter("Packet", packet).singleResult();
	}

}
