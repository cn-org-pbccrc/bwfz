package org.openkoala.dependency.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.packet.packetsimulation.web.controller.MyAnnotation;

/**
 * Description: 抵质押合同业务对象实体
 * @author 
 */
public class CollaBo implements Serializable {
	
	@MyAnnotation(name="姓名")
	private String name;
	
	@MyAnnotation(name="证件类型")
	private Long certId;
	
	@MyAnnotation(name="个人所有两标")
	private List<Long> certIdList;
	
//	@MyAnnotation(name="两标映射")
//	private Map<Long, CertidBo> certidBoMap;
	
	/**
	 * 抵质押合同基础段信息
	 */
	@MyAnnotation(name="抵质押合同基础段信息")
	private CollabaseBo collaBaseBo;
	
//	/**
//	 * 最新一条抵质押合同基本信息
//	 */
//	@MyAnnotation(name="最新一条抵质押合同基本信息")
//	private CollabaseinfoBo collaBaseinfoBo;
	
	/**
	 * 抵质押合同被债务人列表
	 */
	@MyAnnotation(name="抵质押合同被债务人列表")
	private List<ColladebtorBo> colladebtorBoList;
	
//	/**
//	 * 抵质押合同质押物列表
//	 */
//	@MyAnnotation(name="抵质押合同质押物列表")
//	private List<ImpawnBo> impawnBoList;
//	
//	/**
//	 * 抵质押合同抵押物列表
//	 */
//	@MyAnnotation(name="抵质押合同抵押物列表")
//	private List<PledgeBo> pledgeBoList;

	
	/**
	 * 抵质押合同基础段信息
	 * @return
	 */
	public CollabaseBo getCollaBaseBo() {
		return collaBaseBo;
	}

//	/**
//	 * 获取抵质押合同基本信息
//	 * @return
//	 */
//	public CollabaseinfoBo getCollaBaseinfoBo() {
//		return collaBaseinfoBo;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCertId() {
		return certId;
	}

	public void setCertId(Long certId) {
		this.certId = certId;
	}

	public List<Long> getCertIdList() {
		return certIdList;
	}

	public void setCertIdList(List<Long> certIdList) {
		this.certIdList = certIdList;
	}

//	public Map<Long, CertidBo> getCertidBoMap() {
//		return certidBoMap;
//	}
//
//	public void setCertidBoMap(Map<Long, CertidBo> certidBoMap) {
//		this.certidBoMap = certidBoMap;
//	}

	/**
	 * 获取抵质押合同债务人列表
	 * @return
	 */
	public List<ColladebtorBo> getColladebtorBoList() {
		return colladebtorBoList;
	}

//	/**
//	 * 获取抵质押合同质押物列表
//	 * @return
//	 */
//	public List<ImpawnBo> getImpawnBoList() {
//		return impawnBoList;
//	}
//
//	/**
//	 * 获取抵质押合同抵押物列表
//	 * @return
//	 */
//	public List<PledgeBo> getPledgeBoList() {
//		return pledgeBoList;
//	}

	/**
	 * 设置抵质押合同基础段信息
	 * @param collaBaseBo
	 */
	public void setCollaBaseBo(CollabaseBo collaBaseBo) {
		this.collaBaseBo = collaBaseBo;
	}

//	/**
//	 * 设置抵质押合同基本信息
//	 * @param collaBaseinfoBo
//	 */
//	public void setCollaBaseinfoBo(CollabaseinfoBo collaBaseinfoBo) {
//		this.collaBaseinfoBo = collaBaseinfoBo;
//	}

	/**
	 * 设置抵质押合同债务人列表
	 * @param colladebtorBoList
	 */
	public void setColladebtorBoList(List<ColladebtorBo> colladebtorBoList) {
		this.colladebtorBoList = colladebtorBoList;
	}

//	/**
//	 * 设置抵质押合同质押物列表
//	 * @param impawnBoList
//	 */
//	public void setImpawnBoList(List<ImpawnBo> impawnBoList) {
//		this.impawnBoList = impawnBoList;
//	}
//
//	/**
//	 * 设置抵质押合同抵押物列表
//	 * @param pledgeBoList
//	 */
//	public void setPledgeBoList(List<PledgeBo> pledgeBoList) {
//		this.pledgeBoList = pledgeBoList;
//	}
//	
//	
//
//	public CollaBo() {
//		colladebtorBoList = new ArrayList<ColladebtorBo>();
//		impawnBoList = new ArrayList<ImpawnBo>();
//		pledgeBoList = new ArrayList<PledgeBo>();
//	}
//
//	@Override
//	public String toString() {
//		return "(collaBaseBo=" + collaBaseBo + ", collaBaseinfoBo=" + collaBaseinfoBo
//				+ ", colladebtorBoList=" + colladebtorBoList + ", impawnBoList=" + impawnBoList + ", pledgeBoList="
//				+ pledgeBoList + ")";
//	}	
}
