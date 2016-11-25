package org.openkoala.dependency.object;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.packet.packetsimulation.web.controller.MyAnnotation;

/**
 * Description: 抵质押_抵质押合同基础段表对象实体
 * 
 * @author
 */
public class CollabaseBo implements Serializable {


	/**
	 * 抵质押合同ID
	 */
	@MyAnnotation(name="抵质押合同ID")
	private Long ccId;

	/**
	 * 业务管理机构ID
	 */
	@MyAnnotation(name="业务管理机构ID")
	private Long busiOrgId;

	/**
	 * 抵质押合同标识号
	 */
	@MyAnnotation(name="抵质押合同标识号")
	private String ccc;

	/**
	 * 数据提供机构区段码ID
	 */
	@MyAnnotation(name="数据提供机构区段码ID")
	private Long finanId;

	/**
	 * 报告时点说明代码ID
	 */
	@MyAnnotation(name="报告时点说明代码ID")
	private Long rptDateId;

	/**
	 * 信息报告日期
	 */
	@MyAnnotation(name="信息报告日期")
	private Date rptDate;

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
	 * 来源类型Id
	 */
	@MyAnnotation(name="来源类型Id")
	private Long resTypeId;
	
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
	 * set 业务管理机构ID
	 */
	public void setBusiOrgId(Long busiOrgId) {
		this.busiOrgId = busiOrgId;
	}

	/**
	 * get 业务管理机构ID
	 */
	public Long getBusiOrgId() {

		return busiOrgId;
	}

	/**
	 * set 抵质押合同标识号
	 */
	public void setCcc(String ccc) {
		this.ccc = ccc;
	}

	/**
	 * get 抵质押合同标识号
	 */
	public String getCcc() {

		return ccc;
	}

	/**
	 * set 数据提供机构区段码ID
	 */
	public void setFinanId(Long finanId) {
		this.finanId = finanId;
	}

	/**
	 * get 数据提供机构区段码ID
	 */
	public Long getFinanId() {

		return finanId;
	}

	/**
	 * set 报告时点说明代码ID
	 */
	public void setRptDateId(Long rptDateId) {
		this.rptDateId = rptDateId;
	}

	/**
	 * get 报告时点说明代码ID
	 */
	public Long getRptDateId() {

		return rptDateId;
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
	 * get来源类型Id
	 */
	public Long getResTypeId() {
		return resTypeId;
	}

	/**
	 * set来源类型Id
	 */
	public void setResTypeId(Long resTypeId) {
		this.resTypeId = resTypeId;
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

		strBuffer.append("busiOrgId").append(eqStr).append(getBusiOrgId()).append(dStr);

		strBuffer.append("ccc").append(eqStr).append(getCcc()).append(dStr);

		strBuffer.append("finanId").append(eqStr).append(getFinanId()).append(dStr);

		strBuffer.append("resTypeId").append(eqStr).append(getResTypeId()).append(dStr);

		strBuffer.append("rptDateId").append(eqStr).append(getRptDateId()).append(dStr);

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