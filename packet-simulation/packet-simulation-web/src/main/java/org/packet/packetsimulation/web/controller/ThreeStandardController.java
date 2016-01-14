package org.packet.packetsimulation.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	@RequestMapping("/generate")
	public InvokeResult generate(ThreeStandardDTO threeStandardDTO, @RequestParam String threeStandardNumber) {
		return threeStandardFacade.generateThreeStandard(threeStandardDTO, threeStandardNumber);
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
	public InvokeResult load(ThreeStandardDTO threeStandardDTO, HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, ParserConfigurationException, SAXException {
		request.setCharacterEncoding("utf-8"); 
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String ctxPath=request.getSession().getServletContext().getRealPath("/") + File.separator + "importFiles" + File.separator + threeStandardDTO.getCreatedBy() + File.separator;			
		File file = new File(ctxPath);    	
		if (!file.exists()) {    	
			file.mkdirs();    	
		}	
		String fileName = null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    	
			MultipartFile mf = entity.getValue();  	
			fileName = mf.getOriginalFilename();	
			File uploadFile = new File(ctxPath + fileName);	
			try {
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ctxPath + fileName)));  
				String line = br.readLine();				
				String[] threeStandards = line.split(",");
				if(threeStandards.length!=3||!threeStandards[0].equals("\"姓名\"")||!threeStandards[1].equals("\"证件类型\"")||!threeStandards[2].equals("\"证件号码\"")){
					//System.out.println(threeStandards[0]+":"+threeStandards[1]+":"+threeStandards[0]+"?"+threeStandards.length);
					br.close();
					uploadFile.delete();
					response.setHeader("Content-type", "text/html;charset=UTF-8");
				    response.setCharacterEncoding("UTF-8");
					response.getWriter().write("文件第1行应为''姓名','证件类型','证件号码''");
					return null;
				}
				String temp = "";
				int lineNumber = 2;
				while((temp=br.readLine())!=null){
					if(temp.split(",").length!=3){
						br.close();
						uploadFile.delete();
						response.setHeader("Content-type", "text/html;charset=UTF-8");
					    response.setCharacterEncoding("UTF-8");
						response.getWriter().write("文件第"+lineNumber+"行不符合格式规范");						
						return null;
					}
					lineNumber++;
				}
				br.close();
				threeStandardFacade.importThreeStandard(threeStandardDTO, fileName, ctxPath);				  	   
			} catch (IOException e) {  	   	 	       
				e.printStackTrace();  	       
			}	
		}   	
		response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		response.getWriter().write("上传成功!");
		return null;    
	}
		
	@ResponseBody
	@RequestMapping("/downloadCSV")
	public void downloadCSV(HttpServletRequest request, HttpServletResponse response, @RequestParam String createdBy) {
		
		String downloadCSV = threeStandardFacade.downloadCSV(createdBy);
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
