package org.packet.packetsimulation.facade.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
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
import org.openkoala.security.core.domain.MenuResource;
import org.openkoala.security.facade.dto.MenuResourceDTO;
import org.openkoala.security.facade.impl.SecurityAccessFacadeImpl;
import org.packet.packetsimulation.application.MesgTypeApplication;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.Packet;
import org.packet.packetsimulation.facade.MesgTypeFacade;
import org.packet.packetsimulation.facade.dto.MesgTypeDTO;
import org.packet.packetsimulation.facade.impl.assembler.MesgTypeAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

@Named
public class MesgTypeFacadeImpl implements MesgTypeFacade {

	@Inject
	private MesgTypeApplication  application;

	private QueryChannelService queryChannel;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MesgTypeFacadeImpl.class);

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getMesgType(Long id) {
		return InvokeResult.success(MesgTypeAssembler.toDTO(application.getMesgType(id)));
	}
	
	public InvokeResult creatMesgType(MesgTypeDTO mesgTypeDTO) {
		application.creatMesgType(MesgTypeAssembler.toEntity(mesgTypeDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateMesgType(MesgTypeDTO mesgTypeDTO) {
		application.updateMesgType(MesgTypeAssembler.toEntity(mesgTypeDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeMesgType(Long id) {
		application.removeMesgType(application.getMesgType(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeMesgTypes(Long[] ids) {
		Set<MesgType> mesgTypes= new HashSet<MesgType>();
		for (Long id : ids) {
			mesgTypes.add(application.getMesgType(id));
		}
		application.removeMesgTypes(mesgTypes);
		return InvokeResult.success();
	}
	
	public List<MesgTypeDTO> findAllMesgType() {
		return MesgTypeAssembler.toDTOs(application.findAllMesgType());
	}
	
	@SuppressWarnings("unchecked")
	public List<MesgType> findMesgTypes(){
	   	StringBuilder jpql = new StringBuilder("select _mesgType from MesgType _mesgType   where 1=1 ");
	   	jpql.append("order by _mesgType.sort asc");
	   	List<MesgType> mesgTypes = getQueryChannelService().createJpqlQuery(jpql.toString()).list();
	   	return mesgTypes;
	}
	
	@SuppressWarnings("unchecked")
	public Page<MesgTypeDTO> pageQueryMesgType(MesgTypeDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesgType from MesgType _mesgType   where 1=1 ");
	   	if (queryVo.getMesgType() != null && !"".equals(queryVo.getMesgType())) {
	   		jpql.append(" and _mesgType.mesgType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getMesgType()));
	   	}		
	   	if (queryVo.getFilePath() != null && !"".equals(queryVo.getFilePath())) {
	   		jpql.append(" and _mesgType.filePath like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFilePath()));
	   	}		
//	   	if (queryVo.getSort() != null) {
//	   		jpql.append(" and _mesgType.sort=?");
//	   		conditionVals.add(queryVo.getSort());
//	   	}
	   	if (queryVo.getSort() != null && !"".equals(queryVo.getSort())) {
	   		jpql.append(" and _mesgType.sort like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%",queryVo.getSort()));
	   	}
	   	if (queryVo.getCreatedBy() != null && !"".equals(queryVo.getCreatedBy())) {
	   		jpql.append(" and _mesgType.createdBy like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%",queryVo.getCreatedBy()));
	   	}
	   	jpql.append("order by _mesgType.sort asc");
        Page<MesgType> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<MesgTypeDTO>(pages.getStart(), pages.getResultCount(),pageSize, MesgTypeAssembler.toDTOs(pages.getData()));
	}
	
//	@Override
//	public InvokeResult getEditHtmlByMesgType(Long id,String realPath) {
//		MesgTypeDTO dto = MesgTypeAssembler.toDTO(application.getMesgType(id));
//		try {
//			XmlNode xmlNode = XmlUtil.getXmlNodeByXml(dto.getFilePath(),realPath,dto.getCountTag());
//			return InvokeResult.success(xmlNode.toEditHtmlTabString(dto.getFilePath()));
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
//		return null;
//	}
	
	@Override
	public InvokeResult getEditHtmlByMesgType(Long id) {
		MesgType mesgType = application.getMesgType(id);
		try {
			XmlNode xmlNode = XmlUtil.getXmlNodeByXmlContent(mesgType.getXml(),mesgType.getCountTag());
			return InvokeResult.success(xmlNode.toEditHtmlTabString(mesgType.getFilePath()));
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
		return null;
	}

	@Override
	public List<MesgType> findMesgTypesByCreateUser(String userName) {
	 	StringBuilder jpql = new StringBuilder("select _mesgType from MesgType _mesgType   where 1=1 and _mesgType.createdBy ='"+userName+"'");
	   	jpql.append(" order by _mesgType.sort asc");
	   	List<MesgType> mesgTypes = getQueryChannelService().createJpqlQuery(jpql.toString()).list();
	   	return mesgTypes;
	}
	
}
