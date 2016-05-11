package org.packet.packetsimulation.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.shiro.CurrentUser;
import org.packet.packetsimulation.facade.BatchConfigFacade;
import org.packet.packetsimulation.facade.MesgFacade;
import org.packet.packetsimulation.facade.dto.BatchConfigDTO;
import org.packet.packetsimulation.facade.dto.MesgDTO;
import org.packet.packetsimulation.facade.dto.MesgTypeDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

@Controller
@RequestMapping("/BatchConfig")
public class BatchConfigController {
		
	@Inject
	private BatchConfigFacade batchConfigFacade;
	@Inject
	private MesgFacade  mesgFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(BatchConfigDTO batchConfigDTO) {
		batchConfigDTO.setCreatedBy(CurrentUser.getUserAccount());
		batchConfigDTO.setCreateDate(new Date());
		return batchConfigFacade.creatBatchConfig(batchConfigDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(BatchConfigDTO batchConfigDTO) {
		return batchConfigFacade.updateBatchConfig(batchConfigDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(BatchConfigDTO batchConfigDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<BatchConfigDTO> all = batchConfigFacade.pageQueryBatchConfig(batchConfigDTO, page, pagesize);
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
        return batchConfigFacade.removeBatchConfigs(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return batchConfigFacade.getBatchConfig(id);
	}
	
	@ResponseBody
	@RequestMapping("/isExist/{id}")
	public InvokeResult isExist(@PathVariable Long id) {
		MesgDTO mesg = (MesgDTO) mesgFacade.getMesg(id).getData();
		BatchConfigDTO dto = new BatchConfigDTO();
		dto.setCreatedBy(CurrentUser.getUserAccount());
		MesgTypeDTO mesgTypeDTO = new MesgTypeDTO();
		mesgTypeDTO.setId(mesg.getMesgType());
		dto.setMesgTypeDTO(mesgTypeDTO);
		List<BatchConfigDTO> all = batchConfigFacade.queryBatchConfig(dto);
		if(all.size()>0){
			return InvokeResult.success(all.get(0));
		}else{
			return InvokeResult.success(null);
		}
	}
	
	@ResponseBody
	@RequestMapping("/getNodes/{id}")
	public InvokeResult getNodes(@PathVariable Long id) throws SAXException, IOException, ParserConfigurationException {
		return batchConfigFacade.getNodes(id);
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
