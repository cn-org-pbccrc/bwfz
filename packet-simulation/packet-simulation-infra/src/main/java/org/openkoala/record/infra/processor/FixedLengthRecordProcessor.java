package org.openkoala.record.infra.processor;

import java.util.Map;

import org.packet.packetsimulationGeneration.core.domain.RecordSegment;

import com.alibaba.fastjson.JSONObject;

public class FixedLengthRecordProcessor implements RecordProcessor{

	@Override
	public JSONObject string2Json(String record, Object type) {
		Map<String, RecordSegment> segMap = (Map<String, RecordSegment>) type;
		String segMark = record.substring(0, 1);
		RecordSegment recordSegment = segMap.get(segMark);
		return null;
	}

	@Override
	public String json2String(JSONObject jsonObject, Object type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String batch(String record, Object rule) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String convert(String record, Object rule) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
