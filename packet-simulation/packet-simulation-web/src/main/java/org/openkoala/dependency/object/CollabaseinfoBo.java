package org.openkoala.dependency.object;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Description: 抵质押_自然人抵质押合同基本信息表对象实体
 * @author 
 */
public class CollabaseinfoBo implements Serializable{
	
	
	/**
	 * 抵质押合同ID
	 */
	private Long ccId;
	
	/**
	 * 合同类型ID
	 */
	private Long ccTypeId;
	
	/**
	 * 最高额担保标志
	 */
	private char maxGuarFlag;
	
	/**
	 * 抵质押合同生效日期
	 */
	private Date ccValDate;
	
	/**
	 * 抵质押合同到期日期
	 */
	private Date ccExpDate;
	
	/**
	 * 抵质押合同状态ID
	 */
	private Long ccStatusId;
	
	/**
	 * 币种ID
	 */
	private Long cyId;
	
	/**
	 * 信息报告日期
	 */
	private Date rptDate;
	
	/**
	 * 档案入库时间
	 */
	private Date dsrInsertTime;
	
	/**
	 * 受理编号
	 */
	private Long accptNum;
	
	/**
	 * 受理内序号
	 */
	private Long accptSerNum;
	
	/**
	 * set 抵质押合同ID
	 */
	public void setCcId(Long ccId){
		this.ccId = ccId;
	}
	/**
	 * get 抵质押合同ID
	 */	
	public Long getCcId(){
	
		return ccId;
	}
	
	/**
	 * set 合同类型ID
	 */
	public void setCcTypeId(Long ccTypeId){
		this.ccTypeId = ccTypeId;
	}
	/**
	 * get 合同类型ID
	 */	
	public Long getCcTypeId(){
	
		return ccTypeId;
	}
	
	/**
	 * set 最高额担保标志
	 */
	public void setMaxGuarFlag(char maxGuarFlag){
		this.maxGuarFlag = maxGuarFlag;
	}
	/**
	 * get 最高额担保标志
	 */	
	public char getMaxGuarFlag(){
	
		return maxGuarFlag;
	}
	
	/**
	 * set 抵质押合同生效日期
	 */
	public void setCcValDate(Date ccValDate){
		this.ccValDate = ccValDate;
	}
	/**
	 * get 抵质押合同生效日期
	 */	
	public Date getCcValDate(){
	
		return ccValDate;
	}
	
	/**
	 * set 抵质押合同到期日期
	 */
	public void setCcExpDate(Date ccExpDate){
		this.ccExpDate = ccExpDate;
	}
	/**
	 * get 抵质押合同到期日期
	 */	
	public Date getCcExpDate(){
	
		return ccExpDate;
	}
	
	/**
	 * set 抵质押合同状态ID
	 */
	public void setCcStatusId(Long ccStatusId){
		this.ccStatusId = ccStatusId;
	}
	/**
	 * get 抵质押合同状态ID
	 */	
	public Long getCcStatusId(){
	
		return ccStatusId;
	}
	
	/**
	 * set 币种ID
	 */
	public void setCyId(Long cyId){
		this.cyId = cyId;
	}
	/**
	 * get 币种ID
	 */	
	public Long getCyId(){
	
		return cyId;
	}
	
	/**
	 * set 信息报告日期
	 */
	public void setRptDate(Date rptDate){
		this.rptDate = rptDate;
	}
	/**
	 * get 信息报告日期
	 */	
	public Date getRptDate(){
	
		return rptDate;
	}
	
	/**
	 * set 档案入库时间
	 */
	public void setDsrInsertTime(Date dsrInsertTime){
		this.dsrInsertTime = dsrInsertTime;
	}
	/**
	 * get 档案入库时间
	 */	
	public Date getDsrInsertTime(){
	
		return dsrInsertTime;
	}
	
