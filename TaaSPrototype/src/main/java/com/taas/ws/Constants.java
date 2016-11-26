package com.taas.ws;

public class Constants {
	public static final String PORT = "8443";	
	
	/**
	 * Windows Base Image Template
	 */
	public static String Base_Image = "<domain type='kvm'>\n" + "  <name>$vmName</name>\n" + "  <uuid>$uUid</uuid>\n"
			+ "  <memory unit='KiB'>8151629</memory>\n" + "  <currentMemory unit='KiB'>8151629</currentMemory>\n"
			+ "  <vcpu placement='static'>1</vcpu>\n" + "  <os>\n"
			+ "    <type arch='x86_64' machine='gaurav-VirtualBox'>hvm</type>\n" + "    <boot dev='hd'/>\n" + "  </os>\n"
			+ "  <features>\n" + "    <acpi/>\n" + "    <apic/>\n" + "    <pae/>\n" + "  </features>\n"
			+ "  <clock offset='localtime'/>\n" + "  <on_poweroff>destroy</on_poweroff>\n"
			+ "  <on_reboot>restart</on_reboot>\n" + "  <on_crash>restart</on_crash>\n" + "  <devices>\n"
			+ "    <emulator>/usr/bin/kvm-spice</emulator>\n" + "    <disk type='file' device='disk'>\n"
			+ "      <driver name='qemu' type='qcow2'/>\n"
			+ "      <source file='/var/lib/libvirt/images/windows.img'/>\n" + "      <target dev='hda' bus='ide'/>\n"
			+ "      <address type='drive' controller='0' bus='0' target='0' unit='0'/>\n" + "    </disk>\n"
			+ "    <controller type='usb' index='0'>\n"
			+ "      <address type='pci' domain='0x0000' bus='0x00' slot='0x01' function='0x2'/>\n"
			+ "    </controller>\n" + "    <controller type='pci' index='0' model='pci-root'/>\n"
			+ "    <controller type='ide' index='0'>\n"
			+ "      <address type='pci' domain='0x0000' bus='0x00' slot='0x01' function='0x1'/>\n"
			+ "    </controller>\n" + "    <interface type='network'>\n" + "      <mac address=$vmMac/>\n"
			+ "      <source network='default'/>\n" + "      <model type='rtl8139'/>\n"
			+ "      <address type='pci' domain='0x0000' bus='0x00' slot='0x03' function='0x0'/>\n"
			+ "    </interface>\n" + "    <serial type='pty'>\n" + "      <target port='0'/>\n" + "    </serial>\n"
			+ "    <console type='pty'>\n" + "      <target type='serial' port='0'/>\n" + "    </console>\n"
			+ "    <input type='tablet' bus='usb'/>\n" + "    <input type='mouse' bus='ps2'/>\n"
			+ "    <input type='keyboard' bus='ps2'/>\n" + "    <graphics type='vnc' port='-1' autoport='yes'/>\n"
			+ "    <sound model='ich6'>\n"
			+ "      <address type='pci' domain='0x0000' bus='0x00' slot='0x04' function='0x0'/>\n" + "    </sound>\n"
			+ "    <video>\n" + "      <model type='vga' vram='9216' heads='1'/>\n"
			+ "      <address type='pci' domain='0x0000' bus='0x00' slot='0x02' function='0x0'/>\n" + "    </video>\n"
			+ "    <memballoon model='virtio'>\n"
			+ "      <address type='pci' domain='0x0000' bus='0x00' slot='0x05' function='0x0'/>\n"
			+ "    </memballoon>\n" + "  </devices>\n" + "</domain>";

}
