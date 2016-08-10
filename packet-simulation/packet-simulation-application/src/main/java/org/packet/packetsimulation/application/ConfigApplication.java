package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.Config;

public interface ConfigApplication {

	public Config getConfig(Long id);
	
	public void creatConfig(Config config);
	
	public void updateConfig(Config config);
	
	public void removeConfig(Config config);
	
	public void removeConfigs(Set<Config> configs);
	
	public List<Config> findAllConfig();
	
}

