package com.machao.RealComm.webrtc.service;

import java.util.List;

import com.machao.RealComm.webrtc.model.Principal;

public interface PrincipalService {
	public void add(Principal principal);
	public void remove(Principal principal);
	public List<Principal> getPrincipals();
	public List<Principal> getPrincipalsBeside(Principal besidePrincipal);
	public List<Principal> getPrincipalsBeside(String name);
	public Principal getPrincipalByName(String name);
	
	public void addOnOnlinePrincipalChangedListener(OnOnlinePrincipalChangedListener onOnlinePrincipalChangedListener);
	
	public interface OnOnlinePrincipalChangedListener {
		public void onAddPrincipal(Principal principal);
		public void onRemovePrincipal(Principal principal);
	}
}
