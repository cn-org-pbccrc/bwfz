package org.packet.packetsimulation.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.gqc.infra.util.XmlNode;
import org.openkoala.gqc.infra.util.XmlUtil;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.impl.assembler.MesgAssembler;
import org.packet.packetsimulation.facade.impl.assembler.MesgBatchAssembler;
import org.packet.packetsimulation.facade.MesgBatchFacade;
import org.packet.packetsimulation.application.MesgBatchApplication;
import org.packet.packetsimulation.application.MesgTypeApplication;
import org.packet.packetsimulation.core.domain.*;
import org.xml.sax.SAXException;

@Named
public class MesgBatchFacadeImpl implements MesgBatchFacade {

	@Inject
	private MesgBatchApplication  application;

	@Inject
	private MesgTypeApplication  mesgTypeApplication;
	
	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getMesgBatch(Long id) {
		MesgBatchDTO dto = MesgBatchAssembler.toDTO(application.getMesgBatch(id));
		MesgType mesgType = mesgTypeApplication.getMesgType(dto.getMesgType());
		try {
			XmlNode xmlNode = XmlUtil.getXmlNodeByXmlContent(dto.getXml(),mesgType.getCountTag());
			dto.setXml(xmlNode.toHtmlTabString(mesgType.getCode()));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return InvokeResult.success(dto);
	}
	
	public InvokeResult creatMesgBatch(MesgBatchDTO mesgBatchDTO, String ctxPath) {
	    Long count = queryCountOfThreeStandard(mesgBatchDTO.getCreatedBy());
	    if(mesgBatchDTO.getStart() + mesgBatchDTO.getPacketNum() * mesgBatchDTO.getMesgNum() - 1 > count){
			return InvokeResult.failure("批量记录数不能大于三标总个数");
		}
		Set<MesgBatch> mesgBatchs = new HashSet<MesgBatch>();
		MesgType mesgType = mesgTypeApplication.getMesgType(mesgBatchDTO.getMesgType());
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String frontPosition = mesgBatchDTO.getOrigSender() + dateFormat.format(mesgBatchDTO.getOrigSendDate()) + mesgType.getCode() + "0";
		Integer max = findMaxSerialNumberByFrontPositionAndCreatedBy(frontPosition, mesgBatchDTO.getCreatedBy());
		if(max > 9998){
			return InvokeResult.failure("流水号最大值为9999!");
		}	    
		ExecutorService service = Executors.newFixedThreadPool(10);
		List<Future<String>> resultList = new ArrayList<Future<String>>();
		Long start = System.currentTimeMillis();
		for (int i = 0; i < mesgBatchDTO.getPacketNum(); i++){
			MesgBatch mesgBatch = MesgBatchAssembler.toEntity(mesgBatchDTO);
        	String sn = String.format("%04d", max + i + 1);
        	mesgBatch.setPacketName(frontPosition+"0"+sn);
    	    mesgBatch.setFrontPosition(frontPosition);
    	    mesgBatch.setSerialNumber(max + i + 1);
    		mesgBatch.setMesgType(mesgType);
    		mesgBatch.setDataType("0");
    		mesgBatch.setStart(mesgBatchDTO.getStart() + i * mesgBatchDTO.getMesgNum());
    		mesgBatchs.add(mesgBatch);
        	MyThread myThread = new MyThread();
        	myThread.setStart(mesgBatchDTO.getStart() + i * mesgBatchDTO.getMesgNum());
        	myThread.setEnd(mesgBatchDTO.getStart() + (i + 1) * mesgBatchDTO.getMesgNum() - 1);
        	myThread.setPacketName(frontPosition+"0"+sn);
        	myThread.setContent(mesgBatchDTO.getXml());
        	myThread.setCurrentUserId(mesgBatchDTO.getCreatedBy());
        	myThread.setCtxPath(ctxPath);
        	Future<String> future = service.submit(myThread);
        	resultList.add(future);
		}
		service.shutdown();
		for(Future<String> fs : resultList){
        	try{
        		fs.get();
        	} catch (InterruptedException e){
        		e.printStackTrace();
        		return InvokeResult.failure(e.getMessage());
        	} catch (ExecutionException e){
        		e.printStackTrace();
        		return InvokeResult.failure(e.getMessage());
        	}
        }
		Long end = System.currentTimeMillis();
        System.out.println("hahahahahahahahaha:" + (end - start) + "ms");
		application.creatMesgBatchs(mesgBatchs);
		return InvokeResult.success();
	}
	
	public InvokeResult updateMesgBatch(MesgBatchDTO mesgBatchDTO) {
		application.updateMesgBatch(MesgBatchAssembler.toEntity(mesgBatchDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeMesgBatch(Long id) {
		application.removeMesgBatch(application.getMesgBatch(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeMesgBatchs(Long[] ids, String ctxPath) {
		Set<MesgBatch> mesgBatchs= new HashSet<MesgBatch>();
		for (Long id : ids) {
			MesgBatch mesgBatch = application.getMesgBatch(id);
			new File(ctxPath + mesgBatch.getPacketName() + ".csv").delete();
			mesgBatchs.add(mesgBatch);
		}
		application.removeMesgBatchs(mesgBatchs);
		return InvokeResult.success();
	}
	
	public List<MesgBatchDTO> findAllMesgBatch() {
		return MesgBatchAssembler.toDTOs(application.findAllMesgBatch());
	}
	
	public Page<MesgBatchDTO> pageQueryMesgBatch(MesgBatchDTO queryVo, int currentPage, int pageSize, String currentUserId) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesgBatch from MesgBatch _mesgBatch   where 1=1 ");
	   	if (queryVo.getPacketName() != null && !"".equals(queryVo.getPacketName())) {
	   		jpql.append(" and _mesgBatch.packetName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPacketName()));
	   	}		
	   	if (queryVo.getFileVersion() != null && !"".equals(queryVo.getFileVersion())) {
	   		jpql.append(" and _mesgBatch.fileVersion like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFileVersion()));
	   	}		
	   	if (queryVo.getOrigSender() != null && !"".equals(queryVo.getOrigSender())) {
	   		jpql.append(" and _mesgBatch.origSender like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getOrigSender()));
	   	}		
	   	if (queryVo.getOrigSendDate() != null) {
	   		jpql.append(" and _mesgBatch.origSendDate between ? and ? ");
	   		conditionVals.add(queryVo.getOrigSendDate());
	   		conditionVals.add(queryVo.getOrigSendDateEnd());
	   	}	
	   	if (queryVo.getDataType() != null && !"".equals(queryVo.getDataType())) {
	   		jpql.append(" and _mesgBatch.dataType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDataType()));
	   	}		
	   	if (queryVo.getXml() != null && !"".equals(queryVo.getXml())) {
	   		jpql.append(" and _mesgBatch.xml like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getXml()));
	   	}		
	   	if (queryVo.getMesgNum() != null && !"".equals(queryVo.getMesgNum())) {
	   		jpql.append(" and _mesgBatch.mesgNum like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getMesgNum()));
	   	}
	   	if (queryVo.getStart() != null && !"".equals(queryVo.getStart())) {
	   		jpql.append(" and _mesgBatch.start like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getStart()));
	   	}
	   	if (queryVo.getPacketNum() != null && !"".equals(queryVo.getPacketNum())) {
	   		jpql.append(" and _mesgBatch.packetNum like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPacketNum()));
	   	}
	   	if (queryVo.getReserve() != null && !"".equals(queryVo.getReserve())) {
	   		jpql.append(" and _mesgBatch.reserve like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getReserve()));
	   	}		
//	   	if (queryVo.getFrontPosition() != null && !"".equals(queryVo.getFrontPosition())) {
//	   		jpql.append(" and _mesgBatch.frontPosition like ?");
//	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFrontPosition()));
//	   	}		
	   	if (currentUserId != null) {
	   		jpql.append(" and _mesgBatch.createdBy = ?");
	   		conditionVals.add(currentUserId);
	   	}		
        Page<MesgBatch> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<MesgBatchDTO>(pages.getStart(), pages.getResultCount(),pageSize, MesgBatchAssembler.toDTOs(pages.getData()));
	}
	
	public class MyThread implements Callable<String> {
		
		private Integer start;
		
		private Integer end;
		
		private String packetName;
		
		private String content;
		
		private String currentUserId;
		
		private String ctxPath;
			
		public void setStart(Integer start) {
			this.start = start;
		}

		public void setEnd(Integer end) {
			this.end = end;
		}

		public void setPacketName(String packetName) {
			this.packetName = packetName;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public void setCurrentUserId(String currentUserId) {
			this.currentUserId = currentUserId;
		}

		public void setCtxPath(String ctxPath) {
			this.ctxPath = ctxPath;
		}

		@Override
		public String call() throws Exception{			
			// TODO Auto-generated method stub
			int PARTNUM = 10000;
			int a = (end - start + 1) / PARTNUM;
			int b = (end - start + 1) % PARTNUM;
			File f = new File(ctxPath + packetName + ".csv");//新建一个文件对象
			FileWriter fw = new FileWriter(f, true);//新建一个FileWriter
			if (a != 0){
				for(int i = 0; i < a; i++){				
					String xmls = produce(i * PARTNUM + start, (i + 1) * PARTNUM + start - 1);					    
			        fw.write(xmls);//将字符串写入到指定的路径下的文件中
				}
			} else{
				String xmls = produce(start, end);
				fw.write(xmls);
			}
			if (a != 0 && b != 0){
				String xmls = produce(a * PARTNUM + start, end);
				fw.write(xmls);
			}
			fw.flush();
			fw.close();
			return null;
		}
		
		public String produce(int start, int end){
			List<ThreeStandard> list = queryThreeStandardByInput(start, end, currentUserId);
			String xmls = "";
			String replacedContent = content.substring(content.indexOf("<CstCode>"), content.indexOf("</IDNum>")+8);
			for(int i = 0; i < list.size(); i++){
				ThreeStandard threeStandard = list.get(i);
				content = content.replace(replacedContent,"<CstCode>"+threeStandard.getCustomerCode()+"</CstCode>"+"<Name>"+threeStandard.getName()+"</Name>"+"<IDType>"+threeStandard.getCredentialType()+"</IDType>"+"<IDNum>"+threeStandard.getCredentialNumber()+"</IDNum>");				
				xmls = xmls + content + "\r\n";
			}
			return xmls;	
		}	
	}
	
	private Integer findMaxSerialNumberByFrontPositionAndCreatedBy(String frontPosition, String createdBy){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select max(_mesgBatch.serialNumber) from MesgBatch _mesgBatch  where 1=1 ");
	   	
	   	if (frontPosition != null ) {
	   		jpql.append(" and _mesgBatch.frontPosition = ? ");
	   		conditionVals.add(frontPosition);
	   	}
	   	if (createdBy != null ) {
	   		jpql.append(" and _mesgBatch.createdBy = ? ");
	   		conditionVals.add(createdBy);
	   	}
	   	if((getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult()==null)){
	   		return 0;
	   	}
	   	Integer max = (Integer) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return max;
	}
	
	public Long queryCountOfThreeStandard(String currentUserId){
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select count(_threeStandard) from ThreeStandard _threeStandard where 1=1");
		if (currentUserId != null ) {
	   		jpql.append(" and _threeStandard.createdBy = ? ");
	   		conditionVals.add(currentUserId);
	   	}
		Long result = (Long) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
		return result;
	}
	
	public List<ThreeStandard> queryThreeStandardByInput(int start,int end,String currentUserId){
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _threeStandard from ThreeStandard _threeStandard  where 1=1");
		if (currentUserId != null ) {
	   		jpql.append(" and _threeStandard.createdBy = ? ");
	   		conditionVals.add(currentUserId);
	   	}
		List<ThreeStandard> result = getQueryChannelService().createJpqlQuery(jpql.toString()).setFirstResult(start-1).setPageSize(end-start+1).setParameters(conditionVals).list();
		return result;
	}	
}
