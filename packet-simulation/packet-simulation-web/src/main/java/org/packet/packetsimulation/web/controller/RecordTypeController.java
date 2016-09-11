package org.packet.packetsimulation.web.controller;

import javax.inject.Inject;

import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.RecordTypeFacade;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.shiro.CurrentUser;

@Controller
@RequestMapping("/RecordType")
public class RecordTypeController {
		
	@Inject
	private RecordTypeFacade recordTypeFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(RecordTypeDTO recordTypeDTO) {
		recordTypeDTO.setCreatedBy(CurrentUser.getUserAccount());
		return recordTypeFacade.creatRecordType(recordTypeDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(RecordTypeDTO recordTypeDTO) {
		RecordTypeDTO  dto = (RecordTypeDTO) recordTypeFacade.getRecordType(recordTypeDTO.getId()).getData();
		recordTypeDTO.setHeaderItems(dto.getHeaderItems());
		return recordTypeFacade.updateRecordType(recordTypeDTO);
	}
	
	@ResponseBody
	@RequestMapping("/updateHeader")
	public InvokeResult updateHeader(RecordTypeDTO recordTypeDTO) {
		RecordTypeDTO  dto = (RecordTypeDTO) recordTypeFacade.getRecordType(recordTypeDTO.getId()).getData();
		dto.setHeaderItems(recordTypeDTO.getHeaderItems());
		return recordTypeFacade.updateRecordType(dto);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(RecordTypeDTO recordTypeDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<RecordTypeDTO> all = recordTypeFacade.pageQueryRecordType(recordTypeDTO, page, pagesize);
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
        return recordTypeFacade.removeRecordTypes(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return recordTypeFacade.getRecordType(id);
	}
	
	@ResponseBody
	@RequestMapping("/findHeaderItemByRecordType/{id}")
	public RecordTypeDTO findRecordTypeByRecordType(@PathVariable Long id) {
		return recordTypeFacade.findRecordTypeByRecordType(id);
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
