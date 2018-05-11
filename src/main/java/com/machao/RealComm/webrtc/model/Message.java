package com.machao.RealComm.webrtc.model;

public class Message {
	private Object from;
	private Object content;

	public Message(Object from, Object content) {
		super();
		this.from = from;
		this.content = content;
	}

	public Message(Object content) {
		this("server", content);
	}

	public Object getFrom() {
		return from;
	}

	public void setFrom(Object from) {
		this.from = from;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Message [from=" + from + ", content=" + content + "]";
	}

}
