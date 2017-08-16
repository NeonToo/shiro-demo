package com.sap.csc.service.wx.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.sap.csc.service.wx.WxService;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;

@Configuration
@Service
@ConfigurationProperties(prefix = "wechat.mini-app")
public class WxServiceImpl implements WxService {

	private String originId;
	
	private String appId;
	
	private String appSecret;
	
	@Bean
	public WxMaConfig getWxMaConfig() {
		final WxMaInMemoryConfig wxMaConfig = new WxMaInMemoryConfig();
		
		wxMaConfig.setAppid(this.getAppId());
		wxMaConfig.setSecret(this.getAppSecret());

        return wxMaConfig;
	}
	
	@Bean
	public WxMaService getWxMaService() {
		final WxMaService wxMaService = new WxMaServiceImpl();
		
		wxMaService.setWxMaConfig(this.getWxMaConfig());
		
		return wxMaService;
	}
	

	public String getOriginId() {
		return originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
