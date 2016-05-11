package org.packet.packetsimulation.facade.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
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
import org.packet.packetsimulation.facade.impl.MesgFacadeImpl.BaseTask;
import org.packet.packetsimulation.facade.impl.assembler.PacketAssembler;
import org.packet.packetsimulation.facade.impl.assembler.TaskPacketAssembler;
import org.packet.packetsimulation.facade.impl.assembler.ThreeStandardAssembler;
import org.packet.packetsimulation.facade.ThreeStandardFacade;
import org.packet.packetsimulation.application.ThreeStandardApplication;
import org.packet.packetsimulation.core.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

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
		//Date date = new Date(); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//String dateFormat = sdf.format(date);  
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
	
	public InvokeResult generateThreeStandard(String createdBy, int threeStandardNumber, int threadNumber){
		ExecutorService service = Executors.newFixedThreadPool(threadNumber);
		List<Future<String>> resultList = new ArrayList<Future<String>>();
		EmployeeUser employeeUser = findEmployeeUserByCreatedBy(createdBy);
        String organizationCode;
		if(employeeUser.getOrganization().equals("1")){
			organizationCode = employeeUser.getCompany().getSn();
		}else{
			organizationCode = employeeUser.getDepartment().getSn();
		}
		int a = threeStandardNumber / 10000;
		int b = threeStandardNumber % 10000;
		int max = findMaxCustomerCode(createdBy);
		Long start = System.currentTimeMillis();
		if (a != 0){
			for(int i = 0; i < a; i++){
				MyThread myThread = new MyThread();
				myThread.setNumber(10000);
				myThread.setCreatedBy(createdBy);
				myThread.setOrganizationCode(organizationCode);
				myThread.setMax(max + i * 10000);
				Future<String> future = service.submit(myThread);
	        	resultList.add(future);
			}
		} else{
			MyThread myThread = new MyThread();
			myThread.setNumber(threeStandardNumber);
			myThread.setCreatedBy(createdBy);
			myThread.setOrganizationCode(organizationCode);
			myThread.setMax(max);
			Future<String> future = service.submit(myThread);
        	resultList.add(future);
		}
		if (a != 0 && b != 0){
			MyThread myThread = new MyThread();
			myThread.setNumber(threeStandardNumber - a * 10000);
			myThread.setCreatedBy(createdBy);
			myThread.setOrganizationCode(organizationCode);
			myThread.setMax(max + a * 10000);
			Future<String> future = service.submit(myThread);
        	resultList.add(future);
		}
		service.shutdown();
		try {
			service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
    		return InvokeResult.failure(e.getMessage());
		}
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
		return InvokeResult.success();
	}
	
	public class MyThread implements Callable<String> {
		private int number;
		private String createdBy;
		private String organizationCode;
		private int max;
		public int getMax() {
			return max;
		}
		public void setMax(int max) {
			this.max = max;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public String getOrganizationCode() {
			return organizationCode;
		}
		public void setOrganizationCode(String organizationCode) {
			this.organizationCode = organizationCode;
		}
		@Override
		public String call() throws Exception{
			// TODO Auto-generated method stub		
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");       		
    		int a = max + 1;
    		List<ThreeStandard> threeStandards = new ArrayList<ThreeStandard>();
    		for(int i = 0; i < number; i++){       			
    			ThreeStandard threeStandard = new ThreeStandard();
    			String uuid = UUID.randomUUID().toString();
    			threeStandard.setOrganizationCode(organizationCode);
    			threeStandard.setCreatedBy(createdBy);
    			threeStandard.setName(uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,16));
    			threeStandard.setCredentialType("0");
    			threeStandard.setCredentialNumber(uuid.substring(16,18)+uuid.substring(19,23)+uuid.substring(24));
    			threeStandard.setCustomerCode(a);
    			a++;
    			threeStandard.setAcctCode("AcCode"+getShortUuid());
    			threeStandard.setConCode("Cc"+getShortUuid());
    			threeStandard.setCcc("Ccc"+getShortUuid());
    			java.util.Date time=null;
    			try {
    				time= sdf.parse(sdf.format(new Date()));
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    			threeStandard.setCreatedDate(time);
    			threeStandards.add(threeStandard);
    			if(i == (number - 1)){
    				application.creatThreeStandards(threeStandards);
    				threeStandards.clear();
    				System.gc();
    			}else if(i % 1000 == 0){
    				application.creatThreeStandards(threeStandards);
    				threeStandards.clear();
    				System.gc();
    			}       			
    		}
	        return null;			
		}	
	}
	
	public InvokeResult generateThreeStandard(ThreeStandardDTO threeStandardDTO, String threeStandardNumber){
		Long start = new Date().getTime();
		SimpleDateFormat sdf_ = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		String s = sdf_.format(start);
		String createdBy = threeStandardDTO.getCreatedBy();
		EmployeeUser employeeUser = findEmployeeUserByCreatedBy(threeStandardDTO.getCreatedBy());
		if(employeeUser.getOrganization().equals("1")){
			threeStandardDTO.setOrganizationCode(employeeUser.getCompany().getSn());
		}else{
			threeStandardDTO.setOrganizationCode(employeeUser.getDepartment().getSn());
		}
		threeStandardDTO.setCredentialType("0");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		List<ThreeStandard> threeStandards = new ArrayList<ThreeStandard>();
//		Integer max = findMaxCustomerCode(threeStandardDTO.getCreatedBy());
//		Integer a = max + 1;
//		for(int i = 0; i < Integer.parseInt(threeStandardNumber); i++){
//			ThreeStandard threeStandard = ThreeStandardAssembler.toEntity(threeStandardDTO);
//			String uuid = UUID.randomUUID().toString();
//			threeStandard.setName(uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,16));
//			threeStandard.setCredentialNumber(uuid.substring(16,18)+uuid.substring(19,23)+uuid.substring(24));
//			threeStandard.setCustomerCode(a);
//			a++;
//			threeStandard.setAcctCode("AcCode"+getShortUuid());
//			threeStandard.setConCode("Cc"+getShortUuid());
//			threeStandard.setCcc("Ccc"+getShortUuid());
//			java.util.Date time=null;
//			try {
//				time= sdf.parse(sdf.format(new Date()));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			threeStandard.setCreatedDate(time);
//			threeStandards.add(threeStandard);
//		}
		List<ThreeStandard> threeStandards = new ArrayList<ThreeStandard>();
		ForkJoinPool pool = new ForkJoinPool(5);
		Task task = new Task(Integer.parseInt(threeStandardNumber), createdBy);
        Future<List> result =  pool.submit(task);
        try {
        	threeStandards = result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Long insertstart=System.currentTimeMillis();
		application.creatThreeStandards(threeStandards);
		Long insertend=System.currentTimeMillis();
		Long end = new Date().getTime();
		String e = sdf_.format(end);
		System.out.println("插入耗时:"+(insertend-insertstart));
		System.out.println("开始时间:"+s);
		System.out.println("结束时间:"+e);
		return InvokeResult.success();
	}
	
	class Task extends RecursiveTask<List>{
        private int size = 20000;       
        private int threeStandardNumber;
        private String createdBy;
        public Task(int threeStandardNumber, String createdBy){
            this.threeStandardNumber = threeStandardNumber;
            this.createdBy = createdBy;
        }
        @Override
        protected List<ThreeStandard> compute() {
        	List<ThreeStandard> threeStandards = new ArrayList<ThreeStandard>();
            if(threeStandardNumber <= size){
                EmployeeUser employeeUser = findEmployeeUserByCreatedBy(createdBy);
                String organizationCode;
        		if(employeeUser.getOrganization().equals("1")){
        			organizationCode = employeeUser.getCompany().getSn();
        		}else{
        			organizationCode = employeeUser.getDepartment().getSn();
        		}
        		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");       		
        		Integer max = findMaxCustomerCode(createdBy);
        		Integer a = max + 1;
        		for(int i = 0; i < threeStandardNumber; i++){
        			ThreeStandard threeStandard = new ThreeStandard();
        			String uuid = UUID.randomUUID().toString();
        			threeStandard.setOrganizationCode(organizationCode);
        			threeStandard.setCreatedBy(createdBy);
        			threeStandard.setName(uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,16));
        			threeStandard.setCredentialType("0");
        			threeStandard.setCredentialNumber(uuid.substring(16,18)+uuid.substring(19,23)+uuid.substring(24));
        			threeStandard.setCustomerCode(a);
        			a++;
        			threeStandard.setAcctCode("AcCode"+getShortUuid());
        			threeStandard.setConCode("Cc"+getShortUuid());
        			threeStandard.setCcc("Ccc"+getShortUuid());
        			java.util.Date time=null;
        			try {
        				time= sdf.parse(sdf.format(new Date()));
        			} catch (ParseException e) {
        				e.printStackTrace();
        			}
        			threeStandard.setCreatedDate(time);
        			threeStandards.add(threeStandard);
        		}
            }else{
                //细分成小任务
                List<Task> tasks = new ArrayList<ThreeStandardFacadeImpl.Task>();
                for (int index = 0; index * size < threeStandardNumber; index++) {
                	Task task;
                    if((index + 1) * size > threeStandardNumber){
                        task = new Task(threeStandardNumber-index*size, createdBy);
                    }else{
                        task = new Task(size, createdBy);
                    }
                    task.fork();
                    tasks.add(task);
                }
                for (Task task : tasks) {
                	threeStandards.addAll(task.join());
                }
            }            
            return threeStandards;
        }      
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
	  	jpql.append("order by _threeStandard.customerCode asc");
        Page<ThreeStandard> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<ThreeStandardDTO>(pages.getStart(), pages.getResultCount(),pageSize, ThreeStandardAssembler.toDTOs(pages.getData()));
	}
	
	@Transactional
	@Override
	public ModelAndView importThreeStandard(ThreeStandardDTO threeStandardDTO, String fileName, String ctxPath) throws IOException, ParseException {
		ModelAndView modelAndView = new ModelAndView("index");
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		Map<String,String> attributes = new HashMap();
		File uploadFile = new File(ctxPath + fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ctxPath + fileName)));  
		String line = br.readLine();				
		String[] temp = line.split(",");
		if(temp.length!=3||!temp[0].equals("\"姓名\"")||!temp[1].equals("\"证件类型\"")||!temp[2].equals("\"证件号码\"")){
			br.close();
			uploadFile.delete();
			attributes.put("error","文件第1行应为''姓名','证件类型','证件号码''");
			view.setAttributesMap(attributes);
			modelAndView.setView(view);
			return modelAndView;
		}
		int lineNumber = 2;
		List<ThreeStandard> threeStandards = new ArrayList<ThreeStandard>();
		EmployeeUser employeeUser = findEmployeeUserByCreatedBy(threeStandardDTO.getCreatedBy());
		if(employeeUser.getOrganization().equals("1")){
			threeStandardDTO.setOrganizationCode(employeeUser.getCompany().getSn());
		}else{
			threeStandardDTO.setOrganizationCode(employeeUser.getDepartment().getSn());
		}
		Integer max = findMaxCustomerCode(threeStandardDTO.getCreatedBy());
		while((line=br.readLine())!=null){
			temp = line.split(",");
			if(temp.length!=3){
				br.close();
				uploadFile.delete();
				attributes.put("error","文件第"+lineNumber+"行不符合格式规范");
				view.setAttributesMap(attributes);
				modelAndView.setView(view);
				return modelAndView;
			}
			ThreeStandard threeStandard = ThreeStandardAssembler.toEntity(threeStandardDTO);
			threeStandard.setName(temp[0].substring(1,temp[0].length()-1));
			String credentialType = temp[1].substring(1,temp[1].length()-1);
			if(credentialType.equals("身份证")){
				credentialType = "0";
			}else if(credentialType.equals("军官证")){
				credentialType = "1";
			}else if(credentialType.equals("护照")){
				credentialType = "2";
			}
			threeStandard.setCredentialType(credentialType);
			threeStandard.setCredentialNumber(temp[2].substring(1,temp[2].length()-1));
			threeStandard.setCustomerCode(max+1);			
			threeStandard.setAcctCode("AcCode"+getShortUuid());
			threeStandard.setConCode("Cc"+getShortUuid());
			threeStandard.setCcc("Ccc"+getShortUuid());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date time = sdf.parse(sdf.format(new Date()));
			threeStandard.setCreatedDate(time);
			threeStandards.add(threeStandard);
			max++;
			lineNumber++;
		}
		br.close();
		uploadFile.delete();
		application.creatThreeStandards(threeStandards);
		attributes.put("data","上传并解析成功!");
		view.setAttributesMap(attributes);
		modelAndView.setView(view);
		return modelAndView;
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
