package com.machao.RealComm.webrtc.service;

import com.machao.RealComm.webrtc.model.Call;
import com.machao.RealComm.webrtc.model.Principal;

public interface CallService {
	
//	public void setCallChangedListener(CallChangedListener callChangedListener);

	public void add(Call call);
	public Call get(Principal principal);
	public void remove(Call call);
	public void remove(Principal principal);
	public boolean isInCall(Principal... principals);
	
//	public interface CallChangedListener {
//		public void onCallStart(Call call);
//		public void onCallEnd(Call call);
//		public void onCallError(Call call);
//	}
}
