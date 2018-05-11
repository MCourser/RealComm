package com.machao.RealComm.webrtc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.machao.RealComm.webrtc.config.handler.DefaultHandshakeHandler;
import com.machao.RealComm.webrtc.config.interceptor.HttpSessionHandshakeInterceptor;
import com.machao.RealComm.webrtc.config.interceptor.OnlinePrincipalInterceptor;

@Configuration
public class WebSocketConfig  extends AbstractWebSocketMessageBrokerConfigurer {  
	
	@Autowired
	private HttpSessionHandshakeInterceptor httpSessionHandshakeInterceptor;
  
	@Autowired
	private DefaultHandshakeHandler defaultHandshakeHandler;
	
	@Autowired
	private OnlinePrincipalInterceptor onlinePrincipalInterceptor;
	
    @Override  
    public void registerStompEndpoints(StompEndpointRegistry registry) {  
		registry.addEndpoint("/websocket").addInterceptors(httpSessionHandshakeInterceptor).setHandshakeHandler(defaultHandshakeHandler).setAllowedOrigins("*");
		registry.addEndpoint("/websocket/sockjs").addInterceptors(httpSessionHandshakeInterceptor).setHandshakeHandler(defaultHandshakeHandler).setAllowedOrigins("*").withSockJS();
    }  
    
    @Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
    	registration.interceptors(onlinePrincipalInterceptor);
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.interceptors(onlinePrincipalInterceptor);
	}

	@Override  
    public void configureMessageBroker(MessageBrokerRegistry config) {  
        config.enableSimpleBroker("/sender");  
        config.setApplicationDestinationPrefixes("/receiver");  
    }  
}  