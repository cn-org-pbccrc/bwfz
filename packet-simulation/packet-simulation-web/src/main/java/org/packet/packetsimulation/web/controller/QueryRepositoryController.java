package org.packet.packetsimulation.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.openkoala.koala.commons.InvokeResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/QueryRepository")
public class QueryRepositoryController {
	
	@ResponseBody
	@RequestMapping("/query")
	public InvokeResult query() {
        User guestUser = new User();  
        guestUser.setName("guest");  
        guestUser.setAge(28);  
        // 构建用户root  
        User rootUser = new User();  
        rootUser.setName("root");  
        guestUser.setAge(35);  
        // 构建用户组对象  
        UserGroup group = new UserGroup();  
        group.setName("admin");  
        group.getUsers().add(guestUser);  
        group.getUsers().add(rootUser);  
        // 用户组对象转JSON串  
        String jsonString = JSON.toJSONString(group);  
        System.out.println("jsonString:" + jsonString); 
		return InvokeResult.success(jsonString);
	}
	
	class User {  
	    private String name;  
	    private int age;  
	  
	    public String getName() {  
	        return name;  
	    }  
	  
	    public void setName(String name) {  
	        this.name = name;  
	    }  
	  
	    public int getAge() {  
	        return age;  
	    }  
	  
	    public void setAge(int age) {  
	        this.age = age;  
	    }  
	  
	    @Override  
	    public String toString() {  
	        return "User [name=" + name + ", age=" + age + "]";  
	    }  
	};  
	  
	class UserGroup {  
	    private String name;  
	    private List<User> users = new ArrayList<User>();  
	  
	    public String getName() {  
	        return name;  
	    }  
	  
	    public void setName(String name) {  
	        this.name = name;  
	    }  
	  
	    public List<User> getUsers() {  
	        return users;  
	    }  
	  
	    public void setUsers(List<User> users) {  
	        this.users = users;  
	    }  
	  
	    @Override  
	    public String toString() {  
	        return "UserGroup [name=" + name + ", users=" + users + "]";  
	    }  
	}  
}
