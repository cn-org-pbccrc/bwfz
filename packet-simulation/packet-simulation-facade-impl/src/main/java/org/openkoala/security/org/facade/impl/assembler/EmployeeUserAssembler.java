package org.openkoala.security.org.facade.impl.assembler;

import org.openkoala.organisation.core.domain.Company;
import org.openkoala.organisation.core.domain.Department;
import org.openkoala.security.org.facade.command.CreateEmpolyeeUserCommand;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.openkoala.security.org.facade.dto.EmployeeUserDTO;

public class EmployeeUserAssembler {

    public static EmployeeUser toEmployeeUser(CreateEmpolyeeUserCommand command) {
        EmployeeUser employeeUser = new EmployeeUser(command.getName(), command.getUserAccount());
        employeeUser.setCreateOwner(command.getCreateOwner());
        employeeUser.setDescription(command.getDescription());
        employeeUser.setOrganization(command.getOrganization());
//        EmployeeUser employeeUser =new EmployeeUser();
//	 	employeeUser.setId(Long.valueOf(projectDTO.getProjectManager()));
        if(command.getOrganization().equals("1")){
        	Company company = new Company();
        	company.setId(Long.valueOf(command.getOrganization()));
        	employeeUser.setCompany(company);
        }else{
        	Department department = new Department();
        	department.setId(Long.valueOf(command.getOrganization()));
            System.out.println("id:"+Long.valueOf(command.getOrganization()));
            employeeUser.setDepartment(department);
        }
        return employeeUser;
    }

    public static EmployeeUserDTO toEmployeeUserDTO(EmployeeUser employeeUser) {
        EmployeeUserDTO employeeUserDTO = new EmployeeUserDTO(employeeUser.getId(), employeeUser.getVersion(), employeeUser.getName(),//
                employeeUser.getUserAccount(), employeeUser.getCreateDate(), employeeUser.getDescription(),//
                employeeUser.getCreateOwner(), employeeUser.getLastModifyTime(), employeeUser.isDisabled());
        employeeUserDTO.setOrganization(employeeUser.getOrganization());
        //System.out.println("hahahahahahahaha");
        //employeeUserDTO.setDepartmentName("haha");
        //System.out.println("getName:"+ employeeUser.getDepartment().getName());
        //System.out.println("getFullName:"+ employeeUser.getDepartment().getFullName());
        return employeeUserDTO;
    }
}
