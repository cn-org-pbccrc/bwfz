package org.packet.packetsimulation.facade.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.impl.assembler.LotSubmissionAssembler;
import org.packet.packetsimulation.facade.impl.assembler.TaskPacketAssembler;
import org.packet.packetsimulation.facade.LotSubmissionFacade;
import org.packet.packetsimulation.application.LotApplication;
import org.packet.packetsimulation.application.LotSubmissionApplication;
import org.packet.packetsimulation.application.SubmissionApplication;
import org.packet.packetsimulation.core.domain.*;
import org.packet.packetsimulationGeneration.core.domain.Submission;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Named
public class LotSubmissionFacadeImpl implements LotSubmissionFacade {

	@Inject
	private LotSubmissionApplication application;
	
	@Inject
	private LotApplication lotApplication;
	
	@Inject
	private SubmissionApplication submissionApplication;
	
	@Inject
	private SubmissionFacadeImpl submissionFacadeImpl;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getLotSubmission(Long id) {
		return InvokeResult.success(LotSubmissionAssembler.toDTO(application.getLotSubmission(id)));
	}
	
	public InvokeResult creatLotSubmissions(LotSubmissionDTO lotSubmissionDTO, String ctxPath, String[] flags, String[] coms, String[] encs) {
		Set<LotSubmission> lotSubmissions = new HashSet<LotSubmission>();
		Integer maxSerialNumber = findMaxSerialNumber(lotSubmissionDTO.getLotId());
		Lot lot = lotApplication.getLot(lotSubmissionDTO.getLotId());
//		if(lot.getType() == 1){
//			LotSubmission lotSubmission = LotSubmissionAssembler.toEntity(lotSubmissionDTO);
//			lotSubmission.setLot(lot);
//			Submission submission = submissionApplication.getSubmission(Long.parseLong(flags[i]));
//			String frontPosition = "";
//			String sn;
//			Integer maxFileNumber = null;
//			String headJson = submission.getContent();
//			JSONObject obj = JSON.parseObject(headJson);
//		}
		for (int i = 0; i < flags.length; i++) {
			LotSubmission lotSubmission = LotSubmissionAssembler.toEntity(lotSubmissionDTO);			
			lotSubmission.setLot(lot);
			Submission submission = submissionApplication.getSubmission(Long.parseLong(flags[i]));
			String frontPosition = "";
			String sn;
			Integer maxFileNumber = null;
			String headJson = submission.getContent();
			JSONObject obj = JSON.parseObject(headJson);
			switch (lot.getType()) {
			case 0:
				frontPosition = adjustLength(14, 1, obj.getString("6101")) + adjustLength(14, 0, obj.getString("2402")).substring(0, 6);
				maxFileNumber = findMaxPacketNumberByFrontPosition(frontPosition, lot.getLotCreator(), lot.getType());
				sn = String.format("%03d", maxFileNumber + 1);
				lotSubmission.setName(frontPosition + sn + "1000");
				break;
			case 1:
				frontPosition = "11" + adjustLength(11, 1, obj.getString("6501")) + adjustLength(14, 0, obj.getString("2585")).substring(0, 8) + submission.getRecordType().getFileType() + "1";
				maxFileNumber = findMaxPacketNumberByFrontPosition(frontPosition, lot.getLotCreator(), lot.getType());
				if(maxFileNumber > 9998){
					return InvokeResult.failure("流水号最大值为9999");
				}
				sn = String.format("%04d", maxFileNumber + 1);
				lotSubmission.setName(frontPosition + sn + "00");
				break;
			case 2:
				frontPosition = adjustLength(14, 1, obj.getString("6107")) + adjustLength(14, 0, obj.getString("2402")).substring(0, 6);
				maxFileNumber = findMaxPacketNumberByFrontPosition(frontPosition, lot.getLotCreator(), lot.getType());
				sn = String.format("%03d", maxFileNumber + 1);
				if(!submission.getRecordType().getCode().equals("9")){
					lotSubmission.setName(frontPosition + sn + submission.getRecordType().getCode() + submission.getPadding() + "2" + submission.getRecordType().getFileType() + "00");
				}else{
					lotSubmission.setName(frontPosition + sn + submission.getRecordType().getCode());
				}
				break;
			}
			lotSubmission.setCompression(Integer.valueOf(coms[i]));
			lotSubmission.setEncryption(Integer.valueOf(encs[i]));
			lotSubmission.setFrontPosition(frontPosition);
			lotSubmission.setFileNumber(maxFileNumber + 1);
			lotSubmission.setSerialNumber(maxSerialNumber + 1);
			maxSerialNumber++;
			lotSubmissions.add(lotSubmission);						
			generationFile(Long.valueOf(flags[i]), ctxPath + lotSubmission.getName(), lot.getType());
		}
		application.creatLotSubmissions(lotSubmissions);
		return InvokeResult.success();
	}
	
