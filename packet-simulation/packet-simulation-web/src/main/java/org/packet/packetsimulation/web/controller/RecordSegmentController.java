package org.packet.packetsimulation.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.RecordSegmentFacade;
import org.packet.packetsimulation.facade.dto.RecordSegmentDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/RecordSegment")
public class RecordSegmentController {

	@Inject
	private RecordSegmentFacade recordSegmentFacade;

	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(RecordSegmentDTO recordSegmentDTO) {
		return recordSegmentFacade.creatRecordSegment(recordSegmentDTO);
	}

	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(RecordSegmentDTO recordSegmentDTO) {
		return recordSegmentFacade.updateRecordSegment(recordSegmentDTO);
	}

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(RecordSegmentDTO recordSegmentDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<RecordSegmentDTO> all = recordSegmentFacade.pageQueryRecordSegment(recordSegmentDTO, page, pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public InvokeResult remove(@RequestParam String ids) {
		String[] value = ids.split(",");
		Long[] idArrs = new Long[value.length];
		for (int i = 0; i < value.length; i++) {
			idArrs[i] = Long.parseLong(value[i]);
		}
		return recordSegmentFacade.removeRecordSegments(idArrs);
	}

	@ResponseBody
	@RequestMapping("/get/{recordSegmentId}")
	public InvokeResult get(@PathVariable Long recordSegmentId) {
		return recordSegmentFacade.getRecordSegment(recordSegmentId);
	}

	@ResponseBody
	@RequestMapping("/getUpdate/{recordSegmentId}")
	public InvokeResult getUpdate(@PathVariable Long recordSegmentId, @RequestParam String checkedLocalData) {
		return recordSegmentFacade.getUpdateRecordSegment(recordSegmentId, checkedLocalData);
	}
	
	@ResponseBody
	@RequestMapping("/findRecordSegmentByRecordType/{submissionId}")
	public List<RecordSegmentDTO> findRecordTypeByRecordSegment(@PathVariable Long submissionId) {
		return recordSegmentFacade.findRecordSegmentByRecordType(submissionId);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		// CustomDateEditor 可以换成自己定义的编辑器。
		// 注册一个Date类型的绑定器 。
		binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
	}

}
