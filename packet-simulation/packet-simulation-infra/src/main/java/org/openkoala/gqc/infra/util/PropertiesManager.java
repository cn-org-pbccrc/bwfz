package org.openkoala.gqc.infra.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * 读取标签配置文件工具类
 * @author charles
 *
 */
public final class PropertiesManager {
	 
	 private static Map<String, Properties>  propMap = new HashMap<String, Properties>();
	
	 static{
		 try {
			 //String p = PropertiesManager.class.getClassLoader().getResource("").toURI().getPath();
			 //pros.load(new FileInputStream(new File(p+"translate.properties")));   
		     System.out.println(PropertiesManager.class.getResource("/"));
		     String filepath = PropertiesManager.class.getResource("/").getPath();
		     File file = new File(filepath);
           if (file.isDirectory()) {
                     System.out.println("文件夹");
                     String[] filelist = file.list();
                     for (int i = 0; i < filelist.length; i++) {
                    	 Properties properties = new Properties();
                             File readfile = new File(filepath + File.separator + filelist[i]);
					if (!readfile.isDirectory()) {
						properties.load(new FileInputStream(readfile));
						String fileName = readfile.getName().substring(0,
								readfile.getName().lastIndexOf("."));// 获取除后缀1位的名称
						// properties.load(PropertiesManager.class.getClassLoader().getResourceAsStream(readfile.getName()));
						propMap.put(fileName, properties);
						System.out.println("name=" + readfile.getName());

					}
                     }
             }
		     
	     } catch (IOException e) {
	    	 e.printStackTrace();
	     }         
	     //以上是加载类时将 Properties 的 load方法去加载配置文件
	 }
	 
	 public static final String getProperties(String key, String templateName){
			 String cnName=propMap.get(templateName).getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(cnName);
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 
	 }
	 
	 
	//ISO-8859-1  GB2312  GBK  UTF-8 Unicode
	public static String changeChineseCode(String oldStr){
		String value=null;
		try {
			value =new String(oldStr.getBytes("ISO-8859-1"),"utf-8"); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return value;
	}
	
	public static void main(String[] args) {
		  System.out.println(PropertiesManager.getProperties("BaseInf","translate8.1.1"));   //取得配置文件中属性的值!!
	 }
}
