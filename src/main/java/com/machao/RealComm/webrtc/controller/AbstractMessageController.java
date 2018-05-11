package com.machao.RealComm.webrtc.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.machao.RealComm.webrtc.model.Message;
import com.machao.RealComm.webrtc.model.Principal;
import com.machao.RealComm.webrtc.service.PrincipalService;

public abstract class AbstractMessageController {
	private static Logger logger = LoggerFactory.getLogger(AbstractMessageController.class);
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private PrincipalService principalService;
	
	public interface SendToUsersCallback {
		public Object onSend(Principal principal);
	}
	

	public void send(String destination, Object payload) throws MessagingException {
		logger.info(">>> send {} {}", destination, payload);
		this.simpMessagingTemplate.convertAndSend(destination, payload);
	}

	public void sendToUser(Principal principal, String destination, Object payload) throws MessagingException {
		logger.info(">>> sendToUser {} {} {}", destination, principal, payload);
		this.simpMessagingTemplate.convertAndSendToUser(principal.getName(), destination, payload);
	}

	public void sendToUsers(Collection<Principal> principals, String destination, Object payload) throws MessagingException {
		if(principals.isEmpty()) return;
		
		logger.info(">>> sendToUsers {} {} {}", principals.size(), destination, payload);
		principals.forEach(principal->{
			this.simpMessagingTemplate.convertAndSendToUser(principal.getName(), destination, payload);
		});
	}
	
	public void sendToUsers(Collection<Principal> principals, String destination, SendToUsersCallback callback) throws MessagingException {
		if(principals.isEmpty()) return;
		
		logger.info(">>> sendToUsers {} {}", principals.size(), destination);
		principals.forEach(principal->{
			this.simpMessagingTemplate.convertAndSendToUser(principal.getName(), destination, callback.onSend(principal));
		});
	}
	
	public void notifyPrincipalChanged(Principal principal, boolean isNotifyTarget) {
		if(isNotifyTarget) {
			this.sendToUser(principal, "/sender/principal/changed",  new Message(principal));
		}
		this.sendToUsers(principalService.getPrincipalsBeside(principal), "/sender/principal/others/changed", new SendToUsersCallback() {
			@Override
			public Object onSend(Principal principal) {
				return new Message(principalService.getPrincipalsBeside(principal));
			}
		});
	}
}
