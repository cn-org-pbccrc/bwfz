package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.ConfigApplication;
import org.packet.packetsimulation.core.domain.Config;

@Named
@Transactional
public class ConfigApplicationImpl implements ConfigApplication {

	public Config getConfig(Long id) {
		return Config.get(Config.class, id);
	}
	
	public void creatConfig(Config config) {
		config.save();
	}
	
	public void updateConfig(Config config) {
		config .save();
	}
	
	public void removeConfig(Config config) {
		if(config != null){
			config.remove();
		}
	}
	
	public void removeConfigs(Set<Config> configs) {
		for (Config config : configs) {
			config.remove();
		}
	}
	
	public List<Config> findAllConfig() {
		return Config.findAll(Config.class);
	}
	
}
