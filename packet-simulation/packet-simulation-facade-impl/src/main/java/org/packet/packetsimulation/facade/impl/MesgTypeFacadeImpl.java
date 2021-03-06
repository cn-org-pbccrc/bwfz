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
import org.packet.packetsimulation.application.MesgTypeApplication;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.facade.MesgTypeFacade;
import org.packet.packetsimulation.facade.dto.MesgTypeDTO;
import org.packet.packetsimulation.facade.impl.assembler.MesgTypeAssembler;
import org.xml.sax.SAXException;

@Named
public class MesgTypeFacadeImpl implements MesgTypeFacade {

	@Inject
	private MesgTypeApplication  application;

	private QueryChannelService queryChannel;

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
	   	if (queryVo.getSort() != null) {
	   		jpql.append(" and _mesgType.sort=?");
	   		conditionVals.add(queryVo.getSort());
	   	}	
        Page<MesgType> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<MesgTypeDTO>(pages.getStart(), pages.getResultCount(),pageSize, MesgTypeAssembler.toDTOs(pages.getData()));
	}
	
	@Override
	public InvokeResult getEditHtmlByMesgType(Long id,String realPath) {
		MesgTypeDTO dto = MesgTypeAssembler.toDTO(application.getMesgType(id));
		try {
			XmlNode xmlNode = XmlUtil.getXmlNodeByXml(dto.getFilePath(),realPath,dto.getCountTag());
			System.out.println(xmlNode.toEditHtmlTabString());
			return InvokeResult.success(xmlNode.toEditHtmlTabString());
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
	
}
