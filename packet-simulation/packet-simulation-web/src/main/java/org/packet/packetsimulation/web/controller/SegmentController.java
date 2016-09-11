package org.packet.packetsimulation.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.dayatang.utils.Page;
import org.h2.jdbc.JdbcConnection;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.RecordSegmentFacade;
import org.packet.packetsimulation.facade.SegmentFacade;
import org.packet.packetsimulation.facade.dto.RecordSegmentDTO;
import org.packet.packetsimulation.facade.dto.SegmentDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/Segment")
public class SegmentController {

	@Inject
	private RecordSegmentFacade recordSegmentFacade;
	
	@Inject
	private SegmentFacade segmentFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(SegmentDTO segmentDTO) {
		return segmentFacade.creatSegment(segmentDTO);
	}

	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(SegmentDTO segmentDTO) {
		return segmentFacade.updateSegment(segmentDTO);
	}

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page<JSONObject> pageJson(SegmentDTO segmentDTO, @RequestParam int page, @RequestParam int pagesize) {
		return segmentFacade.pageQuerySegment(segmentDTO, page, pagesize);
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
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return recordSegmentFacade.getRecordSegment(id);
	}

	@ResponseBody
	@RequestMapping("/findRecordSegmentByRecordType/{id}")
	public List<RecordSegmentDTO> findRecordTypeByRecordSegment(
			@PathVariable Long id) {
		return recordSegmentFacade.findRecordSegmentByRecordType(id);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
		// CustomDateEditor 可以换成自己定义的编辑器。
		// 注册一个Date 类型的绑定器 。
		binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
	}

}
