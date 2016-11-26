package com.taas.ws.util;

import org.libvirt.Connect;
import org.libvirt.LibvirtException;

public class KvmConnect {
	private static Connect conn = null;

	//Creates new Connection to Hypervisor
	public static Connect getConnection() throws LibvirtException {

		try {
			conn = new Connect("qemu:///system");

		} catch (LibvirtException e) {
			e.printStackTrace();
			e.getMessage();
		}
		return conn;
	}
}
