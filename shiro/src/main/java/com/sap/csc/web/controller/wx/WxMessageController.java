package com.sap.csc.web.controller.wx;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.csc.app.model.enumeration.EncryptType;
import com.sap.csc.service.wx.WxDispatcherService;
import com.sap.csc.web.controller.GeneralController;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;

@RestController
@RequestMapping("/wechat/message")
public class WxMessageController extends GeneralController {

	private final WxMaService wxMaService;

	private final WxDispatcherService wxDispatcherService;

	@Autowired
	public WxMessageController(WxMaService wxMaService, WxDispatcherService wxDispatcherService) {
		this.wxMaService = wxMaService;
		this.wxDispatcherService = wxDispatcherService;
	}

	@GetMapping
	public String echo(
			// signature
			@RequestParam(value = "signature") String signature,
			// nonce
			@RequestParam(value = "nonce") String nonce,
			// timestamp
			@RequestParam(value = "timestamp") String timestamp,
			// echostr
			@RequestParam(value = "echostr", required = false) String echostr) {
		logger.info("----- Get Message from WeChat Server -----");

		if (wxMaService.checkSignature(timestamp, nonce, signature)) {
			return echostr;
		}

		return StringUtils.EMPTY;
	}

	@PostMapping
	public String dispatch(
			// Request Body
			@RequestBody String requestBody,
			// signature
			@RequestParam(value = "signature") String signature,
			// nonce
			@RequestParam(value = "nonce") String nonce,
			// timestamp
			@RequestParam(value = "timestamp") String timestamp,
			// encrypt_type
			@RequestParam(value = "encrypt_type", required = false, defaultValue = "RAW") EncryptType encryptType,
			// msgSignature
			@RequestParam(value = "msg_signature", required = false) String msgSignature) {
		if (!wxMaService.checkSignature(timestamp, nonce, signature)) {
			return StringUtils.EMPTY;
		}

		switch (encryptType) {
		case RAW: {
			wxDispatcherService.dispatch(WxMaMessage.fromXml(requestBody));

			return "success";
		}
		case AES: {
			wxDispatcherService.dispatch(WxMaMessage.fromEncryptedXml(requestBody, wxMaService.getWxMaConfig(), timestamp,
					nonce, msgSignature));

			return "success";
		}
		default:
			return StringUtils.EMPTY;
		}
	}
}
