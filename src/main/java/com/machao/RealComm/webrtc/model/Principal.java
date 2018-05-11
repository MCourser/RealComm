package com.machao.RealComm.webrtc.model;

import java.util.UUID;

public class Principal implements java.security.Principal {
	private String name = UUID.randomUUID().toString();
	private String nickname;
	private Status status = Status.IDLE;
	
	public enum Status {
		IDLE, CALLING, RINGING
	}

	@Override
	public String getName() {
		return name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Principal other = (Principal) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Principal [name=" + name + ", nickname=" + nickname + ", status=" + status + "]";
	}
}