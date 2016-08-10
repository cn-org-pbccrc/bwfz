package org.packet.packetsimulation.facade.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.openkoala.gqc.infra.util.ReadAppointedLine;
import org.openkoala.gqc.infra.util.XmlNode;
import org.openkoala.gqc.infra.util.XmlUtil;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.packet.packetsimulation.application.TaskPacketApplication;
import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.MessageHead;
import org.packet.packetsimulation.core.domain.PACKETCONSTANT;
import org.packet.packetsimulation.core.domain.Packet;
import org.packet.packetsimulation.core.domain.PacketsHead;
import org.packet.packetsimulation.core.domain.TaskPacket;
import org.packet.packetsimulation.facade.AnalyzeFacade;
import org.packet.packetsimulation.facade.FeedBackFacade;
import org.packet.packetsimulation.facade.dto.MesgDTO;
import org.packet.packetsimulation.facade.dto.ProjectDTO;
import org.packet.packetsimulation.facade.impl.assembler.MesgAssembler;
import org.packet.packetsimulation.facade.impl.assembler.ProjectAssembler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Named
public class FeedBackFacadeImpl implements FeedBackFacade {
	@Inject
	private TaskPacketApplication application;
	
	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
        if(queryChannel==null){
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
        }
        return queryChannel;
    }
	
	public Page<MessageHead> showAnalyze(Long taskPacketId,int currentPage,int pageSize,String ctxPath) throws IOException{
		TaskPacket taskPacket = application.getTaskPacket(taskPacketId);
		String path = ctxPath + taskPacket.getSelectedPacketName() + ".csv";
		List<MessageHead> list = new ArrayList<MessageHead>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));  
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
		int totalLines = ReadAppointedLine.getTotalLines(new File(path));
		//String firstLine = ReadAppointedLine.readAppointedLineNumber(new File(pathOfMesg),currentPage*pageSize+1,totalLines);
		
		for(int a=currentPage*pageSize+1;a<=currentPage*pageSize+pageSize;a++){
			if(a+1<=totalLines){
				String line = ReadAppointedLine.readAppointedLineNumber(new File(path),a+1,totalLines);
				MessageHead messageHead = new MessageHead();
	    		messageHead.setId(a);
	    		list.add(messageHead);
			}else{
				break;
			}
			
		}
	    return new Page<MessageHead>(currentPage*pageSize,totalLines-1,pageSize, list);
	}

	public InvokeResult getAnalyze(int id, Long taskPacketId, String ctxPath) throws IOException, ParserConfigurationException, SAXException {
		TaskPacket taskPacket = application.getTaskPacket(taskPacketId);
		MesgType mesgType = MesgType.getRepository().createCriteriaQuery(MesgType.class).eq("code", taskPacket.getSelectedRecordType()).singleResult();
		String path = ctxPath + taskPacket.getSelectedPacketName() + ".csv";
		int totalLines = ReadAppointedLine.getTotalLines(new File(path));
		String xmlContent = ReadAppointedLine.readAppointedLineNumber(new File(path),id+1,totalLines);
		//String a = "<?xml version='1.0' encoding='UTF-8'?><Document xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'xsi:noNamespaceSchemaLocation='file:/C:/daec.001.001.01-en.xsd'><SceIdInf><SceId>00000</SceId></SceIdInf><ContIdInf><CtrctNbInt>CtrctNbInt0</CtrctNbInt><CdOfDtaPdgOgztn>00000000000000</CdOfDtaPdgOgztn></ContIdInf><ConSumInf><CtrctNbTe>CtrctNbTe0</CtrctNbTe><InfmRepDt>2006-05-04</InfmRepDt><Inf><Nm>Nm0</Nm><IDNb>IDNb0</IDNb><IDTp>0</IDTp></Inf><Inf><Nm>Nm1</Nm><IDNb>IDNb1</IDNb><IDTp>0</IDTp></Inf><CtrctMatrDt>2006-05-04</CtrctMatrDt><CtrctEfctDt>2006-05-04</CtrctEfctDt><CrdtLmtCurcy>AAA</CrdtLmtCurcy><CrdtLmtAdjDt>2006-05-04</CrdtLmtAdjDt><CrdtLmtAmt>49999999.50</CrdtLmtAmt><CrdtLmtReLo>1</CrdtLmtReLo><CtrctSts>1</CtrctSts><CtrctStsChgDt>2006-05-04</CtrctStsChgDt></ConSumInf></Document>";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlContent)));
		//获得根元素结点  
		Element root = ((org.w3c.dom.Document) doc).getDocumentElement(); 
		XmlNode xmlNode = new XmlNode();
		xmlNode=XmlUtil.parseElement(root,xmlNode,null); 
		String html = xmlNode.toHtmlTabString(mesgType.getCode());
		Result result = new Result(html, mesgType.getTransform(), mesgType.getCode());
		return InvokeResult.success(result);
	}
	
	public class Result implements Serializable{
		
		private String html;
		private String transform;
		private String mesgType;
		
		public Result(String xml, String transform, String mesgType){
			this.html = xml;
			this.transform = transform;
			this.mesgType = mesgType;
		}		
		public String getHtml() {
			return html;
		}
		public void setHtml(String html) {
			this.html = html;
		}
		public String getTransform() {
			return transform;
		}
		public void setTransform(String transform) {
			this.transform = transform;
		}
		public String getMesgType() {
			return mesgType;
		}
		public void setMesgType(String mesgType) {
			this.mesgType = mesgType;
		}		
	}
	
	public InvokeResult getOriginXml(int id, Long taskPacketId, String ctxPath) throws Exception {
		// TODO Auto-generated method stub
		TaskPacket taskPacket = application.getTaskPacket(taskPacketId);
		String path = ctxPath + taskPacket.getSelectedPacketName() + ".csv";
		int totalLines = ReadAppointedLine.getTotalLines(new File(path));
		String xmlContent = ReadAppointedLine.readAppointedLineNumber(new File(path),id+1,totalLines);
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
	public String getPacketHeadForSend(String code, String userAccount){
		EmployeeUser employeeUser = findEmployeeUserByCreatedBy(userAccount);
		StringBuffer packetHead = new StringBuffer("");
		String currentOrgNO = employeeUser.getDepartment().getSn();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String currentDate = dateFormat.format(new Date());
		Integer dataType = PACKETCONSTANT.TASKPACKET_DATATYPE_NORMAL;
		String counter = String.format("%010d", 1);
		packetHead.append("A").append(PACKETCONSTANT.TASKPACKET_FILEVERSION).append(currentOrgNO).append(currentDate).append(code).append(dataType).append(counter).append("                              ").append("\r\n");
		return packetHead.toString();
	}
	
	@SuppressWarnings("unchecked")
	private EmployeeUser findEmployeeUserByCreatedBy(String createdBy){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _employeeUser from EmployeeUser _employeeUser  where 1=1 ");
	   	
	   	if (createdBy != null ) {
	   		jpql.append(" and _employeeUser.userAccount = ? ");
	   		conditionVals.add(createdBy);
	   	}
	   	EmployeeUser employeeUser = (EmployeeUser) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return employeeUser;
	}
}
