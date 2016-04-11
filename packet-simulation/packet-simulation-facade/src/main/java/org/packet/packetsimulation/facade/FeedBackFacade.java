package org.packet.packetsimulation.facade;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.core.domain.MessageHead;
import org.xml.sax.SAXException;

public interface FeedBackFacade {
	
	public Page<MessageHead> showAnalyze(Long taskPacketId,int currentPage,int pageSize,String ctxPath) throws IOException;
	
	public InvokeResult getAnalyze(int id, Long taskPacketId, String ctxPath) throws IOException, ParserConfigurationException, SAXException;
	
	public InvokeResult getOriginXml(int id, Long taskPacketId, String ctxPath) throws IOException, Exception;

	public String getPacketHeadForSend(String code, String userAccount);
}
