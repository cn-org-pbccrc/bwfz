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
import org.packet.packetsimulation.facade.MissionFacade;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.shiro.CurrentUser;

@Controller
@RequestMapping("/Mission")
public class MissionController {
		
	@Inject
	private MissionFacade missionFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(MissionDTO missionDTO) {
		return missionFacade.creatMission(missionDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(MissionDTO missionDTO) {
		return missionFacade.updateMission(missionDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson/{projectId}")
	public Page pageJson(MissionDTO missionDTO, @RequestParam int page, @RequestParam int pagesize, @PathVariable Long projectId) {
		String currentUserAccount = CurrentUser.getUserAccount();
		Page<MissionDTO> all = missionFacade.pageQueryMission(missionDTO, page, pagesize, projectId, currentUserAccount);
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
        return missionFacade.removeMissions(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return missionFacade.getMission(id);
	}
	
	@ResponseBody
	@RequestMapping("/getProject/{id}")
	public InvokeResult getProject(@PathVariable Long id) {
		return missionFacade.getProject(id);
	}
	
	@ResponseBody
	@RequestMapping("/suspend")
	public InvokeResult suspend(@RequestParam Long missionId) {
		return missionFacade.suspend(missionId);
	}
	
	@ResponseBody
	@RequestMapping("/activate")
	public InvokeResult activate(@RequestParam Long missionId) {
		return missionFacade.activate(missionId);
	}
	
	@ResponseBody
	@RequestMapping("/pagingQueryMissionsByCurrentUser/{projectId}")
	public Page<MissionDTO> pagingQueryMissionsByCurrentUser(@RequestParam int page, @RequestParam int pagesize, @PathVariable Long projectId) {
		String currentUserAccount = CurrentUser.getUserAccount();		
		return missionFacade.pagingQueryMissionsByCurrentUser(page, pagesize, projectId, currentUserAccount);
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
