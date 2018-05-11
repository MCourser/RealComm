package com.machao.RealComm.webrtc.model;

import java.util.List;

public class RTCIceServer {
	private List<String> urls;
	private String username = "";
	private String credential = "";

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	@Override
	public String toString() {
		return "IceServer [urls=" + urls + ", username=" + username + ", credential=" + credential + "]";
	}

}