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
	 private static Properties pros811 = new Properties();
	 private static Properties pros812 = new Properties();
	 private static Properties pros813 = new Properties();
	 private static Properties pros814 = new Properties();
	 private static Properties pros815 = new Properties();
	 private static Properties pros816 = new Properties();
	 private static Properties pros821 = new Properties();
	 private static Properties pros822 = new Properties();
	 private static Properties pros831 = new Properties();
	 private static Properties pros832 = new Properties();
	 private static Properties pros833 = new Properties();
	 private static Properties pros841 = new Properties();
	
	 static{
		 try {
			 //String p = PropertiesManager.class.getClassLoader().getResource("").toURI().getPath();
			 //pros.load(new FileInputStream(new File(p+"translate.properties")));   
		     System.out.println(PropertiesManager.class.getResource("/"));
	         pros811.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.1.1.properties"));   
	         pros812.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.1.2.properties"));
	         pros813.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.1.3.properties"));
	         pros814.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.1.4.properties"));
	         pros815.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.1.5.properties"));
	         pros816.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.1.6.properties"));
	         pros821.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.2.1.properties"));
	         pros822.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.2.2.properties"));
	         pros831.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.3.1.properties"));
	         pros832.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.3.2.properties"));
	         pros833.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.3.3.properties"));
	         pros841.load(PropertiesManager.class.getClassLoader().getResourceAsStream("translate8.4.1.properties"));
	         //读取properties文件 这里是将文件从classpath里读取出来!因为在eclispe里src里的文件最后编译后都会放入bin文件夹下,也就是classpath下面,这样可以保证能找到文件
	     } catch (IOException e) {
	    	 e.printStackTrace();
	     }         
	     //以上是加载类时将 Properties 的 load方法去加载配置文件
	 }
	 private PropertiesManager(){};
	 
	 public static final String getProperties(String key, String templateName){
		 if(templateName.equals("基本信息记录")){
			 String cnName=PropertiesManager.pros811.getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(PropertiesManager.pros811.getProperty(key));
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 }else if(templateName.equals("账户信息记录")){
			 String cnName=PropertiesManager.pros812.getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(PropertiesManager.pros812.getProperty(key));
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 }else if(templateName.equals("合同信息记录")){
			 String cnName=PropertiesManager.pros813.getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(PropertiesManager.pros813.getProperty(key));
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 }else if(templateName.equals("抵质押合同信息记录")){
			 String cnName=PropertiesManager.pros814.getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(PropertiesManager.pros814.getProperty(key));
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 }else if(templateName.equals("证件整合信息记录")){
			 String cnName=PropertiesManager.pros815.getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(PropertiesManager.pros815.getProperty(key));
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 }else if(templateName.equals("家族成员信息记录")){
			 String cnName=PropertiesManager.pros816.getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(PropertiesManager.pros816.getProperty(key));
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 }else if(templateName.equals("基本信息标识变更信息记录")){
			 String cnName=PropertiesManager.pros821.getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(PropertiesManager.pros821.getProperty(key));
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 }else if(templateName.equals("信贷信息标识变更信息记录")){
			 String cnName=PropertiesManager.pros822.getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(PropertiesManager.pros822.getProperty(key));
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 }else if(templateName.equals("基本信息删除请求记录")){
			 String cnName=PropertiesManager.pros831.getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(PropertiesManager.pros831.getProperty(key));
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 }else if(templateName.equals("信贷信息删除请求记录")){
			 String cnName=PropertiesManager.pros832.getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(PropertiesManager.pros832.getProperty(key));
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 }else if(templateName.equals("信贷信息整比删除请求记录")){
			 String cnName=PropertiesManager.pros833.getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(PropertiesManager.pros833.getProperty(key));
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 }else if(templateName.equals("账户修改请求记录")){
			 String cnName=PropertiesManager.pros841.getProperty(key);
			 if(cnName!=null){
				 return changeChineseCode(PropertiesManager.pros841.getProperty(key));
			 }else{
				 System.out.println(key);
				 return key;
			 }
		 }
		return key;

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
		  //System.out.println(PropertiesManager.getProperties("Document"));   //取得配置文件中属性的值!!
	 }
}
