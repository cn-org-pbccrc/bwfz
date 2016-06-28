package org.packet.packetsimulation.web.controller;

import javax.inject.Inject;

import org.springframework.web.bind.WebDataBinder;

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
import org.packet.packetsimulation.facade.DictTypeFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/DictType")
public class DictTypeController {
		
	@Inject
	private DictTypeFacade dictTypeFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(DictTypeDTO dictTypeDTO) {
		if(dictTypeDTO.getCreateTime()==null){
			dictTypeDTO.setCreateTime(new Date());
		}
		return dictTypeFacade.creatDictType(dictTypeDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(DictTypeDTO dictTypeDTO) {
		DictTypeDTO olddata = (DictTypeDTO) dictTypeFacade.getDictType(dictTypeDTO.getId()).getData();
		if(dictTypeDTO.getMendTime()==null){
			dictTypeDTO.setMendTime(new Date());
		}
		dictTypeDTO.setCreateTime(olddata.getCreateTime());
		return dictTypeFacade.updateDictType(dictTypeDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(DictTypeDTO dictTypeDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<DictTypeDTO> all = dictTypeFacade.pageQueryDictType(dictTypeDTO, page, pagesize);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public InvokeResult remove(@RequestParam String id) {
//		String[] value = ids.split(",");
//        Long[] idArrs = new Long[value.length];
//        for (int i = 0; i < value.length; i ++) {
//        	        					idArrs[i] = Long.parseLong(value[i]);
//						        }
//        return dictTypeFacade.removeDictTypes(idArrs);
			return dictTypeFacade.deleteAllByParent(Long.parseLong(id));
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return dictTypeFacade.getDictType(id);
	}
	
	@ResponseBody
	@RequestMapping("/loadDictType")
	public InvokeResult loadDictType() {
		return InvokeResult.success(dictTypeFacade.loadDictType());
	//	return 
	}
	
	@ResponseBody
	@RequestMapping("/findDictItemSetByDictType/{id}")
	public Page findDictItemSetByDictType(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<DictItemDTO> all = dictTypeFacade.findDictItemSetByDictType(id, page, pagesize);
		return all;
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
