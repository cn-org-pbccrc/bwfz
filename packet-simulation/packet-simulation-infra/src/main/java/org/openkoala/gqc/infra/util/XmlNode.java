/**
 * 
 */
package org.openkoala.gqc.infra.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
		String contentStr="<div class='tab-content' id='myTabContent'>";
		String tabClass="active";
		String contentClass="tab-pane fade active in";
		List<XmlNode> tabNodes=nodes.get(0).getNodes();
		if(null!=tabNodes && tabNodes.size()>0){
			for(XmlNode xmlNode : tabNodes){
				List<XmlNode> childNode = xmlNode.getNodes();
				result = result + "<li class='"+tabClass+"'><a data-toggle='tab' href='#"+xmlNode.getTagName()+"'>"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+"</a></li>";
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
						htmlStr = htmlStr + "<p><label class='rgt'>"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+" :</label><label class='lft'>" + xmlNode.getValue() + "</label></p>";
						htmlStr = htmlStr + getTabContents(peerNodes,xmlNode.getTagName(),templateName);	
					}else{
						String value = xmlNode.getValue();
						//System.out.println("value:"+value);
						if(value==null){
							value="";
						}
						//System.out.println("name2:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value2:"+value);
						htmlStr = htmlStr + "<p><label class='rgt'>"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+" :</label><label class='lft'>" + value + "</label></p>";
					}
				}else if(null!= childNode && childNode.size()>0){
					htmlStr = htmlStr + "<fieldset><legend>"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+"</legend>";
					for (XmlNode node:childNode) {
						List<XmlNode> childNodes = node.getNodes();
						if(null!= childNodes && childNodes.size()>0){
							//System.out.println("name3:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value3:"+xmlNode.getValue());
							htmlStr = htmlStr + getTabContents(childNodes,null,templateName);
						}else{
							//System.out.println("name4:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value4:"+xmlNode.getValue());
							htmlStr = htmlStr + "<p><label class='rgt'>"+PropertiesManager.getProperties(node.getTagName(),templateName)+" :</label><label class='lft'>" + node.getValue() + "</label></p>";
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
		String tabClass="active";
		String contentClass="tab-pane fade active in";
		//System.out.println("看看到底nodes是多少个:"+nodes.size());
		//System.out.println("看看到底nodes(0)是啥:"+nodes.get(0).getTagName());
		List<XmlNode> tabNodes=nodes.get(0).getNodes();
		String contentStr="<div class='tab-content' id='"+nodes.get(0).getTagName()+"'>";
		if(null!=tabNodes && tabNodes.size()>0){
			for(XmlNode xmlNode : tabNodes){
				List<XmlNode> childNode = xmlNode.getNodes();
				result = result + "<li class='"+tabClass+"'><a data-toggle='tab' href='#"+xmlNode.getTagName()+"'>"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName) + "<button type='button' style='padding:2px 4px;' class='btn btn-failure' onclick='removeTab(this,\"" + xmlNode.getTagName() + "\");'><span class='glyphicon glyphicon-remove'><span></button></a></li>";
				tabClass="";
				if(null!= childNode && childNode.size()>0){
					contentStr=contentStr+"<div id='"+xmlNode.getTagName()+"' class='"+contentClass+"'>";
					contentClass="tab-pane fade";
					contentStr=contentStr+TAB_TABLE_TAG + TR_TAG;
					contentStr=contentStr+getEditTabContents(childNode,false,null,templateName);
					contentStr=contentStr+TR_ENDTAG+TABLE_ENDTAG+DIV_ENDTAG;
				}
			}
		}
		result=result + UL_ENDTAG;
		contentStr=contentStr+DIV_ENDTAG;
		return result+contentStr;
	}
	
	
	private String getEditTabContents(List<XmlNode> nodes,boolean hasPeerNode,String countTagId,String templateName){
		if(null!=nodes && nodes.size()>0){
			
			String htmlStr = "";
			int index = 1;
			for(XmlNode xmlNode : nodes){
				List<XmlNode> childNode = xmlNode.getNodes();
				//if(xmlNode.getRowspan()==1 && childNode.size()<=0){
				if(childNode.size()<=0){
					List<XmlNode> peerNodes = xmlNode.getPeerNodes();
					if(null!=peerNodes && peerNodes.size()>0){
						//System.out.println("name01:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value01:"+xmlNode.getValue());
						htmlStr = htmlStr + "<p><label class='rgt'>"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+" :</label><label class='lft'><input type='text' value='" + xmlNode.getValue() + "' name='"+xmlNode.getTagName()+ "' class='form-control' readonly id='"+ xmlNode.getTagName() +"' /></label>";
						htmlStr = htmlStr + "<button type='button' style='padding:2px 4px;' class='btn btn-primary' onclick='cloneHtml(this,\"" + xmlNode.getTagName() + "\");'><span class='glyphicon glyphicon-plus'><span></button></p><div id='"+ xmlNode.getTagName() +"_div'>";
						htmlStr = htmlStr + getEditTabContents(peerNodes,true,xmlNode.getTagName(),templateName) + "</div>";
					}else{
						String value = xmlNode.getValue();
						if(value==null){
							value="";
						}
						//System.out.println("name02:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value02:"+value);
						htmlStr = htmlStr + "<p><label class='rgt'>"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+" :</label><label class='lft'><input type='text' value='" + value + "' name='"+xmlNode.getTagName()+ "' class='form-control' /></label></p>";
					}
				}else if(null!= childNode && childNode.size()>0){
					if(hasPeerNode){
						htmlStr = htmlStr  
								+ "<fieldset><legend>"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)
							    + "<button type='button' style='padding:2px 4px;' class='btn btn-failure' name='" + xmlNode.getTagName() +"' onclick='removeHtml(this,\"" + countTagId + "\");'><span class='glyphicon glyphicon-remove'><span></button>"
								+ "</legend>";
						index = index + 1;
					}else{
						htmlStr = htmlStr + "<fieldset><legend>"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+"</legend>";
					}
					for (XmlNode node:childNode) {
						List<XmlNode> childNodes = node.getNodes();
						if(null!= childNodes && childNodes.size()>0){
							//System.out.println("name03:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value03:"+xmlNode.getValue());
							htmlStr = htmlStr + getEditTabContents(childNodes,false,null,templateName);
						}else{
							//System.out.println("name04:"+PropertiesManager.getProperties(xmlNode.getTagName(),templateName)+";value04:"+xmlNode.getValue());
							htmlStr = htmlStr + "<p><label class='rgt'>"+PropertiesManager.getProperties(node.getTagName(),templateName)+" :</label><label class='lft'><input type='text' value='" + node.getValue() + "' subName='"+node.getTagName()+ "' class='form-control' /></label></p>";
						}
					}
					htmlStr = htmlStr +"</fieldset>";
				}
			}
			return htmlStr;
		}
		return null;
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
