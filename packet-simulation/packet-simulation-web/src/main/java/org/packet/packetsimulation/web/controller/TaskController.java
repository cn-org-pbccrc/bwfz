package org.packet.packetsimulation.web.controller;

import javax.inject.Inject;

import org.springframework.web.bind.WebDataBinder;

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
import org.packet.packetsimulation.facade.TaskFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/Task")
public class TaskController {
		
	@Inject
	private TaskFacade taskFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(TaskDTO taskDTO) {
		return taskFacade.creatTask(taskDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(TaskDTO taskDTO) {
		return taskFacade.updateTask(taskDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson/{currentUserId}")
	public Page pageJson(TaskDTO taskDTO, @RequestParam int page, @RequestParam int pagesize, @PathVariable String currentUserId) {
		Page<TaskDTO> all = taskFacade.pageQueryTask(taskDTO, page, pagesize, currentUserId);
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
        return taskFacade.removeTasks(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return taskFacade.getTask(id);
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