	/**
	 * set 受理编号
	 */
	public void setAccptNum(Long accptNum){
		this.accptNum = accptNum;
	}
	/**
	 * get 受理编号
	 */	
	public Long getAccptNum(){
	
		return accptNum;
	}
	
	/**
	 * set 受理内序号
	 */
	public void setAccptSerNum(Long accptSerNum){
		this.accptSerNum = accptSerNum;
	}
	/**
	 * get 受理内序号
	 */	
	public Long getAccptSerNum(){
	
		return accptSerNum;
	}
	
	@Override
	public String toString() {
		StringBuffer strBuffer = new StringBuffer(1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");	
		String leftStr = "(";
		String rightStr = ")";
		String eqStr = "=";
		String dStr = ",";
		strBuffer.append(leftStr);
		
			strBuffer.append("ccId").append(eqStr).append(getCcId()).append(dStr);		
		
			strBuffer.append("ccTypeId").append(eqStr).append(getCcTypeId()).append(dStr);		
		
			strBuffer.append("maxGuarFlag").append(eqStr).append(getMaxGuarFlag()).append(dStr);		
		
			if(getCcValDate() == null){
			strBuffer.append("ccValDate").append(eqStr).append(getCcValDate()).append(dStr);
			}else{
				if("CcValDate".toLowerCase().contains("date") && "CcValDate".toLowerCase().indexOf("date") ==("CcValDate".length() - 4)){
					strBuffer.append("ccValDate").append(eqStr).append(formatDate.format(getCcValDate())).append(dStr);	
				}else{			
					strBuffer.append("ccValDate").append(eqStr).append(format.format(getCcValDate())).append(dStr);					
				}							
			}
		
			if(getCcExpDate() == null){
			strBuffer.append("ccExpDate").append(eqStr).append(getCcExpDate()).append(dStr);
			}else{
				if("CcExpDate".toLowerCase().contains("date") && "CcExpDate".toLowerCase().indexOf("date") ==("CcExpDate".length() - 4)){
					strBuffer.append("ccExpDate").append(eqStr).append(formatDate.format(getCcExpDate())).append(dStr);	
				}else{			
					strBuffer.append("ccExpDate").append(eqStr).append(format.format(getCcExpDate())).append(dStr);					
				}							
			}
		
			strBuffer.append("ccStatusId").append(eqStr).append(getCcStatusId()).append(dStr);		
		
			strBuffer.append("cyId").append(eqStr).append(getCyId()).append(dStr);		
		
			if(getRptDate() == null){
			strBuffer.append("rptDate").append(eqStr).append(getRptDate()).append(dStr);
			}else{
				if("RptDate".toLowerCase().contains("date") && "RptDate".toLowerCase().indexOf("date") ==("RptDate".length() - 4)){
					strBuffer.append("rptDate").append(eqStr).append(formatDate.format(getRptDate())).append(dStr);	
				}else{			
					strBuffer.append("rptDate").append(eqStr).append(format.format(getRptDate())).append(dStr);					
				}							
			}
		
			if(getDsrInsertTime() == null){
			strBuffer.append("dsrInsertTime").append(eqStr).append(getDsrInsertTime()).append(dStr);
			}else{
				if("DsrInsertTime".toLowerCase().contains("date") && "DsrInsertTime".toLowerCase().indexOf("date") ==("DsrInsertTime".length() - 4)){
					strBuffer.append("dsrInsertTime").append(eqStr).append(formatDate.format(getDsrInsertTime())).append(dStr);	
				}else{			
					strBuffer.append("dsrInsertTime").append(eqStr).append(format.format(getDsrInsertTime())).append(dStr);					
				}							
			}
		
			strBuffer.append("accptNum").append(eqStr).append(getAccptNum()).append(dStr);		
		
			strBuffer.append("accptSerNum").append(eqStr).append(getAccptSerNum());		
		strBuffer.append(rightStr);
		return strBuffer.toString();
	}
}