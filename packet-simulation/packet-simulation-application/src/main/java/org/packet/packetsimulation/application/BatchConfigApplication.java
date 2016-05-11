package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulation.core.domain.BatchConfig;

public interface BatchConfigApplication {

	public BatchConfig getBatchConfig(Long id);
	
	public void creatBatchConfig(BatchConfig batchConfig);
	
	public void updateBatchConfig(BatchConfig batchConfig);
	
	public void removeBatchConfig(BatchConfig batchConfig);
	
	public void removeBatchConfigs(Set<BatchConfig> batchConfigs);
	
	public List<BatchConfig> findAllBatchConfig();
	
}

