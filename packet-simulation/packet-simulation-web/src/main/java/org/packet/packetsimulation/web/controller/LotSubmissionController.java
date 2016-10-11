package org.packet.packetsimulation.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.dayatang.utils.Page;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.LotSubmissionFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/LotSubmission")
public class LotSubmissionController {
		
	@Inject
	private LotSubmissionFacade lotSubmissionFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(LotSubmissionDTO lotSubmissionDTO, HttpServletRequest request, @RequestParam String flagIds, @RequestParam String compressions, @RequestParam String encryptions) {
		String[] flags = flagIds.split(",");
		String[] coms = compressions.split(",");
		String[] encs = encryptions.split(",");
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "generationFiles" + File.separator + lotSubmissionDTO.getLotId() + File.separator + "insideFiles" + File.separator;
		File file = new File(ctxPath);    	
		if (!file.exists()) {    	
			file.mkdirs();    	
		}
		return lotSubmissionFacade.creatLotSubmissions(lotSubmissionDTO, ctxPath, flags, coms, encs);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(LotSubmissionDTO lotSubmissionDTO) {
		return lotSubmissionFacade.updateLotSubmission(lotSubmissionDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson/{lotId}")
	public Page pageJson(LotSubmissionDTO lotSubmissionDTO, @RequestParam int page, @RequestParam int pagesize, @PathVariable Long lotId) {
		Page<LotSubmissionDTO> all = lotSubmissionFacade.pageQueryLotSubmission(lotSubmissionDTO, page, pagesize, lotId);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public InvokeResult remove(@RequestParam String ids, HttpServletRequest request) {
		String savePath = request.getSession().getServletContext().getRealPath("/") + File.separator + "generationFiles" + File.separator;
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i ++) {
        	        					idArrs[i] = Long.parseLong(value[i]);
						        }
        return lotSubmissionFacade.removeLotSubmissions(idArrs, savePath);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return lotSubmissionFacade.getLotSubmission(id);
	}
	
	@ResponseBody
	@RequestMapping("/getLotSubmissionView/{id}")
	public InvokeResult getLotSubmissionView(@PathVariable Long id, HttpServletRequest request) throws Exception{		
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "generationFiles" + File.separator;
		String submissionContent = lotSubmissionFacade.showSubmissionContent(id, ctxPath);
		return InvokeResult.success(submissionContent);	
	}
	
	@ResponseBody
	@RequestMapping("/up")
	public InvokeResult up(@RequestParam String sourceId, @RequestParam String destId){
		return lotSubmissionFacade.upLotSubmission(sourceId, destId);
	}
	
	@ResponseBody
	@RequestMapping("/down")
	public InvokeResult down(@RequestParam String sourceId, @RequestParam String destId){
		return lotSubmissionFacade.downLotSubmission(sourceId, destId);
	}
	
	@ResponseBody
	@RequestMapping("/checkExisting")
	public int checkExisting(HttpServletRequest request, @RequestParam String lotId, @RequestParam String fileName){
		File file = new File(request.getSession().getServletContext().getRealPath("/") + File.separator + "generationFiles" + File.separator + lotId + File.separator + "outsideFiles" + File.separator);
		File[] fileList = file.listFiles();
		if (fileList != null){
			for (int i = 0; i < fileList.length; i++) {
				if(fileName.equals(fileList[i].getName())){
					return 1;
				}
			}
		}
		return 0;
	}
	
	@ResponseBody
	@RequestMapping("/uploadFile")  	
	public ModelAndView upload(LotSubmissionDTO lotSubmissionDTO, HttpServletResponse response, HttpServletRequest request) throws IOException, ParseException{			
		ModelAndView modelAndView = new ModelAndView("index");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "generationFiles" + File.separator + lotSubmissionDTO.getLotId() + File.separator + "outsideFiles" + File.separator;
		File file = new File(ctxPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String fileName = null;    	
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();  	
			fileName = mf.getOriginalFilename();
			File uploadFile = new File(ctxPath + fileName);
			FileCopyUtils.copy(mf.getBytes(), uploadFile);
			lotSubmissionDTO.setName(fileName);
			return lotSubmissionFacade.uploadLotSubmission(lotSubmissionDTO, ctxPath);
		}
		return modelAndView;  
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
