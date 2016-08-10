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
import org.packet.packetsimulation.facade.DictItemFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/DictItem")
public class DictItemController {
		
	@Inject
	private DictItemFacade dictItemFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(DictItemDTO dictItemDTO) {
		return dictItemFacade.creatDictItem(dictItemDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(DictItemDTO dictItemDTO) {
		return dictItemFacade.updateDictItem(dictItemDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(DictItemDTO dictItemDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<DictItemDTO> all = dictItemFacade.pageQueryDictItem(dictItemDTO, page, pagesize);
		return all;
	}
	
	// 按照字典类型获取分页的item
	@ResponseBody
	@RequestMapping("/pageJsonByDictId/{dictId}")
	public Page pageJsonByDictId(DictItemDTO dictItemDTO,@PathVariable Long dictId, @RequestParam int page, @RequestParam int pagesize) {
		dictItemDTO.setDictId(dictId);
		Page<DictItemDTO> all = dictItemFacade.pageQueryDictItem(dictItemDTO, page, pagesize);
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
        return dictItemFacade.removeDictItems(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return dictItemFacade.getDictItem(id);
	}
	
	@ResponseBody
	@RequestMapping("/loadDictItem")
	public InvokeResult loadDictItem() {
		return InvokeResult.success(dictItemFacade.loadDictItem());
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
