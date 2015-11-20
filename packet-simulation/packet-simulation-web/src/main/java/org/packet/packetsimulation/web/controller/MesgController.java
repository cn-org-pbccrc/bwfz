package org.packet.packetsimulation.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.MesgFacade;
import org.packet.packetsimulation.facade.dto.MesgDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Mesg")
public class MesgController {
		
	@Inject
	private MesgFacade mesgFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(MesgDTO mesgDTO,HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		return mesgFacade.creatMesg(mesgDTO,realPath);
	}
	
	@ResponseBody
	@RequestMapping("/batch")
	public InvokeResult batch(MesgDTO mesgDTO,HttpServletRequest request,@RequestParam int batchNumber) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		return mesgFacade.creatMesgs(mesgDTO,realPath,batchNumber);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(MesgDTO mesgDTO,HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		return mesgFacade.updateMesg(mesgDTO,realPath);
	}
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/pageJson/{packetId}")
	public Page pageJson(MesgDTO mesgDTO, @RequestParam int page, @RequestParam int pagesize,@PathVariable Long packetId) {
		Page<MesgDTO> all = mesgFacade.pageQueryMesg(mesgDTO, page, pagesize,packetId);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public InvokeResult remove(@RequestParam String ids) {
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i ++) {
        	        					idArrs[i] = Long.parseLong(value[i]);
						        }
        return mesgFacade.removeMesgs(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return mesgFacade.getMesg(id);
	}
	
	@ResponseBody
	@RequestMapping("/initUpdate/{id}")
	public InvokeResult initUpdate(@PathVariable Long id) {
		return mesgFacade.getMesgForUpdate(id);
	}
	
	@ResponseBody
	@RequestMapping("/initBatch/{id}")
	public InvokeResult initBatch(@PathVariable Long id) {
		return mesgFacade.getMesgForBatch(id);
	}
	
//	@ResponseBody
//	@RequestMapping("/findPacketByMesg/{id}")
//	public Map<String, Object> findPacketByMesg(@PathVariable Long id) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("data", mesgFacade.findPacketByMesg(id));
//		return result;
//	}
//
//	@ResponseBody
//	@RequestMapping("/findMesgTypeByMesg/{id}")
//	public Map<String, Object> findMesgTypeByMesg(@PathVariable Long id) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("data", mesgFacade.findMesgTypeByMesg(id));
//		return result;
//	}

	
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
