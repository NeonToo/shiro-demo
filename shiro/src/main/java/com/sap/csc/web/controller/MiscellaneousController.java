package com.sap.csc.web.controller;

import java.io.IOException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MiscellaneousController extends GeneralController {

	@GetMapping("/state")
	public String getSystemState() {
		return "System Working";
	}

	@GetMapping("/test")
	public void testRedirect() throws IOException {
		String serverRootUrl = super.getServerRootUrl();
		
		super.getHttpServletResponse().sendRedirect(serverRootUrl + "/#/login"); 
	}
	
	@GetMapping("/test/fragment")
	public void testFragmentRedirect() throws IOException {
		super.getHttpServletResponse().sendRedirect("/#/login"); 
	}
	
	@GetMapping("/test/permission")
	public void testPermission() {
		Subject currentUser = SecurityUtils.getSubject();
		
		if(currentUser.isPermitted("MANAGER:User:Read")) {
			logger.info("----- higher -----" + true);
		}
		
		if(currentUser.isPermitted("MANAGER:User:Read:Department:Employee")) {
			logger.info("----- lower -----" + true);
		}
	}
}
