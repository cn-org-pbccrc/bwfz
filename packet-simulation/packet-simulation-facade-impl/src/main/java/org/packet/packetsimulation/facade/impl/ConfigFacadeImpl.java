package org.packet.packetsimulation.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.text.MessageFormat;
import javax.inject.Inject;
import javax.inject.Named;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.impl.assembler.ConfigAssembler;
import org.packet.packetsimulation.facade.ConfigFacade;
import org.packet.packetsimulation.application.ConfigApplication;

import org.packet.packetsimulation.core.domain.*;

@Named
public class ConfigFacadeImpl implements ConfigFacade {

	@Inject
	private ConfigApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getConfig(Long id) {
		return InvokeResult.success(ConfigAssembler.toDTO(application.getConfig(id)));
	}
	
	public InvokeResult creatConfig(ConfigDTO configDTO) {
		application.creatConfig(ConfigAssembler.toEntity(configDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateConfig(ConfigDTO configDTO) {
		application.updateConfig(ConfigAssembler.toEntity(configDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeConfig(Long id) {
		application.removeConfig(application.getConfig(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeConfigs(Long[] ids) {
		Set<Config> configs= new HashSet<Config>();
		for (Long id : ids) {
			configs.add(application.getConfig(id));
		}
		application.removeConfigs(configs);
		return InvokeResult.success();
	}
	
	public List<ConfigDTO> findAllConfig() {
		return ConfigAssembler.toDTOs(application.findAllConfig());
	}
	
	public Page<ConfigDTO> pageQueryConfig(ConfigDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _config from Config _config   where 1=1 ");
	   	if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
	   		jpql.append(" and _config.name like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getName()));
	   	}		
	   	if (queryVo.getType() != null && !"".equals(queryVo.getType())) {
	   		jpql.append(" and _config.type like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getType()));
	   	}		
	   	if (queryVo.getParam() != null && !"".equals(queryVo.getParam())) {
	   		jpql.append(" and _config.param like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getParam()));
	   	}		
        Page<Config> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<ConfigDTO>(pages.getStart(), pages.getResultCount(),pageSize, ConfigAssembler.toDTOs(pages.getData()));
	}
	
	
}
