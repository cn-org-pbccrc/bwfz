package org.openkoala.dependency.object;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description: 抵质押_自然人抵押物信息表对象实体
 * 
 * @author
 */
public class PledgeBo implements Serializable {


	/**
	 * 抵质押合同ID
	 */
	private Long ccId;

	/**
	 * 抵押物唯一识别号
	 */
	private String pleCertNum;

	/**
	 * 抵押物识别号类型ID
	 */
	private Long pleCertTypeId;

	/**
	 * 抵押物种类ID
	 */
	private Long pleTypeId;

	/**
	 * 抵押物评估价值
	 */
	private Long pleValue;

	/**
	 * 评估机构类型ID
	 */
	private Long valOrgTypeId;

	/**
	 * 抵押物评估日期
	 */
	private Date valDate;

	/**
	 * 抵押物位置所在地行政区划ID
	 */
	private Long pleDistrId;

	/**
	 * 抵押物说明
	 */
	private String pleDesc;

	/**
	 * 抵押人身份标识类型ID
	 */
	private Long pledgorTypeId;

	/**
	 * 抵押人名称
	 */
	private String pledgorName;

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
	 * set 抵押物唯一识别号
	 */
	public void setPleCertNum(String pleCertNum) {
		this.pleCertNum = pleCertNum;
	}

	/**
	 * get 抵押物唯一识别号
	 */
	public String getPleCertNum() {

		return pleCertNum;
	}

	/**
	 * set 抵押物识别号类型ID
	 */
	public void setPleCertTypeId(Long pleCertTypeId) {
		this.pleCertTypeId = pleCertTypeId;
	}

	/**
	 * get 抵押物识别号类型ID
	 */
	public Long getPleCertTypeId() {

		return pleCertTypeId;
	}

	/**
	 * set 抵押物种类ID
	 */
	public void setPleTypeId(Long pleTypeId) {
		this.pleTypeId = pleTypeId;
	}

	/**
	 * get 抵押物种类ID
	 */
	public Long getPleTypeId() {

		return pleTypeId;
	}

	/**
	 * set 抵押物评估价值
	 */
	public void setPleValue(Long pleValue) {
		this.pleValue = pleValue;
	}

	/**
	 * get 抵押物评估价值
	 */
	public Long getPleValue() {

		return pleValue;
	}

	/**
	 * set 评估机构类型ID
	 */
	public void setValOrgTypeId(Long valOrgTypeId) {
		this.valOrgTypeId = valOrgTypeId;
	}

	/**
	 * get 评估机构类型ID
	 */
	public Long getValOrgTypeId() {

		return valOrgTypeId;
	}

	/**
	 * set 抵押物评估日期
	 */
	public void setValDate(Date valDate) {
		this.valDate = valDate;
	}

	/**
	 * get 抵押物评估日期
	 */
	public Date getValDate() {

		return valDate;
	}

	/**
	 * set 抵押物位置所在地行政区划ID
	 */
	public void setPleDistrId(Long pleDistrId) {
		this.pleDistrId = pleDistrId;
	}

	/**
	 * get 抵押物位置所在地行政区划ID
	 */
	public Long getPleDistrId() {

		return pleDistrId;
	}

	/**
	 * set 抵押物说明
	 */
	public void setPleDesc(String pleDesc) {
		this.pleDesc = pleDesc;
	}

	/**
	 * get 抵押物说明
	 */
	public String getPleDesc() {

		return pleDesc;
	}

	/**
	 * set 抵押人身份标识类型ID
	 */
	public void setPledgorTypeId(Long pledgorTypeId) {
		this.pledgorTypeId = pledgorTypeId;
	}

	/**
	 * get 抵押人身份标识类型ID
	 */
	public Long getPledgorTypeId() {

		return pledgorTypeId;
	}

	/**
	 * set 抵押人名称
	 */
	public void setPledgorName(String pledgorName) {
		this.pledgorName = pledgorName;
	}

	/**
	 * get 抵押人名称
	 */
	public String getPledgorName() {

		return pledgorName;
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
	 * 抵押人证件号码
	 */
	String pledgorCertCode;

	/**
	 * 抵押人证件类型
	 */
	Long pledgorCertTypeId;

	/**
	 * get抵押人证件号码
	 */
	public String getPledgorCertCode() {
		return pledgorCertCode;
	}

	/**
	 * set抵押人证件号码
	 */
	public void setPledgorCertCode(String pledgorCertCode) {
		this.pledgorCertCode = pledgorCertCode;
	}

	/**
	 * get抵押人证件类型
	 */
	public Long getPledgorCertTypeId() {
		return pledgorCertTypeId;
	}

	/**
	 * set抵押人证件类型
	 */
	public void setPledgorCertTypeId(Long pledgorCertTypeId) {
		this.pledgorCertTypeId = pledgorCertTypeId;
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

		strBuffer.append("pleCertNum").append(eqStr).append(getPleCertNum()).append(dStr);

		strBuffer.append("pleCertTypeId").append(eqStr).append(getPleCertTypeId()).append(dStr);

		strBuffer.append("pleTypeId").append(eqStr).append(getPleTypeId()).append(dStr);

		strBuffer.append("pleValue").append(eqStr).append(getPleValue()).append(dStr);

		strBuffer.append("valOrgTypeId").append(eqStr).append(getValOrgTypeId()).append(dStr);

		if (getValDate() == null) {
			strBuffer.append("valDate").append(eqStr).append(getValDate()).append(dStr);
		} else {
			if ("ValDate".toLowerCase().contains("date")
					&& "ValDate".toLowerCase().indexOf("date") == ("ValDate".length() - 4)) {
				strBuffer.append("valDate").append(eqStr).append(formatDate.format(getValDate())).append(dStr);
			} else {
				strBuffer.append("valDate").append(eqStr).append(format.format(getValDate())).append(dStr);
			}
		}

		strBuffer.append("pleDistrId").append(eqStr).append(getPleDistrId()).append(dStr);

		strBuffer.append("pleDesc").append(eqStr).append(getPleDesc()).append(dStr);

		strBuffer.append("pledgorTypeId").append(eqStr).append(getPledgorTypeId()).append(dStr);

		strBuffer.append("pledgorName").append(eqStr).append(getPledgorName()).append(dStr);

		strBuffer.append("pledgorCertCode").append(eqStr).append(getPledgorCertCode()).append(dStr);

		strBuffer.append("pledgorCertTypeId").append(eqStr).append(getPledgorCertTypeId()).append(dStr);

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