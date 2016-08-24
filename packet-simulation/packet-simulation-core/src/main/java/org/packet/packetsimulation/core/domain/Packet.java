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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	@Column(name = "ORIG_SENDER")
	private String origSender;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "ORIG_SEND_DATE")
	private Date origSendDate;
	
	@Column(name = "CREATEDBY")
	private String createdBy;
	
	@Column(name = "DATATYPE")
	private Integer dataType;
	
	@Column(name = "BIZTYPE")
	private String bizType;
	
	@Column(name = "RECORDTYPE")
	private String recordType;
	
	@Column(name = "MESGNUM")
	private Long mesgNum;
	
	@Column(name = "RESERVE")
	private String reserve;
	
	@ManyToOne
	@JoinColumn(name = "MISSION_ID")
	private Mission mission;
	
	public Packet() {
		
	}
	  
    public Packet(String packetName) {
        isExistPacketName(packetName);
        this.packetName = packetName;
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

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Long getMesgNum() {
		return mesgNum;
	}

	public void setMesgNum(Long mesgNum) {
		this.mesgNum = mesgNum;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	private void isExistPacketName(String packetName) {
		if (getByPacketName(packetName) != null) {
			throw new PacketNameIsExistedException("packet packetName is existed.");
	    }
	}
	
    public static Packet getByPacketName(String packetName) {
        return getRepository()
                .createCriteriaQuery(Packet.class)
                .eq("packetName", packetName)
                .singleResult();
    }
	
	public static Long getMesgCountOfPacket(Packet packet) {
		return getRepository().createNamedQuery("getMesgCountOfPacket").addParameter("Packet", packet).singleResult();
	}
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
}
