package org.openkoala.security.shiro.realm;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.openkoala.security.core.domain.EncryptService;
import org.openkoala.security.core.domain.User;
import org.openkoala.security.facade.dto.UserDTO;
import org.openkoala.security.facade.impl.assembler.UserAssembler;

public class CustomJNDIRealm extends JndiLdapRealm {
	@Inject
	protected EncryptService passwordEncryptService;

	LdapContext ctx = null;
	String rootDn;
	
	@Override
	public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo result = new SimpleAuthorizationInfo();
		result.setRoles(shiroUser.getRoles());
		result.setStringPermissions(shiroUser.getPermissions());
		return result;
	}
	
	@Override
	 protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token,
            LdapContextFactory ldapContextFactory)
           		 throws NamingException {
            ctx = ldapContextFactory.getSystemLdapContext(); 
			UsernamePasswordToken toToken = null;
			if (token instanceof UsernamePasswordToken) {
				toToken = (UsernamePasswordToken) token;
				System.out.println("the token is usernametoken");
			}
			System.out.println("the token is");

			System.out.println("the token is"+ (String)token.getPrincipal() + " the token password is "+new String((char[])token.getCredentials()));

			System.out.println("the toToken is " + toToken.getCredentials());
			//System.out.println("the token sss" + token.getCredentials().);
			//toToken.setPassword("{MD5}4QrcOUm6Wau+VuBX8g+IPg==".toCharArray());

			
			String userAccount = (String)token.getPrincipal();
			//通过useraccount查询ldap中用户信息，主要是account和password
			 try {  
				UserDTO user = seachLdapUserByAccount(userAccount);
				
				//System.out.println("the account is "+user.getUserAccount()+user.getUserPassword() +"user name is "+user.getName());
				//System.out.println("the password is "+user.getUserPassword());
				//user.setUserPassword("111111");
				//System.out.println("the stored username is "+user.getName()+" the stored password is "+user.getUserPassword());
				//System.out.println("the token is " + token.getPrincipal() +"credential is "+ token.getCredentials());

				System.out.println("the token is " + toToken.getCredentials() + "the stored password is "+user.getUserPassword() );
				// 此时从数据库中根据useraccount获取用户的role！！！
			    ShiroUser shiroUser = new ShiroUser(user.getUserAccount(),user.getName());
			    //user.setRoleName("超级管理员");
			 	SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(shiroUser, user.getUserPassword(),getName());
			 	
			 	/*暂时不加盐
			 	if (!passwordEncryptService.saltDisabled()) {
			 		info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt() + shiroUser.getUserAccount()));
				}*/
		       return info;
			 }finally {  
		            LdapUtils.closeContext(ctx);  
		        }  
			
	    }
	 
	private UserDTO seachLdapUserByAccount(String userAccount) throws NamingException{
		UserDTO userDTO = ldapGetUserByUserAccount(userAccount);
		return userDTO;
	}
	
	// 根据useraccount从ldap中查询用户信息，主要是username和password
	private UserDTO ldapGetUserByUserAccount(String userAccount) throws NamingException{
		UserDTO user = null;
		if (ctx==null){
			 return user;
		 }
		// 设置过滤条件 
	        // 限制要查询的字段内容 
	        String[] attrPersonArray = { "cn", "sn","userPassword" }; 
	        SearchControls searchControls = new SearchControls(); 
	        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE); 
	        // 设置将被返回的Attribute 
	        searchControls.setReturningAttributes(attrPersonArray); 

	        NamingEnumeration<SearchResult> answer = ctx.search(getRootDn(),"cn="+userAccount, searchControls); 
	    
	        // 输出查到的数据 
	        if (answer.hasMoreElements()) {
	        	    SearchResult entry = answer.next();
		            System.out.println(">>>" + entry.getName());
		            // Print out the groups
		            
		            Attributes attrs = entry.getAttributes();
		            //user = new User( attrs.get("sn").toString(),attrs.get("cn").toString());
		            //user.resetPassword(attrs.get("userPassword").toString());
		            //openldap中密码为二进制，需要处理
		            //Object o=attrs.get("userPassword").get();  
		            //byte[] s=(byte[])o;  
		            //String storedpassword=new String(s);  
		            user = new UserDTO(attrs.get("cn").get().toString(), new String((byte[])attrs.get("userPassword").get()));
		            user.setName(attrs.get("sn").get().toString());
		            System.out.println("the password is "+attrs.get("userPassword").get());
		            System.out.println("the account is "+attrs.get("cn").get());
		            
	        }
		return user;
	}

	
	/*
	 * 初始化密码加密匹配器
	*/
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(passwordEncryptService.getCredentialsStrategy());
		//SimpleCredentialsMatcher matcher = new SimpleCredentialsMatcher();
		matcher.setHashIterations(passwordEncryptService.getHashIterations());
		//HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		setCredentialsMatcher(matcher);
	} 

	public String getRootDn() {
		return rootDn;
	}

	public void setRootDn(String rootDn) {
		this.rootDn = rootDn;
	}
}
