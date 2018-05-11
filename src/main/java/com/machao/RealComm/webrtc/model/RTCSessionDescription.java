package com.machao.RealComm.webrtc.model;

public class RTCSessionDescription {
	private String type;
	private String sdp;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSdp() {
		return sdp;
	}

	public void setSdp(String sdp) {
		this.sdp = sdp;
	}

	@Override
	public String toString() {
		return "RTCSessionDescription [type=" + type + ", sdp=" + sdp + "]";
	}

}
