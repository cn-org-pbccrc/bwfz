package org.openkoala.gqc.infra.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * 读取标签配置文件工具类
 * @author charles
 *
 */
public final class TestPropertiesManager {
	 
	 private static Map<String, Properties>  propMap = new HashMap<String, Properties>();
	
	 static{
		 try {
			 //String p = PropertiesManager.class.getClassLoader().getResource("").toURI().getPath();
			 //pros.load(new FileInputStream(new File(p+"translate.properties")));   
		     System.out.println(TestPropertiesManager.class.getResource("/"));
		     String filepath = "D:\\配置文件修改\\";
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
	
	    
	        
	    private static void getElement(Element element){    
	        List list = element.elements();    
	        //递归方法     
	        for(Iterator its =  list.iterator();its.hasNext();){    
	            Element chileEle = (Element)its.next();    
//	            System.out.println("节点："+chileEle.getName()+",内容："+chileEle.attributeValue("id"));  
	            try {
	            	TestPropertiesManager.getProperties(chileEle.getName(),"daib.002.001.01");   //取得配置文件中属性的值!!
				} catch (Exception e) {
					System.out.println("错误节点："+chileEle.getName());
				}
	            getElement(chileEle);    
	        }    
	    }     

	
	public static void main(String[] args) {
//		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"><Order><Cid>456</Cid><Pwd>密码</Pwd><Pid>商品单号</Pid><Price>商品价格</Price></Order>";
	    
        Document doc = null;    
        try {    
            doc = new SAXReader().read(new File("d:\\xml xsd svn0809\\xml xsd svn0805\\xml\\删除记录\\daib.002.001.01.xml"));    
        } catch (DocumentException e) {    
            e.printStackTrace();    
        }    
       Element root = doc.getRootElement();    
       System.out.println("根节点："+root.getName()+",内容："+root.attributeValue("id"));    
  
       getElement(root);    
            
//		 
	 }
}
