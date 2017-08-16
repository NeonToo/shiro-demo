package com.sap.csc.service.wx;

import cn.binarywang.wx.miniapp.bean.WxMaMessage;

public interface WxDispatcherService {

	void dispatch(WxMaMessage message);
	
}
