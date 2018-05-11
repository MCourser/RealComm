package com.machao.RealComm.webrtc.model;

public class RTCIceCandidate {
	private String candidate;
	private int sdpMLineIndex;
	private String sdpMid;

	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}

	public int getSdpMLineIndex() {
		return sdpMLineIndex;
	}

	public void setSdpMLineIndex(int sdpMLineIndex) {
		this.sdpMLineIndex = sdpMLineIndex;
	}

	public String getSdpMid() {
		return sdpMid;
	}

	public void setSdpMid(String sdpMid) {
		this.sdpMid = sdpMid;
	}

	@Override
	public String toString() {
		return "RTCIceCandidate [candidate=" + candidate + ", sdpMLineIndex=" + sdpMLineIndex + ", sdpMid=" + sdpMid + "]";
	}

}
