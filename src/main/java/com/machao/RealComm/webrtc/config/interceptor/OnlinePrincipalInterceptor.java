package com.machao.RealComm.webrtc.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import com.machao.RealComm.webrtc.model.Principal;
import com.machao.RealComm.webrtc.service.PrincipalService;

@Component
public class OnlinePrincipalInterceptor extends ChannelInterceptorAdapter {

	@Autowired
	private PrincipalService principalService;

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(message);

		StompCommand command = stompHeaderAccessor.getCommand();
		if(command != null) {
			Principal principal = (Principal) stompHeaderAccessor.getUser();
			if(command.equals(StompCommand.CONNECT)) {
				this.principalService.add(principal);
			} else if(command.equals(StompCommand.DISCONNECT)) {
				this.principalService.remove(principal);
			}
		}
	}
}