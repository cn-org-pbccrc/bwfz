package org.packet.packetsimulation.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.shiro.CurrentUser;
import org.packet.packetsimulation.core.domain.MessageHead;
import org.packet.packetsimulation.facade.AnalyzeFacade;
import org.packet.packetsimulation.facade.FeedBackFacade;
import org.packet.packetsimulation.facade.dto.MesgTypeDTO;
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
@RequestMapping("/FeedBack")
public class FeedBackController {
	@Inject	
	private FeedBackFacade feedBackFacade;
	
	@ResponseBody
	@RequestMapping("/pageJson/{taskPacketId}")
	public Page pageJson(@PathVariable long taskPacketId, @RequestParam int page, @RequestParam int pagesize, HttpServletRequest request) throws IOException{
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "uploadFiles" + File.separator + "easySendFiles" + File.separator;
		Page<MessageHead> all = feedBackFacade.showAnalyze(taskPacketId,page,pagesize,ctxPath);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable int id, @RequestParam Long taskPacketId, HttpServletRequest request) throws IOException, ParserConfigurationException, SAXException{
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "uploadFiles" + File.separator + "easySendFiles" + File.separator;
		return feedBackFacade.getAnalyze(id,taskPacketId,ctxPath);	
	}
	
	@ResponseBody
	@RequestMapping("/getXml/{id}")
	public InvokeResult getXml(@PathVariable int id, @RequestParam Long taskPacketId, HttpServletRequest request) throws Exception{
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "uploadFiles" + File.separator + "easySendFiles" + File.separator;
		return feedBackFacade.getOriginXml(id,taskPacketId,ctxPath);	
	}
	
	@ResponseBody
	@RequestMapping("/initSend")
	public InvokeResult initSend(@RequestParam String code) {
		Map map = new HashMap();
        String packetHead = feedBackFacade.getPacketHeadForSend(code, CurrentUser.getUserAccount());
        map.put("packetHead", packetHead);
		return InvokeResult.success(map);
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
