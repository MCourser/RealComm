package com.machao.RealComm.webrtc.config.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

import com.machao.RealComm.webrtc.model.Principal;

@Component
public class HttpSessionHandshakeInterceptor extends org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor{
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		// refuse all http://xxx type
		if(request instanceof HttpServletRequest) return false;

		Principal principal = (Principal) SimpMessageHeaderAccessor.getUser(attributes);
		if (principal == null) {
			principal = new Principal();
			attributes.put(SimpMessageHeaderAccessor.USER_HEADER, principal);
		}
		
		return true;
	}
}
