/**
 * 
 */
package org.packet.packetsimulationGeneration.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;
import org.openkoala.security.core.PacketNameIsExistedException;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Charles 
 *
 */
@Entity 
@Table(name = "RECORD_FILE")
//@NamedQueries({
//	@NamedQuery(name = "getRecordCountOfRecordFile", query = "select count(o) from Record o where o.RecordFile = :RecordFile") })
public class RecordFile extends KoalaAbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6156533878962292627L;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_VERSION")
	private String fileVersion;

	@Column(name = "ORIG_SENDER")
	private String origSender;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "ORIG_SEND_DATE")
	private Date origSendDate;
	
	@Column(name = "CREATEDBY")
	private String createdBy;
	
	@Column(name = "DATATYPE")
	private Integer dataType;

	@Column(name = "RECORDTYPE")
	private String recordType;
	
	@Column(name = "RECORDNUM")
	private Long recordNum;
	
	@Column(name = "RESERVE")
	private String reserve;
	
	public RecordFile() {
	}
	  
	
	public String getFileName() {
		return fileName;
	}


	public Long getRecordNum() {
		return recordNum;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public void setRecordNum(Long recordNum) {
		this.recordNum = recordNum;
	}


	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getOrigSender() {
		return origSender;
	}

	public void setOrigSender(String origSender) {
		this.origSender = origSender;
	}

	public Date getOrigSendDate() {
		return origSendDate;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setOrigSendDate(Date origSendDate) {
		this.origSendDate = origSendDate;
	}

	public String getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

//	private void isExistFileName(String fileName) {
//		if (getByFileName(fileName) != null) {
//			throw new PacketNameIsExistedException("RecordFile fileName is existed.");
//	    }
//	}
//	
//    public static RecordFile getByFileName(String fileName) {
//        return getRepository()
//                .createCriteriaQuery(RecordFile.class)
//                .eq("fileName", fileName)
//                .singleResult();
//    }
	
//	public static Long getRecordCountOfRecordFile(RecordFile recordFile) {
//		return getRepository().createNamedQuery("getRecordCountOfRecordFile").addParameter("RecordFile", recordFile).singleResult();
//	}
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
}
