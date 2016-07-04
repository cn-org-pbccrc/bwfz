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
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 批量配置
 * 
 */
@Entity
@Table(name = "BATCH_CONFIG")
public class BatchConfig extends KoalaAbstractEntity {
	
	private static final long serialVersionUID = -3088017969475345884L;

	/**
	 * 模板类型
	 */
	@ManyToOne
	@JoinColumn(name = "CODE", nullable = false)
	private MesgType mesgType;
	
	/**
	 * 创建者
	 */
	@Column(name = "CREATED_BY") 
	private String createdBy;
	
	/**
	 * 创建日期
	 */
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	/**
	 * 批量规则
	 */
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "BATCH_RULE", joinColumns = @JoinColumn(name = "BC_ID"))
	@OrderColumn(name = "ORDER_COLUMN")
	private List<BatchRule> batchRules = new ArrayList<BatchRule>();
	
 
	public Date getCreateDate() {
		return new Date(createDate.getTime());
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public MesgType getMesgType() {
		return mesgType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public List<BatchRule> getBatchRules() {
		return batchRules;
	}

	public void setMesgType(MesgType mesgType) {
		this.mesgType = mesgType;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setBatchRules(List<BatchRule> batchRules) {
		this.batchRules = batchRules;
	}

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

}
