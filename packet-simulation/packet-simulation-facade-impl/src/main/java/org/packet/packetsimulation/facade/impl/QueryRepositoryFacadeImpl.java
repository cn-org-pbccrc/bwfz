package org.packet.packetsimulation.facade.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.MissionFacade;
import org.packet.packetsimulation.facade.QueryRepositoryFacade;

@Named
public class QueryRepositoryFacadeImpl implements QueryRepositoryFacade {
	
	
	public InvokeResult getInterfaceList(){
		try {
			List result = new ArrayList();
			Document doc = getIfDoc() ;
			Element root = doc.getRootElement();
			for (Iterator i = root.elementIterator(); i.hasNext(); ){
				Map map = new HashMap();
				Element ifNode = (Element) i.next();
				for (Iterator j = ifNode.elementIterator(); j.hasNext(); ){
					Element ifDetail = (Element) j.next();
					if(ifDetail.getName().equals("id")){
						map.put("id", ifDetail.getText());
					}
				}
				result.add(map);
			}
			return InvokeResult.success(result); 	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}
	
	public InvokeResult getMethodList(String interfaceId){
		try {
			List result = new ArrayList();
			Document doc = getIfDoc() ;
			Element root = doc.getRootElement();
			for (Iterator i = root.elementIterator(); i.hasNext();){
				Element ifNode = (Element) i.next();
				for (Iterator j = ifNode.elementIterator(); j.hasNext(); ){
					Element idNode = (Element) j.next();
					if(idNode.getText().equals(interfaceId)){
						Element methodsNode = (Element) j.next();
						for (Iterator k = methodsNode.elementIterator(); k.hasNext(); ){
							Map map = new HashMap();
							Element methodNode = (Element) k.next();
							for (Iterator l = methodNode.elementIterator(); l.hasNext();){
								Element methodDetail = (Element) l.next();
								String key = methodDetail.getName();
								if(key.equals("queryCondition")){
									List queryConditionList = new ArrayList();
									List<Element> conditionNodes = methodDetail.elements();
									for (Element conditonNode : conditionNodes){
										Map conditionMap = new HashMap();
										for (Iterator m = conditonNode.elementIterator(); m.hasNext();){
											Element conditionNode = (Element) m.next();
											String conditionKey = conditionNode.getName();
											String conditionValue = conditionNode.getText();	
											conditionMap.put(conditionKey, conditionValue);
										}
										queryConditionList.add(conditionMap);
									}
									map.put(key, queryConditionList);
								}else{
									String value = methodDetail.getText();
									map.put(key, value);
								}
							}
							result.add(map);
						}
					}
				}
		    }		
			return InvokeResult.success(result); 	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}

	public Document getIfDoc(){
		String confPath = QueryRepositoryFacadeImpl.class.getClassLoader().getResource("/META-INF/ifQuery/queryConf.xml").getPath();
		File file = new File(confPath);
		SAXReader reader = new SAXReader();
		Document doc = null ;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}
}

