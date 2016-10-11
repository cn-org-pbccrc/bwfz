package org.packet.packetsimulation.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

@Entity 
@Table(name = "LOT_SUBMISSION")
public class LotSubmission extends KoalaAbstractEntity{
	
	private static final long serialVersionUID = -8256086377774621262L;
	/**
	 * 批次
	 */
	@ManyToOne
	@JoinColumn(name = "LOT_ID")
	private Lot lot;	
	
	/**
	 * 文件名称
	 */
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 文件名前缀
	 */
	@Column(name = "FRONT_POSITION")
	private String frontPosition;
	
	/**
	 * 文件序列号0~9999
	 */
	@Column(name = "FILE_NUMBER")
	private Integer fileNumber;
	
	
	/**
	 * 加压
	 */
	@Column(name = "COMPRESSION")
	private Integer compression;
	
	/**
	 * 加密
	 */
	@Column(name = "ENCRYPTION")
	private Integer encryption;
	
	/**
	 * 排序字段
	 */
	@Column(name = "SERIAL_NUMBER")
	private Integer serialNumber;
	
	/**
	 * 内外部文件
	 */
	@Column(name = "SUBMISSION_FROM")
	private Integer submissionFrom;
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFrontPosition() {
		return frontPosition;
	}


	public void setFrontPosition(String frontPosition) {
		this.frontPosition = frontPosition;
	}


	public Integer getFileNumber() {
		return fileNumber;
	}


	public void setFileNumber(Integer fileNumber) {
		this.fileNumber = fileNumber;
	}


	public Integer getCompression() {
		return compression;
	}


	public void setCompression(Integer compression) {
		this.compression = compression;
	}


	public Integer getEncryption() {
		return encryption;
	}


	public void setEncryption(Integer encryption) {
		this.encryption = encryption;
	}


	public Integer getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}


	public Lot getLot() {
		return lot;
	}


	public void setLot(Lot lot) {
		this.lot = lot;
	}

	public Integer getSubmissionFrom() {
		return submissionFrom;
	}


	public void setSubmissionFrom(Integer submissionFrom) {
		this.submissionFrom = submissionFrom;
	}


	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
}

