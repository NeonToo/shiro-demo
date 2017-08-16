package com.sap.csc.web.controller.wx;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sap.csc.web.controller.GeneralController;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.exception.WxErrorException;

@RestController
@RequestMapping("/wechat/login")
public class WxAuthenticationController extends GeneralController {

	private final WxMaService wxMaService;

	@Autowired
	public WxAuthenticationController(WxMaService wxMaService) {
		this.wxMaService = wxMaService;
	}

	@GetMapping
	public String wxLogin(@RequestParam String code) {
		logger.info("----- WeChat Log In -----");
//		String sessionId = this.getHttpSession().getId();

		try {
			WxMaJscode2SessionResult wxCode2Session = wxMaService.getUserService().getSessionInfo(code);
			String openId = wxCode2Session.getOpenid();
			UsernamePasswordToken token = new UsernamePasswordToken(openId, "Welcome1");
			Subject currentUser = SecurityUtils.getSubject();

			logger.error("----- OpenID of Login User ----- " + openId);

			try {
				currentUser.login(token);

				// super.getHttpSession().setAttribute(sessionId,
				// wxCode2Session.getSessionKey() + wxCode2Session.getOpenid());
				return openId;
			} catch (UnknownAccountException uae) {
			} catch (IncorrectCredentialsException ice) {
			} catch (LockedAccountException lae) {
			} catch (ExcessiveAttemptsException eae) {
			} catch (AuthenticationException ae) {
				// unexpected error?
			}

		} catch (WxErrorException e) {
			// TODO code2session error
			e.printStackTrace();
		}

		return StringUtils.EMPTY;
	}
	
	@GetMapping("/logout")
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		
		logger.info(" ----- Log Out ----- " + currentUser.getPrincipal());
		
		if(currentUser.isAuthenticated()) {
			currentUser.logout();
		}
	}

}
