/**
 * 
 */
package org.packet.packetsimulation.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

/**
 * @author hbikai
 *
 */

@Entity 
@Table(name = "MESGTYPE") 
public class MesgType extends KoalaAbstractEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6335897849644090133L;

	@Column(name = "MESG_TYPE") 
	private String mesgType; 
	
	@Column(name = "CODE",length=4)
	private String code;
	
	@Column(name = "SORT") 
	private int sort;
	
	@Column(name = "COUNT_TAG") 
	private String countTag;
	
	@Column(name = "XML",length=15000) 
	private String xml;
	
	@Column(name = "CREATED_BY") 
	private String createdBy;
	
	@Column(name = "TRANSFORM",length=5000) 
	private String transform;

	public String getTransform() {
		return transform;
	}

	public void setTransform(String transform) {
		this.transform = transform;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getMesgType() {
		return mesgType;
	}

	public void setMesgType(String mesgType) {
		this.mesgType = mesgType;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCountTag() {
		return countTag;
	}

	public void setCountTag(String countTag) {
		this.countTag = countTag;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { "mesgType"};
	}
}
