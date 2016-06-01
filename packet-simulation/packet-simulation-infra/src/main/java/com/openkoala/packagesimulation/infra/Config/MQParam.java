package com.openkoala.packagesimulation.infra.Config;

public class MQParam {
	private String  mqAddress;
	private int mqPort;
	private int mqCCISID;
	private String mqChannel;
	private String mqQueueManager;
	private String mqQueue;
	public String getMqAddress() {
		return mqAddress;
	}
	public void setMqAddress(String mqAddress) {
		this.mqAddress = mqAddress;
	}
	public int getMqPort() {
		return mqPort;
	}
	public void setMqPort(int mqPort) {
		this.mqPort = mqPort;
	}
	public int getMqCCISID() {
		return mqCCISID;
	}
	public void setMqCCISID(int mqCCISID) {
		this.mqCCISID = mqCCISID;
	}
	public String getMqChannel() {
		return mqChannel;
	}
	public void setMqChannel(String mqChannel) {
		this.mqChannel = mqChannel;
	}
	public String getMqQueueManager() {
		return mqQueueManager;
	}
	public void setMqQueueManager(String mqQueueManager) {
		this.mqQueueManager = mqQueueManager;
	}
	public String getMqQueue() {
		return mqQueue;
	}
	public void setMqQueue(String mqQueue) {
		this.mqQueue = mqQueue;
	}
	
}