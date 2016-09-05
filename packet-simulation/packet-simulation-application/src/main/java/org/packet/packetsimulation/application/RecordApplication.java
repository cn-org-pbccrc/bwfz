package org.packet.packetsimulation.application;


import java.util.List;
import java.util.Set;
import  org.packet.packetsimulationGeneration.core.domain.Record;

public interface RecordApplication {

	public Record getRecord(Long id);
	
	public void creatRecord(Record record);
	
	public void updateRecord(Record record);
	
	public void removeRecord(Record record);
	
	public void removeRecords(Set<Record> records);
	
	public List<Record> findAllRecord();
	
}

