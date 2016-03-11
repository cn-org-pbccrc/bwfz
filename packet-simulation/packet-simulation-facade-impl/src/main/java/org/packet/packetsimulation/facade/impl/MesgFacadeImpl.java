package org.packet.packetsimulation.facade.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.openkoala.gqc.infra.util.XmlNode;
import org.openkoala.gqc.infra.util.XmlUtil;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.application.MesgApplication;
import org.packet.packetsimulation.application.MesgTypeApplication;
import org.packet.packetsimulation.application.PacketApplication;
import org.packet.packetsimulation.application.ThreeStandardApplication;
import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.Packet;
import org.packet.packetsimulation.core.domain.ThreeStandard;
import org.packet.packetsimulation.facade.MesgFacade;
import org.packet.packetsimulation.facade.dto.MesgDTO;
import org.packet.packetsimulation.facade.impl.assembler.MesgAssembler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import org.xml.sax.SAXException;

@Named
public class MesgFacadeImpl implements MesgFacade {

	@Inject
	private MesgApplication  application;
	
	@Inject
	private MesgTypeApplication  mesgTypeApplication;
	
	@Inject
	private PacketApplication  packetApplication;
	
	@Inject
	private ThreeStandardApplication threeStandardApplication;
	
	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getMesg(Long id) {
		MesgDTO dto = MesgAssembler.toDTO(application.getMesg(id));
		MesgType mesgType = mesgTypeApplication.getMesgType(dto.getMesgType());
		try {
			XmlNode xmlNode = XmlUtil.getXmlNodeByXmlContent(dto.getContent(),mesgType.getCountTag());
			dto.setContent(xmlNode.toHtmlTabString(mesgType.getFilePath()));
			//System.out.println("巨头现身吧:"+xmlNode.toHtmlTabString(mesgType.getFilePath()));
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
	
	public InvokeResult creatMesg(MesgDTO mesgDTO) {
		Mesg mesg = MesgAssembler.toEntity(mesgDTO);
		MesgType mesgType = mesgTypeApplication.getMesgType(mesgDTO.getMesgType());
		mesg.setMesgType(mesgType);
		Packet packet = packetApplication.getPacket(mesgDTO.getPacketId());
		mesg.setPacket(packet);
		application.creatMesg(mesg);
		return InvokeResult.success();
	}
	
	public InvokeResult verifyMesgType(Long id){
		Mesg mesg = application.getMesg(id);
		if(mesg.getMesgType().getFilePath().indexOf("正常报送记录")>0){
			return InvokeResult.success();
		}else{
			return InvokeResult.failure("只有正常报送记录才能进行批量");
		}
	}
	
	@Transactional(readOnly = false, rollbackFor = DataAccessException.class)
	public InvokeResult creatBatch(MesgDTO mesgDTO,String realPath,int batchNumber) {
		for(int i = 0; i<batchNumber; i++){
			Mesg mesg = MesgAssembler.toEntity(mesgDTO);
			MesgType mesgType = mesgTypeApplication.getMesgType(mesgDTO.getMesgType());
			mesg.setMesgType(mesgType);
			Packet packet = packetApplication.getPacket(mesgDTO.getPacketId());
			mesg.setPacket(packet);
			mesg.setContent(mesgDTO.getNodeValues());
			application.creatMesg(mesg);
		}
		return InvokeResult.success();
	}
	
	public InvokeResult creatMesgs(MesgDTO mesgDTO,String realPath,String[] values) {		
		List<Mesg> mesgs= new ArrayList<Mesg>();
		Mesg mesgById = application.getMesg(mesgDTO.getId());
		String content = mesgById.getContent();
		if(mesgById.getMesgType().getFilePath().equals("基本信息正常报送记录")){
			for(int i = 0; i < values.length; i++){
				Mesg mesg = new Mesg();
				String name = content.substring(content.indexOf("<Name>")+6,content.indexOf("</Name>"));
				String credentialType = content.substring(content.indexOf("<IDType>")+8,content.indexOf("</IDType>"));
				String credentialNumber = content.substring(content.indexOf("<IDNum>")+7,content.indexOf("</IDNum>"));
				String customerCode = content.substring(content.indexOf("<CstCode>")+9,content.indexOf("</CstCode>"));
				ThreeStandard threeStandard = threeStandardApplication.getThreeStandard(Long.parseLong(values[i]));			
				content = content.replace("<Name>"+name+"</Name>","<Name>"+threeStandard.getName()+"</Name>");
				content = content.replace("<IDType>"+credentialType+"</IDType>","<IDType>"+threeStandard.getCredentialType()+"</IDType>");
				content = content.replace("<IDNum>"+credentialNumber+"</IDNum>","<IDNum>"+threeStandard.getCredentialNumber()+"</IDNum>");
				content = content.replace("<CstCode>"+customerCode+"</CstCode>","<CstCode>"+threeStandard.getCustomerCode()+"</CstCode>");
				mesg.setMesgType(mesgById.getMesgType());
				mesg.setPacket(mesgById.getPacket());
				mesg.setContent(content);
				mesgs.add(mesg);
			}
		}else if(mesgById.getMesgType().getFilePath().equals("账户信息正常报送记录")){
			for(int i = 0; i < values.length; i++){
				Mesg mesg = new Mesg();
				String name = content.substring(content.indexOf("<Name>")+6,content.indexOf("</Name>"));
				String credentialType = content.substring(content.indexOf("<IDType>")+8,content.indexOf("</IDType>"));
				String credentialNumber = content.substring(content.indexOf("<IDNum>")+7,content.indexOf("</IDNum>"));
				String acctCode = content.substring(content.indexOf("<AcctCode>")+10,content.indexOf("</AcctCode>"));
				String conCode = content.substring(content.indexOf("<Mcc>")+5,content.indexOf("</Mcc>"));
				String ccc = content.substring(content.indexOf("<Ccc>")+5,content.indexOf("</Ccc>"));
				ThreeStandard threeStandard = threeStandardApplication.getThreeStandard(Long.parseLong(values[i]));			
				content = content.replace("<Name>"+name+"</Name>","<Name>"+threeStandard.getName()+"</Name>");
				content = content.replace("<IDType>"+credentialType+"</IDType>","<IDType>"+threeStandard.getCredentialType()+"</IDType>");
				content = content.replace("<IDNum>"+credentialNumber+"</IDNum>","<IDNum>"+threeStandard.getCredentialNumber()+"</IDNum>");
				content = content.replace("<AcctCode>"+acctCode+"</AcctCode>","<AcctCode>"+threeStandard.getAcctCode()+"</AcctCode>");
				content = content.replace("<Mcc>"+conCode+"</Mcc>","<Mcc>"+threeStandard.getConCode()+"</Mcc>");
				content = content.replace("<Ccc>"+ccc+"</Ccc>","<Ccc>"+threeStandard.getCcc()+"</Ccc>");
				mesg.setMesgType(mesgById.getMesgType());
				mesg.setPacket(mesgById.getPacket());
				mesg.setContent(content);
				mesgs.add(mesg);
			}
		}else if(mesgById.getMesgType().getFilePath().equals("合同信息正常报送记录")){
			for(int i = 0; i < values.length; i++){
				Mesg mesg = new Mesg();
				String name = content.substring(content.indexOf("<Name>")+6,content.indexOf("</Name>"));
				String credentialType = content.substring(content.indexOf("<IDType>")+8,content.indexOf("</IDType>"));
				String credentialNumber = content.substring(content.indexOf("<IDNum>")+7,content.indexOf("</IDNum>"));
				String conCode = content.substring(content.indexOf("<ConCode>")+9,content.indexOf("</ConCode>"));
				ThreeStandard threeStandard = threeStandardApplication.getThreeStandard(Long.parseLong(values[i]));			
				content = content.replace("<Name>"+name+"</Name>","<Name>"+threeStandard.getName()+"</Name>");
				content = content.replace("<IDType>"+credentialType+"</IDType>","<IDType>"+threeStandard.getCredentialType()+"</IDType>");
				content = content.replace("<IDNum>"+credentialNumber+"</IDNum>","<IDNum>"+threeStandard.getCredentialNumber()+"</IDNum>");
				content = content.replace("<ConCode>"+conCode+"</ConCode>","<ConCode>"+threeStandard.getConCode()+"</ConCode>");
				mesg.setMesgType(mesgById.getMesgType());
				mesg.setPacket(mesgById.getPacket());
				mesg.setContent(content);
				mesgs.add(mesg);
			}
		}else if(mesgById.getMesgType().getFilePath().equals("抵质押合同信息正常报送记录")){
			for(int i = 0; i < values.length; i++){
				Mesg mesg = new Mesg();
				String name = content.substring(content.indexOf("<GuarName>")+10,content.indexOf("</GuarName>"));
				String credentialType = content.substring(content.indexOf("<GuarCertType>")+14,content.indexOf("</GuarCertType>"));
				String credentialNumber = content.substring(content.indexOf("<GuarCertNum>")+13,content.indexOf("</GuarCertNum>"));
				String ccc = content.substring(content.indexOf("<Ccc>")+5,content.indexOf("</Ccc>"));
				ThreeStandard threeStandard = threeStandardApplication.getThreeStandard(Long.parseLong(values[i]));			
				content = content.replace("<GuarName>"+name+"</GuarName>","<GuarName>"+threeStandard.getName()+"</GuarName>");
				content = content.replace("<GuarCertType>"+credentialType+"</GuarCertType>","<GuarCertType>"+threeStandard.getCredentialType()+"</GuarCertType>");
				content = content.replace("<GuarCertNum>"+credentialNumber+"</GuarCertNum>","<GuarCertNum>"+threeStandard.getCredentialNumber()+"</GuarCertNum>");
				content = content.replace("<Ccc>"+ccc+"</Ccc>","<Ccc>"+threeStandard.getCcc()+"</Ccc>");
				mesg.setMesgType(mesgById.getMesgType());
				mesg.setPacket(mesgById.getPacket());
				mesg.setContent(content);
				mesgs.add(mesg);
			}
		}else{
			for(int i = 0; i < values.length; i++){
				Mesg mesg = new Mesg();
				String name = content.substring(content.indexOf("<Name>")+6,content.indexOf("</Name>"));
				String credentialType = content.substring(content.indexOf("<IDType>")+8,content.indexOf("</IDType>"));
				String credentialNumber = content.substring(content.indexOf("<IDNum>")+7,content.indexOf("</IDNum>"));				
				ThreeStandard threeStandard = threeStandardApplication.getThreeStandard(Long.parseLong(values[i]));			
				content = content.replace("<Name>"+name+"</Name>","<Name>"+threeStandard.getName()+"</Name>");
				content = content.replace("<IDType>"+credentialType+"</IDType>","<IDType>"+threeStandard.getCredentialType()+"</IDType>");
				content = content.replace("<IDNum>"+credentialNumber+"</IDNum>","<IDNum>"+threeStandard.getCredentialNumber()+"</IDNum>");				
				mesg.setMesgType(mesgById.getMesgType());
				mesg.setPacket(mesgById.getPacket());
				mesg.setContent(content);
				mesgs.add(mesg);
			}
		}
		application.creatMesgs(mesgs);
		return InvokeResult.success();
	}
	
	class BaseTask extends RecursiveTask<List>{
        private int size = 2000;       
        private List<ThreeStandard> data;
        private Mesg tmpMesg;
        private String content;
        public BaseTask(List<ThreeStandard> data, Mesg tmpMesg){
            this.data = data;
            this.tmpMesg = tmpMesg;
            this.content = tmpMesg.getContent();
        }
        @Override
        protected List<Mesg> compute() {
        	List<Mesg> mesgs = new ArrayList<Mesg>();
            if(data.size() <= size){
                System.out.println("******************************** size:" + data.size());
                for (ThreeStandard threeStandard : data) {
    				Mesg mesg = new Mesg();
    				String name = content.substring(content.indexOf("<Name>")+6,content.indexOf("</Name>"));
    				String credentialType = content.substring(content.indexOf("<IDType>")+8,content.indexOf("</IDType>"));
    				String credentialNumber = content.substring(content.indexOf("<IDNum>")+7,content.indexOf("</IDNum>"));
    				String customerCode = content.substring(content.indexOf("<CstCode>")+9,content.indexOf("</CstCode>"));
    				content = content.replaceFirst("<Name>"+name+"</Name>","<Name>"+threeStandard.getName()+"</Name>");
    				content = content.replaceFirst("<IDType>"+credentialType+"</IDType>","<IDType>"+threeStandard.getCredentialType()+"</IDType>");
    				content = content.replaceFirst("<IDNum>"+credentialNumber+"</IDNum>","<IDNum>"+threeStandard.getCredentialNumber()+"</IDNum>");
    				content = content.replaceFirst("<CstCode>"+customerCode+"</CstCode>","<CstCode>"+threeStandard.getCustomerCode()+"</CstCode>");   				
    				mesg.setMesgType(tmpMesg.getMesgType());
    				mesg.setPacket(tmpMesg.getPacket());
    				mesg.setContent(content);
    				mesgs.add(mesg);
    			}
            }else{
                //细分成小任务
                List<BaseTask> tasks = new ArrayList<MesgFacadeImpl.BaseTask>();
                for (int index = 0; index * size < data.size(); index++) {
                	BaseTask task;
                    if((index + 1) * size > data.size()){
                        task = new BaseTask(data.subList(index * size, data.size()), tmpMesg);
                    }else{
                        task = new BaseTask(data.subList(index * size, (index + 1) * size), tmpMesg);
                    }
                    task.fork();
                    tasks.add(task);
                }
                for (BaseTask task : tasks) {
                	mesgs.addAll(task.join());
                }
            }            
            return mesgs;
        }      
    }
	
	class AcctTask extends RecursiveTask<List>{
        private int size = 2000;       
        private List<ThreeStandard> data;
        private Mesg tmpMesg;
        private String content;
        public AcctTask(List<ThreeStandard> data, Mesg tmpMesg){
            this.data = data;
            this.tmpMesg = tmpMesg;
            this.content = tmpMesg.getContent();
        }
        @Override
        protected List<Mesg> compute() {
        	List<Mesg> mesgs = new ArrayList<Mesg>();
            if(data.size() <= size){
                System.out.println("******************************** size:" + data.size());
                for (ThreeStandard threeStandard : data) {
    				Mesg mesg = new Mesg();
    				String name = content.substring(content.indexOf("<Name>")+6,content.indexOf("</Name>"));
    				String credentialType = content.substring(content.indexOf("<IDType>")+8,content.indexOf("</IDType>"));
    				String credentialNumber = content.substring(content.indexOf("<IDNum>")+7,content.indexOf("</IDNum>"));
    				String acctCode = content.substring(content.indexOf("<AcctCode>")+10,content.indexOf("</AcctCode>"));
    				String conCode = content.substring(content.indexOf("<Mcc>")+5,content.indexOf("</Mcc>"));
    				String ccc = content.substring(content.indexOf("<Ccc>")+5,content.indexOf("</Ccc>"));		
    				content = content.replace("<Name>"+name+"</Name>","<Name>"+threeStandard.getName()+"</Name>");
    				content = content.replace("<IDType>"+credentialType+"</IDType>","<IDType>"+threeStandard.getCredentialType()+"</IDType>");
    				content = content.replace("<IDNum>"+credentialNumber+"</IDNum>","<IDNum>"+threeStandard.getCredentialNumber()+"</IDNum>");
    				content = content.replace("<AcctCode>"+acctCode+"</AcctCode>","<AcctCode>"+threeStandard.getAcctCode()+"</AcctCode>");
    				content = content.replace("<Mcc>"+conCode+"</Mcc>","<Mcc>"+threeStandard.getConCode()+"</Mcc>");
    				content = content.replace("<Ccc>"+ccc+"</Ccc>","<Ccc>"+threeStandard.getCcc()+"</Ccc>");
    				mesg.setMesgType(tmpMesg.getMesgType());
    				mesg.setPacket(tmpMesg.getPacket());
    				mesg.setContent(content);
    				mesgs.add(mesg);
    			}
            }else{
                //细分成小任务
                List<AcctTask> tasks = new ArrayList<MesgFacadeImpl.AcctTask>();
                for (int index = 0; index * size < data.size(); index++) {
                	AcctTask task;
                    if((index + 1) * size > data.size()){
                        task = new AcctTask(data.subList(index * size, data.size()), tmpMesg);
                    }else{
                        task = new AcctTask(data.subList(index * size, (index + 1) * size), tmpMesg);
                    }
                    task.fork();
                    tasks.add(task);
                }
                for (AcctTask task : tasks) {
                	mesgs.addAll(task.join());
                }
            }            
            return mesgs;
        }      
    }
	
	class ConTask extends RecursiveTask<List>{
        private int size = 2000;       
        private List<ThreeStandard> data;
        private Mesg tmpMesg;
        private String content;
        public ConTask(List<ThreeStandard> data, Mesg tmpMesg){
            this.data = data;
            this.tmpMesg = tmpMesg;
            this.content = tmpMesg.getContent();
        }
        @Override
        protected List<Mesg> compute() {
        	List<Mesg> mesgs = new ArrayList<Mesg>();
            if(data.size() <= size){
                System.out.println("******************************** size:" + data.size());
                for (ThreeStandard threeStandard : data) {
    				Mesg mesg = new Mesg();
    				String name = content.substring(content.indexOf("<Name>")+6,content.indexOf("</Name>"));
    				String credentialType = content.substring(content.indexOf("<IDType>")+8,content.indexOf("</IDType>"));
    				String credentialNumber = content.substring(content.indexOf("<IDNum>")+7,content.indexOf("</IDNum>"));
    				String conCode = content.substring(content.indexOf("<ConCode>")+9,content.indexOf("</ConCode>"));		
    				content = content.replace("<Name>"+name+"</Name>","<Name>"+threeStandard.getName()+"</Name>");
    				content = content.replace("<IDType>"+credentialType+"</IDType>","<IDType>"+threeStandard.getCredentialType()+"</IDType>");
    				content = content.replace("<IDNum>"+credentialNumber+"</IDNum>","<IDNum>"+threeStandard.getCredentialNumber()+"</IDNum>");
    				content = content.replace("<ConCode>"+conCode+"</ConCode>","<ConCode>"+threeStandard.getConCode()+"</ConCode>");
    				mesg.setMesgType(tmpMesg.getMesgType());
    				mesg.setPacket(tmpMesg.getPacket());
    				mesg.setContent(content);
    				mesgs.add(mesg);
    			}
            }else{
                //细分成小任务
                List<ConTask> tasks = new ArrayList<MesgFacadeImpl.ConTask>();
                for (int index = 0; index * size < data.size(); index++) {
                	ConTask task;
                    if((index + 1) * size > data.size()){
                        task = new ConTask(data.subList(index * size, data.size()), tmpMesg);
                    }else{
                        task = new ConTask(data.subList(index * size, (index + 1) * size), tmpMesg);
                    }
                    task.fork();
                    tasks.add(task);
                }
                for (ConTask task : tasks) {
                	mesgs.addAll(task.join());
                }
            }            
            return mesgs;
        }      
    }
	
	class CccTask extends RecursiveTask<List>{
        private int size = 2000;       
        private List<ThreeStandard> data;
        private Mesg tmpMesg;
        private String content;
        public CccTask(List<ThreeStandard> data, Mesg tmpMesg){
            this.data = data;
            this.tmpMesg = tmpMesg;
            this.content = tmpMesg.getContent();
        }
        @Override
        protected List<Mesg> compute() {
        	List<Mesg> mesgs = new ArrayList<Mesg>();
            if(data.size() <= size){
                System.out.println("******************************** size:" + data.size());
                for (ThreeStandard threeStandard : data) {
    				Mesg mesg = new Mesg();
    				String name = content.substring(content.indexOf("<GuarName>")+10,content.indexOf("</GuarName>"));
    				String credentialType = content.substring(content.indexOf("<GuarCertType>")+14,content.indexOf("</GuarCertType>"));
    				String credentialNumber = content.substring(content.indexOf("<GuarCertNum>")+13,content.indexOf("</GuarCertNum>"));
    				String ccc = content.substring(content.indexOf("<Ccc>")+5,content.indexOf("</Ccc>"));
    				content = content.replace("<GuarName>"+name+"</GuarName>","<GuarName>"+threeStandard.getName()+"</GuarName>");
    				content = content.replace("<GuarCertType>"+credentialType+"</GuarCertType>","<GuarCertType>"+threeStandard.getCredentialType()+"</GuarCertType>");
    				content = content.replace("<GuarCertNum>"+credentialNumber+"</GuarCertNum>","<GuarCertNum>"+threeStandard.getCredentialNumber()+"</GuarCertNum>");
    				content = content.replace("<Ccc>"+ccc+"</Ccc>","<Ccc>"+threeStandard.getCcc()+"</Ccc>");
    				mesg.setMesgType(tmpMesg.getMesgType());
    				mesg.setPacket(tmpMesg.getPacket());
    				mesg.setContent(content);
    				mesgs.add(mesg);
    			}
            }else{
                //细分成小任务
                List<CccTask> tasks = new ArrayList<MesgFacadeImpl.CccTask>();
                for (int index = 0; index * size < data.size(); index++) {
                	CccTask task;
                    if((index + 1) * size > data.size()){
                        task = new CccTask(data.subList(index * size, data.size()), tmpMesg);
                    }else{
                        task = new CccTask(data.subList(index * size, (index + 1) * size), tmpMesg);
                    }
                    task.fork();
                    tasks.add(task);
                }
                for (CccTask task : tasks) {
                	mesgs.addAll(task.join());
                }
            }            
            return mesgs;
        }      
    }
	
	class OthTask extends RecursiveTask<List>{
        private int size = 2000;       
        private List<ThreeStandard> data;
        private Mesg tmpMesg;
        private String content;
        public OthTask(List<ThreeStandard> data, Mesg tmpMesg){
            this.data = data;
            this.tmpMesg = tmpMesg;
            this.content = tmpMesg.getContent();
        }
        @Override
        protected List<Mesg> compute() {
        	List<Mesg> mesgs = new ArrayList<Mesg>();
            if(data.size() <= size){
                System.out.println("******************************** size:" + data.size());
                for (ThreeStandard threeStandard : data) {
    				Mesg mesg = new Mesg();
    				String name = content.substring(content.indexOf("<Name>")+6,content.indexOf("</Name>"));
    				String credentialType = content.substring(content.indexOf("<IDType>")+8,content.indexOf("</IDType>"));
    				String credentialNumber = content.substring(content.indexOf("<IDNum>")+7,content.indexOf("</IDNum>"));				
    				content = content.replace("<Name>"+name+"</Name>","<Name>"+threeStandard.getName()+"</Name>");
    				content = content.replace("<IDType>"+credentialType+"</IDType>","<IDType>"+threeStandard.getCredentialType()+"</IDType>");
    				content = content.replace("<IDNum>"+credentialNumber+"</IDNum>","<IDNum>"+threeStandard.getCredentialNumber()+"</IDNum>");	
    				mesg.setMesgType(tmpMesg.getMesgType());
    				mesg.setPacket(tmpMesg.getPacket());
    				mesg.setContent(content);
    				mesgs.add(mesg);
    			}
            }else{
                //细分成小任务
                List<OthTask> tasks = new ArrayList<MesgFacadeImpl.OthTask>();
                for (int index = 0; index * size < data.size(); index++) {
                	OthTask task;
                    if((index + 1) * size > data.size()){
                        task = new OthTask(data.subList(index * size, data.size()), tmpMesg);
                    }else{
                        task = new OthTask(data.subList(index * size, (index + 1) * size), tmpMesg);
                    }
                    task.fork();
                    tasks.add(task);
                }
                for (OthTask task : tasks) {
                	mesgs.addAll(task.join());
                }
            }            
            return mesgs;
        }      
    }
	
	public InvokeResult creatMesgsByInput(MesgDTO mesgDTO,int startOfThreeStandard,int endOfThreeStandard,String currentUserId){
		Long start = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		String s = sdf.format(start);		
		List<ThreeStandard> list= queryThreeStandardByInput(startOfThreeStandard,endOfThreeStandard,currentUserId);		
		List<Mesg> mesgs= new ArrayList<Mesg>();
		Mesg mesg = application.getMesg(mesgDTO.getId());
		if(mesg.getMesgType().getFilePath().equals("基本信息正常报送记录")){
//			for(int i = 0; i < list.size(); i++){
//				Mesg mesg = new Mesg();
//				ThreeStandard threeStandard = threeStandardApplication.getThreeStandard(list.get(i).getId());
//				Document document=null;
//				try {
//					document = DocumentHelper.parseText(content);
//					Element root = document.getRootElement();
//					Element bsSgmt = root.element("BaseInf").element("BsSgmt");
//					Element name =bsSgmt.element("Name");
//					name.setText(threeStandard.getName());
//					Element iDType = bsSgmt.element("IDType");
//					iDType.setText(threeStandard.getCredentialType());
//					Element iDNum = bsSgmt.element("IDNum");
//					iDNum.setText(threeStandard.getCredentialNumber());
//					Element cstCode = bsSgmt.element("CstCode");
//					cstCode.setText(String.valueOf(threeStandard.getCustomerCode()));
//					MesgType mesgType = mesgTypeApplication.getMesgType(mesgDTO.getMesgType());
//					mesg.setMesgType(mesgType);
//					Packet packet = packetApplication.getPacket(mesgDTO.getPacketId());
//					mesg.setPacket(packet);
//					mesg.setContent(document.asXML().toString());
//					mesgs.add(mesg);
//				} catch (DocumentException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}			
//			}
			ForkJoinPool pool = new ForkJoinPool(5);
			BaseTask task = new BaseTask(list, mesg);
	        Future<List> result =  pool.submit(task);
	        try {
	            mesgs = result.get();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } catch (ExecutionException e) {
	            e.printStackTrace();
	        }
		}else if(mesg.getMesgType().getFilePath().equals("账户信息正常报送记录")){
			ForkJoinPool pool = new ForkJoinPool(5);
			AcctTask task = new AcctTask(list, mesg);
	        Future<List> result =  pool.submit(task);
	        try {
	            mesgs = result.get();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } catch (ExecutionException e) {
	            e.printStackTrace();
	        }
		}else if(mesg.getMesgType().getFilePath().equals("合同信息正常报送记录")){
			ForkJoinPool pool = new ForkJoinPool(5);
			ConTask task = new ConTask(list, mesg);
	        Future<List> result =  pool.submit(task);
	        try {
	            mesgs = result.get();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } catch (ExecutionException e) {
	            e.printStackTrace();
	        }
		}else if(mesg.getMesgType().getFilePath().equals("抵质押合同信息正常报送记录")){
			ForkJoinPool pool = new ForkJoinPool(5);
			CccTask task = new CccTask(list, mesg);
	        Future<List> result =  pool.submit(task);
	        try {
	            mesgs = result.get();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } catch (ExecutionException e) {
	            e.printStackTrace();
	        }
		}else{
			ForkJoinPool pool = new ForkJoinPool(5);
			OthTask task = new OthTask(list, mesg);
	        Future<List> result =  pool.submit(task);
	        try {
	            mesgs = result.get();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } catch (ExecutionException e) {
	            e.printStackTrace();
	        }
		}
		Long insertstart=System.currentTimeMillis();
		application.creatMesgs(mesgs);
		Long insertend=System.currentTimeMillis();
		Long end = new Date().getTime();
		String e = sdf.format(end);
		System.out.println("插入耗时:"+(insertend-insertstart));
		System.out.println("开始时间:"+s);
		System.out.println("结束时间:"+e);
		return InvokeResult.success();
	}
	
	public InvokeResult updateMesg(MesgDTO mesgDTO) {		
		Mesg mesg = MesgAssembler.toEntity(mesgDTO);		
		MesgType mesgType = mesgTypeApplication.getMesgType(mesgDTO.getMesgType());
		mesg.setMesgType(mesgType);
		Packet packet = packetApplication.getPacket(mesgDTO.getPacketId());
		mesg.setPacket(packet);
		application.updateMesg(mesg);
		return InvokeResult.success();
	}
	
	public InvokeResult removeMesg(Long id) {
		application.removeMesg(application.getMesg(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeMesgs(Long[] ids) {
		Set<Mesg> mesgs= new HashSet<Mesg>();
		for (Long id : ids) {
			mesgs.add(application.getMesg(id));
		}
		application.removeMesgs(mesgs);
		return InvokeResult.success();
	}
	
	public List<MesgDTO> findAllMesg() {
		return MesgAssembler.toDTOs(application.findAllMesg());
	}
	
	@SuppressWarnings("unchecked")
	public Page<MesgDTO> pageQueryMesg(MesgDTO queryVo, int currentPage, int pageSize,Long packetId) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesg from Mesg _mesg   where 1=1 ");
	   	
	   	if (packetId != null ) {
	   		jpql.append(" and _mesg.packet.id = ? ");
	   		conditionVals.add(packetId);
	   	}
	   	if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
	   		jpql.append(" and _mesg.remark like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
	   	}	
	   	if (queryVo.getContent() != null && !"".equals(queryVo.getContent())) {
	   		jpql.append(" and _mesg.content like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getContent()));
	   	}
        Page<Mesg> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<MesgDTO>(pages.getStart(), pages.getResultCount(),pageSize, MesgAssembler.toDTOs(pages.getData()));
	}

//	@Override
//	public InvokeResult getMesgForUpdate(Long id) {
//		MesgDTO dto = MesgAssembler.toDTO(application.getMesg(id));
//		MesgType mesgType = mesgTypeApplication.getMesgType(dto.getMesgType());
//		try {
//			XmlNode xmlNode = XmlUtil.getXmlNodeByXmlContent(dto.getContent(),mesgType.getCountTag());
//			dto.setContent(xmlNode.toEditHtmlTabString(mesgType.getFilePath()));
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return InvokeResult.success(dto);
//	}
	
	@Override
	public InvokeResult getMesgForUpdate(Long id) {
		Mesg mesg = application.getMesg(id);
		MesgType mesgType = mesgTypeApplication.getMesgType(mesg.getMesgType().getId());
		String content = null;
		try {
			XmlNode xmlNode = XmlUtil.getXmlNodeByXmlContent(mesgType.getXml(),mesgType.getCountTag());
			XmlNode xmlNodeForUpdate = XmlUtil.getXmlNodeByXmlContent(mesg.getContent(),mesgType.getCountTag());
			content = xmlNode.toEditHtmlTabStringForUpdate(mesgType.getFilePath(), xmlNodeForUpdate);
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
		MesgDTO dto = MesgAssembler.toDTO(mesg);
		dto.setContent(content);
		return InvokeResult.success(dto);
//		MesgDTO dto = MesgAssembler.toDTO(application.getMesg(id));
//		MesgType mesgType = mesgTypeApplication.getMesgType(dto.getMesgType());
//		try {
//			XmlNode xmlNode = XmlUtil.getXmlNodeByXmlContent(dto.getContent(),mesgType.getCountTag());
//			dto.setContent(xmlNode.toEditHtmlTabString(mesgType.getFilePath()));
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return InvokeResult.success(dto);
	}

	@Override
	public InvokeResult getMesgForBatch(Long id) {
		MesgDTO dto = MesgAssembler.toDTO(application.getMesg(id));
		return InvokeResult.success(dto);
	}
	
	@SuppressWarnings("unchecked")
	public List<MesgDTO> queryMesgByPacketId(Long packetId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mesg from Mesg _mesg   where 1=1 ");
	   	
	   	if (packetId != null ) {
	   		jpql.append(" and _mesg.packet.id = ? ");
	   		conditionVals.add(packetId);
	   	}
	   	List<Mesg> list = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	List<MesgDTO> dtolist = MesgAssembler.toDTOs(list);
	   	return dtolist;
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
	
	public List<ThreeStandard> queryThreeStandardByInput(int startOfThreeStandard,int endOfThreeStandard,String currentUserId){
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _threeStandard from ThreeStandard _threeStandard  where 1=1");
		if (currentUserId != null ) {
	   		jpql.append(" and _threeStandard.createdBy = ? ");
	   		conditionVals.add(currentUserId);
	   	}
		List<ThreeStandard> result = getQueryChannelService().createJpqlQuery(jpql.toString()).setFirstResult(startOfThreeStandard-1).setPageSize(endOfThreeStandard-startOfThreeStandard+1).setParameters(conditionVals).list();
		return result;
	}
}
