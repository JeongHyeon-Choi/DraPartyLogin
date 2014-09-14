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
		System.out.println(packet + "\n");
		System.out.println("this is packet " + i + " :" + "\n");
		i++;

		IPPacket tpt = (IPPacket) packet;
		if (packet != null) {

			int ppp = tpt.protocol;
			String proto = protocoll[ppp];
			System.out.println("about the ip packet in network layer : \n");
			System.out
					.println("******************************************************************");
			if (!tpt.dst_ip.toString().equals("/211.238.6.163"))
				return;

			if (proto.equals(("TCP"))) {
				System.out.println(" /n this is TCP packet");
				TCPPacket tp = (TCPPacket) packet;
				System.out.println("this is destination port of tcp :"
						+ tp.dst_port);
				InetAddress a = tp.src_ip;
				// if("/211.238.6.163".equals(a.toString())){
				InputStream is = new ByteArrayInputStream(packet.data);
				try {
					String sstr = IOUtils.toString(is, "UTF-8");
					System.out.println("STRING " + sstr);
					if (sstr.contains(uid) && sstr.contains("connectBattle.php")) {
						String temp = sstr.split("roomID=")[1].split("\n")[0]
								.toString().trim();
						System.out.println("addddddddddddddddddd : " + temp);
						sl.select(temp);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String ss;
				try {
					ss = new String(packet.data, "UTF-8");
					System.out.println("STRING " + ss);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// }
			}
		}

	}

	public void Start(selectRoomIdListener slt, String uid ,int Device) throws Exception {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		this.sl = slt;
		this.uid = uid;
//		for (int i = 0; i < devices.length; i++) {
//			System.out.println(i + " :" + devices[i].name + "("
//					+ devices[i].description + ")");
//			System.out.println("    data link:" + devices[i].datalink_name
//					+ "(" + devices[i].datalink_description + ")");
//			System.out.print("    MAC address:");
//			for (byte b : devices[i].mac_address) {
//				System.out.print(Integer.toHexString(b & 0xff) + ":");
//			}
//			System.out.println();
//			for (NetworkInterfaceAddress a : devices[i].addresses) {
//				System.out.println("    address:" + a.address + " " + a.subnet
//						+ " " + a.broadcast);
//			}
//		}
//		for (int i = 0; i < devices.length; i++){
			JpcapCaptor jpcap = JpcapCaptor.openDevice(devices[Device], 2000, true, 20);

			jpcap.loopPacket(50, new packet());
//		}

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
	}
}