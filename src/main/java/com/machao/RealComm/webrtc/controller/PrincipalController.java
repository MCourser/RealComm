package com.machao.RealComm.webrtc.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.machao.RealComm.webrtc.model.Message;
import com.machao.RealComm.webrtc.model.Principal;
import com.machao.RealComm.webrtc.service.PrincipalService;
import com.machao.RealComm.webrtc.service.PrincipalService.OnOnlinePrincipalChangedListener;

@Controller
public class PrincipalController extends AbstractMessageController implements OnOnlinePrincipalChangedListener {
	private static Logger logger = LoggerFactory.getLogger(PrincipalController.class);
	
	@Autowired
	private PrincipalService principalService;
	
	@PostConstruct
	public void init() {
		this.principalService.addOnOnlinePrincipalChangedListener(this);
	}
	
	@MessageMapping("/principal/heartbeat")
	public void heartbeat(Principal principal) {
		logger.info("heart beat from {}", principal);;
	}

	@SendToUser("/sender/principal")
	@MessageMapping("/principal")
	public Message principal(Principal principal) {
		return new Message(principal);
	}
	
	@SendToUser("/sender/principal/others")
	@MessageMapping("/principal/others")
	public Message principals(Principal principal) {
		return new Message(principalService.getPrincipalsBeside(principal));
	}
	
	@MessageMapping("/principal/nickname/{nickname}")
	public void nickname(Principal principal, @DestinationVariable String nickname) {
		if(StringUtils.isEmpty(nickname) || nickname.equals(principal.getNickname())) return;
		
		principal.setNickname(nickname);
		
		this.notifyPrincipalChanged(principal, true);
	}

	@Override
	public void onAddPrincipal(Principal principal) {
		logger.info("a principal online: {}", principal);
		
		this.notifyPrincipalChanged(principal, false);
		this.sendToUsers(principalService.getPrincipalsBeside(principal), "/sender/principal/online", new Message(principal));	
	}

	@Override
	public void onRemovePrincipal(Principal principal) {
		logger.info("a principal offline: {}", principal);
		
		this.notifyPrincipalChanged(principal, false);
		this.sendToUsers(principalService.getPrincipalsBeside(principal), "/sender/principal/offline", new Message(principal));
	}

}
