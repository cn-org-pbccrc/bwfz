package org.packet.packetsimulation.web.controller;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.web.bind.WebDataBinder;

import java.io.IOException;
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
import org.xml.sax.SAXException;
import org.dayatang.utils.Page;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.QuantityConfigFacade;
import org.packet.packetsimulation.facade.RecordFacade;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.shiro.CurrentUser;

@Controller
@RequestMapping("/QuantityConfig")
public class QuantityConfigController {
		
	@Inject
	private QuantityConfigFacade quantityConfigFacade;
	
	@Inject
	private RecordFacade recordFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(QuantityConfigDTO quantityConfigDTO) {
		quantityConfigDTO.setCreatedBy(CurrentUser.getUserAccount());
		quantityConfigDTO.setCreateDate(new Date());
		return quantityConfigFacade.creatQuantityConfig(quantityConfigDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(QuantityConfigDTO quantityConfigDTO) {
		return quantityConfigFacade.updateQuantityConfig(quantityConfigDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(QuantityConfigDTO quantityConfigDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<QuantityConfigDTO> all = quantityConfigFacade.pageQueryQuantityConfig(quantityConfigDTO, page, pagesize);
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
        return quantityConfigFacade.removeQuantityConfigs(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return quantityConfigFacade.getQuantityConfig(id);
	}
	
	@ResponseBody
	@RequestMapping("/isExist/{id}")
	public InvokeResult isExist(@PathVariable Long id) {
		RecordDTO record = (RecordDTO) recordFacade.getRecord(id).getData();
		QuantityConfigDTO dto = new QuantityConfigDTO();
		dto.setCreatedBy(CurrentUser.getUserAccount());
		RecordTypeDTO recordTypeDTO = new RecordTypeDTO();
		recordTypeDTO.setId(record.getRecordType().getId());
		dto.setRecordTypeDTO(recordTypeDTO);
		List<QuantityConfigDTO> all = quantityConfigFacade.queryQuantityConfig(dto);
		if(all.size()>0){
			return InvokeResult.success(all.get(0));
		}else{
			return InvokeResult.success(null);
		}
	}
	
	@ResponseBody
	@RequestMapping("/getRecordSegments/{id}")
	public InvokeResult getRecordSegments(@PathVariable Long id){
		return quantityConfigFacade.getRecordSegments(id);
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
