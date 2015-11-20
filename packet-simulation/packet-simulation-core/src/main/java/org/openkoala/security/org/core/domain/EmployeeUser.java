package org.openkoala.security.org.core.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openkoala.organisation.core.domain.Company;
import org.openkoala.organisation.core.domain.Department;
import org.openkoala.organisation.core.domain.Employee;
import org.openkoala.security.core.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * 是一个用户，代表一个员工。可以创建非员工的用户，也可以创建非用户的员工。如果有员工刚好是用户，就创建一个EmployeeUser。
 * 通过中间层（org.openkoala.security.org.core.domain），技术（koala-security）和业务核心（koala-organisation-core）互不依赖。中间层同时起到“分”与“合”的作用。
 *
 * @author lucas
 */
@Entity
@DiscriminatorValue("EMPLOYEE_USER")
public class EmployeeUser extends User {

    private static final long serialVersionUID = 969976616338089081L;

    @OneToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;
    
    @Column(name = "ORGANIZATION")
    //@Transient
    private String organization;
    
    @ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="DEPARTMENT_NAME")
	private Department department;
    
    @ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="COMPANY_NAME")
	private Company company;

    public EmployeeUser() {
    }

    public EmployeeUser(String name, String userAccount) {
        super(name, userAccount);
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String[] businessKeys() {
        return super.businessKeys();
    }

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
    public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(getId())
                .append(getUserAccount())
                .append(getEmail())
                .append(getTelePhone())
                .append(getName())
                .append(getEmployee())
                .build();
    }
}
