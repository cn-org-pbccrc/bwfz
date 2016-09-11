package org.packet.packetsimulation.web.controller;

import javax.inject.Inject;
import org.springframework.web.bind.WebDataBinder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.SubmissionFacade;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.shiro.CurrentUser;

@Controller
@RequestMapping("/Submission")
public class SubmissionController {
		
	@Inject
	private SubmissionFacade submissionFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(SubmissionDTO submissionDTO) {
		String createdBy = CurrentUser.getUserAccount();
		submissionDTO.setCreatedBy(createdBy);
		submissionDTO.setRecordNum(0L);
		return submissionFacade.creatSubmission(submissionDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(SubmissionDTO submissionDTO) {
		return submissionFacade.updateSubmission(submissionDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(SubmissionDTO submissionDTO, @RequestParam int page, @RequestParam int pagesize) {
		String createdBy = CurrentUser.getUserAccount();
		Page<SubmissionDTO> all = submissionFacade.pageQuerySubmission(submissionDTO, page, pagesize, createdBy);
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
        return submissionFacade.removeSubmissions(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return submissionFacade.getSubmission(id);
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
