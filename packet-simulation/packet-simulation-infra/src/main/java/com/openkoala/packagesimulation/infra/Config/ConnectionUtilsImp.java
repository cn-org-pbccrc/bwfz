package com.openkoala.packagesimulation.infra.Config;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.packet.packetsimulation.core.domain.Config;

public class ConnectionUtilsImp {

	public static String TYPE_MQ="mq";

	public static String TYPE_HTTP="http";
	public static void main(String[]args){
		Config config = new Config();
		config.setType("mq");
		sendByConfig(config, "hello , i am from client!12333453");
		
		System.out.println("the receive data is "+ receiveByConfig(config));
	}
	
	public static  void sendByConfig(Config config, String content) {
		// TODO Auto-generated method stub
		// 如果发送方式为mq，则对mq参数进行解析
		if(config.getType().equals(TYPE_MQ)){
			String mqParamJson = config.getParam();
			// 将mq参数由json转为MQParam对象
			mqParamJson = "{\"mqAddress\":\"127.0.0.1\",\"mqPort\":\"1415\",\"mqCCISID\":\"1381\",\"mqChannel\":\"client.orange\",\"mqQueueManager\":\"orange\",\"mqQueue\":\"local1\"}";
		    ObjectMapper mapper = new ObjectMapper();
		    MQParam mqParam =null;
			try {
				mqParam = mapper.readValue(mqParamJson, MQParam.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    sendMsgByMQ(content, mqParam);
		}else if(config.getType().equals(TYPE_HTTP)){
			
		}
	}

	public static  String receiveByConfig(Config config) {
		// TODO Auto-generated method stub
		// 如果发送方式为mq，则对mq参数进行解析
		if(config.getType().equals(TYPE_MQ)){
			String mqParamJson = config.getParam();
			// 将mq参数由json转为MQParam对象
			mqParamJson = "{\"mqAddress\":\"127.0.0.1\",\"mqPort\":\"1415\",\"mqCCISID\":\"1381\",\"mqChannel\":\"client.orange\",\"mqQueueManager\":\"orange\",\"mqQueue\":\"local1\"}";
		    ObjectMapper mapper = new ObjectMapper();
		    MQParam mqParam =null;
			try {
				mqParam = mapper.readValue(mqParamJson, MQParam.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return receiveMsgByMQ( mqParam);
		}else if(config.getType().equals(TYPE_HTTP)){
			return null;
		}
		return null;
	}
//	@Override
//	public void receiveByConfig(Config config, String content) {
//		// TODO Auto-generated method stub
//		
//	}
	
	public static void sendMsgByMQ(String msg, MQParam param){
		MQUtils.send(msg, param);
	}
	public static String receiveMsgByMQ(MQParam param){
		return MQUtils.receive(param);
	}
}
