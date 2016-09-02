package org.packet.packetsimulation.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.dayatang.utils.Page;
import org.openkoala.gqc.facade.dto.GeneralQueryDTO;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.RecordFacade;
import org.packet.packetsimulation.facade.RecordSegmentFacade;
import org.packet.packetsimulation.facade.dto.RecordDTO;
import org.packet.packetsimulation.facade.dto.RecordSegmentDTO;
import org.packet.packetsimulationGeneration.core.domain.RecordType;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Record")
public class RecordController {
		
	@Inject
	private RecordFacade recordFacade;
	
	@Inject
	private RecordSegmentFacade recordSegmentFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(RecordDTO recordDTO) {
		return recordFacade.creatRecord(recordDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(RecordDTO recordDTO) {
		return recordFacade.updateRecord(recordDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(RecordDTO recordDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<RecordDTO> all = recordFacade.pageQueryRecord(recordDTO, page, pagesize);
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
        return recordFacade.removeRecords(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return recordFacade.getRecord(id);
	}
	
	@ResponseBody
	@RequestMapping("/findRecordSegmentByRecordType/{id}")
	public List<RecordSegmentDTO> findRecordSegmentByRecordType(@PathVariable Long id) {
		List<RecordSegmentDTO> dtos = recordSegmentFacade.findRecordSegmentByRecordType(id);
		return dtos;
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