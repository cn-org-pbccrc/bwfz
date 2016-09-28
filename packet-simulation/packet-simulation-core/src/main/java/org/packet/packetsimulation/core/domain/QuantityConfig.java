package org.packet.packetsimulation.core.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.dayatang.domain.CriteriaQuery;
import org.dayatang.utils.Page;
import org.openkoala.gqc.core.domain.utils.PagingQuerier;
import org.openkoala.gqc.core.domain.utils.QueryAllQuerier;
import org.openkoala.gqc.core.domain.utils.SqlStatmentMode;
import org.openkoala.koala.commons.domain.KoalaAbstractEntity;
import org.packet.packetsimulationGeneration.core.domain.RecordType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 一代批量配置
 * 
 */
@Entity
@Table(name = "QUANTITY_CONFIG")
public class QuantityConfig extends KoalaAbstractEntity {
	
	private static final long serialVersionUID = -3088017792405345448L;

	/**
	 * 模板类型
	 */
	@ManyToOne
	@JoinColumn(name = "RECORDTYPE_ID", nullable = false)
	private RecordType recordType;
	
	/**
	 * 创建者
	 */
	@Column(name = "CREATED_BY") 
	private String createdBy;
	
	/**
	 * 创建日期
	 */
	
	@Temporal(TemporalType.DATE)
	//@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	/**
	 * 批量规则
	 */
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "QUANTITY_RULE", joinColumns = @JoinColumn(name = "QC_ID"))
	@OrderColumn(name = "ORDER_COLUMN")
	private List<QuantityRule> quantityRules = new ArrayList<QuantityRule>();
	
 
	public Date getCreateDate() {
		return new Date(createDate.getTime());
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public List<QuantityRule> getQuantityRules() {
		return quantityRules;
	}

	public void setQuantityRules(List<QuantityRule> quantityRules) {
		this.quantityRules = quantityRules;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}
}
