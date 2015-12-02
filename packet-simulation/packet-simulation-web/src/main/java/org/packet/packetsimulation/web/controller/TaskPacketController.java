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
	public InvokeResult add(TaskPacketDTO taskPacketDTO, @RequestParam String packetNames, @RequestParam String fileVersions, @RequestParam String origSenders, @RequestParam String origSendDates, @RequestParam String dataTypes, @RequestParam String recordTypes, @RequestParam String compressions, @RequestParam String encryptions) throws ParseException {
		System.out.println("真相只有一个:"+origSendDates);
		String[] values = packetNames.split(",");
		String[] vers = fileVersions.split(",");
		String[] senders = origSenders.split(",");
		String[] dates = origSendDates.split(",");
		String[] datTs = dataTypes.split(",");
		String[] recTs = recordTypes.split(",");
		String[] coms = compressions.split(",");
		String[] encs = encryptions.split(",");
		return taskPacketFacade.creatTaskPackets(taskPacketDTO,values,vers,senders,dates,datTs,recTs,coms,encs);
		//return taskPacketFacade.creatTaskPacket(taskPacketDTO);
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
	
	
//	@ResponseBody
//	@RequestMapping("/upload")
//	public void upload(TaskPacketDTO taskPacketDTO, HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
//		System.out.println("从哪来DTO:"+taskPacketDTO.getPacketFrom());
//		System.out.println("taskId:"+taskPacketDTO.getTaskId());
//		String name = null;
//		request.setCharacterEncoding("utf-8");  
//        //判断提交过来的表单是否为文件上传菜单  
//        boolean isMultipart= ServletFileUpload.isMultipartContent(request);  
//        List<String> list = new ArrayList<String>();
//        if(isMultipart){  
//            //构造一个文件上传处理对象  
//            FileItemFactory factory = new DiskFileItemFactory();  
//            ServletFileUpload upload = new ServletFileUpload(factory);  
//  
//            Iterator items;  
//            try{  
//                //解析表单中提交的所有文件内容  
//                items = upload.parseRequest(request).iterator();  
//                while(items.hasNext()){  
//                    FileItem item = (FileItem) items.next();  
//                    if(!item.isFormField()){  
//                        //取出上传文件的文件名称  
//                        name = item.getName();
////                        if( name == null ||"".equals(name)){
////                        	System.out.println("哈哈哈哈哈哈");                      	
////                        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
////                        	break;
////                        }
//                        System.out.println("name:"+name);
//                        //取得上传文件以后的存储路径  
//                        String fileName = name.substring(name.lastIndexOf('\\') + 1, name.length());
//                        list.add(fileName);
//                        System.out.println("fileName:"+fileName);
//                        //上传文件以后的存储路径  
//                        String saveDir = request.getSession().getServletContext().getRealPath("/")+ "/upload/"+taskPacketDTO.getTaskId();  
//                        System.out.println("saveDir:"+saveDir);
//                        if (!(new File(saveDir).isDirectory())){  
//                            new File(saveDir).mkdir();  
//                        }  
//                        String path= saveDir + File.separatorChar + fileName;  
//                        System.out.println("path:"+path);
//                        //上传文件  
//                        File uploaderFile = new File(path);  
//                        item.write(uploaderFile);  
//                        taskPacketFacade.creatOutSideTaskPacket(taskPacketDTO,name);
//                        response.setStatus(HttpServletResponse.SC_OK);  
//                        response.getWriter().append("上传成功!");
//                    }  
//                }  
//            }catch(Exception e){
//            	System.out.println("异常啦哈哈哈哈!");
//                e.printStackTrace();  
//                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);       
//            }   
//        }else{
//        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//       
//        	
//	}
	
	@ResponseBody
	@RequestMapping("/uploadFile")  	
	public String upload(TaskPacketDTO taskPacketDTO, HttpServletResponse response, HttpServletRequest request) throws IOException, ParseException{
		request.setCharacterEncoding("utf-8"); 
		System.out.println("哈哈哈哈收到啦！！！！！！！！！");
		String responseStr="";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
//		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String ctxPath=request.getSession().getServletContext().getRealPath("/")+File.separator+"uploadFiles";	
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  	
//		String ymd = sdf.format(new Date());  	
		ctxPath += File.separator + taskPacketDTO.getTaskId() + File.separator;  	
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
					responseStr="上传成功";
				}else{
					taskPacketFacade.creatOutSideTaskPacket(taskPacketDTO,fileName);
					responseStr="上传成功";
				}				  	   
			} catch (IOException e) {  	   	
				responseStr="上传失败";  	       
				e.printStackTrace();  	       
			}	
		}   	
		return responseStr;    

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
