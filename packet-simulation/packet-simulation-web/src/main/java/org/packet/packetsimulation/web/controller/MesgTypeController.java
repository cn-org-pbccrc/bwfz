package org.packet.packetsimulation.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.facade.MesgTypeFacade;
import org.packet.packetsimulation.facade.dto.MesgTypeDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/MesgType")
public class MesgTypeController {
		
	@Inject
	private MesgTypeFacade mesgTypeFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(MesgTypeDTO mesgTypeDTO) {
		return mesgTypeFacade.creatMesgType(mesgTypeDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(MesgTypeDTO mesgTypeDTO) {
		return mesgTypeFacade.updateMesgType(mesgTypeDTO);
	}
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(MesgTypeDTO mesgTypeDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<MesgTypeDTO> all = mesgTypeFacade.pageQueryMesgType(mesgTypeDTO, page, pagesize);
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
        return mesgTypeFacade.removeMesgTypes(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return mesgTypeFacade.getMesgType(id);
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
	
    @ResponseBody
	@RequestMapping("/findAllMesgType")
	public List<MesgTypeDTO> findAllMesgType() {
		return mesgTypeFacade.findAllMesgType();
	}
    
    @ResponseBody
	@RequestMapping("/findMesgTypes")
	public List<MesgType> findMesgTypes() {
		return mesgTypeFacade.findMesgTypes();
	}
    
//  @ResponseBody
//	@RequestMapping("/getEditHtmlByMesgType/{id}")
//	public InvokeResult getEditHtmlByMesgType(@PathVariable Long id,HttpServletRequest request) {
//    	String realPath = request.getSession().getServletContext().getRealPath("/");
//    	//System.out.println("米米二狗哇哈哈:"+realPath);
//    	return mesgTypeFacade.getEditHtmlByMesgType(id,realPath);
//	}
    
    @ResponseBody
	@RequestMapping("/getEditHtmlByMesgType/{id}")
	public InvokeResult getEditHtmlByMesgType(@PathVariable Long id) {
    	return mesgTypeFacade.getEditHtmlByMesgType(id);
	}
    
	/**
	 * 根据角色ID查询菜单权限资源树带有已经选中项。
	 *
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findMesgTypesByCreateUser")
	public List<MesgType> findMesgTypesByCreateUser(String userName) {
		return  mesgTypeFacade.findMesgTypesByCreateUser(userName);
		}
    
}
