package org.packet.packetsimulation.core.domain;

public class PACKETCONSTANT {
	
	/** taskPacket类型-普通 */
	public static final Integer TASKPACKET_TASKPACKETSTATE_NORMAL=0;
	/** taskPacket类型-快速 */
	public static final Integer TASKPACKET_TASKPACKETSTATE_EASYPACKET=1;
	
	/** 文件来源-内部 */
	public static final Integer TASKPACKET_PACKETFROM_INSIDE=0;
	/** 文件来源-外部 */
	public static final Integer TASKPACKET_PACKETFROM_OUTSIDE=1;
	/** 文件来源-快速发送 */
	public static final Integer TASKPACKET_PACKETFROM_EASYSEND=2;
	
	/** 数据类型-正常 */
	public static final Integer TASKPACKET_DATATYPE_NORMAL=0;
	
	/** 传输方向-上报 */
	public static final Integer TASKPACKET_TRANSPORTDIRECTION_REPORT=0;
	/** 传输方向-反馈 */
	public static final Integer TASKPACKET_TRANSPORTDIRECTION_FEEDBACK=1;
	

	
	
	
	
	/** 文件头起始标识*/
	public static final String HEADER_START="A";
	/** 文件格式版本号*/
	public static final String TASKPACKET_FILEVERSION="1.0";
	/** 预留字段*/
	public static final String HEADER_RESERVED="                              ";
	
	
	
	
	
}
