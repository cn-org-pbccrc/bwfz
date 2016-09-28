package org.packet.packetsimulation.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.dayatang.utils.Page;
import org.openkoala.gqc.facade.dto.GeneralQueryDTO;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.shiro.CurrentUser;
import org.packet.packetsimulation.facade.RecordFacade;
import org.packet.packetsimulation.facade.RecordSegmentFacade;
import org.packet.packetsimulation.facade.dto.MesgDTO;
import org.packet.packetsimulation.facade.dto.MesgTypeDTO;
import org.packet.packetsimulation.facade.dto.RecordDTO;
import org.packet.packetsimulation.facade.dto.RecordSegmentDTO;
import org.packet.packetsimulation.facade.dto.RecordTypeDTO;
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
	public InvokeResult add(RecordDTO recordDTO, @RequestParam Long recordTypeId) {
		String createdBy = CurrentUser.getUserAccount();
		recordDTO.setCreatedBy(createdBy);
		return recordFacade.creatRecord(recordDTO, recordTypeId);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(RecordDTO recordDTO, @RequestParam Long recordTypeId) {
		return recordFacade.updateRecord(recordDTO, recordTypeId);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson/{submissionId}")
	public Page pageJson(RecordDTO recordDTO, @RequestParam int page, @RequestParam int pagesize, @PathVariable Long submissionId) {
		Page<RecordDTO> all = recordFacade.pageQueryRecord(recordDTO, page, pagesize, submissionId);
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
	@RequestMapping("/batch")
	public InvokeResult batch(RecordDTO recordDTO, HttpServletRequest request, @RequestParam String ids, @RequestParam Integer start, @RequestParam Integer end) {
		String userAccount = CurrentUser.getUserAccount();
		Long countOfThreeStandard = recordFacade.queryCountOfThreeStandard(userAccount);
		if(start != null && !"".equals(start) && end != null && !"".equals(end)){
			int startOfThreeStandard = Integer.valueOf(start);
			int endOfThreeStandard = Integer.valueOf(end);
			if (startOfThreeStandard >= 1
					&& startOfThreeStandard <= endOfThreeStandard
					&& endOfThreeStandard <= countOfThreeStandard) {
				return recordFacade.batchRecord(recordDTO, startOfThreeStandard, endOfThreeStandard, userAccount);
			} else if (startOfThreeStandard < 1
					|| startOfThreeStandard > endOfThreeStandard
					|| endOfThreeStandard > countOfThreeStandard) {
				return InvokeResult.failure("起始值不能小于1,结束值不能大于三标总个数,且起始值不能大于结束值");
			}
		}else if((start == null || "".equals(start)) && (end == null || "".equals(end))){
			String[] values = ids.split(",");
			return recordFacade.batchRecord(recordDTO, values, userAccount);
		}else if((start == null || "".equals(start)) && (end != null || !"".equals(end))){			
			return InvokeResult.failure("不能起始为空,结束不为空");
		}else if((start != null || !"".equals(start)) && (end == null || "".equals(end))){
			return InvokeResult.failure("不能起始不为空,结束为空");
		}
		return InvokeResult.failure("批量失败");
	}
	
	@ResponseBody
	@RequestMapping("/findRecordSegmentByRecordType/{id}")
	public List<RecordSegmentDTO> findRecordSegmentByRecordType(@PathVariable Long id) {
		List<RecordSegmentDTO> dtos = recordSegmentFacade.findRecordSegmentByRecordType(id);
		return dtos;
	}
	
    @ResponseBody
	@RequestMapping("/findRecordTypes")
	public List<RecordType> findRecordTypes() {
		return recordFacade.findRecordTypes();
	}
    
	@ResponseBody
	@RequestMapping("/initBatch/{id}")
	public InvokeResult initBatch(@PathVariable Long id) {
		return recordFacade.getRecordForBatch(id);
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
