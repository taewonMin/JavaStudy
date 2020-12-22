package network;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.util.StringTokenizer;

public class MyHttpServer {
	private final int port = 8888;
	private final String encoding = "UTF-8";

	/**
	 * http서버 시작
	 */
	public void start() {
		try(ServerSocket server = new ServerSocket(this.port)){
			
			while(true) {
				try {
					Socket socket = server.accept();
					
					HttpHandler handler = new HttpHandler(socket);
					
					new Thread(handler).start();	// 요청처리 시작
					
				}catch(IOException e) {
					e.printStackTrace();
					System.err.println("커넥션 오류!!!");
				}catch(RuntimeException e) {
					e.printStackTrace();
					System.err.println("알수 없는 오류!!");
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
			System.err.println("서버 시작 오류!!!");
		}
	}
	
	
	/**
	 * Http요청 처리를 위한 Runnalbe 클래스
	 */
	private class HttpHandler implements Runnable {
		private final Socket socket;
		
		public HttpHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			OutputStream out = null;
			BufferedReader br = null;
			
			try {
				out = new BufferedOutputStream(socket.getOutputStream());
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				// 요청 헤더정보 파싱하기
				StringBuilder request = new StringBuilder();
				while(true) {
					String str = br.readLine();
					
					if(str.equals("")) break;	// Empty Line 체크
					
					request.append(str + "\n");
				}
				
				System.out.println("요청헤더:\n" + request.toString());
				
				String reqPath = "";	// 요청 경로
				
				// 요청 페이지 정보 가져오기
				StringTokenizer st = new StringTokenizer(request.toString());
				while(st.hasMoreElements()) {
					String token = st.nextToken();
					
					if(token.startsWith("/")) {
						reqPath = token;
						break;
					}
				}
				
				// 상대경로(프로젝트 폴더 기준) 설정
				String fileName = "./WebContent" + reqPath;
				
				// 해당 파일이름을 이용하여 Content-Type정보 추출하기
				String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);	// MIME 타입
				
				System.out.println("Content-Type => " + contentType);
				
				File file = new File(fileName);
				if(!file.exists()) {
					makeErrorPage(out, 404, "Not Found");
					return;
				}
				
				byte[] body = makeResponseBody(fileName);
				
				byte[] header = makeResponseHeader(body.length, contentType);
				
				// 요청헤더가 HTTP/1.0 이나 그 이후의 버전을 지원할 경우 MIME 헤더를 전송한다.
				if(request.toString().indexOf("HTTP/") != -1) {
					out.write(header);	// 응답헤더 보내기
				}
				System.out.println("응답헤더:\n" + new String(header));
				
				out.write(body); 	// 응답내용 보내기
				out.flush();
				
			}catch(IOException e) {
				e.printStackTrace();
				System.err.println("입출력 오류!!!");
			}finally {
				try {
					socket.close();	// 소켓닫기
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 응답내용  생성하기
	 * @param filePath 응답으로 사용할 파일 경로
	 * @return 바이트배열 데이터
	 * @throws IOException 
	 */
	private byte[] makeResponseBody(String filePath) throws IOException {
		FileInputStream fis = null;
		
		byte[] data = null;
		try {
			File file = new File(filePath);
			data = new byte[(int) file.length()];
			
			fis = new FileInputStream(file);
			fis.read(data);
			
		} finally {
			if(fis != null) {
				fis.close();
			}
		}
		return data;
	}
	
	/**
	 * 응답헤더 생성하기
	 * @param contentLength 응답내용 크기
	 * @param mimeType 마임타입
	 * @return 바이트 배열
	 */
	private byte[] makeResponseHeader(int contentLength, String mimeType) {
		String header = "HTTP/1.1 200 OK\r\n"
				+ "Server: MyHttpServer 1.0\r\n"
				+ "Content-length: " + contentLength + "\r\n"
				+ "Content-Type: " + mimeType
				+ "; charset=" + this.encoding
				+ "\r\n\r\n";
		return header.getBytes();
	}
	
	/**
	 * 에러페이지 생성
	 * @param out
	 * @param statusCode
	 * @param errMsg
	 */
	private void makeErrorPage(OutputStream out, int statusCode, String errMsg) {
		String statusLine = "HTTP/1.1" + " " + statusCode + " " + errMsg;
		try {
			out.write(statusLine.getBytes());
			out.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MyHttpServer httpServer = new MyHttpServer();
		httpServer.start();
	}
}
