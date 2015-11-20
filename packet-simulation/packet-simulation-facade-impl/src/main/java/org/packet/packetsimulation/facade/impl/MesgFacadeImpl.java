package org.packet.packetsimulation.facade.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.gqc.infra.util.XmlNode;
import org.openkoala.gqc.infra.util.XmlUtil;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.application.MesgApplication;
import org.packet.packetsimulation.application.MesgTypeApplication;
import org.packet.packetsimulation.application.PacketApplication;
import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.Packet;
import org.packet.packetsimulation.facade.MesgFacade;
import org.packet.packetsimulation.facade.dto.MesgDTO;
import org.packet.packetsimulation.facade.impl.assembler.MesgAssembler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import org.xml.sax.SAXException;

@Named
public class MesgFacadeImpl implements MesgFacade {

	@Inject
	private MesgApplication  application;
	
	@Inject
	private MesgTypeApplication  mesgTypeApplication;
	
	@Inject
	private PacketApplication  packetApplication;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getMesg(Long id) {
		MesgDTO dto = MesgAssembler.toDTO(application.getMesg(id));
		MesgType mesgType = mesgTypeApplication.getMesgType(dto.getMesgType());
		try {
			XmlNode xmlNode = XmlUtil.getXmlNodeByXmlContent(dto.getContent(),mesgType.getCountTag());
			dto.setContent(xmlNode.toHtmlTabString());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return InvokeResult.success(dto);
	}
	
	public InvokeResult creatMesg(MesgDTO mesgDTO,String realPath) {
//		Mesg mesg = MesgAssembler.toEntity(mesgDTO);
//		mesg.setMesgId(new Date().getTime()+"");
//		
//		MesgType mesgType = mesgTypeApplication.getMesgType(mesgDTO.getMesgType());
//		mesg.setMesgType(mesgType);
//		Packet packet = packetApplication.getPacket(mesgDTO.getPacketId());
//		mesg.setPacket(packet);
//		
//		String[] nodeValues = mesgDTO.getNodeValues();
//		try {
//			XmlNode xmlNode = XmlUtil.getXmlNodeByXml(mesgType.getFilePath(), realPath,mesgType.getCountTag());
//			try {
//				xmlNode.setValues(nodeValues);
//			} catch (CloneNotSupportedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			String errors = XmlUtil.validateXMLByXSD(mesgType.getFilePath(), realPath,xmlNode.toXMLString());
//			if(null!=errors){
//				return InvokeResult.failure(errors);
//			}
//			mesg.setContent(xmlNode.toXMLString());
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		application.creatMesg(mesg);
//		return InvokeResult.success();
		
		Mesg mesg = MesgAssembler.toEntity(mesgDTO);
		mesg.setMesgId(new Date().getTime()+"");
		
		MesgType mesgType = mesgTypeApplication.getMesgType(mesgDTO.getMesgType());
		mesg.setMesgType(mesgType);
		Packet packet = packetApplication.getPacket(mesgDTO.getPacketId());
		mesg.setPacket(packet);
		mesg.setContent(mesgDTO.getNodeValues());
		System.out.println("O(∩_∩)O哈！O(∩_∩)O哈！"+mesgDTO.getNodeValues());
		application.creatMesg(mesg);
		return InvokeResult.success();
	}
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	public InvokeResult creatBatch(MesgDTO mesgDTO,String realPath,int batchNumber) {
		for(int i = 0; i<batchNumber; i++){
			Mesg mesg = MesgAssembler.toEntity(mesgDTO);
			mesg.setMesgId(new Date().getTime()+"");
			MesgType mesgType = mesgTypeApplication.getMesgType(mesgDTO.getMesgType());
			mesg.setMesgType(mesgType);
			Packet packet = packetApplication.getPacket(mesgDTO.getPacketId());
			mesg.setPacket(packet);
			mesg.setContent(mesgDTO.getNodeValues());
			application.creatMesg(mesg);
		}
		return InvokeResult.success();
	}
	
	public InvokeResult creatMesgs(MesgDTO mesgDTO,String realPath,int batchNumber) {
		Set<Mesg> mesgs= new HashSet<Mesg>();
		for(int i = 0; i<batchNumber; i++){
			Mesg mesg = MesgAssembler.toEntity(mesgDTO);
			mesg.setMesgId(new Date().getTime()+"");
			MesgType mesgType = mesgTypeApplication.getMesgType(mesgDTO.getMesgType());
			mesg.setMesgType(mesgType);
			Packet packet = packetApplication.getPacket(mesgDTO.getPacketId());
			mesg.setPacket(packet);
			mesg.setContent(mesgDTO.getNodeValues());
			mesgs.add(mesg);
		}
		application.creatMesgs(mesgs);
		return InvokeResult.success();
	}
	
	public InvokeResult updateMesg(MesgDTO mesgDTO,String realPath) {
		
		Mesg mesg = MesgAssembler.toEntity(mesgDTO);
		
		MesgType mesgType = mesgTypeApplication.getMesgType(mesgDTO.getMesgType());
		mesg.setMesgType(mesgType);
		Packet packet = packetApplication.getPacket(mesgDTO.getPacketId());
		mesg.setPacket(packet);
		mesg.setContent(mesgDTO.getNodeValues());
		
//		try {
//			XmlNode xmlNode = XmlUtil.getXmlNodeByXml(mesgType.getFilePath(), realPath,mesgType.getCountTag());
//			xmlNode.setValues(nodeValues);
//			
//			String errors = XmlUtil.validateXMLByXSD(mesgType.getFilePath(), realPath,xmlNode.toXMLString());
//			if(null!=errors){
//				return InvokeResult.failure(errors);
//			}
//			
//			mesg.setContent(xmlNode.toXMLString());
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CloneNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		application.updateMesg(mesg);
		return InvokeResult.success();
	}
	
	public InvokeResult removeMesg(Long id) {
		application.removeMesg(application.getMesg(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeMesgs(Long[] ids) {
		Set<Mesg> mesgs= new HashSet<Mesg>();
		for (Long id : ids) {
			mesgs.add(application.getMesg(id));
		}
		application.removeMesgs(mesgs);
		return InvokeResult.success();
	}
	
	public List<MesgDTO> findAllMesg() {
		return MesgAssembler.toDTOs(application.findAllMesg());
	}
	
	@SuppressWarnings("unchecked")
	public Page<MesgDTO> pageQueryMesg(MesgDTO queryVo, int currentPage, int pageSize,Long packetId) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesg from Mesg _mesg   where 1=1 ");
	   	
	   	if (packetId != null ) {
	   		jpql.append(" and _mesg.packet.id = ? ");
	   		conditionVals.add(packetId);
	   	}
	   	if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
	   		jpql.append(" and _mesg.remark like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
	   	}	
	   	if (queryVo.getContent() != null && !"".equals(queryVo.getContent())) {
	   		jpql.append(" and _mesg.content like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getContent()));
	   	}		
        Page<Mesg> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<MesgDTO>(pages.getStart(), pages.getResultCount(),pageSize, MesgAssembler.toDTOs(pages.getData()));
	}

	@Override
	public InvokeResult getMesgForUpdate(Long id) {
		MesgDTO dto = MesgAssembler.toDTO(application.getMesg(id));
		MesgType mesgType = mesgTypeApplication.getMesgType(dto.getMesgType());
		try {
			XmlNode xmlNode = XmlUtil.getXmlNodeByXmlContent(dto.getContent(),mesgType.getCountTag());
			dto.setContent(xmlNode.toEditHtmlTabString());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return InvokeResult.success(dto);
	}

	@Override
	public InvokeResult getMesgForBatch(Long id) {
		MesgDTO dto = MesgAssembler.toDTO(application.getMesg(id));
		//Mesg mesg = application.getMesg(id);
		System.out.println("mimimimimimimimi:::"+dto.getContent());
		return InvokeResult.success(dto);
	}
	
	@SuppressWarnings("unchecked")
	public List<MesgDTO> queryMesgByPacketId(Long packetId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesg from Mesg _mesg   where 1=1 ");
	   	
	   	if (packetId != null ) {
	   		jpql.append(" and _mesg.packet.id = ? ");
	   		conditionVals.add(packetId);
	   	}
	   	List<Mesg> list = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	List<MesgDTO> dtolist = MesgAssembler.toDTOs(list);
	   	return dtolist;
	}
	
}
