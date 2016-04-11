package org.packet.packetsimulation.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.WebDataBinder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.MesgBatchFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/MesgBatch")
public class MesgBatchController {
		
	@Inject
	private MesgBatchFacade mesgBatchFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(MesgBatchDTO mesgBatchDTO, HttpServletRequest request) {
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "pocFiles" + File.separator;
		File file = new File(ctxPath);    	
		if (!file.exists()) {    	
			file.mkdirs();    	
		}	
		return mesgBatchFacade.creatMesgBatch(mesgBatchDTO, ctxPath);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(MesgBatchDTO mesgBatchDTO) {
		return mesgBatchFacade.updateMesgBatch(mesgBatchDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson/{currentUserId}")
	public Page pageJson(MesgBatchDTO mesgBatchDTO, @RequestParam int page, @RequestParam int pagesize, @PathVariable String currentUserId) {
		Page<MesgBatchDTO> all = mesgBatchFacade.pageQueryMesgBatch(mesgBatchDTO, page, pagesize, currentUserId);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public InvokeResult remove(@RequestParam String ids, HttpServletRequest request) {
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "pocFiles" + File.separator;
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i ++) {
        	        					idArrs[i] = Long.parseLong(value[i]);
						        }
        return mesgBatchFacade.removeMesgBatchs(idArrs, ctxPath);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return mesgBatchFacade.getMesgBatch(id);
	}
	
		
    @InitBinder    
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
        dateFormat.setLenient(false);    
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
        //CustomDateEditor 可以换成自己定义的编辑器。  
        //注册一个Date 类型的绑定器 。
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }
	
}
