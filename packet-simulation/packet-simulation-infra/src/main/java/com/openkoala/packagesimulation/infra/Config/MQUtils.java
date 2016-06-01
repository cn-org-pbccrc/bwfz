package com.openkoala.packagesimulation.infra.Config;

import java.io.IOException;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;

public class MQUtils {

	public static void main(String[] args) {

		MQParam param = new MQParam();
		param.setMqAddress("localhost");
		param.setMqCCISID(1381);
		param.setMqPort(1415);
		param.setMqChannel("client.orange");
		param.setMqQueueManager("orange");
		param.setMqQueue("local1");
		send("测试12333", param);
		
		System.out.println("receive: "+receive(param));
	}

	public static void send(String msg, MQParam param) {
		MQQueueManager qmanager = null;
		MQQueue queue = null;
		MQEnvironment.hostname = param.getMqAddress();
		MQEnvironment.port = param.getMqPort();
		MQEnvironment.channel = param.getMqChannel();
		MQEnvironment.CCSID = param.getMqCCISID();
		try {
			qmanager = new MQQueueManager(param.getMqQueueManager());

			int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF
					| MQConstants.MQOO_INQUIRE | MQConstants.MQOO_OUTPUT;
			queue = qmanager.accessQueue(param.getMqQueue(), openOptions);

			MQMessage m = new MQMessage();
			m.writeObject(msg);
			queue.put(m);
			System.out.println("send completed!");
			if (qmanager != null) {
				qmanager.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String receive(MQParam param) {
		 MQMessage mqMsg = new MQMessage();
		MQQueueManager qmanager = null;
		MQQueue queue = null;
		MQEnvironment.hostname = param.getMqAddress();
		MQEnvironment.port = param.getMqPort();
		MQEnvironment.channel = param.getMqChannel();
		MQEnvironment.CCSID = param.getMqCCISID();
		int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF
				| MQConstants.MQOO_INQUIRE | MQConstants.MQOO_OUTPUT;
		String msg =null;
		try {
			qmanager = new MQQueueManager(param.getMqQueueManager());

            MQGetMessageOptions mqGetMessageOptions = new MQGetMessageOptions();

			queue = qmanager.accessQueue(param.getMqQueue(), openOptions);
			queue.get(mqMsg, mqGetMessageOptions);

		     msg = mqMsg.readObject().toString();
			
			System.out.println("receive completed!");
			if (qmanager != null) {
				qmanager.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
}
