package org.packet.packetsimulation.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.packet.packetsimulation.application.RecordApplication;
import org.packet.packetsimulationGeneration.core.domain.Record;

@Named
@Transactional
public class RecordApplicationImpl implements RecordApplication {

	public Record getRecord(Long id) {
		return Record.get(Record.class, id);
	}
	
	public void creatRecord(Record record) {
		record.save();
	}
	
	public void updateRecord(Record record) {
		record .save();
	}
	
	public void removeRecord(Record record) {
		if(record != null){
			record.remove();
		}
	}
	
	public void removeRecords(Set<Record> records) {
		for (Record record : records) {
			record.remove();
		}
	}
	
	public List<Record> findAllRecord() {
		return Record.findAll(Record.class);
	}
	
}
