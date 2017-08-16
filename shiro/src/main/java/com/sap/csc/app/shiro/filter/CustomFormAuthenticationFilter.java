package com.sap.csc.app.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public CustomFormAuthenticationFilter() {
		super();
		logger.info("----- Custom Form Authentication Filter -----");
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		logger.info("----- Access Denied -----");
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (logger.isTraceEnabled()) {
                	logger.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (logger.isTraceEnabled()) {
                	logger.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (logger.isTraceEnabled()) {
            	logger.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

            // TODO handle access denied
//            saveRequestAndRedirectToLogin(request, response);
            handleUnauthorized(request, response);
            return false;
        }
    }

	private void handleUnauthorized(ServletRequest request, ServletResponse response) {
		saveRequest(request);
		toHttp(response).setStatus(401);
	}
	
	private HttpServletResponse toHttp(ServletResponse response) {
		return (HttpServletResponse) response;
	}
	
//	private HttpServletRequest toHttp(ServletRequest request) {
//		return (HttpServletRequest) request;
//	}
//	
//	protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
//        saveRequest(request);
//        redirectToLogin(toHttp(request), toHttp(response));
//    }
//	
//	protected void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.setContentType("text/html;charset=UTF-8");
//		response.sendRedirect(getLoginUrl());
//    }
	
}
