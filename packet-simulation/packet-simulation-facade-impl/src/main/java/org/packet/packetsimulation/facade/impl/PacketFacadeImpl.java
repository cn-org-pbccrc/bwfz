package org.packet.packetsimulation.facade.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;






//import org.openkoala.security.shiro.CurrentUser;
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
import org.openkoala.organisation.core.domain.Employee;
import org.openkoala.security.core.PacketNameIsExistedException;
import org.openkoala.security.core.UserAccountIsExistedException;
import org.openkoala.security.core.domain.User;
import org.openkoala.security.facade.impl.SecurityConfigFacadeImpl;
import org.openkoala.security.facade.impl.assembler.UserAssembler;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.packet.packetsimulation.application.MesgApplication;
import org.packet.packetsimulation.application.MesgTypeApplication;
import org.packet.packetsimulation.application.PacketApplication;
import org.packet.packetsimulation.core.domain.FileName;
import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.Packet;
import org.packet.packetsimulation.core.domain.TaskPacket;
import org.packet.packetsimulation.facade.PacketFacade;
import org.packet.packetsimulation.facade.dto.PacketDTO;
import org.packet.packetsimulation.facade.impl.assembler.PacketAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;



@Named
public class PacketFacadeImpl implements PacketFacade {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PacketFacadeImpl.class);
	
	@Inject
	private PacketApplication  application;
	
	@Inject
	private MesgApplication  mesgApplication;
	
	@Inject
	private MesgTypeApplication  mesgTypeApplication;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getPacket(Long id) {
		return InvokeResult.success(PacketAssembler.toDTO(application.getPacket(id)));
	}
	
	public InvokeResult creatPacket(PacketDTO packetDTO) {
		try {
			packetDTO.setPackId(new Date().getTime()+"");
			//packetDTO.setOrigSendDate(new Date());		
			application.creatPacket(PacketAssembler.toEntity(packetDTO));
			return InvokeResult.success();
        } catch (PacketNameIsExistedException e) {
        	System.out.println("报文名称重复啦！！！！");
            LOGGER.error(e.getMessage(), e);
            return InvokeResult.failure("报文名称已经存在。");
        } catch (Exception e) {
        	System.out.println("进入了Exception");
            LOGGER.error(e.getMessage(), e);
            return InvokeResult.failure("添加报文失败。");
        }
	}
	
	public InvokeResult updatePacket(PacketDTO packetDTO) {
		//application.updatePacket(PacketAssembler.toEntity(packetDTO));
		//return InvokeResult.success();
        Packet packet = application.getPacket(packetDTO.getId());
        packet.setFileVersion(packetDTO.getFileVersion());
        packet.setOrigSender(packetDTO.getOrigSender());
        packet.setOrigSendDate(packetDTO.getOrigSendDate());
        packet.setRecordType(packetDTO.getRecordType());
        packet.setDataType(packetDTO.getDataType());
        packet.setMesgNum(packetDTO.getMesgNum());
        packet.setCreatedBy(packetDTO.getCreatedBy());
        application.updatePacket(packet);
		return InvokeResult.success();
	}
	
	public InvokeResult removePacket(Long id) {
		application.removePacket(application.getPacket(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removePackets(Long[] ids, String savePath) {
		
		Set<Packet> packets= new HashSet<Packet>();
		for (Long id : ids) {
			packets.add(application.getPacket(id));
			System.out.println("要删除的文件名称:"+savePath+application.getPacket(id).getCreatedBy()+File.separator+application.getPacket(id).getPacketName());
			new File(savePath+application.getPacket(id).getCreatedBy()+File.separator+application.getPacket(id).getPacketName()).delete();
			System.out.println("哈哈哈删除啦米米");
			List<Mesg> mesgList = findMesgsByPacketId(id);
			Set<Mesg> mesgs= new HashSet<Mesg>();
			mesgs.addAll(mesgList);
			mesgApplication.removeMesgs(mesgs);
		}
		
		application.removePackets(packets);
		return InvokeResult.success();
	}
	
	public List<PacketDTO> findAllPacket() {
		return PacketAssembler.toDTOs(application.findAllPacket());
	}
	
	@SuppressWarnings("unchecked")
	public Page<PacketDTO> pageQueryPacket(PacketDTO queryVo, int currentPage, int pageSize,String currentUserId) {
		//String userAccount = CurrentUser.getUserAccount();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _packet from Packet _packet where 1=1 ");
	   	if (queryVo.getPackId() != null && !"".equals(queryVo.getPackId())) {
	   		jpql.append(" and _packet.packId like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPackId()));
	   	}		
	   	if (queryVo.getOrigSender() != null && !"".equals(queryVo.getOrigSender())) {
	   		jpql.append(" and _packet.origSender like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getOrigSender()));
	   	}		
	   	if (queryVo.getOrigSendDate() != null) {
	   		jpql.append(" and _packet.origSendDate between ? and ? ");
	   		conditionVals.add(queryVo.getOrigSendDate());
	   		conditionVals.add(queryVo.getOrigSendDateEnd());
	   	}
	   	if (queryVo.getPacketName() != null && !"".equals(queryVo.getPacketName())) {
	   		jpql.append(" and _packet.packetName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPacketName()));
	   	}
	 	if (queryVo.getFileVersion() != null && !"".equals(queryVo.getFileVersion())) {
	   		jpql.append(" and _packet.fileVersion like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFileVersion()));
	   	}
	 	if (queryVo.getDataType() != null && !"".equals(queryVo.getDataType())) {
	   		jpql.append(" and _packet.dataType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDataType()));	   			 
	   	}
	 	if (queryVo.getRecordType() != null && !"".equals(queryVo.getRecordType())) {
	   		jpql.append(" and _packet.recordType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRecordType()));	   			 
	   	}
		if (queryVo.getMesgNum() != null && !"".equals(queryVo.getMesgNum())) {
	   		jpql.append(" and _packet.mesgNum like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getMesgNum()));	   			 
	   	}
		if (queryVo.getReserve() != null && !"".equals(queryVo.getReserve())) {
	   		jpql.append(" and _packet.reserve like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getReserve()));	   			 
	   	}
//	   	if (queryVo.getCreatedAt() != null) {
//	   		jpql.append(" and _packet.createdAt between ? and ? ");
//	   		conditionVals.add(queryVo.getCreatedAt());
//	   		conditionVals.add(queryVo.getCreatedAtEnd());
//	   	}	
	   	if (currentUserId != null) {
	   		jpql.append(" and _packet.createdBy = ?");
	   		conditionVals.add(currentUserId);
	   	}		
        Page<Packet> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<PacketDTO>(pages.getStart(), pages.getResultCount(),pageSize, PacketAssembler.toDTOs(pages.getData()));
	}
	
	public Page<PacketDTO> pageQueryPacket(PacketDTO queryVo, int currentPage, int pageSize) {
		//String userAccount = CurrentUser.getUserAccount();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _packet from Packet _packet where 1=1 ");
	   	if (queryVo.getPackId() != null && !"".equals(queryVo.getPackId())) {
	   		jpql.append(" and _packet.packId like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPackId()));
	   	}		
	   	if (queryVo.getOrigSender() != null && !"".equals(queryVo.getOrigSender())) {
	   		jpql.append(" and _packet.origSender like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getOrigSender()));
	   	}		
	   	if (queryVo.getOrigSendDate() != null) {
	   		jpql.append(" and _packet.origSendDate between ? and ? ");
	   		conditionVals.add(queryVo.getOrigSendDate());
	   		conditionVals.add(queryVo.getOrigSendDateEnd());
	   	}
	   	if (queryVo.getPacketName() != null && !"".equals(queryVo.getPacketName())) {
	   		jpql.append(" and _packet.packetName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPacketName()));
	   	}
	 	if (queryVo.getFileVersion() != null && !"".equals(queryVo.getFileVersion())) {
	   		jpql.append(" and _packet.fileVersion like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFileVersion()));
	   	}
	 	if (queryVo.getDataType() != null && !"".equals(queryVo.getDataType())) {
	   		jpql.append(" and _packet.dataType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDataType()));	   			 
	   	}
	 	if (queryVo.getRecordType() != null && !"".equals(queryVo.getRecordType())) {
	   		jpql.append(" and _packet.recordType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRecordType()));	   			 
	   	}
		if (queryVo.getMesgNum() != null && !"".equals(queryVo.getMesgNum())) {
	   		jpql.append(" and _packet.mesgNum like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getMesgNum()));	   			 
	   	}
		if (queryVo.getReserve() != null && !"".equals(queryVo.getReserve())) {
	   		jpql.append(" and _packet.reserve like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getReserve()));	   			 
	   	}
//	   	if (queryVo.getCreatedAt() != null) {
//	   		jpql.append(" and _packet.createdAt between ? and ? ");
//	   		conditionVals.add(queryVo.getCreatedAt());
//	   		conditionVals.add(queryVo.getCreatedAtEnd());
//	   	}	
	
        Page<Packet> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<PacketDTO>(pages.getStart(), pages.getResultCount(),pageSize, PacketAssembler.toDTOs(pages.getData()));
	}
	
	@Override
	public String downloadCSV(Long packetId) {
		Packet packet = application.getPacket(packetId);
		
	   	List<Mesg> mesgList = findMesgsByPacketId(packetId);
//	   	for(Mesg mesg : mesgList){
//	   		System.out.println("^^^^^^^^^^^^^^^^"+mesg.getContent()+"--"+mesg.getMesgType()+"--"+mesg.getMesgType().getCountTag());
//	   	}
	   	
	   	if(null!=mesgList && mesgList.size()>0){
	   		Date origSendDate = packet.getOrigSendDate();
	   		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
	   		DateFormat timeFormat = new SimpleDateFormat("HHmmss");  
	   		//DateFormat haha = new SimpleDateFormat("yyyyMMddHHmmss");
	   		String counter = mesgList.size() + "";
	   		counter = fillStringToHead(10,counter,"0");
	   		String result = "A" + packet.getFileVersion() + packet.getOrigSender() + dateFormat.format(origSendDate) + packet.getRecordType() + packet.getDataType() + counter + "                              " + "\r\n";
//	   		System.out.println("OrigSender:"+packet.getOrigSender());
//	   		System.out.println("counter:"+counter);
//	   		System.out.println("size:"+mesgList.size());
	   		//System.out.println("年月日:"+dateFormat.format(origSendDate));
	   		//System.out.println("时分秒:"+timeFormat.format(origSendDate));
	   		for(Mesg mesg : mesgList){
	   			//XmlNode xmlNode;
				//try {
					//xmlNode = XmlUtil.getXmlNodeByXmlContent(mesg.getContent(),mesg.getMesgType().getCountTag());
//					result = result + "{H:02" + packet.getOrigSender() + dateFormat.format(origSendDate) 
//							+ timeFormat.format(origSendDate) + mesg.getMesgType().getCode() + mesg.getMesgId()
//							+ mesg.getMesgPriority() + mesg.getMesgDirection() + mesg.getReserve() + ":}";
					
					//String uuid = UUID.randomUUID().toString().replaceAll("-", "");
					//result = result + "{H:123456789012345" + uuid + "0000000000000}";
					result = result + mesg.getContent() + "\r\n";
					
				//} catch (SAXException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
	   		}
	   		return result;
	   	}
		return null;
	}
	
	
	
	@SuppressWarnings("unchecked")
	private List<Mesg> findMesgsByPacketId(Long packetId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesg from Mesg _mesg  where 1=1 ");
	   	
	   	if (packetId != null ) {
	   		jpql.append(" and _mesg.packet.id = ? ");
	   		conditionVals.add(packetId);
	   	}
	   	List<Mesg> mesgList = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return mesgList;
	}
	
	public FileName findIdByFrontPosition(String frontPosition){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _fileName from FileName _fileName  where 1=1 ");
	   	
	   	if (frontPosition != null ) {
	   		jpql.append(" and _fileName.frontPosition = ? ");
	   		conditionVals.add(frontPosition);
	   	}
	   	FileName fileName = (FileName)getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return fileName;
	}
	
	private String fillStringToHead(int length,String target,String filler){
		if(null!=target && target.length()<length && null!=filler && !"".equals(filler)){
			String result = "";
			for(int i=target.length();i<length;i++){
				result = result + filler;
			}
			result = result + target;
			return result;
		}
		return target;
	}
	
	@Transactional
	@Override
	public InvokeResult uploadPacket(PacketDTO packetDTO, String path, String ctxPath) throws IOException, ParseException {
		packetDTO.setPackId(new Date().getTime()+"");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ctxPath+path)));  
		String line = br.readLine();
		PacketDTO dt=new PacketDTO();
		System.out.println(dt.getId());
		packetDTO.setPacketName(path);
		System.out.println("第一名:"+line.substring(0,1));
		System.out.println("第二名:"+line.substring(1,4));
		String recordType = null;
		if(line.substring(0,1).equals("A")){
			packetDTO.setFileVersion(line.substring(1,4));
			System.out.println("``````````````"+line.substring(1,4));
			packetDTO.setOrigSender(line.substring(4,18));
			String date = line.substring(18,32);
			String dateFormat = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟    
			Date origSendDate = sdf.parse(dateFormat);   
			packetDTO.setOrigSendDate(origSendDate);
			recordType = line.substring(32,36);
			packetDTO.setRecordType(recordType);
			packetDTO.setDataType(line.substring(36,37));
		}
		br.close();
		int totalLines = ReadAppointedLine.getTotalLines(new File(ctxPath+path));
		packetDTO.setMesgNum(String.valueOf(totalLines-1));
		Packet packet=PacketAssembler.toEntity(packetDTO);
		application.creatPacket(packet);
		System.out.println("最重要的来啦啊哈哈哈哈"+packet.getId());
		Set<Mesg> mesgs= new HashSet<Mesg>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _mesgType from MesgType _mesgType  where 1=1 ");
		if (recordType != null && !"".equals(recordType)) {
			jpql.append(" and _mesgType.code = ? ");
		   	conditionVals.add(recordType);
		}
		MesgType mesgType = (MesgType) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
		System.out.println("次重要的来啦啊哈哈哈哈"+mesgType.getId());
		for(int i = 1; i < totalLines; i++){
			String appointedLine = ReadAppointedLine.readAppointedLineNumber(new File(ctxPath+path),i+1,totalLines);			
			System.out.println("第"+(i+1)+"行:"+appointedLine);
			Mesg mesg = new Mesg();
			mesg.setMesgType(mesgType);
			mesg.setPacket(packet);
			mesg.setContent(appointedLine);
			mesgs.add(mesg);
		}
		mesgApplication.creatMesgs(mesgs);
		return InvokeResult.success();
	}
	
	@Transactional
	@Override
	public InvokeResult updateUploadPacket(String fileName, String ctxPath) throws IOException, ParseException {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _packet from Packet _packet  where 1=1 ");
		if (fileName != null && !"".equals(fileName)) {
			jpql.append(" and _packet.packetName = ? ");
		   	conditionVals.add(fileName);
		}
		Packet packet = (Packet) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
		System.out.println("222啊哈哈哈哈"+packet.getId());
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ctxPath+fileName)));  
		String line = br.readLine();
		System.out.println("第一名:"+line.substring(0,1));
		System.out.println("第二名:"+line.substring(1,4));
		String recordType = null;
		if(line.substring(0,1).equals("A")){
			packet.setFileVersion(line.substring(1,4));
			System.out.println("``````````````"+line.substring(1,4));
			packet.setOrigSender(line.substring(4,18));
			String date = line.substring(18,26);
			String dateFormat = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟    
			Date origSendDate = sdf.parse(dateFormat);   
			packet.setOrigSendDate(origSendDate);
			recordType = line.substring(32,36);
			packet.setRecordType(recordType);
			packet.setDataType(line.substring(36,37));
		}
		application.updatePacket(packet);
		System.out.println("最重要的来啦啊哈哈哈哈"+packet.getId()+"嘻嘻:"+packet.getFileVersion());
		br.close();
		int totalLines = ReadAppointedLine.getTotalLines(new File(ctxPath+fileName));
		Set<Mesg> mesgs= new HashSet<Mesg>();
		List<Object> conditionVals2 = new ArrayList<Object>();
		StringBuilder jpql2 = new StringBuilder("select _mesgType from MesgType _mesgType  where 1=1 ");
		if (recordType != null && !"".equals(recordType)) {
			jpql2.append(" and _mesgType.code = ? ");
		   	conditionVals2.add(recordType);
		}
		MesgType mesgType = (MesgType) getQueryChannelService().createJpqlQuery(jpql2.toString()).setParameters(conditionVals2).singleResult();
		System.out.println("次重要的来啦啊哈哈哈哈"+mesgType.getId());
		List<Object> conditionVals3 = new ArrayList<Object>();
	   	StringBuilder jpql3 = new StringBuilder("select _mesg from Mesg _mesg   where 1=1 ");
	   	if (packet.getId() != null && !"".equals(packet.getId())) {
	   		jpql3.append(" and _mesg.packet.id = ? ");
	   		conditionVals3.add(packet.getId());
	   	}
	   	List<Mesg> list = getQueryChannelService().createJpqlQuery(jpql3.toString()).setParameters(conditionVals3).list();
	   	for(int i = 0; i < list.size(); i++ ){
	   		mesgApplication.removeMesg(list.get(i));;
	   	}
		for(int i = 1; i < totalLines; i++){
			String appointedLine = ReadAppointedLine.readAppointedLineNumber(new File(ctxPath+fileName),i+1,totalLines);			
			System.out.println("第"+(i+1)+"行:"+appointedLine);
			Mesg mesg = new Mesg();
			mesg.setMesgType(mesgType);
			mesg.setPacket(packet);
			mesg.setContent(appointedLine);
			mesgs.add(mesg);
		}
		mesgApplication.updateMesgs(mesgs);
		return InvokeResult.success();
	}
	
	public InvokeResult creatFileName(String frontPosition, String serialNumber){
		FileName fileName = new FileName(frontPosition, serialNumber);
		application.creatFileName(fileName);
		return InvokeResult.success();
	}
	
	public InvokeResult updateFileName(Long id, String serialNumber){
		FileName fileName = application.getFileName(id);
		fileName.setSerialNumber(serialNumber);
		application.updateFileName(fileName);
		return InvokeResult.success();
	}
}

