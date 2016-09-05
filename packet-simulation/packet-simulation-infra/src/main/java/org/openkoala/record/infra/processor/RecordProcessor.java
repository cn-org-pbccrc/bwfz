package org.openkoala.record.infra.processor;

import com.alibaba.fastjson.JSONObject;
/**
 * 记录处理接口
 * @author charles
 *
 */
public interface RecordProcessor {
	
	public JSONObject string2Json(String record, Object type);
	public String json2String(JSONObject jsonObject, Object type);
	public String batch(String record, Object rule);
	public String convert(String record, Object rule);
}
