package org.packet.packetsimulation.facade.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.core.domain.Authorization;
import org.openkoala.security.facade.dto.RoleDTO;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.packet.packetsimulation.application.MissionApplication;
import org.packet.packetsimulation.application.ProjectApplication;
import org.packet.packetsimulation.core.domain.Mission;
import org.packet.packetsimulation.core.domain.Project;
import org.packet.packetsimulation.facade.MissionFacade;
import org.packet.packetsimulation.facade.ProjectFacade;
import org.packet.packetsimulation.facade.dto.ProjectDTO;
import org.packet.packetsimulation.facade.impl.assembler.ProjectAssembler;

@Named
public class ProjectFacadeImpl implements ProjectFacade {

	@Inject
	private ProjectApplication  application;
	
	@Inject
	private MissionApplication  missionApplication;
	
	@Inject
	private MissionFacade  missionFacade;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getProject(Long id) {
		return InvokeResult.success(ProjectAssembler.toDTO(application.getProject(id)));
	}
	
	public InvokeResult creatProject(ProjectDTO projectDTO) {
		application.creatProject(ProjectAssembler.toEntity(projectDTO));
		/*//创建测试用对象
        City beijing = new City();
        beijing.setName("北京");
        beijing.setCode("010");
        City shanghai = new City();
        shanghai.setName("上海");
        shanghai.setCode("020");
        City tianjin = new City();
        tianjin.setName("天津");
        tianjin.setCode("021");
        List<City> cityList = new ArrayList<City>();
        cityList.add(beijing);
        cityList.add(shanghai);
        cityList.add(tianjin);
        TestObject obj = new TestObject();
        obj.setName("贾昌鑫");
        obj.setPassword("123456");
        obj.setDate(new Date());
        obj.setCityList(cityList);
       
        try{
            //将对象存入blob字段
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
            con.setAutoCommit(false);
            //将一个对象序列化保存到数据库
            PreparedStatement pstmt = con.prepareStatement("insert into obj (object,mimi) values (?,?)");
            pstmt.setObject(1, obj);
            pstmt.setString(2,"米米");
            pstmt.executeUpdate();
            con.commit();
            //从数据库中提取记录
            Statement state = con.createStatement();
            ResultSet rs = state.executeQuery("select object from obj");
            rs.last();
            int rowCount=rs.getRow();
            rs.beforeFirst();
            System.out.println("成败在此一举:"+rowCount);
            while (rs.next()) {
                //以下是读取的方法一定要注意了！
                Blob inblob = (Blob) rs.getBlob("object");
                InputStream is = inblob.getBinaryStream();
                BufferedInputStream input = new BufferedInputStream(is);
               
                byte[] buff = new byte[(int) inblob.length()];//放到一个buff 字节数组
                while(-1 != (input.read(buff, 0, buff.length))){
                    ObjectInputStream in =new ObjectInputStream(new ByteArrayInputStream(buff));
                    TestObject w3 = (TestObject)in.readObject();//从IO流中读取出来.可以得到一个对象了
                    System.out.println(w3.getName());
                    System.out.println(w3.getPassword());
                    System.out.println(w3.getDate());
                }             
            }
           
           } catch (Exception ex) {
            ex.printStackTrace();
           }*/
		return InvokeResult.success();
	}
	
