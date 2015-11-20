package org.packet.packetsimulation.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.xml.sax.SAXException;
import org.dayatang.utils.Page;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.ThreeStandardFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/ThreeStandard")
public class ThreeStandardController {
		
	@Inject
	private ThreeStandardFacade threeStandardFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(ThreeStandardDTO threeStandardDTO) {
		return threeStandardFacade.creatThreeStandard(threeStandardDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(ThreeStandardDTO threeStandardDTO) {
		return threeStandardFacade.updateThreeStandard(threeStandardDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson/{currentUserId}")
	public Page pageJson(ThreeStandardDTO threeStandardDTO, @RequestParam int page, @RequestParam int pagesize, @PathVariable String currentUserId ) {
		Page<ThreeStandardDTO> all = threeStandardFacade.pageQueryThreeStandard(threeStandardDTO, page, pagesize, currentUserId);
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
        return threeStandardFacade.removeThreeStandards(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return threeStandardFacade.getThreeStandard(id);
	}
	
	@ResponseBody
	@RequestMapping("/importFile")
	public String load(ThreeStandardDTO threeStandardDTO, HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, ParserConfigurationException, SAXException {
		request.setCharacterEncoding("utf-8"); 
		System.out.println("哈哈哈哈收到啦！！！！！！！！！");
		String responseStr="";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String ctxPath=request.getSession().getServletContext().getRealPath("/")+File.separator+"importFiles";		
		ctxPath += File.separator + threeStandardDTO.getCreatedBy() + File.separator;  	
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
					//threeStandardFacade.updateImportThreeStandard(fileName, ctxPath);
					responseStr="上传成功";
				}else{
					threeStandardFacade.importThreeStandard(threeStandardDTO, fileName, ctxPath);
					responseStr="上传成功";
				}				  	   
			} catch (IOException e) {  	   	
				responseStr="上传失败";  	       
				e.printStackTrace();  	       
			}	
		}   	
		return responseStr;    
	}
		
	@ResponseBody
	@RequestMapping("/downloadCSV")
	public void downloadCSV(HttpServletRequest request, HttpServletResponse response) {
		
		String downloadCSV = threeStandardFacade.downloadCSV();
		response.setContentType("application/csv;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + new Date().getTime() + ".csv");
		response.setCharacterEncoding("UTF-8");
		
		InputStream in = null;;
		OutputStream out;
		try {
			in = new ByteArrayInputStream(downloadCSV.getBytes("UTF-8"));
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
