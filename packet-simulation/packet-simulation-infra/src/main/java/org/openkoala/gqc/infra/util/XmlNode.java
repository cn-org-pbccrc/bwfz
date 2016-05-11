/**
 * 
 */
package org.openkoala.gqc.infra.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author hbikai
 *
 */
public class XmlNode implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5770689415960061272L;

//	private static final String TABLE_TAG = "<table class='table table-bordered' style='width:88%;'>";
	
	private static final String TAB_TABLE_TAG = "<table class='table'>";
	
//	private static final String TAB_CONTENT_TAG = "<div class='tab-content' id='myTabContent'>";
	
	private static final String DIV_ENDTAG = "</div>";
	
	private static final String UL_TAG = "<ul class='nav nav-tabs' style='font-size:5px' id='myTab'>";
	
	private static final String TABLE_ENDTAG = "</table>";
	
	private static final String UL_ENDTAG = "</ul>";
	
	private static final String TR_TAG = "<tr>";
	
	private static final String TR_ENDTAG = "</tr>";
//	
//	private static final String TD_TAG = "<td>";
//	
//	private static final String TD_ENDTAG = "</td>";
//	
//	private static final String ROWSPAN = "<td  rowspan='";
//	
//	private static final String ROWSPAN_END = "'>";
	
	public XmlNode(){
		
	}
	
	private String tagName;
	
	private String path;
	
	private String cnName;
	
	/**
	 * 备注信息
	 */
	private String hint;
	
	/**
	 * 是否必填
	 */
	private boolean isNull;
	
	/**
	 * 节点类型
	 */
	private short nodeType;
	
	private String value;
	
	private List<XmlNode> nodes;
	
	private int rowspan;

	private String prototype;
	
	private List<XmlNode> peerNodes;
	
	private boolean isCountTag=false;
	
	private int peerNodeSize;
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getHint() {
		return hint;
	}

	public boolean isNull() {
		return isNull;
	}

	public short getNodeType() {
		return nodeType;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}

	public void setNodeType(short nodeType) {
		this.nodeType = nodeType;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public List<XmlNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<XmlNode> nodes) {
		this.nodes = nodes;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getPeerNodeSize() {
		return peerNodeSize;
	}

	public void setPeerNodeSize(int peerNodeSize) {
		this.peerNodeSize = peerNodeSize;
	}

	public String getPrototype() {
		return prototype;
	}

	public void setPrototype(String prototype) {
		this.prototype = prototype;
	}

	public List<XmlNode> getPeerNodes() {
		return peerNodes;
	}

	public void setPeerNodes(List<XmlNode> peerNodes) {
		this.peerNodes = peerNodes;
	}

	public boolean isCountTag() {
		return isCountTag;
	}

	public void setCountTag(boolean isCountTag) {
		this.isCountTag = isCountTag;
	}

//	public String toHtmlString(){
//		
//		String result = TABLE_TAG + TR_TAG;
//		if(null!=nodes && nodes.size()>0){
//			result=result + getHtmlParseNodes(nodes);
//		}
//		result=result + TABLE_ENDTAG;
//		return result;
//	}
	
	public String toHtmlTabString(String templateName){
		
		String result = UL_TAG;
		String contentStr="<div class='tab-content info' id='myTabContent'>";
		String tabClass="active";
		String contentClass="tab-pane fade active in";
		List<XmlNode> tabNodes=nodes.get(0).getNodes();
		if(null!=tabNodes && tabNodes.size()>0){
			for(XmlNode xmlNode : tabNodes){
				List<XmlNode> childNode = xmlNode.getNodes();
				result = result + "<li class='"+tabClass+"'><a data-toggle='tab' href='#"+xmlNode.getTagName()+"'>"+JSON.parseObject(PropertiesManager.getProperties(xmlNode.getTagName(),templateName)).getString("0")+"</a></li>";
				tabClass="";
				if(null!= childNode && childNode.size()>0){
					contentStr=contentStr+"<div id='"+xmlNode.getTagName()+"' class='"+contentClass+"'>";
					contentClass="tab-pane fade";
					contentStr=contentStr+TAB_TABLE_TAG + TR_TAG;
					contentStr=contentStr+getTabContents(childNode,null,templateName);
					contentStr=contentStr+TABLE_ENDTAG+DIV_ENDTAG;
				}
			}
		}
		result=result + UL_ENDTAG;
		contentStr=contentStr+DIV_ENDTAG;
		return result+contentStr;
	}
	
	
	private String getTabContents(List<XmlNode> nodes,String countTagId,String templateName){
		if(null!=nodes && nodes.size()>0){
			
			String htmlStr = "";
			for(XmlNode xmlNode : nodes){
				List<XmlNode> childNode = xmlNode.getNodes();
				//System.out.println("胡梅尔斯哈哈:"+xmlNode.getTagName()+";瓦拉内哈哈:"+xmlNode.getValue());
				//if(xmlNode.getRowspan()==1 && childNode.size()<=0){
				if(childNode.size()<=0){
					List<XmlNode> peerNodes = xmlNode.getPeerNodes();
					if(null!=peerNodes && peerNodes.size()>0){
						//System.out.println("name1:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value1:"+xmlNode.getValue());
						htmlStr = htmlStr + "<p><label class='rgt'>"+JSON.parseObject(PropertiesManager.getProperties(xmlNode.getTagName(),templateName)).getString("0")+" :</label><label class='lft'>" + xmlNode.getValue() + "</label></p>";
						htmlStr = htmlStr + getTabContents(peerNodes,xmlNode.getTagName(),templateName);	
					}else{
						String value = xmlNode.getValue();
						//System.out.println("value:"+value);
						if(value==null){
							value="";
						}
						//System.out.println("name2:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value2:"+value);
						htmlStr = htmlStr + "<p><label class='rgt'>"+JSON.parseObject(PropertiesManager.getProperties(xmlNode.getTagName(),templateName)).getString("0")+" :</label><label class='lft' value='" + value + "' name='" + xmlNode.getTagName() + "'>" + value + "</label></p>";
					}
				}else if(null!= childNode && childNode.size()>0){
					htmlStr = htmlStr + "<fieldset><legend name='"+xmlNode.getTagName()+"'>"+JSON.parseObject(PropertiesManager.getProperties(xmlNode.getTagName(),templateName)).getString("0")+"</legend>";
					for (XmlNode node:childNode) {
						List<XmlNode> childNodes = node.getNodes();
						if(null!= childNodes && childNodes.size()>0){
							//System.out.println("name3:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value3:"+xmlNode.getValue());
							htmlStr = htmlStr + getTabContents(childNodes,null,templateName);
						}else{
							//System.out.println("name4:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value4:"+xmlNode.getValue());
							htmlStr = htmlStr + "<p><label class='rgt'>"+JSON.parseObject(PropertiesManager.getProperties(node.getTagName(),templateName)).getString("0")+" :</label><label class='lft' value='" + node.getValue() + "' subName='" + node.getTagName() + "'>" + node.getValue() + "</label></p>";
						}
					}
					htmlStr = htmlStr +"</fieldset>";
				}
			}
			return htmlStr;
		}
		return null;
//		if(null!=nodes && nodes.size()>0){
//			
//			String htmlStr = "";
//			for(XmlNode xmlNode : nodes){
//				List<XmlNode> childNode = xmlNode.getNodes();
//				//htmlStr = htmlStr + "<p><label class='rgt'>"+PropertiesManager.getProperties(xmlNode.getTagName())+"</label><label class='lft'>"+xmlNode.getValue()+"</label></p>";
//				if(xmlNode.getRowspan()==1 && childNode.size()<=0){
//					htmlStr = htmlStr + "<p><label class='rgt'>"+PropertiesManager.getProperties(xmlNode.getTagName())+" :</label><label class='lft'>"+xmlNode.getValue()+"</label></p>";
//				}
//				
//				if(null!= childNode && childNode.size()>0){
//					htmlStr = htmlStr + "<fieldset><legend>"+PropertiesManager.getProperties(xmlNode.getTagName())+"</legend>";
//					for (XmlNode node:childNode) {
//						htmlStr = htmlStr + "<p><label class='rgt'>"+PropertiesManager.getProperties(node.getTagName())+" :</label><label class='lft'>"+node.getValue()+"</label></p>";
//					}
//					htmlStr = htmlStr +"</fieldset>";
//					
//				}
//			}
//			return htmlStr;
//		}
//		return null;
	}
	

//	private String getHtmlParseNodes(List<XmlNode> nodes){
//
//		if(null!=nodes && nodes.size()>0){
//			
//			String htmlStr = "";
//			for(XmlNode xmlNode : nodes){
//
//		htmlStr = htmlStr + ROWSPAN + xmlNode.getRowspan() + ROWSPAN_END + xmlNode.getTagName() + TD_ENDTAG;
//				List<XmlNode> childNode = xmlNode.getNodes();
//				htmlStr = htmlStr + "<td  style='word-break: keep-all;white-space:nowrap;background:#C0C0C0'  rowspan='" + xmlNode.getRowspan() + "'>" + PropertiesManager.getProperties(xmlNode.getTagName()) + TD_ENDTAG;
//				if(xmlNode.getRowspan()==1 && childNode.size()<=0){
//					htmlStr = htmlStr + TD_TAG + xmlNode.getValue() + TD_ENDTAG + TR_ENDTAG;
//				}
//				
//				if(null!= childNode && childNode.size()>0){
//					htmlStr = htmlStr + getHtmlParseNodes(childNode);
//				}
//			}
//			return htmlStr;
//		}
//		return null;
//	}
	
//	public String toEditHtmlString(){
//		
//		String result = TABLE_TAG + TR_TAG;
//		if(null!=nodes && nodes.size()>0){
//			result=result + getEditHtmlParseNodes(nodes);
//		}
//		result=result + TABLE_ENDTAG;
//		return result;
//	}
//	
//	private String getEditHtmlParseNodes(List<XmlNode> nodes){
//		if(null!=nodes && nodes.size()>0){
//			
//			String htmlStr = "";
//			for(XmlNode xmlNode : nodes){
//
//				htmlStr = htmlStr + ROWSPAN + xmlNode.getRowspan() + ROWSPAN_END + xmlNode.getTagName() + TD_ENDTAG;
//				List<XmlNode> childNode = xmlNode.getNodes();
//				if(xmlNode.getRowspan()==1 && childNode.size()<=0){
//					htmlStr = htmlStr + TD_TAG + "<input type='text' value='" + xmlNode.getValue() + "' name='nodeValues' class='form-control' />" + TD_ENDTAG + TR_ENDTAG + "\n";
//				}
//				
//				if(null!= childNode && childNode.size()>0){
//					htmlStr = htmlStr + getEditHtmlParseNodes(childNode);
//				}
//			}
//			return htmlStr;
//		}
//		return null;
//	}
	
	public String toXMLString(){
		String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		result=result + "<" + tagName;
		if(null!=prototype && !"".equals(prototype)){
			result=result + prototype;
		}
		result=result + ">\n";
		
		if(null!=nodes && nodes.size()>0){
			result=result + parseNodes(nodes);
		}
		result=result + "</" + tagName + ">\n";
		
		return result;
	}
	
	private String parseNodes(List<XmlNode> nodes){
		if(null!=nodes && nodes.size()>0){
			String xmlStr = "";
			for(XmlNode xmlNode : nodes){
				xmlStr = xmlStr + "<" + xmlNode.getTagName() + ">";
				xmlStr = xmlStr + xmlNode.getValue();
				
				List<XmlNode> peerNodes = xmlNode.getPeerNodes();
				if(null!= peerNodes && peerNodes.size()>0){
					xmlStr = xmlStr + "</" + xmlNode.getTagName() + ">\r\n";
					xmlStr = xmlStr + parseNodesNoStruct(peerNodes);
				}
				
				List<XmlNode> childNode = xmlNode.getNodes();
				if(null!= childNode && childNode.size()>0){
					xmlStr = xmlStr + parseNodes(childNode);
				}
				
				if(null== peerNodes || peerNodes.size()<=0){
					xmlStr = xmlStr + "</" + xmlNode.getTagName() + ">\r\n";
				}
			}
			return xmlStr;
		}
		return null;
	}
	
	public String toXMLStringNoStruct(){
		String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		result=result + "<" + tagName;
		if(null!=prototype && !"".equals(prototype)){
			result=result + prototype;
		}
		result=result + ">";
		
		if(null!=nodes && nodes.size()>0){
			result=result + parseNodesNoStruct(nodes);
		}
		result=result + "</" + tagName + ">";
		
		return result;
	}
	
	private String parseNodesNoStruct(List<XmlNode> nodes){
		if(null!=nodes && nodes.size()>0){
			String xmlStr = "";
			for(XmlNode xmlNode : nodes){
				xmlStr = xmlStr + "<" + xmlNode.getTagName() + ">";
				if(xmlNode.getValue()!=null&&!"\n".equals(xmlNode.getValue().trim()) && "\n"!=xmlNode.getValue().trim()){
					xmlStr = xmlStr + xmlNode.getValue().trim();
				}
				
				List<XmlNode> peerNodes = xmlNode.getPeerNodes();
				if(null!= peerNodes && peerNodes.size()>0){
					xmlStr = xmlStr + "</" + xmlNode.getTagName() + ">";
					xmlStr = xmlStr + parseNodesNoStruct(peerNodes);
				}
				
				List<XmlNode> childNode = xmlNode.getNodes();
				if(null!= childNode && childNode.size()>0){
					xmlStr = xmlStr + parseNodesNoStruct(childNode);
				}
				
				if(null== peerNodes || peerNodes.size()<=0){
					xmlStr = xmlStr + "</" + xmlNode.getTagName() + ">";
				}
			}
			return xmlStr;
		}
		return null;
	}
	
	public void setValues(String[] values) throws CloneNotSupportedException{
		
		if(null!=values && values.length>0){
			int valueIndex = 0;
			if(null!=nodes && nodes.size()>0){
				 setNodeValues(nodes,valueIndex,values);
			}
		}
	}
	
	private int setNodeValues(List<XmlNode> nodes,int valueIndex,String[] values) throws CloneNotSupportedException{
		if(null!=nodes && nodes.size()>0){
			int p=1;
			for(XmlNode xmlNode : nodes){
				
				List<XmlNode> childNode = xmlNode.getNodes();
				
				if(xmlNode.getRowspan()==1 && childNode.size()==0){
					xmlNode.setValue(values[valueIndex]);
					valueIndex=valueIndex+1;
					xmlNode.setCnName(p++ + "");
					List<XmlNode> peerNodes = xmlNode.getPeerNodes();
					if(null!= peerNodes && peerNodes.size()>0){
						int peerNodeSize = Integer.valueOf(values[valueIndex-1]);
						
						xmlNode.setPeerNodeSize(peerNodeSize);
						if(peerNodeSize!=peerNodes.size()){
							XmlNode peerNode = peerNodes.get(0);
							peerNodes.clear();;
							peerNodes.add(peerNode);
							for(int i=1;i<peerNodeSize;i++){
								peerNodes.add((XmlNode) peerNode.clone());
							}
							xmlNode.setPeerNodes(peerNodes);
						}
						valueIndex = setNodeValues(peerNodes,valueIndex,values);
					}
				}
				
				if(null!= childNode && childNode.size()>0){
					valueIndex = setNodeValues(childNode,valueIndex,values);
				}
			}
		}
		return valueIndex;
	}
	
	public String toEditHtmlTabString(String templateName){
		String result = UL_TAG;
		//String result = "<!DOCTYPE html><html><head lang='en'><meta charset='UTF-8'><link href='"+realPath+"/lib/bootstrap/css/bootstrap.min.css' rel='stylesheet'></head><body>"+UL_TAG;
		String tabClass="active";
		String contentClass="tab-pane fade active in true";
		//System.out.println("看看到底nodes是多少个:"+nodes.size());
		//System.out.println("看看到底nodes(0)是啥:"+nodes.get(0).getTagName());
		List<XmlNode> tabNodes=nodes.get(0).getNodes();
		String contentStr="<div class='tab-content info' id='"+nodes.get(0).getTagName()+"'>";
		if(null!=tabNodes && tabNodes.size()>0){
			for(XmlNode xmlNode : tabNodes){
				JSONObject jsonObject = JSON.parseObject(PropertiesManager.getProperties(xmlNode.getTagName(),templateName));
				List<XmlNode> childNode = xmlNode.getNodes();
				result = result + "<li class='"+tabClass+"'><a data-toggle='tab' href='#"+xmlNode.getTagName()+"'>"+jsonObject.getString("0") + "<span class='required'>" + jsonObject.getString("2") + "</span><button type='button' style='padding:2px 4px;display:none;' class='btn btn-link' onclick='removeTab(this,\"" + xmlNode.getTagName() + "\");'><span class='glyphicon glyphicon-check'><span></button></a></li>";
				tabClass="";
				if(null!= childNode && childNode.size()>0){
					contentStr=contentStr+"<div id='"+xmlNode.getTagName()+"' class='"+contentClass+"'>";
					contentClass="tab-pane fade true";
					contentStr=contentStr+TAB_TABLE_TAG + TR_TAG;
					contentStr=contentStr+getEditTabContents(childNode,false,null,templateName);
					contentStr=contentStr+TR_ENDTAG+TABLE_ENDTAG+DIV_ENDTAG;
				}
			}
		}
		result=result + UL_ENDTAG;
		//contentStr=contentStr+DIV_ENDTAG;
		//contentStr=contentStr+DIV_ENDTAG+"<script src='"+realPath+"/lib/jquery-1.11.3.min.js'></script><script src='"+realPath+"/lib/Koala_ToolTip.js'></script><script>$(function(){$('[data-toggle=\"tooltip\"]').tooltip();});</script></body></html>";
		contentStr=contentStr+DIV_ENDTAG+"<script>$(function(){$('[data-toggle=\"tooltip\"]').tooltip();});</script>";
		//System.out.println("臭米米33333333333333333333333:"+result+contentStr);
		return result+contentStr;
	}
	
	private String getEditTabContents(List<XmlNode> nodes,boolean hasPeerNode,String countTagId,String templateName){
		if(null!=nodes && nodes.size()>0){			
			String htmlStr = "";
			int index = 1;
			for(XmlNode xmlNode : nodes){
				List<XmlNode> childNode = xmlNode.getNodes();
				JSONObject jsonObject = JSON.parseObject(PropertiesManager.getProperties(xmlNode.getTagName(),templateName));
				if(childNode.size()<=0){
					List<XmlNode> peerNodes = xmlNode.getPeerNodes();
					if(null!=peerNodes && peerNodes.size()>0){
						//System.out.println("name01:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value01:"+xmlNode.getValue());
						htmlStr = htmlStr + "<p><label class='rgt'>"+jsonObject.getString("0")+" :</label><label class='lft'><input type='text' value='" + xmlNode.getValue() + "' name='"+xmlNode.getTagName()+ "' save='true' class='form-control' readonly id='"+ xmlNode.getTagName() +"'/><span class='required'>"+jsonObject.getString("2")+"</span></label>";
						htmlStr = htmlStr + "<button type='button' style='padding:2px 4px;' class='btn btn-primary' onclick='cloneHtml(this,\"" + xmlNode.getTagName() + "\");'><span class='glyphicon glyphicon-plus'></span></button></p><div id='"+ xmlNode.getTagName() +"_div'>";
						htmlStr = htmlStr + getEditTabContents(peerNodes,true,xmlNode.getTagName(),templateName) + "</div>";
					}else{
						String value = xmlNode.getValue();
						if(value==null){
							value="";
						}
						//System.out.println("name02:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value02:"+value);
						htmlStr = htmlStr + "<p><label class='rgt'>"+jsonObject.getString("0")+" :</label><label class='lft'><input type='text' value='" + value + "' name='"+xmlNode.getTagName()+ "' save='true' class='form-control' data-toggle='tooltip' data-placement='right' title='"+jsonObject.getString("1")+"' onFocus='if (value ==\""+value+"\"){value =\"\"}'/><span class='required'>"+jsonObject.getString("2")+"</span></label><button type='button' style='padding:2px 4px;display:none' class='btn btn-link' onclick='removeField(this);'><span class='glyphicon glyphicon-check'><span></button></p>";
					}
				}else if(null!= childNode && childNode.size()>0){
					if(hasPeerNode){
						htmlStr = htmlStr  
								+ "<fieldset><legend>"+jsonObject.getString("0")
							    + "<button type='button' style='padding:2px 4px' class='btn btn-failure' name='" + xmlNode.getTagName() +"' onclick='removeHtml(this,\"" + countTagId + "\");'><span class='glyphicon glyphicon-remove'></span></button>"
								+ "</legend>";
						index = index + 1;
					}else{
						htmlStr = htmlStr + "<fieldset><legend>"+jsonObject.getString("0")+"</legend>";
					}
					for (XmlNode node:childNode) {
						List<XmlNode> childNodes = node.getNodes();
						if(null!= childNodes && childNodes.size()>0){
							//System.out.println("name03:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value03:"+xmlNode.getValue());
							htmlStr = htmlStr + getEditTabContents(childNodes,false,null,templateName);
						}else{
							JSONObject object = JSON.parseObject(PropertiesManager.getProperties(node.getTagName(),templateName));
							//System.out.println("name04:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value04:"+xmlNode.getValue());
							htmlStr = htmlStr + "<p><label class='rgt'>"+object.getString("0")+" :</label><label class='lft'><input type='text' value='" + node.getValue() + "' subName='"+node.getTagName()+ "' save='true' class='form-control' data-toggle='tooltip' data-placement='right' title='"+object.getString("1")+"' onFocus='if (value ==\""+node.getValue()+"\"){value =\"\"}'/><span class='required'>"+object.getString("2")+"</span></label><button type='button' style='padding:2px 4px;display:none' class='btn btn-link' onclick='removeField(this,\"" + node.getTagName() + "\");'><span class='glyphicon glyphicon-check'><span></button></p>";
						}
					}
					htmlStr = htmlStr +"</fieldset>";
				}
			}
			return htmlStr;
		}
		return null;
	}
	
	public String toEditHtmlTabStringForUpdate(String templateName, XmlNode xmlNodeForUpdate){
		//System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaa:"+xmlNodeForUpdate.nodes.get(0).getNodes().get(0).getTagName());
		//System.out.println("haaaaaaaaaaaaaaaaaaaaaaaaaa:"+xmlNodeForUpdate.nodes.get(0).getNodes().get(1).getTagName());
		List<String> tabs = new ArrayList<String>();
		for(int i = 0; i < xmlNodeForUpdate.nodes.get(0).getNodes().size(); i++){
			tabs.add(xmlNodeForUpdate.nodes.get(0).getNodes().get(i).getTagName());
		}
		String result = UL_TAG;
		//String result = "<!DOCTYPE html><html><head lang='en'><meta charset='UTF-8'><link href='"+realPath+"/lib/bootstrap/css/bootstrap.min.css' rel='stylesheet'></head><body>"+UL_TAG;
		String tabClass="active";
		String showContentClass="tab-pane fade active in true";
		String contentClass="tab-pane fade false";
		//System.out.println("看看到底nodes是多少个:"+nodes.size());
		//System.out.println("看看到底nodes(0)是啥:"+nodes.get(0).getTagName());
		List<XmlNode> tabNodes=nodes.get(0).getNodes();
		String contentStr="<div class='tab-content info' id='"+nodes.get(0).getTagName()+"'>";
		int j = 0;
		if(null!=tabNodes && tabNodes.size()>0){
//			if(tabs.contains(tabNodes.get(0).getTagName())){
//				List<XmlNode> childNode = xmlNodeForUpdate.nodes.get(0).getNodes().get(j).getNodes();
//				List<XmlNode> templateNode = tabNodes.get(0).getNodes();
//				JSONObject jsonObject1 = JSON.parseObject(PropertiesManager.getProperties(xmlNodeForUpdate.nodes.get(0).getNodes().get(j).getTagName(),templateName));
//				result = result + "<li class='"+tabClass+"'><a data-toggle='tab' href='#"+xmlNodeForUpdate.nodes.get(0).getNodes().get(j).getTagName()+"'>"+jsonObject1.getString("0") + "<span class='required'>" + jsonObject1.getString("2") + "</span><button type='button' style='padding:2px 4px;display:none;' class='btn btn-link' onclick='removeTab(this,\"" + xmlNodeForUpdate.nodes.get(0).getNodes().get(j).getTagName() + "\");'><span class='glyphicon glyphicon-check'><span></button></a></li>";
//				tabClass="";
//				if(null != childNode && childNode.size() > 0){
//					contentStr=contentStr+"<div id='"+xmlNodeForUpdate.nodes.get(0).getNodes().get(j).getTagName()+"' class='"+"tab-pane fade active in true"+"'>";
//					contentStr=contentStr+TAB_TABLE_TAG + TR_TAG;
//					contentStr=contentStr+getEditTabContentsForUpdate(childNode,templateNode,false,null,templateName);
//					contentStr=contentStr+TR_ENDTAG+TABLE_ENDTAG+DIV_ENDTAG;
//				}
//				j++;
//			}else{
//				List<XmlNode> childNode = tabNodes.get(0).getNodes();
//				JSONObject jsonObject2 = JSON.parseObject(PropertiesManager.getProperties(tabNodes.get(0).getTagName(),templateName));
//				result = result + "<li class='"+tabClass_hidden+"'><a data-toggle='tab' href='#"+tabNodes.get(0).getTagName()+"'>"+jsonObject2.getString("0") + "<span class='required'>" + jsonObject2.getString("2") + "</span><button type='button' style='padding:2px 4px;display:none;' class='btn btn-link' onclick='removeTab(this,\"" + tabNodes.get(0).getTagName() + "\");'><span class='glyphicon glyphicon-unchecked'><span></button></a></li>";
//				tabClass="";
//				if(null != childNode && childNode.size() > 0){
//					contentStr=contentStr+"<div id='"+tabNodes.get(0).getTagName()+"' class='"+contentClass+"'>";
//					contentClass="tab-pane fade false";
//					contentStr=contentStr+TAB_TABLE_TAG + TR_TAG;
//					contentStr=contentStr+getEditTabContents(childNode,false,null,templateName);
//					contentStr=contentStr+TR_ENDTAG+TABLE_ENDTAG+DIV_ENDTAG;
//				}
//			}
			for(int i = 0; i < tabNodes.size(); i++){				
				if(tabs.contains(tabNodes.get(i).getTagName())){
					List<XmlNode> childNode = xmlNodeForUpdate.nodes.get(0).getNodes().get(j).getNodes();
					List<XmlNode> templateNode = tabNodes.get(i).getNodes();
					JSONObject jsonObject3 = JSON.parseObject(PropertiesManager.getProperties(xmlNodeForUpdate.nodes.get(0).getNodes().get(j).getTagName(),templateName));
					result = result + "<li class='"+tabClass+"'><a data-toggle='tab' href='#"+xmlNodeForUpdate.nodes.get(0).getNodes().get(j).getTagName()+"'>"+jsonObject3.getString("0") + "<span class='required'>" + jsonObject3.getString("2") + "</span><button type='button' style='padding:2px 4px;display:none;' class='btn btn-link' onclick='removeTab(this,\"" + xmlNodeForUpdate.nodes.get(0).getNodes().get(j).getTagName() + "\");'><span class='glyphicon glyphicon-check'><span></button></a></li>";
					tabClass="";
					if(null != childNode && childNode.size() > 0){
						contentStr=contentStr+"<div id='"+xmlNodeForUpdate.nodes.get(0).getNodes().get(j).getTagName()+"' class='"+showContentClass+"'>";
						showContentClass="tab-pane fade true";
						contentStr=contentStr+TAB_TABLE_TAG + TR_TAG;
						contentStr=contentStr+getEditTabContentsForUpdate(childNode,templateNode,false,null,templateName);
						contentStr=contentStr+TR_ENDTAG+TABLE_ENDTAG+DIV_ENDTAG;
					}
					j++;
				}else{
					List<XmlNode> childNode = tabNodes.get(i).getNodes();
					JSONObject jsonObject4 = JSON.parseObject(PropertiesManager.getProperties(tabNodes.get(i).getTagName(),templateName));
					result = result + "<li class='' style='display:none'><a data-toggle='tab' href='#"+tabNodes.get(i).getTagName()+"'>"+jsonObject4.getString("0") + "<span class='required'>" + jsonObject4.getString("2") + "</span><button type='button' style='padding:2px 4px;display:none;' class='btn btn-link' onclick='removeTab(this,\"" + tabNodes.get(i).getTagName() + "\");'><span class='glyphicon glyphicon-unchecked'><span></button></a></li>";
					if(null != childNode && childNode.size() > 0){
						contentStr=contentStr+"<div id='"+tabNodes.get(i).getTagName()+"' class='"+contentClass+"'>";
						contentStr=contentStr+TAB_TABLE_TAG + TR_TAG;
						contentStr=contentStr+getEditTabContents(childNode,false,null,templateName);
						contentStr=contentStr+TR_ENDTAG+TABLE_ENDTAG+DIV_ENDTAG;
					}
				}				
			}
		}
		result=result + UL_ENDTAG;
		//contentStr=contentStr+DIV_ENDTAG;
		//contentStr=contentStr+DIV_ENDTAG+"<script src='"+realPath+"/lib/jquery-1.11.3.min.js'></script><script src='"+realPath+"/lib/Koala_ToolTip.js'></script><script>$(function(){$('[data-toggle=\"tooltip\"]').tooltip();});</script></body></html>";
		contentStr=contentStr+DIV_ENDTAG+"<script>$(function(){$('[data-toggle=\"tooltip\"]').tooltip();});</script>";
		//System.out.println("臭米米33333333333333333333333:"+result+contentStr);
		return result+contentStr;
	}
	
	private String getEditTabContentsForUpdate(List<XmlNode> nodes,List<XmlNode> templateNode,boolean hasPeerNode,String countTagId,String templateName){		
		if(null!=nodes && nodes.size()>0){
			List<String> names = new ArrayList<String>();
			List<XmlNode> subNodes = new ArrayList<XmlNode>();
			for(int i = 0; i < nodes.size(); i++){
				names.add(nodes.get(i).getTagName());					
			}
			for(int i = 0; i < templateNode.size(); i++){
				if(templateNode.get(i).getPeerNodes() != null){
					subNodes = templateNode.get(i).getPeerNodes().get(0).getNodes();
				}		
			}
			String htmlStr = "";
			int index = 1;
			int i = 0;
			for(int j = 0; j < templateNode.size(); j++){
				if(names.contains(templateNode.get(j).getTagName())){									
					List<XmlNode> childNode = nodes.get(i).getNodes();
					JSONObject jsonObject = JSON.parseObject(PropertiesManager.getProperties(nodes.get(i).getTagName(),templateName));
					if(childNode.size()<=0){
						List<XmlNode> peerNodes = nodes.get(i).getPeerNodes();							
						if(null!=peerNodes && peerNodes.size()>0){							
							//System.out.println("name01:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value01:"+xmlNode.getValue());
							htmlStr = htmlStr + "<p><label class='rgt'>"+jsonObject.getString("0")+" :</label><label class='lft'><input type='text' value='" + nodes.get(i).getValue() + "' name='"+nodes.get(i).getTagName()+ "' save='true' class='form-control' readonly id='"+ nodes.get(i).getTagName() +"'/><span class='required'>"+jsonObject.getString("2")+"</span></label>";
							htmlStr = htmlStr + "<button type='button' style='padding:2px 4px;' class='btn btn-primary' onclick='cloneHtml(this,\"" + nodes.get(i).getTagName() + "\");'><span class='glyphicon glyphicon-plus'></span></button></p><div id='"+ nodes.get(i).getTagName() +"_div'>";
							htmlStr = htmlStr + getEditTabContents(peerNodes,subNodes,true,nodes.get(i).getTagName(),templateName) + "</div>";
						}else{
							String value = nodes.get(i).getValue();
							if(value==null){
								value="";
							}
							//System.out.println("name02:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value02:"+value);
							htmlStr = htmlStr + "<p><label class='rgt'>"+jsonObject.getString("0")+" :</label><label class='lft'><input type='text' value='" + value + "' name='"+nodes.get(i).getTagName()+ "' save='true' class='form-control' data-toggle='tooltip' data-placement='right' title='"+jsonObject.getString("1")+"' onFocus='if (value ==\""+value+"\"){value =\"\"}'/><span class='required'>"+ jsonObject.getString("2")+"</span></label><button type='button' style='padding:2px 4px;display:none;' class='btn btn-link' onclick='removeField(this);'><span class='glyphicon glyphicon-check'><span></button></p>";
						}
					}else if(null!= childNode && childNode.size()>0){
						if(hasPeerNode){
							htmlStr = htmlStr
									+ "<fieldset><legend>"+jsonObject.getString("0")
								    + "<button type='button' style='padding:2px 4px;' class='btn btn-failure' name='" + nodes.get(i).getTagName() +"' onclick='removeHtml(this,\"" + countTagId + "\");'><span class='glyphicon glyphicon-remove'></span></button>"
									+ "</legend>";
							index = index + 1;
						}else{
							htmlStr = htmlStr + "<fieldset><legend>"+jsonObject.getString("0")+"</legend>";
						}
						for (XmlNode node:childNode) {
							List<XmlNode> childNodes = node.getNodes();
							if(null!= childNodes && childNodes.size()>0){
								//System.out.println("name03:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value03:"+xmlNode.getValue());
								htmlStr = htmlStr + getEditTabContentsForUpdate(childNodes,templateNode,false,null,templateName);
							}else{
								//System.out.println("name04:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value04:"+xmlNode.getValue());
								JSONObject object = JSON.parseObject(PropertiesManager.getProperties(node.getTagName(),templateName));
								htmlStr = htmlStr + "<p><label class='rgt'>"+object.getString("0")+" :</label><label class='lft'><input type='text' value='" + node.getValue() + "' subName='"+node.getTagName()+ "' save='true' class='form-control' data-toggle='tooltip' data-placement='right' title='"+object.getString("1")+"' onFocus='if (value ==\""+node.getValue()+"\"){value =\"\"}'/><span class='required'>"+object.getString("2") + "</span></label><button type='button' style='padding:2px 4px;display:none;' class='btn btn-link' onclick='removeField(this,\"" + node.getTagName() + "\");'><span class='glyphicon glyphicon-check'><span></button></p>";
							}
						}
						htmlStr = htmlStr +"</fieldset>";
					}
					i++;
				} else {
					String value = templateNode.get(j).getValue();
					if(value==null){
						value="";
					}
					JSONObject jsonObject2 = JSON.parseObject(PropertiesManager.getProperties(templateNode.get(j).getTagName(),templateName));
					htmlStr = htmlStr + "<p style='display:none'><label class='rgt'>"+jsonObject2.getString("0")+" :</label><label class='lft'><input type='text' value='" + value + "' name='"+templateNode.get(j).getTagName()+ "' save='false' class='form-control' data-toggle='tooltip' data-placement='right' title='"+jsonObject2.getString("1")+"' onFocus='if (value ==\""+value+"\"){value =\"\"}'/><span class='required'>"+jsonObject2.getString("2") + "</span></label><button type='button' style='padding:2px 4px;display:none;' class='btn btn-link' onclick='removeField(this);'><span class='glyphicon glyphicon-unchecked'><span></button></p>";					
				}
			}
			return htmlStr;
		}
		return null;
	}
	
	private String getEditTabContents(List<XmlNode> nodes,List<XmlNode> subNodes,boolean hasPeerNode,String countTagId,String templateName){		
		String htmlStr = "";
		for(XmlNode xmlNode : nodes){
			List<XmlNode> childNode = xmlNode.getNodes();
			JSONObject jsonObject1 = JSON.parseObject(PropertiesManager.getProperties(xmlNode.getTagName(),templateName));
			if(hasPeerNode){
				htmlStr = htmlStr  
					+ "<fieldset><legend>"+jsonObject1.getString("0")
					+ "<button type='button' style='padding:2px 4px;' class='btn btn-failure' name='" + xmlNode.getTagName() +"' onclick='removeHtml(this,\"" + countTagId + "\");'><span class='glyphicon glyphicon-remove'></span></button>"
					+ "</legend>";
			}else{
				htmlStr = htmlStr + "<fieldset><legend>"+jsonObject1.getString("0")+"</legend>";
			}
			List<String> subNames = new ArrayList<String>(); 
			for (XmlNode node:childNode) {
				subNames.add(node.getTagName());
			}
			int j = 0;
			for(int i = 0; i < subNodes.size(); i++){
				if(subNames.contains(subNodes.get(i).getTagName())){
					List<XmlNode> childNodes = childNode.get(j).getNodes();
					if(null!= childNodes && childNodes.size()>0){
						//System.out.println("name03:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value03:"+xmlNode.getValue());
						htmlStr = htmlStr + getEditTabContents(childNodes,false,null,templateName);
					}else{
						//System.out.println("name04:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value04:"+xmlNode.getValue());
						JSONObject jsonObject2 = JSON.parseObject(PropertiesManager.getProperties(childNode.get(j).getTagName(),templateName));
						htmlStr = htmlStr + "<p><label class='rgt'>"+jsonObject2.getString("0")+" :</label><label class='lft'><input type='text' value='" + childNode.get(j).getValue() + "' subName='"+childNode.get(j).getTagName()+ "' save='true' class='form-control' data-toggle='tooltip' data-placement='right' title='"+jsonObject2.getString("1")+"' onFocus='if (value ==\""+childNode.get(j).getValue()+"\"){value =\"\"}'/><span class='required'>"+jsonObject2.getString("2")+"</span></label><button type='button' style='padding:2px 4px;display:none;' class='btn btn-link' onclick='removeField(this,\"" + childNode.get(j).getTagName() + "\");'><span class='glyphicon glyphicon-check'><span></button></p>";
						//System.out.println("擦擦擦:"+htmlStr);
					}
					j++;
				}else{
					JSONObject jsonObject3 = JSON.parseObject(PropertiesManager.getProperties(subNodes.get(i).getTagName(),templateName));
					htmlStr = htmlStr + "<p style='display:none'><label class='rgt'>"+jsonObject3.getString("0")+" :</label><label class='lft'><input type='text' value='" + subNodes.get(i).getValue() + "' subName='"+subNodes.get(i).getTagName()+ "' save='false' class='form-control' data-toggle='tooltip' data-placement='right' title='"+jsonObject3.getString("1")+"' onFocus='if (value ==\""+subNodes.get(i).getValue()+"\"){value =\"\"}'/><span class='required'>"+jsonObject3.getString("2")+"</span></label><button type='button' style='padding:2px 4px;display:none;' class='btn btn-link' onclick='removeField(this,\"" + subNodes.get(i).getTagName() + "\");'><span class='glyphicon glyphicon-unchecked'><span></button></p>";
				}						
			}
			htmlStr = htmlStr +"</fieldset>";
		}
		return htmlStr;
	}
	
	public XmlNode clone(){  
		XmlNode newXmlNode = null;  
		try{  
			newXmlNode = (XmlNode)super.clone(); 
			
			List<XmlNode> nodes2 = newXmlNode.getNodes();
			if(null!=nodes2 && nodes2.size()>0){
				List<XmlNode> nodesList = new ArrayList<XmlNode>();
				for(XmlNode xmlNode : nodes2){
					nodesList.add(xmlNode.clone());
				}
				newXmlNode.setNodes(nodesList);
			}
			
			List<XmlNode> peerNodes = newXmlNode.getPeerNodes();
			if(null!=peerNodes && peerNodes.size()>0){
				List<XmlNode> peerNodesList = new ArrayList<XmlNode>();
				for(XmlNode peerNode : peerNodes){
					peerNodesList.add(peerNode.clone());
				}
				newXmlNode.setPeerNodes(peerNodesList);
			}
			
		}catch(CloneNotSupportedException e){  
		    e.printStackTrace();  
		}  
		return newXmlNode;  
	}	

}
