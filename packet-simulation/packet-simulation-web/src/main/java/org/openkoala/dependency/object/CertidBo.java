package org.openkoala.dependency.object;

import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import org.packet.packetsimulation.web.controller.MyAnnotation;

/**
 * Description: 内码_证件三标内码表对象实体
 * 
 * @author
 */
public class CertidBo implements Serializable {

	/**
	 * 两标ID
	 */
	@MyAnnotation(name="两标ID")
	private Long certId;

	/**
	 * 证件号码
	 */
	@MyAnnotation(name="证件号码")
	private String certNum;
	
	/**
	 * 姓名
	 */
	@MyAnnotation(name="姓名")
	private  String certName;

	/**
	 * 证件类型ID
	 */
	@MyAnnotation(name="证件类型ID")
	private Long certTypeId;

	/**
	 * get 两标ID
	 */
	public Long getCertId() {
		return certId;
	}

	/**
	 * set 两标ID
	 */
	public void setCertId(Long certId) {
		this.certId = certId;
	}

	/**
	 * get 证件号码
	 */
	public String getCertNum() {
		return certNum;
	}

	/**
	 * set 证件号码
	 */
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	/**
	 * get 证件类型ID
	 */
	public Long getCertTypeId() {
		return certTypeId;
	}

	/**
	 * set 证件类型ID
	 */
	public void setCertTypeId(Long certTypeId) {
		this.certTypeId = certTypeId;
	}
	/**
	 * get 姓名
	 */
	public String getCertName() {
		return certName;
	}
	/**
	 * set 姓名
	 */
	public void setCertName(String certName) {
		this.certName = certName;
	}

	@Override
	public String toString() {
		Field[] fields = this.getClass().getDeclaredFields();
		int len = fields.length;
		AccessibleObject.setAccessible(fields, true);
		StringBuilder strBuil = new StringBuilder(len);
		strBuil.append("(");
		for (int i = 0; i < len; i++) {
			Field fd = fields[i];
			strBuil.append(fd.getName());
			Object val = "";
			try {
				val = fd.get(this);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			strBuil.append("=" + val);
			if (i != len - 1)
				strBuil.append(",");
		}

		strBuil.append(")");
		return strBuil.toString();
	}
}