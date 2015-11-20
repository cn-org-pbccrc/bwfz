package org.openkoala.security.org.facade.command;

import org.openkoala.security.facade.command.CreateUserCommand;

public class CreateEmpolyeeUserCommand extends CreateUserCommand {

	private Long employeeId;
	
	private String organization;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
}
