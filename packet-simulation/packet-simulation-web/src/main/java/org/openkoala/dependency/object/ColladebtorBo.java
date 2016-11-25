package org.openkoala.dependency.object;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.packet.packetsimulation.web.controller.MyAnnotation;

/**
 * Description: 抵质押_抵质押合同债务人表对象实体
 * 
 * @author
 */
public class ColladebtorBo implements Serializable {


	/**
	 * 抵质押合同ID
	 */
	@MyAnnotation(name="抵质押合同ID")
	private Long ccId;

	/**
	 * 债务人两标ID
	 */
	@MyAnnotation(name="债务人两标ID")
	private Long debtorCertId;

	/**
	 * 债务人姓名
	 */
	@MyAnnotation(name="债务人姓名")
	private String debtorCertName;

	/**
	 * 债务人身份标识类型ID
	 */
	@MyAnnotation(name="债务人身份标识类型ID")
	private Long debtorTypeId;

	/**
	 * 档案入库时间
	 */
	@MyAnnotation(name="档案入库时间")
	private Date dsrInsertTime;

	/**
	 * 受理编号
	 */
	@MyAnnotation(name="受理编号")
	private Long accptNum;

	/**
	 * 受理内序号
	 */
	@MyAnnotation(name="受理内序号")
	private Long accptSerNum;
	
	/**
	 * 是否主债务人
	 */
	@MyAnnotation(name="是否主债务人")
	private char isMainDebtor;
	
	/**
	 * 信息报告日期
	 */
	@MyAnnotation(name="信息报告日期")
	private Date rptDate;

	
	/**
	 * set 抵质押合同ID
	 */
	public void setCcId(Long ccId) {
		this.ccId = ccId;
	}

	/**
	 * get 抵质押合同ID
	 */
	public Long getCcId() {

		return ccId;
	}

	/**
	 * set 债务人两标ID
	 */
	public void setDebtorCertId(Long debtorCertId) {
		this.debtorCertId = debtorCertId;
	}

	/**
	 * get 债务人两标ID
	 */
	public Long getDebtorCertId() {

		return debtorCertId;
	}

	/**
	 * set 债务人姓名
	 */
	public void setDebtorCertName(String debtorCertName) {
		this.debtorCertName = debtorCertName;
	}

	/**
	 * get 债务人姓名
	 */
	public String getDebtorCertName() {

		return debtorCertName;
	}

	/**
	 * set 债务人身份标识类型ID
	 */
	public void setDebtorTypeId(Long debtorTypeId) {
		this.debtorTypeId = debtorTypeId;
	}

	/**
	 * get 债务人身份标识类型ID
	 */
	public Long getDebtorTypeId() {

		return debtorTypeId;
	}

	/**
	 * set 档案入库时间
	 */
	public void setDsrInsertTime(Date dsrInsertTime) {
		this.dsrInsertTime = dsrInsertTime;
	}

	/**
	 * get 档案入库时间
	 */
	public Date getDsrInsertTime() {

		return dsrInsertTime;
	}

	/**
	 * set 受理编号
	 */
	public void setAccptNum(Long accptNum) {
		this.accptNum = accptNum;
	}

	/**
	 * get 受理编号
	 */
	public Long getAccptNum() {

		return accptNum;
	}

	/**
	 * set 受理内序号
	 */
	public void setAccptSerNum(Long accptSerNum) {
		this.accptSerNum = accptSerNum;
	}

	/**
	 * get 受理内序号
	 */
	public Long getAccptSerNum() {

		return accptSerNum;
	}

	/**
	 * set 是否主债务人
	 */
	public void setIsMainDebtor(char isMainDebtor) {
		this.isMainDebtor = isMainDebtor;
	}

	/**
	 * get 是否主债务人
	 */
	public char getIsMainDebtor() {

		return isMainDebtor;
	}

	/**
	 * get信息报告日期
	 */
	public Date getRptDate() {
		return rptDate;
	}

	/**
	 * set信息报告日期
	 */
	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
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

		strBuffer.append("debtorCertId").append(eqStr).append(getDebtorCertId()).append(dStr);

		strBuffer.append("debtorCertName").append(eqStr).append(getDebtorCertName()).append(dStr);

		strBuffer.append("debtorTypeId").append(eqStr).append(getDebtorTypeId()).append(dStr);

		strBuffer.append("isMaindebtor").append(eqStr).append(getIsMainDebtor()).append(dStr);

		if (getDsrInsertTime() == null) {
			strBuffer.append("dsrInsertTime").append(eqStr).append(getDsrInsertTime()).append(dStr);
		} else {
			if ("DsrInsertTime".toLowerCase().contains("date")
					&& "DsrInsertTime".toLowerCase().indexOf("date") == ("DsrInsertTime".length() - 4)) {
				strBuffer.append("dsrInsertTime").append(eqStr).append(formatDate.format(getDsrInsertTime()))
						.append(dStr);
			} else {
				strBuffer.append("dsrInsertTime").append(eqStr).append(format.format(getDsrInsertTime())).append(dStr);
			}
		}

		strBuffer.append("rptDate").append(eqStr).append(getRptDate()).append(dStr);

		strBuffer.append("accptNum").append(eqStr).append(getAccptNum()).append(dStr);

		strBuffer.append("accptSerNum").append(eqStr).append(getAccptSerNum());
		strBuffer.append(rightStr);
		return strBuffer.toString();
	}
}