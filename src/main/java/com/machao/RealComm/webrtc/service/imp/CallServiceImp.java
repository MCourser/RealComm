package com.machao.RealComm.webrtc.service.imp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.machao.RealComm.webrtc.model.Call;
import com.machao.RealComm.webrtc.model.Principal;
import com.machao.RealComm.webrtc.service.CallService;

@Service
public class CallServiceImp implements CallService {
	private Set<Call> calls = new HashSet<>();
	private Map<Principal, Call> callingCallMapping = new HashMap<>();
	private Map<Principal, Call> calleeCallMapping = new HashMap<>();
	
	@Override
	public void add(Call call) {
		this.calls.add(call);
		this.callingCallMapping.put(call.getCalling(), call);
		this.calleeCallMapping.put(call.getCallee(), call);
	}

	@Override
	public Call get(Principal principal) {
		Call call = callingCallMapping.get(principal);
		if(call == null) call = calleeCallMapping.get(principal);
		return call;
	}

	@Override
	public void remove(Call call) {
		this.calls.remove(call);
		this.callingCallMapping.remove(call.getCalling(), call);
		this.calleeCallMapping.remove(call.getCallee(), call);
	}

	@Override
	public void remove(Principal principal) {
		Call call = get(principal);
		if(call == null) return;

		this.calls.remove(call);
	}

	@Override
	public boolean isInCall(Principal... principals) {
		for(Principal principal : principals) {
			if(principal.getStatus().equals(Principal.Status.CALLING)) {
				return true;
			}
		}
		return false;
	}
}
