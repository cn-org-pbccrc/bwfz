package org.packet.packetsimulation.facade.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.utils.Page;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.openkoala.gqc.infra.util.ReadAppointedLine;
import org.openkoala.gqc.infra.util.XmlNode;
import org.openkoala.gqc.infra.util.XmlUtil;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.core.domain.MessageHead;
import org.packet.packetsimulation.core.domain.PacketsHead;
import org.packet.packetsimulation.facade.AnalyzeFacade;
import org.packet.packetsimulation.facade.dto.ProjectDTO;
import org.packet.packetsimulation.facade.impl.assembler.ProjectAssembler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Named
public class AnalyzeFacadeImpl implements AnalyzeFacade {
	public Page<MessageHead> showAnalyze(String pathOfMesg,int currentPage,int pageSize) throws IOException{
		List<MessageHead> list = new ArrayList<MessageHead>();
		if(pathOfMesg==null||"".equals(pathOfMesg))
			return new Page(currentPage, pageSize, list);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathOfMesg)));  
		int id = 1;
//			for (String line = br.readLine(); line != null; line = br.readLine()) {
//		    	if(line.substring(0,3).equals("[A:")){
//		    		
//		    		PacketsHead packetsHead = new PacketsHead();
//		    		packetsHead.setOrgId(line.substring(3,21));
//		    		String date = line.substring(21,35);
//		    		String dateformat = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);	    	
//		    		packetsHead.setPacDt(dateformat);
//		    		packetsHead.setPacID(line.substring(35,45));
//		    		packetsHead.setMesgNum(line.substring(45,55));
//		    		
//		    	}
//		    	if(line.substring(0,3).equals("{H:")){
//		    		
//		    		MessageHead messageHead = new MessageHead();
//		    		messageHead.setId(id);
//		    		id++;
//		    		messageHead.setRecType(line.substring(3,18));
//		    		messageHead.setRecUuid(line.substring(18,50));
//		    		messageHead.setXml(line.substring(64,line.length()));
//		    		list.add(messageHead);
//		    	}	    	
//		    }
		int totalLines = ReadAppointedLine.getTotalLines(new File(pathOfMesg));
		//String firstLine = ReadAppointedLine.readAppointedLineNumber(new File(pathOfMesg),currentPage*pageSize+1,totalLines);
		
		for(int a=currentPage*pageSize+1;a<=currentPage*pageSize+pageSize;a++){
			if(a+1<=totalLines){
				String line = ReadAppointedLine.readAppointedLineNumber(new File(pathOfMesg),a+1,totalLines);
				MessageHead messageHead = new MessageHead();
	    		messageHead.setId(a);
	    		messageHead.setXml(line.substring(0,line.length()));
	    		list.add(messageHead);
			}else{
				break;
			}
			
		}
	    return new Page<MessageHead>(currentPage*pageSize,totalLines-1,pageSize, list);
	}

	public InvokeResult getAnalyze(int id,String pathOfMesg) throws IOException, ParserConfigurationException, SAXException {
		int totalLines = ReadAppointedLine.getTotalLines(new File(pathOfMesg));
		String appointedLine = ReadAppointedLine.readAppointedLineNumber(new File(pathOfMesg),id+1,totalLines);
		int lineLength = appointedLine.length();
		String xmlContent = appointedLine.substring(0,lineLength);
		//String a = "<?xml version='1.0' encoding='UTF-8'?><Document xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'xsi:noNamespaceSchemaLocation='file:/C:/daec.001.001.01-en.xsd'><SceIdInf><SceId>00000</SceId></SceIdInf><ContIdInf><CtrctNbInt>CtrctNbInt0</CtrctNbInt><CdOfDtaPdgOgztn>00000000000000</CdOfDtaPdgOgztn></ContIdInf><ConSumInf><CtrctNbTe>CtrctNbTe0</CtrctNbTe><InfmRepDt>2006-05-04</InfmRepDt><Inf><Nm>Nm0</Nm><IDNb>IDNb0</IDNb><IDTp>0</IDTp></Inf><Inf><Nm>Nm1</Nm><IDNb>IDNb1</IDNb><IDTp>0</IDTp></Inf><CtrctMatrDt>2006-05-04</CtrctMatrDt><CtrctEfctDt>2006-05-04</CtrctEfctDt><CrdtLmtCurcy>AAA</CrdtLmtCurcy><CrdtLmtAdjDt>2006-05-04</CrdtLmtAdjDt><CrdtLmtAmt>49999999.50</CrdtLmtAmt><CrdtLmtReLo>1</CrdtLmtReLo><CtrctSts>1</CtrctSts><CtrctStsChgDt>2006-05-04</CtrctStsChgDt></ConSumInf></Document>";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlContent)));
		//获得根元素结点  
		Element root = ((org.w3c.dom.Document) doc).getDocumentElement(); 
		XmlNode xmlNode = new XmlNode();
		xmlNode=XmlUtil.parseElement(root,xmlNode,null); 
		String ss = xmlNode.toHtmlTabString("账户信息正常报送记录");
		return InvokeResult.success(ss);
	}
	
	public InvokeResult getOriginXml(int id, String pathOfMesg) throws Exception {
		// TODO Auto-generated method stub
		int totalLines = ReadAppointedLine.getTotalLines(new File(pathOfMesg));
		String appointedLine = ReadAppointedLine.readAppointedLineNumber(new File(pathOfMesg),id+1,totalLines);
		int lineLength = appointedLine.length();
		String xmlContent = appointedLine.substring(0,lineLength);
		//String headOfRecord = appointedLine.substring(0,64);
		
		String xmlFormat = formatXml(xmlContent);
		//System.out.println(headOfRecord+"\n"+xmlFormat);
		return InvokeResult.success(xmlFormat);
	}
	
	 public static String formatXml(String str) throws Exception {
		  org.dom4j.Document document = null;
		  document = DocumentHelper.parseText(str);
		  // 格式化输出格式
		  OutputFormat format = OutputFormat.createPrettyPrint();
		  format.setEncoding("gb2312");
		  StringWriter writer = new StringWriter();
		  // 格式化输出流
		  XMLWriter xmlWriter = new XMLWriter(writer, format);
		  // 将document写入到输出流
		  xmlWriter.write(document);
		  xmlWriter.close();
		  return writer.toString();
		 }

}
