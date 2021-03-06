/**
 * 
 */
package org.packet.packetsimulation.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

/**
 * @author hbikai
 *
 */
@Entity 
@Table(name = "MESG") 
public class Mesg extends KoalaAbstractEntity{

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5947299243827448177L;

	@ManyToOne
	@JoinColumn(name = "PACKET_ID")
	private Packet packet;
	
	@OneToOne
    @JoinColumn(name = "MESGTYPE_ID")
	private MesgType mesgType;
	
	@Column(name = "CONTENT",length=5000)
	private String content;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "MESGID")
	private String mesgId;
//	
//	@Column(name = "MESG_PRIORITY")
//	private String mesgPriority;
//	
//	@Column(name = "MESG_DIRECTION")
//	private String mesgDirection;
//	
//	@Column(name = "RESERVE")
//	private String reserve;
	
	public String getMesgId() {
		return mesgId;
	}

	public void setMesgId(String mesgId) {
		this.mesgId = mesgId;
	}

//	public String getMesgPriority() {
//		return mesgPriority;
//	}
//
//	public void setMesgPriority(String mesgPriority) {
//		this.mesgPriority = mesgPriority;
//	}
//
//	public String getMesgDirection() {
//		return mesgDirection;
//	}
//
//	public void setMesgDirection(String mesgDirection) {
//		this.mesgDirection = mesgDirection;
//	}
//
//	public String getReserve() {
//		return reserve;
//	}
//
//	public void setReserve(String reserve) {
//		this.reserve = reserve;
//	}

	@Override
	public String[] businessKeys() {
		return null;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public MesgType getMesgType() {
		return mesgType;
	}

	public void setMesgType(MesgType mesgType) {
		this.mesgType = mesgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
