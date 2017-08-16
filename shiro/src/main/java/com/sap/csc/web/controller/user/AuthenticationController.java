package com.sap.csc.web.controller.user;

import java.io.IOException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.csc.app.model.dto.request.UserRequest;
import com.sap.csc.app.model.dto.response.UserResponse;
import com.sap.csc.app.model.jpa.User;
import com.sap.csc.service.user.UserService;
import com.sap.csc.web.controller.GeneralController;

@RestController
@RequestMapping("/api")
public class AuthenticationController extends GeneralController {

	private final UserService userService;

	@Autowired
	public AuthenticationController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signin")
	public UserResponse login(@RequestBody UserRequest userRequest,
			@RequestParam(value = "route", required = false) String route) throws IOException {
		UsernamePasswordToken token = new UsernamePasswordToken(userRequest.getUsername(), userRequest.getPassword());
		Subject currentUser = SecurityUtils.getSubject();

		try {
			currentUser.login(token);

			if (currentUser.isAuthenticated()) {
				User user = userService.findByUsername(userRequest.getUsername());

				if (user != null) {
					// super.getHttpServletResponse().sendRedirect("/#" +
					// route);
					return new UserResponse(user);
				}
			}
		} catch (UnknownAccountException uae) {
			logger.error("----- UnknownAccountException -----");
		} catch (IncorrectCredentialsException ice) {
			logger.error("----- IncorrectCredentialsException -----");
		} catch (LockedAccountException lae) {
			logger.error("----- LockedAccountException -----");
		} catch (ExcessiveAttemptsException eae) {
			logger.error("----- ExcessiveAttemptsException -----");
		} catch (AuthenticationException ae) {
			logger.error("----- AuthenticationException -----");
		}

		return null;
	}

	@GetMapping("/signout")
	public void logout() {
		logger.info(" ----- Log Out ----- ");
		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.isAuthenticated()) {
			currentUser.logout();
		}
	}
}
