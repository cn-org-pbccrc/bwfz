package org.packet.packetsimulation.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

@Entity 
@Table(name = "THREE_STANDARD")
public class ThreeStandard extends KoalaAbstractEntity {
	
	private static final long serialVersionUID = 6516533873270292621L;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "CREDENTIAL_TYPE")
	private String credentialType;
	
	@Column(name = "CREDENTIAL_NUMBER")
	private String credentialNumber;
	
	@Column(name = "ORGANIZATION_CODE")
	private String organizationCode;
	
//	@GeneratedValue(strategy = GenerationType.AUTO) 
//  @Column(name = "CUSTOMER_CODE",nullable=false,insertable=false,updatable=false,columnDefinition="numeric(6,0) IDENTITY")
	@Column(name = "CUSTOMER_CODE")
	private Integer customerCode;
	
	@Column(name = "ACCT_CODE")
	private String acctCode;
	
	@Column(name = "CON_CODE")
	private String conCode;
	
	@Column(name = "CCC")
	private String ccc;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public String getConCode() {
		return conCode;
	}

	public void setConCode(String conCode) {
		this.conCode = conCode;
	}

	public String getCcc() {
		return ccc;
	}

	public void setCcc(String ccc) {
		this.ccc = ccc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCredentialNumber() {
		return credentialNumber;
	}

	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}

	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public Integer getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(Integer customerCode) {
		this.customerCode = customerCode;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
}
