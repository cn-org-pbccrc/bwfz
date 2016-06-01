package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.core.domain.*;

public class ConfigAssembler {
	
	public static ConfigDTO  toDTO(Config  config){
		if (config == null) {
			return null;
		}
    	ConfigDTO result  = new ConfigDTO();
	    	result.setId (config.getId());
     	    	result.setVersion (config.getVersion());
     	    	result.setName (config.getName());
     	    	result.setType (config.getType());
     	    	result.setParam (config.getParam());
     	    return result;
	 }
	
	public static List<ConfigDTO>  toDTOs(Collection<Config>  configs){
		if (configs == null) {
			return null;
		}
		List<ConfigDTO> results = new ArrayList<ConfigDTO>();
		for (Config each : configs) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static Config  toEntity(ConfigDTO  configDTO){
	 	if (configDTO == null) {
			return null;
		}
	 	Config result  = new Config();
        result.setId (configDTO.getId());
         result.setVersion (configDTO.getVersion());
         result.setName (configDTO.getName());
         result.setType (configDTO.getType());
         result.setParam (configDTO.getParam());
 	  	return result;
	 }
	
	public static List<Config> toEntities(Collection<ConfigDTO> configDTOs) {
		if (configDTOs == null) {
			return null;
		}
		
		List<Config> results = new ArrayList<Config>();
		for (ConfigDTO each : configDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
