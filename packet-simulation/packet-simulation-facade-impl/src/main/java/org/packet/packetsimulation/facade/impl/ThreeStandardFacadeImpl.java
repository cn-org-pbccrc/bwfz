package org.packet.packetsimulation.facade.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import org.openkoala.gqc.infra.util.ReadAppointedLine;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.packet.packetsimulation.facade.dto.*;
import org.packet.packetsimulation.facade.impl.assembler.PacketAssembler;
import org.packet.packetsimulation.facade.impl.assembler.TaskPacketAssembler;
import org.packet.packetsimulation.facade.impl.assembler.ThreeStandardAssembler;
import org.packet.packetsimulation.facade.ThreeStandardFacade;
import org.packet.packetsimulation.application.ThreeStandardApplication;
import org.packet.packetsimulation.core.domain.*;
import org.springframework.transaction.annotation.Transactional;

@Named
public class ThreeStandardFacadeImpl implements ThreeStandardFacade {

	@Inject
	private ThreeStandardApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getThreeStandard(Long id) {
		return InvokeResult.success(ThreeStandardAssembler.toDTO(application.getThreeStandard(id)));
	}
	
	public InvokeResult creatThreeStandard(ThreeStandardDTO threeStandardDTO){
		EmployeeUser employeeUser = findEmployeeUserByCreatedBy(threeStandardDTO.getCreatedBy());
		if(employeeUser.getOrganization().equals("1")){
			threeStandardDTO.setOrganizationCode(employeeUser.getCompany().getSn());
		}else{
			threeStandardDTO.setOrganizationCode(employeeUser.getDepartment().getSn());
		}
		ThreeStandard threeStandard = ThreeStandardAssembler.toEntity(threeStandardDTO);
		//String uuid = UUID.randomUUID().toString().substring(0,8);
		Integer max = findMaxCustomerCode(threeStandardDTO.getCreatedBy());
		threeStandard.setCustomerCode(max+1);
		threeStandard.setAcctCode("AcCode"+getShortUuid());
		threeStandard.setConCode("Cc"+getShortUuid());
		threeStandard.setCcc("Ccc"+getShortUuid());
		Date date = new Date(); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String dateFormat = sdf.format(date);  
		java.util.Date time=null;
		try {
			time= sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		threeStandard.setCreatedDate(time);
		application.creatThreeStandard(threeStandard);
		return InvokeResult.success();
	}
	
	public static String[] chars = new String[]{
		"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
	    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
	    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V","W", "X", "Y", "Z"
	}; 
	 
	public static String getShortUuid(){ 
		StringBuffer stringBuffer = new StringBuffer(); 
	    String uuid = UUID.randomUUID().toString().replace("-", ""); 
	    for (int i = 0; i < 8; i++){ 
	    	String str = uuid.substring(i * 4, i * 4 + 4); 
	        int strInteger  = Integer.parseInt(str, 16); 
	        stringBuffer.append(chars[strInteger % 0x3E]); 
	    } 
	    return stringBuffer.toString(); 
	}
	
	private Integer findMaxCustomerCode(String createdBy){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select max(_threeStandard.customerCode) from ThreeStandard _threeStandard  where 1=1 ");
	   	
	   	if (createdBy != null ) {
	   		jpql.append(" and _threeStandard.createdBy = ? ");
	   		conditionVals.add(createdBy);
	   	}
	   	if((getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult()==null)){
	   		return 0;
	   	}
	   	Integer max = (Integer) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return max;
	}
	
	public InvokeResult updateThreeStandard(ThreeStandardDTO threeStandardDTO){
		application.updateThreeStandard(ThreeStandardAssembler.toEntity(threeStandardDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeThreeStandard(Long id) {
		application.removeThreeStandard(application.getThreeStandard(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeThreeStandards(Long[] ids) {
		Set<ThreeStandard> threeStandards= new HashSet<ThreeStandard>();
		for (Long id : ids) {
			threeStandards.add(application.getThreeStandard(id));
		}
		application.removeThreeStandards(threeStandards);
		return InvokeResult.success();
	}
	
	public List<ThreeStandardDTO> findAllThreeStandard() {
		return ThreeStandardAssembler.toDTOs(application.findAllThreeStandard());
	}
	
	public Page<ThreeStandardDTO> pageQueryThreeStandard(ThreeStandardDTO queryVo, int currentPage, int pageSize, String currentUserId) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _threeStandard from ThreeStandard _threeStandard   where 1=1 ");
	   	if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
	   		jpql.append(" and _threeStandard.name like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getName()));
	   	}		
	   	if (queryVo.getCredentialType() != null && !"".equals(queryVo.getCredentialType())) {
	   		jpql.append(" and _threeStandard.credentialType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCredentialType()));
	   	}		
	   	if (queryVo.getCredentialNumber() != null && !"".equals(queryVo.getCredentialNumber())) {
	   		jpql.append(" and _threeStandard.credentialNumber like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCredentialNumber()));
	   	}		
	   	if (queryVo.getOrganizationCode() != null && !"".equals(queryVo.getOrganizationCode())) {
	   		jpql.append(" and _threeStandard.organizationCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getOrganizationCode()));
	   	}		
	   	if (queryVo.getCustomerCode() != null && !"".equals(queryVo.getCustomerCode())) {
	   		jpql.append(" and _threeStandard.customerCode like ?");
	   		conditionVals.add(queryVo.getCustomerCode());
	   	}
	   	if (queryVo.getAcctCode() != null && !"".equals(queryVo.getAcctCode())) {
	   		jpql.append(" and _threeStandard.acctCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getAcctCode()));
	   	}
	   	if (queryVo.getConCode() != null && !"".equals(queryVo.getConCode())) {
	   		jpql.append(" and _threeStandard.conCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getConCode()));
	   	}
	   	if (queryVo.getCcc() != null && !"".equals(queryVo.getCcc())) {
	   		jpql.append(" and _threeStandard.ccc like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCcc()));
	   	}
	   	if (queryVo.getCreatedDate() != null) {
	   		jpql.append(" and _threeStandard.createdDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreatedDate());
	   		conditionVals.add(queryVo.getCreatedDateEnd());
	   	}	
	   	if (queryVo.getCreatedBy() != null && !"".equals(queryVo.getCreatedBy())) {
	   		jpql.append(" and _threeStandard.createdBy like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreatedBy()));
	   	}	
	  	if (currentUserId != null) {
	   		jpql.append(" and _threeStandard.createdBy = ?");
	   		conditionVals.add(currentUserId);
	   	}
        Page<ThreeStandard> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<ThreeStandardDTO>(pages.getStart(), pages.getResultCount(),pageSize, ThreeStandardAssembler.toDTOs(pages.getData()));
	}
	
	//@Transactional
	@Override
	public InvokeResult importThreeStandard(ThreeStandardDTO threeStandardDTO, String path, String ctxPath) throws IOException, ParseException {	
		String[] temp = new String[3];
		List<ThreeStandard> threeStandards= new ArrayList<ThreeStandard>();
		int totalLines = ReadAppointedLine.getTotalLines(new File(ctxPath+path));
		String flag = null;
		EmployeeUser employeeUser = findEmployeeUserByCreatedBy(threeStandardDTO.getCreatedBy());
		System.out.println("xxxxx"+employeeUser);
		if(employeeUser.getOrganization().equals("1")){
			threeStandardDTO.setOrganizationCode(employeeUser.getCompany().getSn());
		}else{
			threeStandardDTO.setOrganizationCode(employeeUser.getDepartment().getSn());
		}
		Integer max = findMaxCustomerCode(threeStandardDTO.getCreatedBy());
		Integer a = max + 1;
		for(int i = 1; i < totalLines; i++){
			ThreeStandard threeStandard = ThreeStandardAssembler.toEntity(threeStandardDTO);
			String appointedLine = ReadAppointedLine.readAppointedLineNumber(new File(ctxPath+path),i+1,totalLines);			
			System.out.println("第"+(i+1)+"行:"+appointedLine);
			temp = appointedLine.split(",");
			threeStandard.setName(temp[0].substring(1,temp[0].length()-1));
			if(temp[1].substring(1,temp[1].length()-1).equals("身份证")){
				flag = "0";
			}else if(temp[1].substring(1,temp[1].length()-1).equals("军官证")){
				flag = "1";
			}else if((temp[1].substring(1,temp[1].length()-1).equals("护照"))){
				flag = "2";
			}
			threeStandard.setCredentialType(flag);
			threeStandard.setCredentialNumber(temp[2].substring(1,temp[2].length()-1));
			//String uuid = UUID.randomUUID().toString().substring(0,8);
			
			threeStandard.setCustomerCode(a);
			a++;
			threeStandard.setAcctCode("AcCode"+getShortUuid());
			threeStandard.setConCode("Cc"+getShortUuid());
			threeStandard.setCcc("Ccc"+getShortUuid());
			Date date = new Date(); 
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String dateFormat = sdf.format(date);  
			java.util.Date time=null;
			try {
				time= sdf.parse(sdf.format(new Date()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			threeStandard.setCreatedDate(time);
			threeStandards.add(threeStandard);			
		}
		new File(ctxPath+path).delete();
		application.creatThreeStandards(threeStandards);
		return InvokeResult.success();
	}

	@SuppressWarnings("unchecked")
	private EmployeeUser findEmployeeUserByCreatedBy(String createdBy){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _employeeUser from EmployeeUser _employeeUser  where 1=1 ");
	   	
	   	if (createdBy != null ) {
	   		jpql.append(" and _employeeUser.userAccount = ? ");
	   		conditionVals.add(createdBy);
	   	}
	   	EmployeeUser employeeUser = (EmployeeUser) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return employeeUser;
	}
	
	@Override
	public String downloadCSV(String createdBy) {
		// TODO Auto-generated method stub
		List<ThreeStandard> threeStandardList = findThreeStandards(createdBy);
		if(null != threeStandardList && threeStandardList.size()>0){
			String flag = null;
	   		String result = "\"姓名\"" + ","+ "\"证件类型\"" + "," + "\"证件号码\"" + "\r\n";
	   		for(ThreeStandard threeStandard : threeStandardList){
	   			if(threeStandard.getCredentialType().equals("0")){
					flag = "身份证";
				}else if(threeStandard.getCredentialType().equals("1")){
					flag = "军官证";
				}else if(threeStandard.getCredentialType().equals("2")){
					flag = "护照";
				}
				result = result + "\"" + threeStandard.getName() + "\"" + "," + "\"" + flag + "\"" + "," + "\"" + threeStandard.getCredentialNumber() + "\"" + "\r\n";
	   		}
	   		return result;
	   	}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	private List<ThreeStandard> findThreeStandards(String createdBy) {
		// TODO Auto-generated method stub
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _threeStandard from ThreeStandard _threeStandard  where 1=1 ");
		if (createdBy != null && !"".equals(createdBy)) {
			jpql.append(" and _threeStandard.createdBy = ? ");
			conditionVals.add(createdBy);
		}
	   	List<ThreeStandard> threeStandardList = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return threeStandardList;
	}
}
