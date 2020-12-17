package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MultiChatClient {
	
	private Scanner sc = new Scanner(System.in);
	
	private String nickName;	// 대화명
	
	// 클라이언트 시작
	public void clientStart() {
		// 대화명 입력하기
		System.out.print("대화명 >> ");
		
		nickName = sc.next();
		
		Socket socket = null;
		try {
			socket = new Socket("localhost", 7777);
			
			System.out.println("서버에 연결되었습니다.");
			
			// 송신용 스레드 생성
			ClientSender sender = new ClientSender(socket, nickName);
			// 수신용 스레드 생성
			ClientReceiver receiver = new ClientReceiver(socket);
			
			//스레드 실행
			sender.start();
			receiver.start();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// 메시지를 전송하는 스레드
	class ClientSender extends Thread {
		private DataOutputStream dos;
		private String name;
		private Scanner scan = new Scanner(System.in);
		
		public ClientSender(Socket socket, String name) {
			this.name = name;
			try {
				dos = new DataOutputStream(socket.getOutputStream());
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				// 시작하자마자 자신의 대화명을 서버로 전송
				if(dos != null) {
					dos.writeUTF(name);
				}
				while(dos != null) {
					// 키보드로 입력받은 메시지를 서버로 전송
					dos.writeUTF(scan.nextLine());
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 수신용 스레드 클래스
	class ClientReceiver extends Thread {
		private DataInputStream dis;
		
		public ClientReceiver(Socket socket) {
			try {
				dis = new DataInputStream(socket.getInputStream());
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			while(dis != null) {
				try {
					// 서버로부터 수신한 메시지 출력하기
					System.out.println(dis.readUTF());
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		MultiChatClient client = new MultiChatClient();
		client.clientStart();
	}
}
