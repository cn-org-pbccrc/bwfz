package org.packet.packetsimulation.facade;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.core.domain.MessageHead;
import org.xml.sax.SAXException;

public interface AnalyzeFacade {
	
	public Page<MessageHead> showAnalyze(String pathOfMesg,int currentPage,int pageSize) throws IOException;
	
	public InvokeResult getAnalyze(int id,String pathOfMesg) throws IOException, ParserConfigurationException, SAXException;
	
	public InvokeResult getOriginXml(int id, String pathOfMesg) throws IOException, Exception;

}