	public InvokeResult updateProject(ProjectDTO projectDTO) {
		application.updateProject(ProjectAssembler.toEntity(projectDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeProject(Long id) {
		application.removeProject(application.getProject(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeProjects(Long[] ids) {
		Set<Project> projects= new HashSet<Project>();
		for (Long id : ids) {
			projects.add(application.getProject(id));
			List<Mission> missions = findMissionsByProjectId(id);
			if(missions != null){
				Long[] missionIds = new Long[missions.size()];
				for(int i = 0; i < missions.size(); i++){
					missionIds[i] = missions.get(i).getId();
				}
				missionFacade.removeMissions(missionIds);
			}			
		}
		application.removeProjects(projects);
		return InvokeResult.success();
	}
	
	public List<ProjectDTO> findAllProject() {
		return ProjectAssembler.toDTOs(application.findAllProject());
	}
	
	public Page<ProjectDTO> pageQueryProject(ProjectDTO queryVo, int currentPage, int pageSize, String currentUserAccount) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _project from Project _project   where 1=1 ");
		if(currentUserAccount != null){
			EmployeeUser employeeUser = findEmployeeUserByCurrentUserAccount(currentUserAccount);
			Long employeeUserId = employeeUser.getId();
			List<Authorization> authorizations = findAuthorizationsByEmployeeUserId(employeeUserId);
			int flag = 0;
			for(int i = 0; i < authorizations.size(); i++){
				if(authorizations.get(i).getAuthority().getId() == 1){
					flag = 1;
					break;
				}
			}
			if(flag == 0){
				jpql.append(" and _project.employeeUser.id = ?");
		   		conditionVals.add(employeeUserId);
			}
		}
		
	   	if (queryVo.getProjectName() != null && !"".equals(queryVo.getProjectName())) {
	   		jpql.append(" and _project.projectName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getProjectName()));
	   	}		
	   	if (queryVo.getProjectCode() != null && !"".equals(queryVo.getProjectCode())) {
	   		jpql.append(" and _project.projectCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getProjectCode()));
	   	}		
	   	if (queryVo.getProjectManager() != null && !"".equals(queryVo.getProjectManager())) {
	   		jpql.append(" and _project.projectManager like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getProjectManager()));
	   	}		
	   	if (queryVo.getProjectstartDate() != null) {
	   		jpql.append(" and _project.projectstartDate between ? and ? ");
	   		conditionVals.add(queryVo.getProjectstartDate());
	   		conditionVals.add(queryVo.getProjectstartDateEnd());
	   	}	
	   	if (queryVo.getProjectendDate() != null) {
	   		jpql.append(" and _project.projectendDate between ? and ? ");
	   		conditionVals.add(queryVo.getProjectendDate());
	   		conditionVals.add(queryVo.getProjectendDateEnd());
	   	}	
	   	if (queryVo.getProjectRemark() != null && !"".equals(queryVo.getProjectRemark())) {
	   		jpql.append(" and _project.projectRemark like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getProjectRemark()));
	   	}		
        Page<Project> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<ProjectDTO>(pages.getStart(), pages.getResultCount(),pageSize, ProjectAssembler.toDTOs(pages.getData()));
	}
	
	public Page<ProjectDTO> pagingQueryProjectsByCurrentUser(int page, int pagesize, String currentUserAccount){
		StringBuilder jpql = new StringBuilder("select _project from Project _project where _project.id in (select _mission.project.id from Mission _mission where _mission.employeeUser.id = (select _employeeUser.id from EmployeeUser _employeeUser  where _employeeUser.userAccount = ?) and _mission.disabled = ?)");
		List<Object> conditionVals = new ArrayList<Object>();
		conditionVals.add(currentUserAccount);
		conditionVals.add(false);
		Page<Project> pages = getQueryChannelService()
				   .createJpqlQuery(jpql.toString())
				   .setParameters(conditionVals)
				   .setPage(page, pagesize)
				   .pagedList();
		return new Page<ProjectDTO>(pages.getStart(), pages.getResultCount(),pagesize, ProjectAssembler.toDTOs(pages.getData()));
	}
	
	@SuppressWarnings("unchecked")
	private EmployeeUser findEmployeeUserByCurrentUserAccount(String currentUserAccount){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _employeeUser from EmployeeUser _employeeUser  where 1=1 ");	   	
	   	if (currentUserAccount != null ) {
	   		jpql.append(" and _employeeUser.userAccount = ? ");
	   		conditionVals.add(currentUserAccount);
	   	}
	   	EmployeeUser employeeUser = (EmployeeUser) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
	   	return employeeUser;
	}
	
	@SuppressWarnings("unchecked")
	private List<Authorization> findAuthorizationsByEmployeeUserId(Long employeeUserId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _authorization from Authorization _authorization  where 1=1 ");   	
	   	if (employeeUserId != null ) {
	   		jpql.append(" and _authorization.actor.id = ? ");
	   		conditionVals.add(employeeUserId);
	   	}
	   	List<Authorization> authorizations = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return authorizations;
	}
	
	@SuppressWarnings("unchecked")
	private List<Mission> findMissionsByCurrentUserAccount(String currentUserAccount){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mission from Mission _mission  where 1=1 ");	   	
	   	if (currentUserAccount != null ) {
	   		jpql.append(" and _mission.employeeUser.userAccount = ? ");
	   		conditionVals.add(currentUserAccount);
	   	}
	   	List<Mission> missions = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return missions;
	}
	
	@SuppressWarnings("unchecked")
	private List<Mission> findMissionsByProjectId(Long projectId){
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mission from Mission _mission  where 1=1 ");	   	
	   	if (projectId != null ) {
	   		jpql.append(" and _mission.project.id = ? ");
	   		conditionVals.add(projectId);
	   	}
	   	List<Mission> missions = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).list();
	   	return missions;
	}
}
