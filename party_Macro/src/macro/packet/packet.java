package macro.packet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;

import macro.method.SetConsole;

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
	static boolean next = false;
	String protocoll[] = { "HOPOPT", "ICMP", "IGMP", "GGP", "IPV4", "ST",
			"TCP", "CBT", "EGP", "IGP", "BBN", "NV2", "PUP", "ARGUS", "EMCON",
			"XNET", "CHAOS", "UDP", "mux" };
	static selectRoomIdListener sl;
	static String uid , cate;

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
					String temp = "";
					if(sstr.contains("drapoker")){
						if (sstr.contains(uid) && sstr.contains("connectBattle.php")) {
							cate = "B";
							temp = sstr.contains("roomID") ? sstr.split("roomID=")[1].split("\n")[0]
									.toString().trim() : "next";
						} else if (sstr.contains(uid) && sstr.contains("connectColosseum.php")){
							cate = "C";
							temp = sstr.contains("roomID") ? sstr.split("roomID=")[1].split("\n")[0]
									.toString().trim() : "next";
						}
						sl.select(cate+temp);
						sl.header("uid: " + (sstr.split("uid: ")[1].split("\n\n")[0]).trim());
						next = temp.equals("next") ? true : false;
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				String ss;
				try {
					ss = new String(packet.data, "UTF-8");
					System.out.println("ss : " + ss);
					if(next && ss.contains("roomID")) {
						next = false;
						sl.select(cate+ss.split("roomID=")[1].split("\n")[0].toString().trim());
					}
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
		jpcap.loopPacket(100, new packet());
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