package com.taas.ws.services.impl;

import java.util.UUID;

import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.DomainInfo;
import org.libvirt.Error;
import org.libvirt.LibvirtException;
import org.libvirt.DomainInfo.DomainState;
import org.libvirt.Error.ErrorNumber;

import com.taas.ws.Constants;
import com.taas.ws.services.ITaaSService;
import com.taas.ws.util.KvmUtils;
import com.taas.ws.util.KvmConnect;
import com.taas.ws.VMStatus;

public class TaaSService implements ITaaSService {
	private Connect conn = null;
	private Domain domain = null;
	private DomainState state = null;

	public static String vmTemplate;

	
	@Override
	public String startVM(String macAddress) {
		// Connect to KVM first
		establishConnection();
		
		// TODO Auto-generated method stub
		String vmState = null;
		String mac = "'" + macAddress + "'";
		String vmName = "windows_" + macAddress;
		try {
			UUID uuid = UUID.randomUUID();
			vmTemplate = Constants.Base_Image;
			vmTemplate = vmTemplate.replace("$uUid", uuid.toString());
			vmTemplate = vmTemplate.replace("$vmName", vmName);
			vmTemplate = vmTemplate.replace("$vmMac", mac);

			/*
			 * Check if domain with same name and mac exists else create domain
			 */
			int domainExist = KvmUtils.findDomains(vmName);
			if (domainExist == 1) {
				vmState = Error.ErrorNumber.VIR_ERR_DOM_EXIST.toString() + ": Already Running";
			} else if (domainExist == 0) {
				vmState = ErrorNumber.VIR_ERR_DOM_EXIST.toString() + ": Already Defined";
			} else if (domainExist == -1) {
				vmState = ErrorNumber.VIR_ERR_UNKNOWN.toString() + ": Unknown Error";
			} else {
				domain = conn.domainDefineXML(vmTemplate);
				domain.create();

				state = domain.getInfo().state;
				if (state == DomainState.VIR_DOMAIN_RUNNING) {
					vmState = VMStatus.STARTED.toString();
				}
				conn.close();
			}
		} catch (LibvirtException e) {
			e.getMessage();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (LibvirtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				
			}
		}
		return vmState;
	}

	@Override
	public String stopVM(String macAddress) {
		// Connect to KVM first
		establishConnection();
		
		String vmStatus = null;
		try {
			int[] runningDomains = conn.listDomains();
			String[] defDom = conn.listDefinedDomains();

			/* Shutdown and Undefine Active Domain */
			if (runningDomains.length >= 1) {
				for (int i : runningDomains) {
					domain = conn.domainLookupByID(i);
					String domainDesc = domain.getXMLDesc(0);
					if (domainDesc.contains(macAddress)) {
						domain.shutdown();
						domain.undefine();
						vmStatus = VMStatus.STOPPED.name();
					} else {
						vmStatus = "domain with mac: " + macAddress + " not found";
					}
				}
			} else if (defDom.length >= 1) {
				/* Undefine Inactive Domain */
				for (String definedDomName : defDom) {
					domain = conn.domainLookupByName(definedDomName);
					String domainDesc = domain.getXMLDesc(0);
					if (domainDesc.contains(macAddress)) {
						domain.undefine();
						vmStatus = "Inactive Domain Undefined";
					}
				}
			} else {
				vmStatus = "No Active Domains";
			}
		} catch (LibvirtException e) {
			e.getMessage();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (LibvirtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
			}
		}
		return vmStatus;
	}

	@Override
	public String statusVM(String macAddress) {
		// Connect to KVM first
		establishConnection();
		
		String vmStatus = null;

		try {
			int[] runningDomains = conn.listDomains();
			if (runningDomains.length < 1) {
				vmStatus = DomainState.VIR_DOMAIN_NOSTATE.name();
			}
			for (int i : runningDomains) {
				domain = conn.domainLookupByID(i);
				String domainDesc = domain.getXMLDesc(0);
				if (domainDesc.contains(macAddress)) {
					state = domain.getInfo().state;
					if (state == DomainInfo.DomainState.VIR_DOMAIN_RUNNING) {
						vmStatus = VMStatus.STARTED.name();
					} else if (state == DomainInfo.DomainState.VIR_DOMAIN_SHUTOFF) {
						vmStatus = VMStatus.STOPPED.name();
					} else {
						vmStatus = VMStatus.BOOTING.name();
					}
				}
			}

		} catch (LibvirtException e) {
			e.getMessage();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (LibvirtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
			}
		}
		return vmStatus;
	}
	
	private void establishConnection() {
		System.setProperty("jna.library.path", "/usr/lib/");

		try {
			this.conn = KvmConnect.getConnection();
		} catch (LibvirtException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}
}
