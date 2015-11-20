package org.packet.packetsimulation.web.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.dayatang.utils.Page;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.openkoala.gqc.infra.util.ReadAppointedLine;
import org.openkoala.gqc.infra.util.XmlNode;
import org.openkoala.gqc.infra.util.XmlUtil;
import org.openkoala.koala.commons.InvokeResult;
import org.packet.packetsimulation.application.MesgApplication;
import org.packet.packetsimulation.application.MesgTypeApplication;
import org.packet.packetsimulation.application.PacketApplication;
import org.packet.packetsimulation.core.domain.Mesg;
import org.packet.packetsimulation.core.domain.MesgType;
import org.packet.packetsimulation.core.domain.Packet;
import org.packet.packetsimulation.facade.PacketFacade;
import org.packet.packetsimulation.facade.dto.MesgDTO;
import org.packet.packetsimulation.facade.dto.PacketDTO;
import org.packet.packetsimulation.facade.dto.TaskPacketDTO;
import org.packet.packetsimulation.facade.impl.assembler.MesgAssembler;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;



@Controller
@RequestMapping("/Packet")
public class PacketController {
	private final static String DES = "DES";
	public final static String PWD_KEY = "MZTHPWDJM";
	
	@Inject
	private PacketFacade packetFacade;
	

	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(PacketDTO packetDTO) {
		System.out.println("哈哈哈哈呱呱:"+packetDTO.getOrigSendDate());
		return packetFacade.creatPacket(packetDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(PacketDTO packetDTO) {
		return packetFacade.updatePacket(packetDTO);
	}
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/pageJson/{currentUserId}")
	public Page pageJson(PacketDTO packetDTO, @RequestParam int page, @RequestParam int pagesize,@PathVariable String currentUserId){
		Page<PacketDTO> all = packetFacade.pageQueryPacket(packetDTO, page, pagesize,currentUserId);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(PacketDTO packetDTO, @RequestParam int page, @RequestParam int pagesize){
		Page<PacketDTO> all = packetFacade.pageQueryPacket(packetDTO, page, pagesize);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public InvokeResult remove(@RequestParam String ids, HttpServletRequest request) {
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        String savePath = request.getSession().getServletContext().getRealPath("/")+File.separator+"loadFiles"+File.separator;
        for (int i = 0; i < value.length; i ++) {
        	        					idArrs[i] = Long.parseLong(value[i]);
						        }
        return packetFacade.removePackets(idArrs, savePath);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return packetFacade.getPacket(id);
	}
	
	@ResponseBody
	@RequestMapping("/downloadCSV/{id}")
	public void downloadCSV(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response) {
		
		String downloadCSV = packetFacade.downloadCSV(id);
		String fileVersion = downloadCSV.substring(1,4);
		String origSender = downloadCSV.substring(4,18);
		String origSenderDate = downloadCSV.substring(18,32);
		String recordType = downloadCSV.substring(32,36);
		String dataType = downloadCSV.substring(36,37);
		
		response.setContentType("application/csv;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileVersion + origSender +origSenderDate + recordType + dataType + "0" + "0001" + ".csv");
		response.setCharacterEncoding("UTF-8");
		
		InputStream in = null;;
		OutputStream out;
		try {
			in = new ByteArrayInputStream(downloadCSV.getBytes("UTF-8"));
			out = response.getOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = in.read(buffer)) > 0) {
//				out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
				out.write(buffer, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/getPacketView/{id}")
	public InvokeResult getPacketView(@PathVariable Long id) throws Exception{
		String downloadCSV = packetFacade.downloadCSV(id);
//		String[] part = downloadCSV.split("\r\n");
//		System.out.println("叔叔叔:"+part.length);
//		System.out.println("downloadCSV:"+downloadCSV);
//		String xmlFormat = formatXml(part[1]);
//		System.out.println("xmlFormat:"+xmlFormat);
		return InvokeResult.success(downloadCSV);	
	}
	
	public static String formatXml(String str) throws Exception {
		  org.dom4j.Document document = null;
		  document = DocumentHelper.parseText(str);
		  // 格式化输出格式
		  OutputFormat format = OutputFormat.createPrettyPrint();
		  format.setEncoding("gb2312");
		  StringWriter writer = new StringWriter();
		  // 格式化输出流
		  XMLWriter xmlWriter = new XMLWriter(writer, format);
		  // 将document写入到输出流
		  xmlWriter.write(document);
		  xmlWriter.close();
		  return writer.toString();
	 }
	
	@ResponseBody
	@RequestMapping("/downloadENC/{id}")
	public void downloadENC(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String key = "37d5aed075525d4fa0fe635231cba447";
		String downloadCSV = packetFacade.downloadCSV(id);
		System.out.println(downloadCSV.length());
		String downloadCSV2 = "[A:1234567890123456782015061700000000011:]\r\n{H:0212345678901234567820150617000000100114345212638961u:}<?xml version='1.0' encoding='UTF-8'?><Document><AcctInf><AcctBsSgmt><InfRecTp>信息记录类型</InfRecTp><AcctTp>账户类型</AcctTp><RepTmCd>报告时点说明代码</RepTmCd><CdOfDtaPdgOgztn>数据提供机构代码</CdOfDtaPdgOgztn><CdOfBnesOgztn>业务发生机构代码</CdOfBnesOgztn><AcctId>账户标识号</AcctId><InfmRepDt>信息报告日期</InfmRepDt><BrwNm>3</BrwNm><AcctCertRel><Nm>姓名1</Nm><IdTp>证件类型1</IdTp><IdNb>证件号码1</IdNb></AcctCertRel><AcctCertRel><Nm>姓名2</Nm><IdTp>证件类型2</IdTp><IdNb>证件号码2</IdNb></AcctCertRel></AcctBsSgmt><AcctBsInfSgmt><ClsOfBses>业务种类</ClsOfBses><CdtLmt>账户授信额度</CdtLmt><LnAmt>借款金额</LnAmt><Curcy>币种</Curcy><DtOpe>开户日期</DtOpe><AcctClsDt>账户关闭日期</AcctClsDt><IntlMatrDt>初始到期日期</IntlMatrDt><MatrDt>到期日期</MatrDt><PamtTp>还款方式</PamtTp><PymtFrqe>还款频率</PymtFrqe><﻿TmDutn>还款期数</TmDutn><AdstvDvsnOfBnesApctn>业务申请地行政区划</AdstvDvsnOfBnesApctn><MotgaCltalTp>抵质押方式</MotgaCltalTp><OtrPamtGrntTp>其他还款保证方式</OtrPamtGrntTp><AstTrasLo>资产转让标识</AstTrasLo><SrcOfFund>资金来源</SrcOfFund></AcctBsInfSgmt><MiCtrctInfSgmt><MiCtrctId>主合同标识号</MiCtrctId></MiCtrctInfSgmt><MotgaCltalCtrctInfSgmt><MotgaCltalCtrctId>抵质押合同标识号</MotgaCltalCtrctId></MotgaCltalCtrctInfSgmt><RltRepymtInfSgmt><RltRepymtNum>关联还款责任人数</RltRepymtNum><RltRepymtInf><IdCy>身份类别</IdCy><RltRepymtNm>关联还款责任人名称</RltRepymtNm><RltRepymtIdTp>关联还款责任人证件类型</RltRepymtIdTp><RltRepymtIdNb>关联还款责任人证件号码</RltRepymtIdNb><RltRepymtTp>还款责任人类型</RltRepymtTp><RltRepyAmt>还款责任金额</RltRepyAmt></RltRepymtInf></RltRepymtInfSgmt><RtInfSgmt><FxdRtLg>利率固定标志</FxdRtLg><DtmRtTp>基准利率类型</DtmRtTp><ItersRtFloRto>利率浮动比例</ItersRtFlo﻿Rto><EetnRtAjtFrqe>执行利率调整频率</EetnRtAjtFrqe><EetnRtEfteDt>执行利率生效日期</EetnRtEfteDt><CphsEfteRt>综合有效利率</CphsEfteRt><CphsEfteRtEfteDt>综合有效利率</CphsEfteRtEfteDt></RtInfSgmt><AcctDbtInfSgmt><AcctSts>账户状态</AcctSts><Blc>余额</Blc><FivCtgs>五级分类</FivCtgs><FivCtgsAjmtDt>五级分类调整日期</FivCtgsAjmtDt><LftTrmOfLoa>剩余还款期数</LftTrmOfLoa><PymtSts>当前还款状态</PymtSts><DlqcyTm>当前逾期期数</DlqcyTm><AmtPsDe>当前逾期总额</AmtPsDe><PcplAmtPsDe>当前逾期本金</PcplAmtPsDe><PcplAmt31-60DysPsDe>逾期31-60天未归还贷款本金</PcplAmt31-60DysPsDe><PcplAmt61-90DysPsDe>逾期61-90天未归还贷款本金</PcplAmt61-90DysPsDe><PcplAmt91-180DysPsDe>逾期91-180天未归还贷款本金</PcplAmt91-180DysPsDe><PcplAmt180DysPuPsDe>逾期180天以上未归还贷款本金</PcplAmt180DysPuPsDe><OeDatAmtOe180Dys>透支180天以上未还余额</OeDatAmtOe180Dys></AcctDbtInfSgmt><AcctMthlyBlgInfSgmt><BlgDt>结算/应还款日期</BlgDt><SduldMtyPymt>本月应还款金额</SduldMtyPymt><AtlMtyPymt>本月实际还款金额</AtlMtyPymt><DtOfLsPymt>最近一次实际还款日期</DtOfLsPymt><BilAmt>账单金额</BilAmt></AcctMthlyBlgInfSgmt><AcctPymtInfSgmt><PymtDt>还款日</PymtDt><ShdTheAmt>应还金额</ShdTheAmt><PymtAmt>还款金额</PymtAmt></AcctPymtInfSgmt><AcctSpecTrstDspnSgmt><CagOfTrdNum>2</CagOfTrdNum><CagOfTrdInf><CagOfTrdgTp>变更交易类型</CagOfTrdgTp><TrstnDt>交易日期</TrstnDt><TrasacAmt>交易金额</TrasacAmt><DtldInfo>明细信息</DtldInfo></CagOfTrdInf><CagOfTrdInf><CagOfTrdgTp>变更交易类型</CagOfTrdgTp><TrstnDt>交易日期</TrstnDt><TrasacAmt>交易金额</TrasacAmt><DtldInfo>明细信息</DtldInfo></CagOfTrdInf></AcctSpecTrstDspnSgmt><LSStageSegment><SpclLmt>专项额度</SpclLmt><SpclStgSttDt>专项分期起始日期</SpclStgSttDt><SpclStgDeDt>专项分期到期日期</SpclStgDeDt><StgAmt>分期金额</StgAmt><EhStgAmt>每期分期金额</EhStgAmt><StgBlc>分期余额</StgBlc><RemStgTm>剩余分﻿期期数</RemStgTm></LSStageSegment><CrdtCrdIdSgmt><CreditCardID>卡片标识号</CreditCardID></CrdtCrdIdSgmt><OrigCreditorInfSgmt><OrgnCrdtrNm>原债权人名称</OrgnCrdtrNm></OrigCreditorInfSgmt></AcctInf></Document>\n";
		
		System.out.println(downloadCSV2.equals(downloadCSV));
		System.out.println(downloadCSV2.length());
		String downloadENC = encryption(compress(downloadCSV), key);
		System.out.println("--------------");
		System.out.println(downloadENC.length());
		System.out.println(downloadENC);
		String haha = uncompress(decryption(downloadENC,key));
		System.out.println(haha);
		response.setContentType("application/enc;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new Date().getTime() + ".enc");	
		response.setCharacterEncoding("UTF-8");
		
		InputStream in = null;;
		OutputStream out = null;
		try {
			in = new ByteArrayInputStream(downloadENC.getBytes("UTF-8"));
			out = response.getOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = in.read(buffer)) > 0) {
				out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
				System.out.println("@@@@@@@@@@@ len :"+len);
				System.out.println("@@@@@@@@@@@ buffer :"+buffer.length);
				out.write(buffer, 0, len);
				out.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (in != null) {
				try {
					in.close();
					out.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

	}
	
	@ResponseBody
	@RequestMapping("/load")
	public String load(PacketDTO packetDTO, HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, ParserConfigurationException, SAXException {
		request.setCharacterEncoding("utf-8"); 
		System.out.println("哈哈哈哈收到啦！！！！！！！！！");
		String responseStr="";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String ctxPath=request.getSession().getServletContext().getRealPath("/")+File.separator+"loadFiles";		
		ctxPath += File.separator + packetDTO.getCreatedBy() + File.separator;  	
		System.out.println("ctxpath="+ctxPath);	
		File file = new File(ctxPath);    	
		if (!file.exists()) {    	
			file.mkdirs();    	
		}	
		File[] fileList = file.listFiles();
		boolean flag = false;
		String fileName = null;    	
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    	
			MultipartFile mf = entity.getValue();  	
			fileName = mf.getOriginalFilename();
			System.out.println("filename="+fileName);	
			File uploadFile = new File(ctxPath + fileName);	
			try {
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
				for (int i = 0; i < fileList.length; i++) {
					if(fileName.equals(fileList[i].getName())){
						flag = true;
						break;
					}
				}
				if(flag){
					packetFacade.updateUploadPacket(fileName, ctxPath);
					responseStr="上传成功";
				}else{
					packetFacade.uploadPacket(packetDTO, fileName, ctxPath);
					responseStr="上传成功";
				}				  	   
			} catch (IOException e) {  	   	
				responseStr="上传失败";  	       
				e.printStackTrace();  	       
			}	
		}   	
		return responseStr;    
		
//		String ctxPath = request.getSession().getServletContext().getRealPath("/")+File.separator+"lib"+File.separator+"loading"+File.separator;
//		packetFacade.uploadPacket(packetDTO, path, ctxPath);
//		return InvokeResult.success();
	}
	
	//压缩
	public static String compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out.toString("ISO-8859-1");
	}
	
	// 解压缩
	public static String uncompress(String str) throws IOException {
	    if (str == null || str.length() == 0) {
	    	return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(
		str.getBytes("ISO-8859-1"));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		return out.toString();
    }
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		byte[] strs =Base64.encodeBase64(bt, true);
//		String strs = new BASE64Encoder().encode(bt);
		return new String(strs);
	}
	
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}
	
	public static String decrypt(String data, String key) throws IOException,Exception {
		if (data == null)
			return null;
	    //BASE64Decoder decoder = new BASE64Decoder();
	    byte[] buf = new Base64().decodeBase64(new String(data).getBytes());
	    //byte[] buf = decoder.decodeBuffer(data);
	    byte[] bt = decrypt(buf, key.getBytes());
	    return new String(bt);
	}
	
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
	    SecureRandom sr = new SecureRandom();
	    // 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);
        // 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }
	
	
	
	
	
	
	
	
private static final String DES_ALGORITHM = "DES";
	
	/**
	 * DES加密
	 * @param plainData
	 * @param secretKey
	 * @return
	 * @throws Exception
	 */
	public static String encryption(String plainData, String secretKey) throws Exception{

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(DES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, generateKey(secretKey));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}catch(InvalidKeyException e){
			
		}
		
		try {
			// 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher异常，
			// 不能把加密后的字节数组直接转换成字符串
			byte[] buf = cipher.doFinal(plainData.getBytes());
			
			return Base64Utils.encode(buf);
			
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("IllegalBlockSizeException", e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("BadPaddingException", e);
		}
	    
	}

	/**
	 * DES解密
	 * @param secretData
	 * @param secretKey
	 * @return
	 * @throws Exception
	 */
	public static String decryption(String secretData, String secretKey) throws Exception{
		
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(DES_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, generateKey(secretKey));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("NoSuchAlgorithmException", e);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new Exception("NoSuchPaddingException", e);
		}catch(InvalidKeyException e){
			e.printStackTrace();
			throw new Exception("InvalidKeyException", e);
			
		}
		
		try {
			
			byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));
			
			return new String(buf);
			
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("IllegalBlockSizeException", e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("BadPaddingException", e);
		}
	}
	
	
	/**
	 * 获得秘密密钥
	 * 
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException{
		SecureRandom secureRandom = new SecureRandom(secretKey.getBytes());
				
		// 为我们选择的DES算法生成一个KeyGenerator对象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(DES_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
		}
		kg.init(secureRandom);
		//kg.init(56, secureRandom);
		
		// 生成密钥
		return kg.generateKey();
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
