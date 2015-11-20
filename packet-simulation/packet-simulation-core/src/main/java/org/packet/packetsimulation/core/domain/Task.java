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
import org.openkoala.organisation.core.domain.Post;

@Entity 
@Table(name = "TASK") 
@NamedQueries({
	@NamedQuery(name = "getPacketCountOfTask", query = "select count(o) from TaskPacket o where o.task = :Task") })
public class Task extends KoalaAbstractEntity{
	
	
	private static final long serialVersionUID = -7392000927483920113L;
	
	@Column(name = "TASK_NAME")
	private String taskName;
	
	@Column(name = "SEND_CHANNEL")
	private String sendChannel;
	
	@Column(name = "SET_TIME")
	private String setTime;
	
	@Column(name = "TASK_CREATOR")
	private String taskCreator;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "TASK_CREATEDTIME")
	private Date taskCreatedTime;
	
	@Column(name = "TASK_STATUS")
	private String taskStatus;
	
	@Column(name = "PACKET_NUM")
	private String packetNum;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public String getTaskCreator() {
		return taskCreator;
	}

	public void setTaskCreator(String taskCreator) {
		this.taskCreator = taskCreator;
	}

	public Date getTaskCreatedTime() {
		return taskCreatedTime;
	}

	public void setTaskCreatedTime(Date taskCreatedTime) {
		this.taskCreatedTime = taskCreatedTime;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getPacketNum() {
		return packetNum;
	}

	public void setPacketNum(String packetNum) {
		this.packetNum = packetNum;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获得某个任职某个岗位的员工人数
	 * 
	 * @param post
	 * @param date
	 * @return
	 */
	public static Long getPacketCountOfTask(Task task) {
		return getRepository().createNamedQuery("getPacketCountOfTask").addParameter("Task", task).singleResult();
	}

}
