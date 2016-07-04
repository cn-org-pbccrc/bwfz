package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulationGeneration.core.domain.RecordType;

public interface RecordTypeApplication {

	public RecordType getRecordType(Long id);
	
	public void creatRecordType(RecordType recordType);
	
	public void updateRecordType(RecordType recordType);
	
	public void removeRecordType(RecordType recordType);
	
	public void removeRecordTypes(Set<RecordType> recordTypes);
	
	public List<RecordType> findAllRecordType();
	
}

