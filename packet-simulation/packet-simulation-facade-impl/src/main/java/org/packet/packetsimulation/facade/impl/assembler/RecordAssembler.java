package org.packet.packetsimulation.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulationGeneration.core.domain.*;

public class RecordAssembler {

	public static RecordDTO toDTO(Record record) {
		if (record == null) {
			return null;
		}
		RecordDTO result = new RecordDTO();
		result.setId(record.getId());
		result.setVersion(record.getVersion());
		result.setRecordType(record.getRecordType());
		result.setRecordTypeStr(record.getRecordType().getRecordType());
		result.setContent(record.getContent());
		result.setRemark(record.getRemark());
		result.setCreateBy(record.getCreateBy());
		result.setMesgFrom(record.getMesgFrom());
		return result;
	}

	public static List<RecordDTO> toDTOs(Collection<Record> records) {
		if (records == null) {
			return null;
		}
		List<RecordDTO> results = new ArrayList<RecordDTO>();
		for (Record each : records) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static Record toEntity(RecordDTO recordDTO) {
		if (recordDTO == null) {
			return null;
		}
		Record result = new Record();
		result.setId(recordDTO.getId());
		result.setVersion(recordDTO.getVersion());
		result.setRecordType(recordDTO.getRecordType());
		result.setContent(recordDTO.getContent());
		result.setRemark(recordDTO.getRemark());
		result.setCreateBy(recordDTO.getCreateBy());
		result.setMesgFrom(recordDTO.getMesgFrom());
		return result;
	}

	public static List<Record> toEntities(Collection<RecordDTO> recordDTOs) {
		if (recordDTOs == null) {
			return null;
		}

		List<Record> results = new ArrayList<Record>();
		for (RecordDTO each : recordDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
