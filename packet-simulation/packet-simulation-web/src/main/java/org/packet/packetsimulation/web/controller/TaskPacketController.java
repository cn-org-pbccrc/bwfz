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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileItemFactory;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.dayatang.utils.Page;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.TaskPacket;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.TaskPacketFacade;
import org.openkoala.koala.commons.InvokeResult;


@Controller
@RequestMapping("/TaskPacket")
public class TaskPacketController {
		
	@Inject
	private TaskPacketFacade taskPacketFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(TaskPacketDTO taskPacketDTO, HttpServletRequest request, @RequestParam String flagIds, @RequestParam String compressions, @RequestParam String encryptions) throws ParseException {
		String[] flags = flagIds.split(",");
		String[] coms = compressions.split(",");
		String[] encs = encryptions.split(",");
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "uploadFiles" + File.separator + taskPacketDTO.getTaskId() + File.separator + "insideFiles" + File.separator;
		File file = new File(ctxPath);    	
		if (!file.exists()) {    	
			file.mkdirs();    	
		}	
		return taskPacketFacade.creatTaskPackets(taskPacketDTO,ctxPath,flags,coms,encs);
		//return taskPacketFacade.creatTaskPacket(taskPacketDTO);
	}
	
	@ResponseBody
	@RequestMapping("/verify")
	public InvokeResult verify(@RequestParam String selectedPacketNames, @RequestParam Long taskId){
		String[] values = selectedPacketNames.split(",");
		return taskPacketFacade.verifyTaskPacketName(values,taskId);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(TaskPacketDTO taskPacketDTO) {
		return taskPacketFacade.updateTaskPacket(taskPacketDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson/{taskId}")
	public Page pageJson(TaskPacketDTO taskPacketDTO, @RequestParam int page, @RequestParam int pagesize, @PathVariable Long taskId ) {
		Page<TaskPacketDTO> all = taskPacketFacade.pageQueryTaskPacket(taskPacketDTO, page, pagesize, taskId);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public InvokeResult remove(@RequestParam String ids, HttpServletRequest request) {
		String savePath = request.getSession().getServletContext().getRealPath("/")+File.separator+"uploadFiles"+File.separator;
		//System.out.println(savePath);
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i ++) {
        	        					idArrs[i] = Long.parseLong(value[i]);
						        }
        return taskPacketFacade.removeTaskPackets(idArrs,savePath);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return taskPacketFacade.getTaskPacket(id);
	}
	
	@ResponseBody
	@RequestMapping("/getTaskPacketView/{id}")
	public InvokeResult getPacketView(@PathVariable Long id, HttpServletRequest request) throws Exception{		
		String ctxPath=request.getSession().getServletContext().getRealPath("/") + File.separator + "uploadFiles" + File.separator;
		String packetContent = taskPacketFacade.showPacketContent(id,ctxPath);
		return InvokeResult.success(packetContent);	
	}
	
	@ResponseBody
	@RequestMapping("/up")
	public InvokeResult up(@RequestParam String sourceId, @RequestParam String destId){
		return taskPacketFacade.upTaskPacket(sourceId, destId);
	}
	
	@ResponseBody
	@RequestMapping("/down")
	public InvokeResult down(@RequestParam String sourceId, @RequestParam String destId){
		return taskPacketFacade.downTaskPacket(sourceId, destId);
	}
	
	@ResponseBody
	@RequestMapping("/checkExisting")
	public int checkExisting(HttpServletRequest request, @RequestParam String taskId, @RequestParam String filename){
		File file = new File(request.getSession().getServletContext().getRealPath("/") + File.separator + "uploadFiles" + File.separator + taskId + File.separator + "outsideFiles" + File.separator);
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if(filename.equals(fileList[i].getName())){
				return 1;
			}
		}
		return 0;
	}
	
	@ResponseBody
	@RequestMapping("/uploadFile")  	
	public InvokeResult upload(TaskPacketDTO taskPacketDTO, HttpServletResponse response, HttpServletRequest request) throws IOException, ParseException{
		request.setCharacterEncoding("utf-8"); 
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
//		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String ctxPath=request.getSession().getServletContext().getRealPath("/") + File.separator + "uploadFiles" + File.separator + taskPacketDTO.getTaskId() + File.separator + "outsideFiles" + File.separator;
		System.out.println("ctxpath="+ctxPath);	
		File file = new File(ctxPath);    	
		if (!file.exists()) {    	
			file.mkdirs();    	
		}	
		File[] fileList = file.listFiles();
		boolean flag = false;
		String fileName = null;    	
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    	
			MultipartFile mf = entity.getValue();  	
			fileName = mf.getOriginalFilename();
			System.out.println("filename="+fileName);	
			File uploadFile = new File(ctxPath + fileName);	
			try {
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
				for (int i = 0; i < fileList.length; i++) {
					if(fileName.equals(fileList[i].getName())){
						flag = true;
						break;
					}
				}
				if(flag){
					taskPacketFacade.updateOutSideTaskPacket(fileName);
				}else{
					taskPacketFacade.creatOutSideTaskPacket(taskPacketDTO,fileName);
				}				  	   
			} catch (IOException e) {  	   		       
				e.printStackTrace();  	       
			}	
		}   	
		response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		response.getWriter().write("上传成功!");
		return null;    
	}    	
	
	
    @InitBinder    
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
        dateFormat.setLenient(false);    
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
        //CustomDateEditor 可以换成自己定义的编辑器。  
        //注册一个Date 类型的绑定器 。
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }
	
}
