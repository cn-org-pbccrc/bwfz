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
	public InvokeResult add(MesgDTO mesgDTO) {
		return mesgFacade.creatMesg(mesgDTO);
	}
	
	@ResponseBody
	@RequestMapping("/verifyMesgType")
	public InvokeResult verifyMesgType(@RequestParam String id) {		
		return mesgFacade.verifyMesgType(Long.parseLong(id));
	}
	
	@ResponseBody
	@RequestMapping("/batch")
	public InvokeResult batch(MesgDTO mesgDTO,HttpServletRequest request,@RequestParam String ids,@RequestParam String start,@RequestParam String end,@RequestParam String currentUserId) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		Long count = mesgFacade.queryCountOfThreeStandard(currentUserId);
		int countOfThreeStandard = Integer.parseInt(String.valueOf(count));
		if(start != null && !"".equals(start) && end != null && !"".equals(end)){
			int startOfThreeStandard = Integer.parseInt(start);
			int endOfThreeStandard = Integer.parseInt(end);
			if(startOfThreeStandard>=1&&startOfThreeStandard<=endOfThreeStandard&&endOfThreeStandard<=countOfThreeStandard){
				return mesgFacade.creatMesgsByInput(mesgDTO,startOfThreeStandard,endOfThreeStandard,currentUserId);
			}
			else if(startOfThreeStandard<1 || startOfThreeStandard>endOfThreeStandard || endOfThreeStandard>countOfThreeStandard){
				return InvokeResult.failure("起始值不能小于1,结束值不能大于三标总个数,且起始值不能大于结束值");
			}
		}else if((start == null || "".equals(start)) && (end == null || "".equals(end))){
			String[] values = ids.split(",");
			return mesgFacade.creatMesgs(mesgDTO,realPath,values);
		}else if((start == null || "".equals(start)) && (end != null || !"".equals(end))){			
			return InvokeResult.failure("不能起始为空,结束不为空");
		}else if((start != null || !"".equals(start)) && (end == null || "".equals(end))){
			return InvokeResult.failure("不能起始不为空,结束为空");
		}else{
			return InvokeResult.failure("批量失败");
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(MesgDTO mesgDTO) {
		return mesgFacade.updateMesg(mesgDTO);
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
