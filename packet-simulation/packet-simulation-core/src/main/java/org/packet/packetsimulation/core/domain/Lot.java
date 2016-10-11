package org.packet.packetsimulation.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

@Entity 
@Table(name = "LOT") 
@NamedQueries({
	@NamedQuery(name = "getSubmissionCountOfLot", query = "select count(o) from LotSubmission o where o.lot = :Lot") 
})
public class Lot extends KoalaAbstractEntity{
	private static final long serialVersionUID = -7393479817483920113L;
	
	@Column(name = "LOT_NAME")
	private String lotName;
	
	@Column(name = "SEND_CHANNEL")
	private String sendChannel;
	
	@Column(name = "SET_TIME")
	private String setTime;
	
	@Column(name = "LOT_CREATOR")
	private String lotCreator;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "LOT_CREATEDTIME")
	private Date lotCreatedTime;
	
	@Column(name = "LOT_STATUS")
	private String lotStatus;
	
	@Column(name = "SUBMISSION_NUM")
	private String submissionNum;

	@Column(name = "TYPE")
	private Integer type;
	
	public String getLotName() {
		return lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}

	public String getSendChannel() {
		return sendChannel;
	}

	public void setSendChannel(String sendChannel) {
		this.sendChannel = sendChannel;
	}

	public String getSetTime() {
		return setTime;
	}

	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}

	public String getLotCreator() {
		return lotCreator;
	}

	public void setLotCreator(String lotCreator) {
		this.lotCreator = lotCreator;
	}

	public Date getLotCreatedTime() {
		return lotCreatedTime;
	}

	public void setLotCreatedTime(Date lotCreatedTime) {
		this.lotCreatedTime = lotCreatedTime;
	}

	public String getLotStatus() {
		return lotStatus;
	}

	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}

	public String getSubmissionNum() {
		return submissionNum;
	}

	public void setSubmissionNum(String submissionNum) {
		this.submissionNum = submissionNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Long getSubmissionCountOfLot(Lot lot) {
		return getRepository().createNamedQuery("getSubmissionCountOfLot").addParameter("Lot", lot).singleResult();
	}
}
