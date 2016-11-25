package org.openkoala.dependency.object;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description: 抵质押_自然人质押物信息表对象实体
 * 
 * @author
 */
public class ImpawnBo implements Serializable {


	/**
	 * 抵质押合同ID
	 */
	private Long ccId;

	/**
	 * 质押物种类ID
	 */
	private Long impTypeId;

	/**
	 * 质物价值
	 */
	private Long impValue;

	/**
	 * 出质人身份标识类型ID
	 */
	private Long ippcTypeId;

	/**
	 * 出质人名称
	 */
	private String ippcName;

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
	 * set 质押物种类ID
	 */
	public void setImpTypeId(Long impTypeId) {
		this.impTypeId = impTypeId;
	}

	/**
	 * get 质押物种类ID
	 */
	public Long getImpTypeId() {

		return impTypeId;
	}

	/**
	 * set 质物价值
	 */
	public void setImpValue(Long impValue) {
		this.impValue = impValue;
	}

	/**
	 * get 质物价值
	 */
	public Long getImpValue() {

		return impValue;
	}

	/**
	 * set 出质人身份标识类型ID
	 */
	public void setIppcTypeId(Long ippcTypeId) {
		this.ippcTypeId = ippcTypeId;
	}

	/**
	 * get 出质人身份标识类型ID
	 */
	public Long getIppcTypeId() {

		return ippcTypeId;
	}

	/**
	 * set 出质人名称
	 */
	public void setIppcName(String ippcName) {
		this.ippcName = ippcName;
	}

	/**
	 * get 出质人名称
	 */
	public String getIppcName() {

		return ippcName;
	}

	/**
	 * set 信息报告日期
	 */
	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
	}

	/**
	 * get 信息报告日期
	 */
	public Date getRptDate() {

		return rptDate;
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
	 * 出质人证件号码
	 */
	String ippcCertCode;

	/**
	 * 出质人证件类型
	 */
	Long ippcCertTypeId;

	/**
	 * get出质人证件号码
	 */
	public String getIppcCertCode() {
		return ippcCertCode;
	}

	/**
	 * set出质人证件号码
	 */
	public void setIppcCertCode(String ippcCertCode) {
		this.ippcCertCode = ippcCertCode;
	}

	/**
	 * get出质人证件类型
	 */
	public Long getIppcCertTypeId() {
		return ippcCertTypeId;
	}

	/**
	 * set出质人证件类型
	 */
	public void setIppcCertTypeId(Long ippcCertTypeId) {
		this.ippcCertTypeId = ippcCertTypeId;
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

		strBuffer.append("impTypeId").append(eqStr).append(getImpTypeId()).append(dStr);

		strBuffer.append("impValue").append(eqStr).append(getImpValue()).append(dStr);

		strBuffer.append("ippcTypeId").append(eqStr).append(getIppcTypeId()).append(dStr);

		strBuffer.append("ippcName").append(eqStr).append(getIppcName()).append(dStr);

		strBuffer.append("ippcCertCode").append(eqStr).append(getIppcCertCode()).append(dStr);

		strBuffer.append("ippcCertTypeId").append(eqStr).append(getIppcCertTypeId()).append(dStr);

		if (getRptDate() == null) {
			strBuffer.append("rptDate").append(eqStr).append(getRptDate()).append(dStr);
		} else {
			if ("RptDate".toLowerCase().contains("date")
					&& "RptDate".toLowerCase().indexOf("date") == ("RptDate".length() - 4)) {
				strBuffer.append("rptDate").append(eqStr).append(formatDate.format(getRptDate())).append(dStr);
			} else {
				strBuffer.append("rptDate").append(eqStr).append(format.format(getRptDate())).append(dStr);
			}
		}

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

		strBuffer.append("accptNum").append(eqStr).append(getAccptNum()).append(dStr);

		strBuffer.append("accptSerNum").append(eqStr).append(getAccptSerNum());
		strBuffer.append(rightStr);
		return strBuffer.toString();
	}
}