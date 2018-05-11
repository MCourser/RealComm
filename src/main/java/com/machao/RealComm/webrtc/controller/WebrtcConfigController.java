package com.machao.RealComm.webrtc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.machao.RealComm.webrtc.config.WebrtcConfiguration;
import com.machao.RealComm.webrtc.model.Message;

@Controller
public class WebrtcConfigController extends AbstractMessageController {

	@Autowired
	private WebrtcConfiguration webrtcConfiguration;
	
	@SendToUser("/sender/webrtc/config")
	@MessageMapping("/webrtc/config")
	public Message webrtcConfiguration() {
		return new Message(webrtcConfiguration.toBean());
	}

}
