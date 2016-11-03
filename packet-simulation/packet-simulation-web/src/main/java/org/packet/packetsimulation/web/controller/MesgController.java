package org.packet.packetsimulation.web.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.org.facade.SecurityOrgAccessFacade;
import org.openkoala.security.org.facade.dto.EmployeeUserDTO;
import org.openkoala.security.shiro.CurrentUser;
import org.packet.packetsimulation.facade.MesgFacade;
import org.packet.packetsimulation.facade.MesgTypeFacade;
import org.packet.packetsimulation.facade.TaskFacade;
import org.packet.packetsimulation.facade.dto.MesgDTO;
import org.packet.packetsimulation.facade.dto.MesgTypeDTO;
import org.packet.packetsimulation.facade.dto.TaskDTO;
import org.packet.packetsimulation.facade.dto.TaskPacketDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

@Controller
@RequestMapping("/Mesg")
public class MesgController {
		
	@Inject
	private MesgFacade mesgFacade;
	
	@Inject
	private MesgTypeFacade mesgTypeFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(MesgDTO mesgDTO,HttpServletRequest request) {
		String createBy = CurrentUser.getUserAccount();
        mesgDTO.setCreateBy(createBy);
		return mesgFacade.creatMesg(mesgDTO);
	}
	
	/**
	 * 记录批量
	 * @param mesgDTO
	 * @param request
	 * @param ids
	 * @param start
	 * @param end
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/batch")
	public InvokeResult batch(MesgDTO mesgDTO, HttpServletRequest request, @RequestParam String ids, @RequestParam Integer start, @RequestParam Integer end) {
		String userAccount = CurrentUser.getUserAccount();
		Long countOfThreeStandard = mesgFacade.queryCountOfThreeStandard(userAccount);
		if(start != null && !"".equals(start) && end != null && !"".equals(end)){
			int startOfThreeStandard = Integer.valueOf(start);
			int endOfThreeStandard = Integer.valueOf(end);
			if (startOfThreeStandard >= 1
					&& startOfThreeStandard <= endOfThreeStandard
					&& endOfThreeStandard <= countOfThreeStandard) {
				return mesgFacade.batchMesg(mesgDTO, startOfThreeStandard, endOfThreeStandard, userAccount);
			} else if (startOfThreeStandard < 1
					|| startOfThreeStandard > endOfThreeStandard
					|| endOfThreeStandard > countOfThreeStandard) {
				return InvokeResult.failure("起始值不能小于1,结束值不能大于三标总个数,且起始值不能大于结束值");
			}
		}else if((start == null || "".equals(start)) && (end == null || "".equals(end))){
			String[] values = ids.split(",");
			return mesgFacade.batchMesg(mesgDTO, values, userAccount);
		}else if((start == null || "".equals(start)) && (end != null || !"".equals(end))){			
			return InvokeResult.failure("不能起始为空,结束不为空");
		}else if((start != null || !"".equals(start)) && (end == null || "".equals(end))){
			return InvokeResult.failure("不能起始不为空,结束为空");
		}
		return InvokeResult.failure("批量失败");
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(MesgDTO mesgDTO) {
		return mesgFacade.updateMesg(mesgDTO);
	}
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/pageJson/{packetId}")
	public Page pageJson(MesgDTO mesgDTO, @RequestParam int page, @RequestParam int pagesize,@PathVariable Long packetId) {
		Page<MesgDTO> all = mesgFacade.pageQueryMesg(mesgDTO, page, pagesize, packetId, 0);
		return all;
	}
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(MesgDTO mesgDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<MesgDTO> all = mesgFacade.pageQueryMesg(mesgDTO, page, pagesize,null, 1);
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
        return mesgFacade.removeMesgs(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return mesgFacade.getMesg(id);
	}
	
	@ResponseBody
	@RequestMapping("/initUpdate/{id}")
	public InvokeResult initUpdate(@PathVariable Long id) {
		return mesgFacade.getMesgForUpdate(id);
	}
	
	/**
	 * 初始化发送配置界面
	 * @param ids 记录id
	 * @param mesgType 记录类型id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping("/initSend")
	public InvokeResult initSend(@RequestParam String ids ,@RequestParam Long mesgType) {
		Map map = new HashMap();
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
		for (int i = 0; i < value.length; i++) {
			idArrs[i] = Long.parseLong(value[i]);
		}
		MesgTypeDTO mesgTypeCode = (MesgTypeDTO) mesgTypeFacade.getMesgType(mesgType).getData();
        String mesgContent = mesgFacade.getMesgForSend(idArrs, mesgTypeCode.getCode(),CurrentUser.getUserAccount());
        map.put("mesgContent", mesgContent);
        map.put("mesgType", mesgTypeCode.getCode());
        map.put("bizType", mesgTypeCode.getBizType());
		return InvokeResult.success(map);
	}
	
	/**
	 * 初始化发送配置界面
	 * @param ids 记录id
	 * @param mesgType 记录类型id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping("/initUpdateSend")
	public InvokeResult initUpdateSend(@RequestParam Long mesgType) {
		Map map=new HashMap();
		MesgTypeDTO mesgTypeCode=(MesgTypeDTO) mesgTypeFacade.getMesgType(mesgType).getData();
		String fileHeader=mesgFacade.getFileHeaderForSend(mesgTypeCode.getCode(),CurrentUser.getUserAccount());
		map.put("fileHeader", fileHeader);
		map.put("mesgType", mesgTypeCode.getCode());
		return InvokeResult.success(map);
	}
	
	/**
	 * 快速发送
	 * @param taskDTO
	 * @param taskPacketDTO
	 * @param mesgContent 文件内容
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/send")
	public InvokeResult send(TaskDTO taskDTO, TaskPacketDTO taskPacketDTO, @RequestParam String mesgContent, @RequestParam String oriMesgType, HttpServletRequest request) {
		Long missionId = CurrentUser.getMissionId();
		if(missionId == null){
			return InvokeResult.failure("暂未分配任务,不能快速发送");
		}
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "uploadFiles" + File.separator + "easySendFiles" + File.separator;
		File file = new File(ctxPath);    	
		if (!file.exists()) {    	
			file.mkdirs();    	
		}	
		String taskCreator = CurrentUser.getUserAccount();
		taskDTO.setMissionId(missionId);
		taskDTO.setTaskCreator(taskCreator);
		taskDTO.setTaskCreatedTime(new Date());
		taskPacketDTO.setCreatedBy(taskCreator);
		mesgFacade.createTask(taskDTO, taskPacketDTO, mesgContent, oriMesgType, ctxPath);
		return InvokeResult.success();
	}
	
	@ResponseBody
	@RequestMapping("/initBatch/{id}")
	public InvokeResult initBatch(@PathVariable Long id) {
		return mesgFacade.getMesgForBatch(id);
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
