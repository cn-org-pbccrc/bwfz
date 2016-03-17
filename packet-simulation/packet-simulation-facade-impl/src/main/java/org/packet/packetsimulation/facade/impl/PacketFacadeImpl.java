package org.packet.packetsimulation.facade.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import org.openkoala.security.core.PacketHeadFormatException;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
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
		Packet packet = PacketAssembler.toEntity(packetDTO);
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Integer serialNumber = 1;
		String sn;
		String frontPosition = packet.getOrigSender() + dateFormat.format(packet.getOrigSendDate()) + packet.getRecordType() + packet.getDataType();
		packet.setFrontPosition(frontPosition);
		if(findPacketsByFrontPositionAndCreatedBy(frontPosition,packetDTO.getCreatedBy())==null){			
			packet.setSerialNumber(serialNumber);
		}else{
			Integer max = findMaxSerialNumberByFrontPosition(frontPosition, packetDTO.getCreatedBy());
			serialNumber = max + 1;
			packet.setSerialNumber(serialNumber);
		}
		sn = ""+serialNumber; 
		if(sn.length()>4){
			return InvokeResult.failure("流水号最大值为9999!");
		}
		int size = 4-sn.length(); 
		for(int j=0; j<size; j++){ 
			sn="0"+sn; 
		}
		packet.setPacketName(frontPosition+"0"+sn);
		application.creatPacket(packet);
		return InvokeResult.success();
	}
	
	private Integer findMaxSerialNumberByFrontPosition(String frontPosition, String createdBy){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select max(_packet.serialNumber) from Packet _packet  where 1=1 ");
	   	
	   	if (frontPosition != null ) {
	   		jpql.append(" and _packet.frontPosition = ? ");
	   		conditionVals.add(frontPosition);
	   	}
	 	if (createdBy != null ) {
	   		jpql.append(" and _packet.createdBy = ? ");
	   		conditionVals.add(createdBy);
	   	}
	   	if((getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult()==null)){
	   		return 0;
	   	}
	   	Integer max = (Integer) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return max;
	}
	
	public boolean verifyPacketName(String fileName){
		Packet packet = new Packet();
		if(packet.verify(fileName)){
			return true;
		}
		return false;
	}
	
	public InvokeResult updatePacket(PacketDTO packetDTO) {
		Packet packet = PacketAssembler.toEntity(packetDTO);
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Integer serialNumber = 1;
		String sn;
		String frontPosition = packet.getOrigSender() + dateFormat.format(packet.getOrigSendDate()) + packet.getRecordType() + packet.getDataType();
		packet.setFrontPosition(frontPosition);
		if(findPacketsByFrontPositionAndCreatedBy(frontPosition,packetDTO.getCreatedBy())==null){			
			packet.setSerialNumber(serialNumber);
		}else{
			Integer max = findMaxSerialNumberByFrontPosition(frontPosition, packetDTO.getCreatedBy());
			serialNumber = max + 1;
			packet.setSerialNumber(serialNumber);
		}
		sn = ""+serialNumber; 
		if(sn.length()>4){
			return InvokeResult.failure("流水号最大值为9999!");
		}
		int size = 4-sn.length(); 
		for(int j=0; j<size; j++){ 
			sn="0"+sn; 
		}
		packet.setPacketName(frontPosition+"0"+sn);
		application.updatePacket(packet);
		return InvokeResult.success();
	}
	
	public InvokeResult saveAsPacket(PacketDTO packetDTO, String idOfPacket){
		Packet packet = PacketAssembler.toEntity(packetDTO);
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Integer serialNumber = 1;
		String sn;
		String frontPosition = packet.getOrigSender() + dateFormat.format(packet.getOrigSendDate()) + packet.getRecordType() + packet.getDataType();
		packet.setFrontPosition(frontPosition);
		if(findPacketsByFrontPositionAndCreatedBy(frontPosition,packetDTO.getCreatedBy())==null){			
			packet.setSerialNumber(serialNumber);
		}else{
			Integer max = findMaxSerialNumberByFrontPosition(frontPosition, packetDTO.getCreatedBy());
			serialNumber = max + 1;
			packet.setSerialNumber(serialNumber);
		}
		sn = ""+serialNumber; 
		if(sn.length()>4){
			return InvokeResult.failure("流水号最大值为9999!");
		}
		int size = 4-sn.length(); 
		for(int j=0; j<size; j++){ 
			sn="0"+sn; 
		}
		packet.setPacketName(frontPosition+"0"+sn);
		application.updatePacket(packet);
		List<Mesg> mesgsOfSourcePacket = findMesgsByIdOfPacket(Long.valueOf(idOfPacket));
		List<Mesg> mesgs= new ArrayList<Mesg>();
		for(int i = 0; i < mesgsOfSourcePacket.size(); i++){
			Mesg mesg = new Mesg();
			mesg.setMesgType(mesgsOfSourcePacket.get(i).getMesgType());
			mesg.setPacket(packet);
			mesg.setContent(mesgsOfSourcePacket.get(i).getContent());
			mesgs.add(mesg);
		}
		mesgApplication.creatMesgs(mesgs);
		return InvokeResult.success();
	}
	
	public List<Mesg> findMesgsByIdOfPacket(Long idOfPacket){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesg from Mesg _mesg  where 1=1 ");
	   	if (idOfPacket != null ) {
	   		jpql.append(" and _mesg.packet.id = ? ");
	   		conditionVals.add(idOfPacket);
	   	}
	   	List<Mesg> mesgs = (List<Mesg>) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return mesgs;
	}
	
	public InvokeResult removePacket(Long id) {
		application.removePacket(application.getPacket(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removePackets(Long[] ids, String savePath) {
		
		Set<Packet> packets= new HashSet<Packet>();
		for (Long id : ids) {
			packets.add(application.getPacket(id));
			//System.out.println("要删除的文件名称:"+savePath+application.getPacket(id).getCreatedBy()+File.separator+application.getPacket(id).getPacketName());
			//new File(savePath+application.getPacket(id).getCreatedBy()+File.separator+application.getPacket(id).getPacketName()).delete();
			//System.out.println("哈哈哈删除啦米米");
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
	
	@Override
	public String downloadCSV(Long packetId) {	
		Packet packet = application.getPacket(packetId);
		Date origSendDate = packet.getOrigSendDate();
   		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
   		String counter;
   		String result;
	   	List<Mesg> mesgList = findMesgsByPacketId(packetId);
	   	if(null!=mesgList && mesgList.size()>0){
	   		counter = fillStringToHead(10,""+mesgList.size(),"0");
	   		result = "A" + packet.getFileVersion() + packet.getOrigSender() + dateFormat.format(origSendDate) + packet.getRecordType() + packet.getDataType() + counter + "                              " + "\r\n";
	   		for(Mesg mesg : mesgList){  			
				result = result + mesg.getContent() + "\r\n";
	   		}
	   	}else{
	   		counter = fillStringToHead(10,"0","0");
	   		result = "A" + packet.getFileVersion() + packet.getOrigSender() + dateFormat.format(origSendDate) + packet.getRecordType() + packet.getDataType() + counter + "                              " + "\r\n";
	   	}
		return result;
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
	
	@SuppressWarnings("unchecked")
	public List<Packet> findPacketsByFrontPositionAndCreatedBy(String frontPosition, String createdBy){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _packet from Packet _packet  where 1=1 ");
	   	if (frontPosition != null ) {
	   		jpql.append(" and _packet.frontPosition = ? ");
	   		conditionVals.add(frontPosition);
	   	}
	   	if (createdBy != null ) {
	   		jpql.append(" and _packet.createdBy = ? ");
	   		conditionVals.add(createdBy);
	   	}
	   	List<Packet> packets = (List<Packet>) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return packets;
	}
	
	@SuppressWarnings("unchecked")
	public Packet findPacketByPacketNameAndCreatedBy(String packetName, String createdBy){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _packet from Packet _packet  where 1=1 ");
	   	if (packetName != null ) {
	   		jpql.append(" and _packet.packetName = ? ");
	   		conditionVals.add(packetName);
	   	}
	   	if (createdBy != null ) {
	   		jpql.append(" and _packet.createdBy = ? ");
	   		conditionVals.add(createdBy);
	   	}
	   	Packet packet = (Packet) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return packet;
	}
	
//	public Packet findPacketByFrontPosition(String frontPosition){
//		List<Object> conditionVals = new ArrayList<Object>();
//	   	StringBuilder jpql = new StringBuilder("select _packet from Packet _packet  where 1=1 ");
//	   	
//	   	if (frontPosition != null ) {
//	   		jpql.append(" and _packet.frontPosition = ? ");
//	   		conditionVals.add(frontPosition);
//	   	}
//	   	Packet packet = (Packet)getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
//	   	return packet;
//	}
	
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
	
	@SuppressWarnings("resource")
	@Transactional
	@Override
	public InvokeResult uploadPacket(PacketDTO packetDTO, String path, String ctxPath) throws IOException, ParseException, ParserConfigurationException, SAXException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ctxPath+path)));  
		String line = br.readLine();
		//packetDTO.setPacketName(path);	
		Packet packet=PacketAssembler.toEntity(packetDTO);
		//System.out.println("第一名:"+line.substring(0,1));
		//System.out.println("第二名:"+line.substring(1,4));
		packet.setFileVersion(line.substring(1,4));
		//System.out.println("``````````````"+line.substring(1,4));
		packet.setOrigSender(line.substring(4,18));
		String date = line.substring(18,32);
		String dateFormat = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟    
		Date origSendDate = sdf.parse(dateFormat);   
		packet.setOrigSendDate(origSendDate);
		String recordType = line.substring(32,36);
		packet.setRecordType(recordType);
		packet.setDataType(line.substring(36,37));
		packet.setMesgNum(String.valueOf(Integer.parseInt(line.substring(37,47))));
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Integer serialNumber = 1;
		String sn;
		String frontPosition = packet.getOrigSender() + df.format(packet.getOrigSendDate()) + packet.getRecordType() + packet.getDataType();
		packet.setFrontPosition(frontPosition);
		if(findPacketsByFrontPositionAndCreatedBy(frontPosition,packetDTO.getCreatedBy())==null){			
			packet.setSerialNumber(serialNumber);
		}else{
			Integer max = findMaxSerialNumberByFrontPosition(frontPosition, packetDTO.getCreatedBy());
			serialNumber = max + 1;
			packet.setSerialNumber(serialNumber);
		}
		sn = ""+serialNumber; 
		if(sn.length()>4){
			return InvokeResult.failure("流水号最大值为9999!");
		}
		int size = 4-sn.length(); 
		for(int j=0; j<size; j++){ 
			sn="0"+sn; 
		}
		packet.setPacketName(frontPosition+"0"+sn);
		br.close();
		int totalLines = ReadAppointedLine.getTotalLines(new File(ctxPath+path));
		application.creatPacket(packet);
		//System.out.println("最重要的来啦啊哈哈哈哈"+packet.getId());
		List<Mesg> mesgs= new ArrayList<Mesg>();
//		List<Object> conditionVals = new ArrayList<Object>();
//		StringBuilder jpql = new StringBuilder("select _mesgType from MesgType _mesgType  where 1=1 ");
//		if (recordType != null && !"".equals(recordType)) {
//			jpql.append(" and _mesgType.code = ? ");
//			conditionVals.add(recordType);
//		}
//		MesgType mesgType = (MesgType) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
//		System.out.println("次重要的来啦啊哈哈哈哈"+mesgType.getId());
		for(int i = 1; i < totalLines; i++){
			String appointedLine = ReadAppointedLine.readAppointedLineNumber(new File(ctxPath+path),i+1,totalLines);			
			System.out.println("第"+(i+1)+"行:"+appointedLine);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = (Document) db.parse(new InputSource(new ByteArrayInputStream(appointedLine.getBytes("utf-8"))));
			Element root = ((org.w3c.dom.Document) doc).getDocumentElement();
			System.out.println("root:"+root.getTagName());
			System.out.println("firstChild:"+root.getFirstChild().getNodeName());
			String filePath = null;
			if(root.getFirstChild().getNodeName().equals("AcctInf")){
				filePath = "账户信息正常报送记录";
			}else if(root.getFirstChild().getNodeName().equals("BaseInf")){
				filePath = "基本信息正常报送记录";
			}else if(root.getFirstChild().getNodeName().equals("CtrctInf")){
				filePath = "合同信息正常报送记录";
			}else if(root.getFirstChild().getNodeName().equals("MotgaCltalCtrctInf")){
				filePath = "抵质押合同信息正常报送记录";
			}else if(root.getFirstChild().getNodeName().equals("CtfItgInf")){
				filePath = "证件整合信息正常报送记录";
			}else if(root.getFirstChild().getNodeName().equals("FalMmbsInf")){
				filePath = "家族成员信息正常报送记录";
			}else if(root.getFirstChild().getNodeName().equals("BsInfIdCagsInf")){
				filePath = "基本信息标识变更信息记录";
			}else if(root.getFirstChild().getNodeName().equals("CtrctInfIdCagsInf")){
				filePath = "合同信息标识变更信息记录";
			}else if(root.getFirstChild().getNodeName().equals("AcctInfIdCagsInf")){
				filePath = "账户信息标识变更信息记录";
			}else if(root.getFirstChild().getNodeName().equals("MotgaCltalCtrctInfIdCagsInf")){
				filePath = "抵质押合同信息标识变更信息记录";
			}else if(root.getFirstChild().getNodeName().equals("BsInfDlt")){
				filePath = "基本信息删除请求记录";
			}else if(root.getFirstChild().getNodeName().equals("CtrctInfDlt")){
				filePath = "合同信息按段删除请求记录";
			}else if(root.getFirstChild().getNodeName().equals("AcctInfDlt")){
				filePath = "账户信息按段删除请求记录";
			}else if(root.getFirstChild().getNodeName().equals("CtrctInfEntDlt")){
				filePath = "合同信息整笔删除请求记录";
			}else if(root.getFirstChild().getNodeName().equals("AcctInfEntDlt")){
				filePath = "账户信息整笔删除请求记录";
			}else if(root.getFirstChild().getNodeName().equals("MotgaCltalCtrctInfEntDlt")){
				filePath = "抵质押合同信息整笔删除请求记录";
			}else if(root.getFirstChild().getNodeName().equals("ActMdfc")){
				filePath = "账户修改请求记录";
			}else if(root.getFirstChild().getNodeName().equals("CtrctMdfc")){
				filePath = "合同修改请求记录";
			}
			MesgType mesgType = findMesgTypeByFilePath(filePath);
			Mesg mesg = new Mesg();
			mesg.setMesgType(mesgType);
			mesg.setPacket(packet);
			mesg.setContent(appointedLine);
			mesgs.add(mesg);
		}
		mesgApplication.creatMesgs(mesgs);
		new File(ctxPath+path).delete();
		return InvokeResult.success();
	}
	
	private MesgType findMesgTypeByFilePath(String filePath){
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _mesgType from MesgType _mesgType  where 1=1 ");
		if (filePath != null && !"".equals(filePath)) {
			jpql.append(" and _mesgType.filePath = ? ");
			conditionVals.add(filePath);
		}
		MesgType mesgType = (MesgType) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
		return mesgType;
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
	
	public Packet getPacketById(long id){
		Packet packet = application.getPacket(id);
		return packet;
	}
}

