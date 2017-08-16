package com.sap.csc.service.wx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.csc.service.wx.WxDispatcherService;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;

@Service
public class WxDispatcherServiceImpl implements WxDispatcherService {
	
	private final WxMaService wxMaService;
	
	private WxMaMessageRouter wxMaMessageRouter;
	
	@Autowired
	public WxDispatcherServiceImpl(WxMaService wxMaService) {
		this.wxMaService = wxMaService;
		this.wxMaMessageRouter = new WxMaMessageRouter(wxMaService);
		
	}

	@Override
	public void dispatch(WxMaMessage message) {
		this.wxMaMessageRouter.route(message);
	}

}
