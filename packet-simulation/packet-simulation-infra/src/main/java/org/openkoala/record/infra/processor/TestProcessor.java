package org.openkoala.record.infra.processor;

import java.util.HashMap;
import java.util.Map;

import org.packet.packetsimulationGeneration.core.domain.RecordSegment;

public class TestProcessor {
	public static void main(String[] args) {
		RecordProcessor processor = new FixedLengthRecordProcessor();
		Map<String, RecordSegment> map= new HashMap<String, RecordSegment>();
		processor.string2Json("123", map);
	}
}
