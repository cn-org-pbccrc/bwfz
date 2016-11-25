package org.packet.packetsimulation.web.controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.IOCase;
import org.openkoala.dependency.object.CertidBo;
import org.openkoala.dependency.object.CollaBo;
import org.openkoala.dependency.object.CollabaseBo;
import org.openkoala.dependency.object.ColladebtorBo;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.QueryRepositoryFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/QueryRepository")
public class QueryRepositoryController {
	
	@Inject
	private QueryRepositoryFacade queryRepositoryFacade;
	
	@ResponseBody
	@RequestMapping("/query")
	public InvokeResult query() {
		List list = new ArrayList();
		CollaBo collaBo = new CollaBo();
		CollabaseBo collaBaseBo = new CollabaseBo();
		collaBaseBo.setAccptNum(1L);
		collaBaseBo.setAccptSerNum(2L);
		collaBaseBo.setBusiOrgId(3L);
		collaBaseBo.setCcc("1234");
		collaBaseBo.setCcId(4L);
		collaBaseBo.setDsrInsertTime(new Date());
		collaBaseBo.setFinanId(12345678901234L);
		collaBaseBo.setResTypeId(5L);
		collaBaseBo.setRptDate(new Date());
		collaBaseBo.setRptDateId(6L);
		collaBo.setCollaBaseBo(collaBaseBo);
		List<ColladebtorBo> colladebtorBos = new ArrayList<ColladebtorBo>();
		ColladebtorBo colladebtorBo1 = new ColladebtorBo();
		colladebtorBo1.setAccptNum(1L);
		colladebtorBo1.setAccptSerNum(2L);
		colladebtorBo1.setCcId(3L);
		colladebtorBo1.setDebtorCertId(4L);
		colladebtorBo1.setDebtorCertName("小明");
		colladebtorBo1.setDebtorTypeId(5L);
		colladebtorBo1.setDsrInsertTime(new Date());
		colladebtorBo1.setIsMainDebtor('a');
		colladebtorBo1.setRptDate(new Date());
		ColladebtorBo colladebtorBo2 = new ColladebtorBo();
		colladebtorBo2.setAccptNum(1L);
		colladebtorBo2.setAccptSerNum(2L);
		colladebtorBo2.setCcId(3L);
		colladebtorBo2.setDebtorCertId(4L);
		colladebtorBo2.setDebtorCertName("小明");
		colladebtorBo2.setDebtorTypeId(5L);
		colladebtorBo2.setDsrInsertTime(new Date());
		colladebtorBo2.setIsMainDebtor('a');
		colladebtorBo2.setRptDate(new Date());
		colladebtorBos.add(colladebtorBo1);
		colladebtorBos.add(colladebtorBo2);
		collaBo.setColladebtorBoList(colladebtorBos);
		collaBo.setName("贾不死");
		collaBo.setCertId(0L);
		List<Long> certIdList = new ArrayList<Long>();
		certIdList.add(1L);
		certIdList.add(2L);
		certIdList.add(3L);
		collaBo.setCertIdList(certIdList);
/*		Map<Long, CertidBo> map = new HashMap<Long, CertidBo>();
		CertidBo certidBo1 = new CertidBo();
		certidBo1.setCertId(1L);
		certidBo1.setCertName("二山");
		certidBo1.setCertNum("110");
		certidBo1.setCertTypeId(2L);
		CertidBo certidBo2 = new CertidBo();
		certidBo2.setCertId(3L);
		certidBo2.setCertName("三山");
		certidBo2.setCertNum("120");
		certidBo2.setCertTypeId(4L);
		map.put(1L, certidBo1);
		map.put(2L, certidBo2);
		collaBo.setCertidBoMap(map);*/
		// 用户组对象转JSON串  
		JSONObject jsonObject = new JSONObject();
		Field[] fields = collaBo.getClass().getDeclaredFields();
		for (Field field : fields) {
			MyAnnotation anno = field.getAnnotation(MyAnnotation.class);
			if (field.getType().isInstance(list)) {
				List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
				String fieldName = field.getName();
				StringBuffer sb = new StringBuffer();
				sb.append("get");
				sb.append(fieldName.substring(0, 1).toUpperCase());
				sb.append(fieldName.substring(1));
				Method method;
				try {
					method = collaBo.getClass().getMethod(sb.toString());
					List childList = (List) method.invoke(collaBo);
					if (childList.get(0) instanceof String || childList.get(0) instanceof Long || childList.get(0) instanceof Date){
						jsonObject.put(anno.name(), childList);
					} else {
						for (Object object : childList) {
							Field[] childFields = object.getClass().getDeclaredFields();
							JSONObject jsonObject1 = new JSONObject();
							for (Field childField : childFields) {
								MyAnnotation childAnno = childField.getAnnotation(MyAnnotation.class);
								String fieldName1 = childField.getName();
								StringBuffer sb1 = new StringBuffer();
								sb1.append("get");
								sb1.append(fieldName1.substring(0, 1).toUpperCase());
								sb1.append(fieldName1.substring(1));
								Method method1;
								method1 = object.getClass().getMethod(sb1.toString());
								jsonObject1.put(childAnno.name(), method1.invoke(object));
							}
							jsonObjects.add(jsonObject1);
						}
						jsonObject.put(anno.name(), jsonObjects);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (field.getType() == String.class || field.getType() == Long.class || field.getType() == Date.class){
				MyAnnotation anno2 = field.getAnnotation(MyAnnotation.class);
				String fieldName = field.getName();
				StringBuffer sb = new StringBuffer();
				sb.append("get");
				sb.append(fieldName.substring(0, 1).toUpperCase());
				sb.append(fieldName.substring(1));
				Method method;
				try {
					method = collaBo.getClass().getMethod(sb.toString());
					jsonObject.put(anno2.name(), method.invoke(collaBo));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				MyAnnotation anno2 = field.getAnnotation(MyAnnotation.class);
				String fieldName = field.getName();
				StringBuffer sb = new StringBuffer();
				sb.append("get");
				sb.append(fieldName.substring(0, 1).toUpperCase());
				sb.append(fieldName.substring(1));
				Method method;
				Object object;
				try {
					method = collaBo.getClass().getMethod(sb.toString());
					object = method.invoke(collaBo);
					Field[] childFields = object.getClass().getDeclaredFields();
					JSONObject jsonObject2 = new JSONObject();
					for (Field childField : childFields) {
						MyAnnotation childAnno = childField.getAnnotation(MyAnnotation.class);
						String fieldName2 = childField.getName();
						StringBuffer sb2 = new StringBuffer();
						sb2.append("get");
						sb2.append(fieldName2.substring(0, 1).toUpperCase());
						sb2.append(fieldName2.substring(1));
						Method method2;
						method2 = object.getClass().getMethod(sb2.toString());
						jsonObject2.put(childAnno.name(), method2.invoke(object));
					}
					jsonObject.put(anno2.name(), jsonObject2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        String jsonString = JSON.toJSONString(jsonObject);  
        System.out.println("jsonString:" + jsonString); 
		return InvokeResult.success(jsonString);
	}
	
	@ResponseBody
	@RequestMapping("/getInterfaceList")
	public InvokeResult getInterfaceList(){
		InvokeResult result ;
		try {
			result = queryRepositoryFacade.getInterfaceList();
		}catch(Exception e){
			result = InvokeResult.failure(e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getMethodList")
	public InvokeResult getMethodList(@RequestParam String interfaceId){
		InvokeResult result ;
		try {
			result = queryRepositoryFacade.getMethodList(interfaceId);
		}catch(Exception e){
			result = InvokeResult.failure(e.getMessage());
		}
		return result;
	}
}