	private String adjustLength(int itemLength, int itemType, String itemValue){
		if(itemType == 0){
			//itemValue = String.format("%0" + itemLength + "d", Integer.parseInt(itemValue));
			while (itemValue.length() < itemLength) {  
				StringBuffer sb = new StringBuffer();  
				sb.append("0").append(itemValue);//左补0  				
				itemValue = sb.toString();
			}
		}else{
			itemValue = String.format("%-" + itemLength + "s", itemValue);
		}
		return itemValue;
	}
	
	private void generationFile(Long id, String name, Integer type){
		String result = submissionFacadeImpl.exportSubmission(id, type);
	    File f = new File(name + ".txt");//新建一个文件对象
        FileWriter fw;
        try {
        	fw = new FileWriter(f);//新建一个FileWriter	    
        	fw.write(result);//将字符串写入到指定的路径下的文件中
        	fw.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	
	public InvokeResult updateLotSubmission(LotSubmissionDTO lotSubmissionDTO) {
		LotSubmission lotSubmission = LotSubmissionAssembler.toEntity(lotSubmissionDTO);
		Lot lot = lotApplication.getLot(lotSubmissionDTO.getLotId());
		lotSubmission.setLot(lot);
		application.updateLotSubmission(lotSubmission);
		return InvokeResult.success();
	}
	
	public InvokeResult removeLotSubmission(Long id) {
		application.removeLotSubmission(application.getLotSubmission(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeLotSubmissions(Long[] ids, String savePath) {
		for (Long id : ids) {
			LotSubmission lotSubmission = application.getLotSubmission(id);
			Integer submissionFrom = lotSubmission.getSubmissionFrom();
			if(submissionFrom == PACKETCONSTANT.TASKPACKET_PACKETFROM_INSIDE){
				new File(savePath + lotSubmission.getLot().getId() + File.separator + "insideFiles" + File.separator + lotSubmission.getName() + ".csv").delete();
			}else if(submissionFrom == PACKETCONSTANT.TASKPACKET_PACKETFROM_OUTSIDE){
				new File(savePath + lotSubmission.getLot().getId() + File.separator + "outsideFiles" + File.separator + lotSubmission.getName()).delete();
			}
			application.removeLotSubmission(lotSubmission);
		}
		return InvokeResult.success();
	}
	
	public String showSubmissionContent(Long id, String ctxPath){
		LotSubmission lotSubmission = application.getLotSubmission(id);
		String path = "";
		if(lotSubmission.getSubmissionFrom() == PACKETCONSTANT.TASKPACKET_PACKETFROM_INSIDE){
			path = ctxPath + lotSubmission.getLot().getId() + File.separator + "insideFiles" + File.separator + lotSubmission.getName() + ".txt";
		}else if(lotSubmission.getSubmissionFrom() == PACKETCONSTANT.TASKPACKET_PACKETFROM_OUTSIDE){
			path = ctxPath + lotSubmission.getLot().getId() + File.separator + "outsideFiles" + File.separator + lotSubmission.getName();
		}
		StringBuffer sb = new StringBuffer();
		try {
			readToBuffer(sb, path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return sb.toString();
	}
	
	public List<LotSubmissionDTO> findAllLotSubmission() {
		return LotSubmissionAssembler.toDTOs(application.findAllLotSubmission());
	}
	
	public ModelAndView uploadLotSubmission(LotSubmissionDTO lotSubmissionDTO, String ctxPath){
		ModelAndView modelAndView = new ModelAndView("index");
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		Map<String,String> attributes = new HashMap();
		LotSubmission lotSubmission = findLotSubmissionByName(lotSubmissionDTO.getName());
		if(lotSubmission == null){
			lotSubmission = LotSubmissionAssembler.toEntity(lotSubmissionDTO);
			Integer max = findMaxSerialNumber(lotSubmissionDTO.getLotId());
			Lot lot = lotApplication.getLot(lotSubmissionDTO.getLotId());
			lotSubmission.setLot(lot);
			lotSubmission.setSubmissionFrom(PACKETCONSTANT.TASKPACKET_PACKETFROM_OUTSIDE);					
			lotSubmission.setCompression(1);
			lotSubmission.setEncryption(1);
			lotSubmission.setSerialNumber(max + 1);			
		}
		application.creatLotSubmission(lotSubmission);
		attributes.put("data", "上传成功");
		view.setAttributesMap(attributes);
		modelAndView.setView(view);
		return modelAndView;
	}
	
	public InvokeResult upLotSubmission(String sourceId, String destId){
		LotSubmission sourceLotSubmission = application.getLotSubmission(Long.parseLong(sourceId));
		LotSubmission destLotSubmission = application.getLotSubmission(Long.parseLong(destId));
		Integer sourceSerialNumber = sourceLotSubmission.getSerialNumber();
		Integer destSerialNumber = destLotSubmission.getSerialNumber();
		sourceLotSubmission.setSerialNumber(destSerialNumber);
		destLotSubmission.setSerialNumber(sourceSerialNumber);
		application.updateLotSubmission(sourceLotSubmission);
		application.updateLotSubmission(destLotSubmission);
		return InvokeResult.success();
	}
	
	public InvokeResult downLotSubmission(String sourceId, String destId){
		LotSubmission sourceLotSubmission = application.getLotSubmission(Long.parseLong(sourceId));
		LotSubmission destLotSubmission = application.getLotSubmission(Long.parseLong(destId));
		Integer sourceSerialNumber = sourceLotSubmission.getSerialNumber();
		Integer destSerialNumber = destLotSubmission.getSerialNumber();
		sourceLotSubmission.setSerialNumber(destSerialNumber);
		destLotSubmission.setSerialNumber(sourceSerialNumber);
		application.updateLotSubmission(sourceLotSubmission);
		application.updateLotSubmission(destLotSubmission);
		return InvokeResult.success();
	}
	
	public Page<LotSubmissionDTO> pageQueryLotSubmission(LotSubmissionDTO queryVo, int currentPage, int pageSize, Long lotId) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _lotSubmission from LotSubmission _lotSubmission   where 1=1 ");
	   	if (lotId != null) {
	   		jpql.append(" and _lotSubmission.lot.id = ?");
	   		conditionVals.add(lotId);
	   	}
	   	if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
	   		jpql.append(" and _lotSubmission.name like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getName()));
	   	}
	   	if (queryVo.getFrontPosition() != null && !"".equals(queryVo.getFrontPosition())) {
	   		jpql.append(" and _lotSubmission.frontPosition like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFrontPosition()));
	   	}
	   	jpql.append("order by  _lotSubmission.serialNumber asc");
        Page<LotSubmission> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();		   
        return new Page<LotSubmissionDTO>(pages.getStart(), pages.getResultCount(),pageSize, LotSubmissionAssembler.toDTOs(pages.getData()));
	}
	
	public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }
	
	private Integer findMaxSerialNumber(Long lotId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select max(_lotSubmission.serialNumber) from LotSubmission _lotSubmission  where 1=1 ");	   	
	   	if (lotId != null ) {
	   		jpql.append(" and _lotSubmission.lot.id = ? ");
	   		conditionVals.add(lotId);
	   	}
	   	if((getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult() == null)){
	   		return 0;
	   	}
	   	Integer max = (Integer) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return max;
	}
	
	@SuppressWarnings("unchecked")
	private LotSubmission findLotSubmissionByName(String name){
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _lotSubmission from LotSubmission _lotSubmission  where 1=1 ");
		if (name != null && !"".equals(name)) {
			jpql.append(" and _lotSubmission.name = ? ");
		   	conditionVals.add(name);
		}
		jpql.append(" and _lotSubmission.submissionFrom = ? ");
		conditionVals.add(PACKETCONSTANT.TASKPACKET_PACKETFROM_OUTSIDE);
		LotSubmission lotSubmission = (LotSubmission) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
		return lotSubmission;
	}
	
	private Integer findMaxPacketNumberByFrontPosition(String frontPosition, String createdBy, Integer type){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select max(_lotSubmission.fileNumber) from LotSubmission _lotSubmission  where 1=1 ");	   	
	   	if (frontPosition != null ) {
	   		jpql.append(" and _lotSubmission.frontPosition = ? ");
	   		conditionVals.add(frontPosition);
	   	}
	   	if (createdBy != null ) {
	   		jpql.append(" and _lotSubmission.lot.lotCreator = ? ");
	   		conditionVals.add(createdBy);
	   	}
	   	if (type != null ) {
	   		jpql.append(" and _lotSubmission.lot.type = ? ");
	   		conditionVals.add(type);
	   	}
	   	if((getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult() == null)){
	   		return 0;
	   	}
	   	Integer max = (Integer) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return max;
	}
}
