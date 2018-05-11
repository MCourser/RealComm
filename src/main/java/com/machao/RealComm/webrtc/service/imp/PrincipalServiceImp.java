package com.machao.RealComm.webrtc.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.machao.RealComm.webrtc.model.Principal;
import com.machao.RealComm.webrtc.service.PrincipalService;

@Service
public class PrincipalServiceImp implements PrincipalService{
	private Set<Principal> principals = new HashSet<>();
	private Map<String, Principal> namePrincipalMapping = new HashMap<>();
	
	private List<OnOnlinePrincipalChangedListener> onOnlinePrincipalChangedListeners = new ArrayList<>();
	
	@Override
	public void add(Principal principal) {
		this.principals.add(principal);
		this.namePrincipalMapping.put(principal.getName(), principal);
		
		this.onOnlinePrincipalChangedListeners.forEach(onOnlinePrincipalChangedListener->{
			onOnlinePrincipalChangedListener.onAddPrincipal(principal);
		});
	}

	@Override
	public void remove(Principal principal) {
		this.principals.remove(principal);
		this.namePrincipalMapping.remove(principal.getName());
		
		this.onOnlinePrincipalChangedListeners.forEach(onOnlinePrincipalChangedListener->{
			onOnlinePrincipalChangedListener.onRemovePrincipal(principal);
		});
	}

	@Override
	public List<Principal> getPrincipals() {
		return new ArrayList<>(principals);
	}

	@Override
	public List<Principal> getPrincipalsBeside(Principal besidePrincipal) {
		List<Principal> retList = new ArrayList<>(principals);
		retList.remove(besidePrincipal);
		return retList;
	}

	@Override
	public List<Principal> getPrincipalsBeside(String name) {
		return this.getPrincipalsBeside(getPrincipalByName(name));
	}

	@Override
	public Principal getPrincipalByName(String name) {
		return namePrincipalMapping.get(name);
	}

	@Override
	public void addOnOnlinePrincipalChangedListener(OnOnlinePrincipalChangedListener onOnlinePrincipalChangedListener) {
		this.onOnlinePrincipalChangedListeners.add(onOnlinePrincipalChangedListener);
	}
	
}
