package com.machao.RealComm.webrtc.config.handler;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

import com.machao.RealComm.webrtc.model.Principal;

@Component
public class DefaultHandshakeHandler extends org.springframework.web.socket.server.support.DefaultHandshakeHandler{
	
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
		return (Principal) SimpMessageHeaderAccessor.getUser(attributes);
	}
}
