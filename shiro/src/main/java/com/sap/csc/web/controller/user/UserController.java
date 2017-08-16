package com.sap.csc.web.controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
public class UserController extends GeneralController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/users")
	public Collection<UserResponse> findAll(@RequestParam(required = false) Integer roleId) {
		Subject currentUser = SecurityUtils.getSubject();
		List<User> users = new ArrayList<>();
		
		if(currentUser.isPermitted("User:Read")) {
			users = userService.findAll();
		} else if(currentUser.isPermitted("User:Read:Department")) {
			users = userService.findByDepartment(currentUser);
		} else {
			super.getHttpServletResponse().setStatus(401);
		}

//		if (super.getHttpServletRequest().getParameterMap().isEmpty()) {
//			users = userService.findAll();
//		} else {
//			if (roleId != null) {
//				users = userService.findByRoleId(roleId);
//			}
//		}

		if (CollectionUtils.isNotEmpty(users)) {
			return users.stream().map(user -> new UserResponse(user)).collect(Collectors.toList());
		}

		return CollectionUtils.EMPTY_COLLECTION;
	}

	@GetMapping("/users/{id}")
	public UserResponse findOne(@PathVariable Long id) {
		User user = userService.findOne(id);

		if (user != null) {
			return new UserResponse(user);
		}

		return null;
	}

	@PostMapping("/users")
	public UserResponse insertUser(@Valid UserRequest userRequest, BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			super.getHttpServletResponse().sendRedirect("/error.html");
		}

		User user = userService.insertUser(userRequest);

		if (user != null) {
			return new UserResponse(user);
		}

		return null;
	}

	@PutMapping("/users/{id}")
	public UserResponse updateUser(@RequestBody UserRequest userRequest, @PathVariable Long id) {
		User user = userService.updateUser(userRequest);

		if (user != null) {
			return new UserResponse(user);
		}

		return null;
	}
}
