package org.openkoala.gqc.infra.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
/**
 * 读取标签配置文件工具类
 * @author charles
 *
 */
public final class PropertiesManager {
	 private static Properties pros = new Properties();
	 
	 
	
	 static{
		 try {
			 //String p = PropertiesManager.class.getClassLoader().getResource("").toURI().getPath();
			 //pros.load(new FileInputStream(new File(p+"translate.properties")));   
		     System.out.println(PropertiesManager.class.getResource("/"));
	         pros.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.1.3.properties"));        
	         //读取properties文件 这里是将文件从classpath里读取出来!因为在eclispe里src里的文件最后编译后都会放入bin文件夹下,也就是classpath下面,这样可以保证能找到文件
	     } catch (IOException e) {
	    	 e.printStackTrace();
	     }         
	     //以上是加载类时将 Properties 的 load方法去加载配置文件
	 }
	 private PropertiesManager(){};
	 
	 public static final String getProperties(String key){
		 String cnName=PropertiesManager.pros.getProperty(key);
		 if(cnName!=null){
			 return changeChineseCode(PropertiesManager.pros.getProperty(key));
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
		  System.out.println(PropertiesManager.getProperties("Document"));   //取得配置文件中属性的值!!
	 }
}
