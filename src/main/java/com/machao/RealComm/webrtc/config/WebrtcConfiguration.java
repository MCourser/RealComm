package com.machao.RealComm.webrtc.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.machao.RealComm.webrtc.model.RTCIceServer;

@Configuration
@ConfigurationProperties(prefix = "webrtc")
public class WebrtcConfiguration {
	private List<RTCIceServer> iceServers;

	public List<RTCIceServer> getIceServers() {
		return iceServers;
	}

	public void setIceServers(List<RTCIceServer> iceServers) {
		this.iceServers = iceServers;
	}

	public WebrtcConfiguration toBean() {
		WebrtcConfiguration bean = new WebrtcConfiguration();
		bean.setIceServers(iceServers);
		return bean;
	}

	@Override
	public String toString() {
		return "WebrtcConfiguration [iceServers=" + iceServers + "]";
	}

}
