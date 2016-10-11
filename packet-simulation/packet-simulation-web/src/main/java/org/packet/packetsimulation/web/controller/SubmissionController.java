package org.packet.packetsimulation.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.WebDataBinder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.packet.packetsimulation.core.domain.PACKETCONSTANT;
import org.packet.packetsimulation.core.domain.Packet;
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
	public InvokeResult add(SubmissionDTO submissionDTO, @RequestParam Long recordTypeId) {
		String createdBy = CurrentUser.getUserAccount();
		submissionDTO.setCreatedBy(createdBy);
		submissionDTO.setRecordNum(0L);
		return submissionFacade.creatSubmission(submissionDTO, recordTypeId);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(SubmissionDTO submissionDTO, @RequestParam Long recordTypeId) {
		return submissionFacade.updateSubmission(submissionDTO, recordTypeId);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(SubmissionDTO submissionDTO, @RequestParam int page, @RequestParam int pagesize) {
		String createdBy = CurrentUser.getUserAccount();
		Page<SubmissionDTO> all = submissionFacade.pageQuerySubmission(submissionDTO, page, pagesize, createdBy);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/pageJsonByType/{lotId}")
	public Page pageJsonByType(SubmissionDTO submissionDTO, @RequestParam int page, @RequestParam int pagesize, @PathVariable Long lotId) {
		String createdBy = CurrentUser.getUserAccount();
		Page<SubmissionDTO> all = submissionFacade.pageJsonByType(submissionDTO, page, pagesize, createdBy, lotId);
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
	
	@ResponseBody
	@RequestMapping("/exportSubmissions")
	public void downloadCSV(@RequestParam String ids, HttpServletRequest request, HttpServletResponse response) {	
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i ++) {
        	idArrs[i] = Long.parseLong(value[i]);
		}
		String exportSubmissions = submissionFacade.exportSubmissions(idArrs);
		response.setContentType("application/txt;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + new Date().getTime() + ".txt");
		response.setCharacterEncoding("UTF-8");
		
		InputStream in = null;;
		OutputStream out;
		try {
			in = new ByteArrayInputStream(exportSubmissions.getBytes("UTF-8"));
			out = response.getOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = in.read(buffer)) > 0) {
//				out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
				out.write(buffer, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
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
