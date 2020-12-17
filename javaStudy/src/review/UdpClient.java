package review;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
	public void start() throws IOException {
		// 데이터가 저장될 공간으로 byte배열을 생성한다.(패킷 수신용)
		byte[] msg = new byte[100];
		
		DatagramPacket inPacket = new DatagramPacket(msg, msg.length);
		
		InetAddress serverAddress = InetAddress.getByName("192.168.46.22");
		DatagramPacket outPacket = new DatagramPacket(msg, 1, serverAddress, 8888);
		
		DatagramSocket datagramSocket = new DatagramSocket();
		
		datagramSocket.send(outPacket);
		
		datagramSocket.receive(inPacket);
		
		System.out.println("현재 서버 시간 => " + new String(inPacket.getData()));
		
		datagramSocket.close();
	}
	
	public static void main(String[] args) throws IOException {
		new UdpClient().start();
	}
}
