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
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.openkoala.gqc.infra.util.XmlNode;
import org.openkoala.gqc.infra.util.XmlUtil;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.core.domain.MenuResource;
import org.openkoala.security.facade.dto.MenuResourceDTO;
import org.openkoala.security.facade.impl.SecurityAccessFacadeImpl;
import org.packet.packetsimulation.application.MesgTypeApplication;
import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.Packet;
import org.packet.packetsimulation.core.domain.TaskPacket;
import org.packet.packetsimulation.facade.MesgTypeFacade;
import org.packet.packetsimulation.facade.dto.MesgDTO;
import org.packet.packetsimulation.facade.dto.MesgTypeDTO;
import org.packet.packetsimulation.facade.impl.assembler.MesgAssembler;
import org.packet.packetsimulation.facade.impl.assembler.MesgTypeAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	public InvokeResult creatMesgType(MesgTypeDTO mesgTypeDTO) {		
		MesgType mesgType = MesgTypeAssembler.toEntity(mesgTypeDTO);
		Integer maxSort = findMaxSort();
		mesgType.setSort(maxSort + 1);
		maxSort++;
		application.creatMesgType(mesgType);
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
	
	public InvokeResult getMesgType(Long id) {
		return InvokeResult.success(MesgTypeAssembler.toDTO(application.getMesgType(id)));
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
	   	if (queryVo.getCode() != null && !"".equals(queryVo.getCode())) {
	   		jpql.append(" and _mesgType.code like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCode()));
	   	}
	   	if (queryVo.getSort() != null &&!"".equals(queryVo.getSort())) {
	   		jpql.append(" and _mesgType.sort like ?");
	   		conditionVals.add(queryVo.getSort());
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
        return new Page<MesgTypeDTO>(pages.getStart(), pages.getResultCount(), pageSize, MesgTypeAssembler.toDTOs(pages.getData()));
	}
	
	public InvokeResult upMesgType(String sourceId, String destId){
		MesgType sourceMesgType = application.getMesgType(Long.parseLong(sourceId));
		MesgType destMesgType = application.getMesgType(Long.parseLong(destId));
		Integer sourceSort = sourceMesgType.getSort();
		Integer destSort = destMesgType.getSort();
		sourceMesgType.setSort(destSort);
		destMesgType.setSort(sourceSort);
		application.updateMesgType(sourceMesgType);
		application.updateMesgType(destMesgType);
		return InvokeResult.success();
	}
	
	public InvokeResult downMesgType(String sourceId, String destId){
		MesgType sourceMesgType = application.getMesgType(Long.parseLong(sourceId));
		MesgType destMesgType = application.getMesgType(Long.parseLong(destId));
		Integer sourceSort = sourceMesgType.getSort();
		Integer destSort = destMesgType.getSort();
		sourceMesgType.setSort(destSort);
		destMesgType.setSort(sourceSort);
		application.updateMesgType(sourceMesgType);
		application.updateMesgType(destMesgType);
		return InvokeResult.success();
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
			return InvokeResult.success(xmlNode.toEditHtmlTabString(mesgType.getCode()));
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
	public InvokeResult getEditHtmlByCode(String code, String sourceCode, String xml) {
		MesgType mesgType = findMesgTypeByCode(code);
		String newXml = mesgType.getXml();
		String countTag = mesgType.getCountTag();
		if(!xml.equals("")){
			MesgType sourceMesgType = findMesgTypeByCode(sourceCode);
			newXml = mesgType.getXml().replace("<MdfcSgmt></MdfcSgmt>", xml);
			countTag = sourceMesgType.getCountTag();
		}		
		try {
			XmlNode xmlNode = XmlUtil.getXmlNodeByXmlContent(newXml, countTag);
			return InvokeResult.success(xmlNode.toEditHtmlTabString(mesgType.getCode()));
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
	
	public InvokeResult getEditHtmlByMesgType(MesgType mesgType) {
		try {
			XmlNode xmlNode = XmlUtil.getXmlNodeByXmlContent(mesgType.getXml(),mesgType.getCountTag());
			return InvokeResult.success(xmlNode.toEditHtmlTabString(mesgType.getCode()));
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
	public InvokeResult getEditHtmlOfChange(String code, String finanCode, String cstCode){
		MesgType mesgType = findMesgTypeByCode(code);
		Document document = null;
		try {
			document = DocumentHelper.parseText(mesgType.getXml());
			Element root = document.getRootElement();
			Element first = (Element) root.elements().get(0);
			Element second = (Element) first.elements().get(0);
			Element fin = second.element("Old_Finan_Code");
			fin.setText(finanCode);
			Element cst = second.element("Old_Cst_Code");
			cst.setText(cstCode);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mesgType.setXml(document.asXML().toString());
		return getEditHtmlByMesgType(mesgType);
	}

	public MesgType findMesgTypeByCode(String code) {
		List<Object> conditionVals = new ArrayList<Object>();
	 	StringBuilder jpql = new StringBuilder("select _mesgType from MesgType _mesgType   where 1=1");
	 	if (code != null ) {
	   		jpql.append(" and _mesgType.code =? ");
	   		conditionVals.add(code);
	   	}
	   	MesgType mesgType = (MesgType) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return mesgType;
	}
	
	@Override
	public List<MesgType> findMesgTypesByCreateUser(String userName) {
	 	StringBuilder jpql = new StringBuilder("select _mesgType from MesgType _mesgType   where 1=1 and _mesgType.createdBy ='"+userName+"'");
	   	jpql.append(" order by _mesgType.sort asc");
	   	List<MesgType> mesgTypes = getQueryChannelService().createJpqlQuery(jpql.toString()).list();
	   	return mesgTypes;
	}
	
	private Integer findMaxSort(){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select max(_mesgType.sort) from MesgType _mesgType  where 1=1 ");
	   	if((getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult()==null)){
	   		return 0;
	   	}
	   	Integer max = (Integer) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return max;
	}
}
