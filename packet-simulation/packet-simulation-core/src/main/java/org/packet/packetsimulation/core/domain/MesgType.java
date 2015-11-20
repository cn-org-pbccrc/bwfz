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
	
	@Column(name = "FILEPATH") 
	private String filePath; 
	
	@Column(name = "CODE",length=4)
	private String code;
	
	@Column(name = "SORT") 
	private int sort;
	
	@Column(name = "COUNT_TAG") 
	private String countTag;
	
	@Override
	public String[] businessKeys() {
		return new String[] { "mesgType"};
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

}
