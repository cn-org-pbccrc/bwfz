package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.BatchConfigApplication;
import org.packet.packetsimulation.core.domain.BatchConfig;

@Named
@Transactional
public class BatchConfigApplicationImpl implements BatchConfigApplication {

	public BatchConfig getBatchConfig(Long id) {
		return BatchConfig.get(BatchConfig.class, id);
	}
	
	public void creatBatchConfig(BatchConfig batchConfig) {
		batchConfig.save();
	}
	
	public void updateBatchConfig(BatchConfig batchConfig) {
		batchConfig .save();
	}
	
	public void removeBatchConfig(BatchConfig batchConfig) {
		if(batchConfig != null){
			batchConfig.remove();
		}
	}
	
	public void removeBatchConfigs(Set<BatchConfig> batchConfigs) {
		for (BatchConfig batchConfig : batchConfigs) {
			batchConfig.remove();
		}
	}
	
	public List<BatchConfig> findAllBatchConfig() {
		return BatchConfig.findAll(BatchConfig.class);
	}
	
}
