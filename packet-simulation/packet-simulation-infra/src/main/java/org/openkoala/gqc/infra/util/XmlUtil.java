package org.openkoala.gqc.infra.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * xml操作工具
 * 
 * @author hbikai
 *
 */
public class XmlUtil {

	private static final String FOLDER = "/xmlTemplate/";

	private static final String XML_SUFFIX = ".xml";

	private static final String XSD_SUFFIX = ".xsd";

	private XmlUtil() {

	}
	
	/**
	 * 根据路径将xml转为xmlNode
	 * @param filePath
	 * @param realPath
	 * @param countTags
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static XmlNode getXmlNodeByXml(String filePath, String realPath, String countTags)
			throws SAXException, IOException, ParserConfigurationException {
		String[] countTag = null;
		if(null!=countTags && !"".equals(countTags)){
			countTag = countTags.split(",");
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		String fileName = realPath + FOLDER + filePath + XML_SUFFIX;
		Document doc = (Document) db.parse(new File(fileName));
		// 获得根元素结点
		Element root = ((org.w3c.dom.Document) doc).getDocumentElement();
		XmlNode xmlNode = new XmlNode();
		xmlNode = parseElement(root, xmlNode,countTag);
		return xmlNode;
	}
	
	/**
	 * 将xml字符串转为xmlNode
	 * @param xmlContent
	 * @param countTags
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static XmlNode getXmlNodeByXmlContent(String xmlContent,String countTags)
			throws SAXException, IOException, ParserConfigurationException {
		String[] countTag = null;
		if(null!=countTags && !"".equals(countTags)){
			countTag = countTags.split(",");
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlContent)));
		// 获得根元素结点
		Element root = ((org.w3c.dom.Document) doc).getDocumentElement();
		XmlNode xmlNode = new XmlNode();
		xmlNode = parseElement(root, xmlNode,countTag);
		return xmlNode;
	}
	
	/**
	 * 将xml字符串转为xmlNode
	 * @param xmlContent
	 * @param countTags
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static XmlNode getXmlNodeByXmlContent1(String xmlContent, String countTags, String templateName)
			throws SAXException, IOException, ParserConfigurationException {
		String[] countTag = null;
		if(null!=countTags && !"".equals(countTags)){
			countTag = countTags.split(",");
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlContent)));
		// 获得根元素结点
		Element root = ((org.w3c.dom.Document) doc).getDocumentElement();
		XmlNode xmlNode = new XmlNode();
		xmlNode = parseElement1(root, xmlNode, countTag, templateName);
		return xmlNode;
	}
	
	/**
	 * 递归将element的子孙节点转为xmlNode
	 * @param element 根节点元素
	 * @param xmlNode xmlNode对象
	 * @param countTag 重复域统计字段
	 * @return
	 */
	public static XmlNode parseElement1(Element element, XmlNode xmlNode,String[] countTag,String templateName) {
		String tagName = element.getNodeName();
		NodeList children = element.getChildNodes();
		xmlNode.setTagName(tagName);
		xmlNode.setNodeType(element.getNodeType());
		JSONObject object = JSON.parseObject(PropertiesManager.getProperties(tagName, templateName));
		xmlNode.setCnName(object.getString("0"));
		xmlNode.setHint(object.getString("1"));
		if(object.getString("2")!=null && !"".equals(object.getString("2"))){
			xmlNode.setNull(false);
		}else {
			xmlNode.setNull(true);
		}
		
		if ("Document".equals(tagName)) {
			// element元素的所有属性所构成的NamedNodeMap对象，需要对其进行判断
			String prototype = "";
			NamedNodeMap map = element.getAttributes();
			// 如果该元素存在属性
			if (null != map) {
				for (int i = 0; i < map.getLength(); i++) {
					// 获得该元素的每一个属性
					Attr attr = (Attr) map.item(i);
					String attrName = attr.getName();
					String attrValue = attr.getValue();
					//System.out.println("attrName:"+attrName+";attrValue:"+attrValue);
					prototype = prototype + " " + attrName + "=\"" + attrValue
							+ "\"";
				}
			}
			xmlNode.setPrototype(prototype);
		} else {
			if (xmlNode.getPath() == null) {
				xmlNode.setPath(tagName);
			} else {
				xmlNode.setPath(xmlNode.getPath() + "." + tagName);
			}
		}
		xmlNode.setRowspan(0);
		
		List<XmlNode> xmlNodes = new ArrayList<XmlNode>();
		if (children.getLength() == 1) {
			xmlNode.setRowspan(1);
		}
		List<XmlNode> peerNodes = new ArrayList<XmlNode>();
		int countIndex = 0;
		for (int i = 0; i < children.getLength(); i++) {
			
			Node node = children.item(i);
			//System.out.println("取所有结点值看一看:"+node.getNodeName()+";它的类型:"+node.getNodeType());
			// 获得结点的类型
			short nodeType = node.getNodeType();
			
			if (nodeType == Node.ELEMENT_NODE) {
				// 是元素，继续递归
				XmlNode childNode = new XmlNode();
				childNode.setPath(xmlNode.getPath());
				childNode = parseElement1((Element) node, childNode, countTag, templateName);
				
				if (children.getLength() != 1) {
					xmlNode.setRowspan(xmlNode.getRowspan() + childNode.getRowspan());
				}
				
				if(xmlNode.isCountTag() && countIndex<=(xmlNode.getPeerNodeSize())){
					peerNodes.add(childNode);
					//System.out.println("peerNode:"+childNode.getTagName());
					if((countIndex+1)==xmlNode.getPeerNodeSize()){
						xmlNode.setCountTag(false);
					}
					XmlNode countNode = xmlNodes.get(xmlNodes.size()-1);
					countNode.setPeerNodes(peerNodes);
					xmlNode.setNodes(xmlNodes);
					countIndex=countIndex+1;
				}else{
					xmlNodes.add(childNode);
				}
				
				if(null!=countTag){
					for(String countTagName : countTag){
						if(childNode.getTagName().equals(countTagName)){
							xmlNode.setCountTag(true);
							xmlNode.setPeerNodeSize(Integer.valueOf(childNode.getValue()));
						}
					}
				}
			} else if (nodeType == Node.TEXT_NODE) {
				// 递归出口
				xmlNode.setValue(node.getNodeValue());
				//System.out.println("所有标签:"+node.getTextContent());
				//System.out.println("递归出口:"+node.getNodeValue());
			}
			// else if(nodeType == Node.COMMENT_NODE) {
			// Comment comment = (Comment)node; //注释内容
			// String data = comment.getData();
		}
		xmlNode.setNodes(xmlNodes);
		return xmlNode;
	}
	/**
	 * 递归将element的子孙节点转为xmlNode
	 * @param element 根节点元素
	 * @param xmlNode xmlNode对象
	 * @param countTag 重复域统计字段
	 * @return
	 */
	public static XmlNode parseElement(Element element, XmlNode xmlNode,String[] countTag) {
		String tagName = element.getNodeName();
		NodeList children = element.getChildNodes();
		xmlNode.setTagName(tagName);

		if ("Document".equals(tagName)) {
			// element元素的所有属性所构成的NamedNodeMap对象，需要对其进行判断
			String prototype = "";
			NamedNodeMap map = element.getAttributes();
			// 如果该元素存在属性
			if (null != map) {
				for (int i = 0; i < map.getLength(); i++) {
					// 获得该元素的每一个属性
					Attr attr = (Attr) map.item(i);
					String attrName = attr.getName();
					String attrValue = attr.getValue();
					//System.out.println("attrName:"+attrName+";attrValue:"+attrValue);
					prototype = prototype + " " + attrName + "=\"" + attrValue
							+ "\"";
				}
			}
			xmlNode.setPrototype(prototype);
		} else {
			if (xmlNode.getPath() == null) {
				xmlNode.setPath(tagName);
			} else {
				xmlNode.setPath(xmlNode.getPath() + "." + tagName);
			}
		}
		xmlNode.setRowspan(0);

		List<XmlNode> xmlNodes = new ArrayList<XmlNode>(0);
		if (children.getLength() == 1) {
			xmlNode.setRowspan(1);
		}
		List<XmlNode> peerNodes = new ArrayList<XmlNode>(0);
		int countIndex = 0;
		for (int i = 0; i < children.getLength(); i++) {
			
			Node node = children.item(i);
			//System.out.println("取所有结点值看一看:"+node.getNodeName()+";它的类型:"+node.getNodeType());
			// 获得结点的类型
			short nodeType = node.getNodeType();

			if (nodeType == Node.ELEMENT_NODE) {
				// 是元素，继续递归
				XmlNode childNode = new XmlNode();
				childNode.setPath(xmlNode.getPath());
				childNode = parseElement((Element) node, childNode,countTag);
				
				if (children.getLength() != 1) {
					xmlNode.setRowspan(xmlNode.getRowspan() + childNode.getRowspan());
				}
				
				if(xmlNode.isCountTag() && countIndex<=(xmlNode.getPeerNodeSize())){
					peerNodes.add(childNode);
					//System.out.println("peerNode:"+childNode.getTagName());
					if((countIndex+1)==xmlNode.getPeerNodeSize()){
						xmlNode.setCountTag(false);
					}
					XmlNode countNode = xmlNodes.get(xmlNodes.size()-1);
					countNode.setPeerNodes(peerNodes);
					xmlNode.setNodes(xmlNodes);
					countIndex=countIndex+1;
				}else{
					xmlNodes.add(childNode);
				}
				
				if(null!=countTag){
					for(String countTagName : countTag){
						if(childNode.getTagName().equals(countTagName)){
							xmlNode.setCountTag(true);
							xmlNode.setPeerNodeSize(Integer.valueOf(childNode.getValue()));
						}
					}
				}
			} else if (nodeType == Node.TEXT_NODE) {
				// 递归出口
				xmlNode.setValue(node.getNodeValue());
				//System.out.println("所有标签:"+node.getTextContent());
				//System.out.println("递归出口:"+node.getNodeValue());
			}
			// else if(nodeType == Node.COMMENT_NODE) {
			// Comment comment = (Comment)node; //注释内容
			// String data = comment.getData();
		}
		xmlNode.setNodes(xmlNodes);
		return xmlNode;
	}

