package com.sap.csc.app.shiro.realm;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sap.csc.app.model.jpa.Role;
import com.sap.csc.app.model.jpa.User;
import com.sap.csc.app.persistence.repository.user.UserRepository;

public class UserRealm extends AuthorizingRealm {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String AUTHORIZATION_CACHE_KEY_PREFIX = "user:";
	
	@Autowired
	private UserRepository userRepository;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		logger.info("----- UserRealm - Get Authentication Info ----- " + authenticationToken.getPrincipal());
		
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String username = token.getUsername();
		User user = userRepository.findByUsername(username);
		
		if(user != null){
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
        }
		
		return null;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		logger.info("----- UserRealm - Get Authorization Info ----- " + principalCollection.toString());
		
		String username = (String) principalCollection.getPrimaryPrincipal();
		User user = userRepository.findByUsername(username);
		
		if(user != null) {
			//权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<Role> roles = user.getRoles();
            
            if(CollectionUtils.isNotEmpty(roles)) {
            	info.setRoles(user.getStringRoles());
            	
            	for(Role role: roles) {
            		info.setStringPermissions(role.getStringPermissions());
            	}
            }
            
            return info;
		}
		
		return null;
	}
	
	@Override
	protected String getAuthorizationCacheKey(PrincipalCollection principals) {
		return new StringBuilder(AUTHORIZATION_CACHE_KEY_PREFIX).append(principals).toString();
	}

}
