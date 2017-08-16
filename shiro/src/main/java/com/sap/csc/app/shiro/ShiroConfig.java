package com.sap.csc.app.shiro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.sap.csc.app.shiro.cache.RedisCacheManager;
import com.sap.csc.app.shiro.filter.CustomFormAuthenticationFilter;
import com.sap.csc.app.shiro.realm.UserRealm;
import com.sap.csc.app.shiro.session.RedisSessionDAO;

@Configuration
public class ShiroConfig {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	public FilterRegistrationBean shiroFilterRegistrationBean() {
		logger.info("----- Shiro FilterRegisteration -----");
		FilterRegistrationBean bean = new FilterRegistrationBean(new DelegatingFilterProxy("shiroFilter"));

		bean.setEnabled(true);
		bean.addUrlPatterns("/*");

		return bean;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
		logger.info("----- Shiro Filter -----");
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		Map<String, Filter> customFilters = new HashMap<>();

		customFilters.put("authc", new CustomFormAuthenticationFilter());
		shiroFilter.setFilters(customFilters);

		filterChainDefinitionMap.put("/api/users/**", "authc");
		filterChainDefinitionMap.put("/api/roles/**", "authc, roles[ADMIN]");
		filterChainDefinitionMap.put("/api/signout", "logout");
		filterChainDefinitionMap.put("/**", "anon");

		shiroFilter.setSecurityManager(securityManager);
		// shiroFilter.setLoginUrl("/login.html");
		// shiroFilter.setUnauthorizedUrl("/login.html");
		// shiroFilter.setSuccessUrl("/");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilter;
	}

	@Bean
	public DefaultWebSecurityManager securityManager(RedisCacheManager redisCacheManager, UserRealm userRealm,
			DefaultWebSessionManager sessionManager) {
		logger.info("----- Shiro SecurityManager -----");
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

		securityManager.setCacheManager(redisCacheManager);
		securityManager.setRealm(userRealm);
		securityManager.setSessionManager(sessionManager);

		return securityManager;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		logger.info("----- Shiro LifecycleBeanPostProcess -----");
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public UserRealm userRealm() {
		logger.info("----- Shiro UserRealm -----");
		UserRealm userRealm = new UserRealm();

		// userRealm.setCacheManager(redisCacheManager); // must be set if want
		// setCachingEnabled()
		// to work
		userRealm.setCachingEnabled(true);
		userRealm.setAuthenticationCachingEnabled(false);

		return userRealm;
	}

	@Bean
	public RedisCacheManager redisCacheManager() {
		logger.info("----- Shiro RedisCacheManager -----");
		return new RedisCacheManager();
	}

	@Bean
	public DefaultWebSessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
		logger.info("----- Shiro SessionManager Configuration -----");
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

		// sessionManager.setCacheManager(redisCacheManager);
		sessionManager.setSessionDAO(redisSessionDAO);

		return sessionManager;
	}

	@Bean
	public RedisSessionDAO redisSessionDAO() {
		logger.info("----- Shiro RedisSessionDAO -----");
		return new RedisSessionDAO();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		logger.info("----- Shiro AdvisorAutoProxyCreator -----");
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();

		creator.setProxyTargetClass(true);

		return creator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		logger.info("----- Shiro AuthorizationAttributeSourceAdvisor -----");
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();

		advisor.setSecurityManager(securityManager);

		return advisor;
	}

	// @Bean(name = "filterChainDefinitions")
	// public ShiroFilterChainDefinition filterChainDefinitions() {
	// DefaultShiroFilterChainDefinition filterChainDefinitionMap = new
	// DefaultShiroFilterChainDefinition();
	//
	// filterChainDefinitionMap.addPathDefinition("/users", "authc");
	// filterChainDefinitionMap.addPathDefinition("/logout", "logout");
	// filterChainDefinitionMap.addPathDefinition("/**", "anon");
	//
	// return filterChainDefinitionMap;
	// }

	// private CacheManager cacheManager() {
	// return new MemoryConstrainedCacheManager(); // note: it's not
	// clustered/distributed
	// }
}