	/**
	 * 通过XSD（XML Schema）校验XML
	 * @param fileName
	 * @param realPath
	 * @param xmlContent
	 * @return
	 */
	public static String validateXMLByXSD(String fileName,String realPath,String xmlContent) {
		
		String xsdfile = realPath + FOLDER + fileName + XSD_SUFFIX;
		return null;
//		try {
//			// 创建默认的XML错误处理器
//			XMLErrorHandler errorHandler = new XMLErrorHandler();
//			// 获取基于 SAX 的解析器的实例
//			SAXParserFactory factory = SAXParserFactory.newInstance();
//			// 解析器在解析时验证 XML 内容。
//			factory.setValidating(true);
//			// 指定由此代码生成的解析器将提供对 XML 名称空间的支持。
//			factory.setNamespaceAware(true);
//			// 使用当前配置的工厂参数创建 SAXParser 的一个新实例。
//			SAXParser parser = factory.newSAXParser();
//			// 创建一个读取工具
//			SAXReader xmlReader = new SAXReader();
//			// 获取要校验xml文档实例
//			org.dom4j.Document xmlDocument = (org.dom4j.Document) xmlReader.read(new StringReader(xmlContent));
//			// 设置 XMLReader 的基础实现中的特定属性。核心功能和属性列表可以在
//			// [url]http://sax.sourceforge.net/?selected=get-set[/url] 中找到。
//			parser.setProperty(
//					"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
//					"http://www.w3.org/2001/XMLSchema");
//			parser.setProperty(
//					"http://java.sun.com/xml/jaxp/properties/schemaSource",
//					"file:" + xsdfile);
//			// 创建一个SAXValidator校验工具，并设置校验工具的属性
//			SAXValidator validator = new SAXValidator(parser.getXMLReader());
//			// 设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。
//			validator.setErrorHandler(errorHandler);
//			// 校验
//			validator.validate(xmlDocument);
//			
//			ByteArrayOutputStream errorMesgs = new ByteArrayOutputStream();
//			OutputFormat outputFormat = OutputFormat.createPrettyPrint();
//			outputFormat.setEncoding("UTF-8");
//			XMLWriter writer = new XMLWriter(errorMesgs,outputFormat); 
////			XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
//			
//			// 如果错误信息不为空，说明校验失败，打印错误信息
//			if (errorHandler.getErrors().hasContent()) {
////				System.out.println("XML文件通过XSD文件校验失败！");
//				org.dom4j.Element errors = errorHandler.getErrors();
//				Object data = errors.getData();
//				writer.write(errorHandler.getErrors());
//				writer.close();
//				return errorMesgs.toString();
//			} else {
//				return null;
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return ex.toString();
//		}
	}
	
	/**
	 * 校验xml
	 * @param xsdpath
	 * @param xmlpath
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public static boolean Validatexml(String xsdpath, String xmlpath)
			throws SAXException, IOException {
		// 建立schema工厂
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");
		// 建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
		File schemaFile = new File(xsdpath);
		// 利用schema工厂，接收验证文档文件对象生成Schema对象
		Schema schema = schemaFactory.newSchema(schemaFile);
		// 通过Schema产生针对于此Schema的验证器，利用schenaFile进行验证
		Validator validator = schema.newValidator();
		// 得到验证的数据源
		Source source = new StreamSource(xmlpath);
		// 开始验证，成功输出success!!!，失败输出fail
		try {
			validator.validate(source);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}
	
}
