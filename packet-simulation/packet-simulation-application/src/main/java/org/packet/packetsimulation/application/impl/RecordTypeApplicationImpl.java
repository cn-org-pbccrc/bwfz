package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.RecordTypeApplication;
import org.packet.packetsimulationGeneration.core.domain.RecordType;

@Named
@Transactional
public class RecordTypeApplicationImpl implements RecordTypeApplication {

	public RecordType getRecordType(Long id) {
		return RecordType.get(RecordType.class, id);
	}
	
	public void creatRecordType(RecordType recordType) {
		recordType.save();
	}
	
	public void updateRecordType(RecordType recordType) {
		recordType.save();
	}
	
	public void removeRecordType(RecordType recordType) {
		if(recordType != null){
			recordType.remove();
		}
	}
	
	public void removeRecordTypes(Set<RecordType> recordTypes) {
		for (RecordType recordType : recordTypes) {
			recordType.remove();
		}
	}
	
	public List<RecordType> findAllRecordType() {
		return RecordType.findAll(RecordType.class);
	}
	
}
