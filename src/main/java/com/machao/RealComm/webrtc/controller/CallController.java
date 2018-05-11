package com.machao.RealComm.webrtc.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.machao.RealComm.webrtc.model.Call;
import com.machao.RealComm.webrtc.model.Message;
import com.machao.RealComm.webrtc.model.Principal;
import com.machao.RealComm.webrtc.model.Principal.Status;
import com.machao.RealComm.webrtc.model.RTCIceCandidate;
import com.machao.RealComm.webrtc.model.RTCSessionDescription;
import com.machao.RealComm.webrtc.service.CallService;
import com.machao.RealComm.webrtc.service.PrincipalService;
import com.machao.RealComm.webrtc.service.PrincipalService.OnOnlinePrincipalChangedListener;

@Controller
public class CallController extends AbstractMessageController implements OnOnlinePrincipalChangedListener {
	
	@Autowired
	private PrincipalService principalService;
	
	@Autowired
	private CallService callService;
	
	@PostConstruct
	public void init() {
		this.principalService.addOnOnlinePrincipalChangedListener(this);
	}

	@MessageMapping("/call/offer/{name}")
	public void offer(Principal calling, @DestinationVariable String name, RTCSessionDescription callingSessionDescription) {
		if(StringUtils.isEmpty(name) || callingSessionDescription==null) return;
		
		Principal callee = principalService.getPrincipalByName(name);
		if(callee == null) return;
		if(callService.isInCall(calling) || callService.isInCall(callee)) return;

		calling.setStatus(Status.CALLING);
		callee.setStatus(Status.RINGING);
		
		this.notifyPrincipalChanged(calling, true);
		this.notifyPrincipalChanged(callee, true);
		
		Call call = new Call(calling, callee);
		call.setCallingSessionDescription(callingSessionDescription);
		
		this.callService.add(call);
		
		this.sendToUser(call.getCallee(), "/sender/call/offer", new Message(call));
	}

	@MessageMapping("/call/answer/{name}")
	public void answer(Principal principal, @DestinationVariable String name, RTCSessionDescription calleeSessionDescription) {
		if(StringUtils.isEmpty(name) || calleeSessionDescription==null) return;
		
		if(callService.isInCall(principal)) return;
		
		Call call = callService.get(principal);
		if(call == null) return;
		if(!name.equals(call.getCalling().getName())) return;
		
		call.getCallee().setStatus(Status.CALLING);
		call.getCalling().setStatus(Status.CALLING);
		call.setCalleeSessionDescription(calleeSessionDescription);
		
		this.notifyPrincipalChanged(call.getCalling(), true);
		this.notifyPrincipalChanged(call.getCallee(), true);
		
		this.sendToUser(call.getCalling(), "/sender/call/answer", new Message(call));
	}
	
	@MessageMapping("/call/end")
	public void end(Principal principal) {
		Call call = callService.get(principal);
		if(call == null) return;
		
		call.getCalling().setStatus(Principal.Status.IDLE);
		call.getCallee().setStatus(Principal.Status.IDLE);
		
		this.callService.remove(call);
		
		if(principal.equals(call.getCalling())) {
			this.sendToUser(call.getCallee(), "/sender/call/end", new Message(principal, call));
		} else if(principal.equals(call.getCallee())) {
			this.sendToUser(call.getCalling(), "/sender/call/end", new Message(principal, call));
		}
		
		this.notifyPrincipalChanged(call.getCalling(), true);
		this.notifyPrincipalChanged(call.getCallee(), true);
	}

	@MessageMapping("/call/candidate")
	public void candidate(Principal principal, String payload) {
		try {
			if(payload==null) return;
			
			RTCIceCandidate iceCandidate = JSONObject.parseObject(payload, RTCIceCandidate.class);
			if(iceCandidate == null || iceCandidate.getCandidate() == null) return;
			
			Call call = callService.get(principal);
			if(call == null) return;
			
			if(principal.equals(call.getCalling())) {
				this.sendToUser(call.getCallee(), "/sender/call/candidate", new Message(principal, iceCandidate));
			} else if(principal.equals(call.getCallee())) {
				this.sendToUser(call.getCalling(), "/sender/call/candidate", new Message(principal, iceCandidate));
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onAddPrincipal(Principal principal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemovePrincipal(Principal principal) {
		this.end(principal);
	}

}
