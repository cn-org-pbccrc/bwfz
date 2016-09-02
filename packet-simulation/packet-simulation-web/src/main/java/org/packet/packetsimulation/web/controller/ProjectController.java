package org.packet.packetsimulation.web.controller;

import javax.inject.Inject;

import org.springframework.web.bind.WebDataBinder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.ProjectFacade;
import org.openkoala.gqc.infra.util.ReadAppointedLine;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/Project")
public class ProjectController {
		
	@Inject
	private ProjectFacade projectFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(ProjectDTO projectDTO) {
		return projectFacade.creatProject(projectDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(ProjectDTO projectDTO) {
		return projectFacade.updateProject(projectDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(ProjectDTO projectDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<ProjectDTO> all = projectFacade.pageQueryProject(projectDTO, page, pagesize);
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
        return projectFacade.removeProjects(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return projectFacade.getProject(id);		
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
