package macro.packet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import jpcap.*;

import jpcap.packet.DatalinkPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class packet implements PacketReceiver {

	static int i = 0;
	String protocoll[] = { "HOPOPT", "ICMP", "IGMP", "GGP", "IPV4", "ST",
			"TCP", "CBT", "EGP", "IGP", "BBN", "NV2", "PUP", "ARGUS", "EMCON",
			"XNET", "CHAOS", "UDP", "mux" };
	static selectRoomIdListener sl;
	static String uid;

	public packet() {
		// TODO Auto-generated constructor stub
	}

	public void receivePacket(Packet packet) {
		i++;

		IPPacket tpt = (IPPacket) packet;
		if (packet != null) {

			int ppp = tpt.protocol;
			String proto = protocoll[ppp];
			if (!tpt.dst_ip.toString().equals("/211.238.6.163"))
				return;

			if (proto.equals(("TCP"))) {
				TCPPacket tp = (TCPPacket) packet;
				InetAddress a = tp.src_ip;
				InputStream is = new ByteArrayInputStream(packet.data);
				try {
					String sstr = IOUtils.toString(is, "UTF-8");
					if(sstr.contains("drapoker")){
						if (sstr.contains(uid) && sstr.contains("connectBattle.php")) {
							String temp = sstr.split("roomID=")[1].split("\n")[0]
									.toString().trim();
							sl.select("B"+temp);
						} else if (sstr.contains(uid) && sstr.contains("connectColosseum.php")){
							String temp = sstr.split("roomID=")[1].split("\n")[0]
									.toString().trim();
							sl.select("C"+temp);
						}
						sl.header((sstr.split("HTTP/1.1")[1].split("Keep-Alive")[0] + "Keep-Alive").trim());
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				String ss;
				try {
					ss = new String(packet.data, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void Start(selectRoomIdListener slt, String uid ,int Device) throws Exception {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		this.sl = slt;
		this.uid = uid;
		JpcapCaptor jpcap = JpcapCaptor.openDevice(devices[Device], 2000, true,20);
		jpcap.loopPacket(50, new packet());
	}
	
	public ArrayList<String> getDevice(){
		ArrayList<String> deviceList = new ArrayList<String>();
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		for (int i = 0; i < devices.length; i++) {
			deviceList.add(i,devices[i].description);
		}
		return deviceList;
	}

	public interface selectRoomIdListener {
		void select(String str);
		void header(String str);
	}
}