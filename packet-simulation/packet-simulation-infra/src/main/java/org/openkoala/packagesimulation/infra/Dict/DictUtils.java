package org.openkoala.packagesimulation.infra.Dict;

import java.util.HashMap;
import java.util.Map;

public class DictUtils {
	/**
	 * 获取数据字典，将数据字典项封装成map形式并返回
	 */
	private static Map dictData;
	public static Map getDictMap(){
		if (dictData !=null){
			return dictData;
		}
		return loadDict();
	}
	public static Map loadDict(){
		dictData = new HashMap();
		return dictData;
	}
}
