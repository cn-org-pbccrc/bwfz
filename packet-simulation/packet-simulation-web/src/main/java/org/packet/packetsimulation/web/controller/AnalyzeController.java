package org.packet.packetsimulation.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.core.domain.MessageHead;
import org.packet.packetsimulation.facade.AnalyzeFacade;
import org.packet.packetsimulation.facade.dto.ProjectDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

@Controller
@RequestMapping("/Analyze")
public class AnalyzeController {
	@Inject	
	private AnalyzeFacade analyzeFacade;
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(@RequestParam(value="pathOfMesg",required=false)  String pathOfMesg,@RequestParam int page, @RequestParam int pagesize) throws IOException{
		Page<MessageHead> all = analyzeFacade.showAnalyze(pathOfMesg,page,pagesize);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable int id,@RequestParam(value="pathOfMesg",required=false) String pathOfMesg) throws IOException, ParserConfigurationException, SAXException{
		return analyzeFacade.getAnalyze(id,pathOfMesg);	
	}
	
	@ResponseBody
	@RequestMapping("/getXml/{id}")
	public InvokeResult getXml(@PathVariable int id,@RequestParam(value="pathOfMesg",required=false) String pathOfMesg) throws Exception{
		return analyzeFacade.getOriginXml(id,pathOfMesg);	
	}

    @InitBinder    
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
        dateFormat.setLenient(false);    
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
        //CustomDateEditor 可以换成自己定义的编辑器。  
        //注册一个Date 类型的绑定器 。
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }
	
}
