package com.taas.ws.services;

public interface ITaaSService extends IAbstractService {
	public String startVM(String macAddress);
	public String stopVM(String macAddress);
	public String statusVM(String macAddress);	
}
