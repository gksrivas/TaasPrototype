package com.taas.ws.util;

import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;

public class KvmUtils {
	static int exist;
	private static Connect conn;

	public static int findDomains(String domName) {
		System.setProperty("jna.library.path", "/usr/lib/");
		try {
			conn = KvmConnect.getConnection();
			int[] actDom = conn.listDomains();
			if (actDom.length < 1) {
				exist = 2;
			} else {
				for (int i : actDom) {
					Domain name = conn.domainLookupByID(i);
					if ((name.getName()).equals(domName)) {
						if (name.isActive() == 1) {
							exist = 1;
						} else if (name.isPersistent() == 1) {
							exist = 0;
						} else if (name.isActive() == -1) {
							exist = -1;
						}
					}
				}
			}

		} catch (LibvirtException e) {
			// TODO Auto-generated catch block
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

		return exist;
	}
}