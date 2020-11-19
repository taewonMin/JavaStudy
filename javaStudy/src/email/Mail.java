package email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 	SMTP & Mail
 	- SMTP ( Simple Mail Transfer Protocol )
 	: 인터넷상에서 메일을 보내기 위해 사용되는 통신 규약
 	: 이메일 송수신 서버를 SMTP서버라고 함
 	: SMTP서버를 구축하기 위해서는 리눅스같은 물리적인 서버를 구축하고 네트워크 환경을 잡아줘야하지만
 	  NAVER, GOOGLE은 SMTP서버를 제공해준다.
 	
	개발 순서)
	1. JavaMail 라이브러리 다운로드
		http://mvnrepository.com/artifact/javax.mail/mail/1.4.7
		-> mail-1.4.7.jar 파일 import
		
	2. 구글 또는 네이버에서 STMP환경설정(발신하는 이메일)
		네이버 : 메일 설정 > POP3/IMAP설정 > 사용함
		
	3. 구현
	  1) 발신자의 메일계정과 비밀번호 설정
	  2) Properties클래스를 이용하여 SMTP서버 정보 설정
	  3) SMTP서버 정보와 사용자 정보를 기반으로 Session클래스의 인스턴스 생성
	  4) Message클래스의 객체를 사용하여 수신자의 메일주소, 내용, 제목 작성
	  5) Transport클래스를 사용하여 작성한 메세지 전송
	  
	관련 정보)
	- Properties 클래스
	  : HashTables의 하위 클래스이기 때문에 Map의 속성(key,value)를 갖는다.
	  : HashMap과 큰 차이는 없으나 '파일 입출력'을 지원한다.
	  
		  load() -> 파일의  내용을 읽어서 key-value의 형태로 분류하여 맵에 보관
		  
		  	Properties p = new Properties();
		  	p.load(new Filereader(filePath));
		  	
		  keySet() -> 저장된 모든 keySet을 반납
		  getProperty(String key) -> key에 해당하는 value를 반환
		  
		    for(Object i : p.keySet()){
		    	key = (String)i;
		    	value = p.getProperty(key);
		   	}
	- Session 클래스 ( mail-1.4.7.jar 라이브러리 )
	  : 사용자의 이름, 암호, 메일 서버등의 모든 정보를 관리한다.
	  : JavaMail API를 이용한 모든 작업은 Session객체를 필요로 한다.
	  
	  	getInstance(Properties props, Authenticator authenticator)
	  	- props : 관련 속성을 가지고있는 Properties 객체
	  	- Authenticator 클래스
	  	  : 네트워크 접속에 필요한 인증을 얻기위한 추상 클래스
	  	  : PasswordAuthentication getPasswordAuthentication() 메서드를 구현해야 객체 생성 가능
	    - PasswordAuthentication 클래스
	      : 단순히 사용자 이름과 암호를 저장하기 위한 클래스
	      : Authenticator 에서 비밀번호인증이 필요할 때 사용
	      : PasswordAuthentication(String userName, String password) 생성자로 사용
	     
	- MimeMessage 클래스
	  : Message 클래스 상속
	  : MIME는 전자 우편을 위한 인터넷 표준 포맷이다.
	  : 수취인, 발신인, 제목, 내용등을 설정할 수 있음
	  
	    setFrom(new InternetAddress(email)) -> 발신인 이메일 설정
	    - InternetAddress 클래스
	      : Message 클래스의 수취인과 발신인을 지정할 때 사용.
	      : InternetAddress(String email)
	      
	    addRecipient(Message.RecipientType.TO, new InternetAddress(email)) -> 수신인 이메일 설정
	    
	    setSubject(String title) -> 제목 설정
	    
	    setText(String content) -> 내용 설정
	    
	- Transport 클래스
	  : 메시지 전송을 위한 클래스
	  : send(Message message) -> 메시지 보내기
 */


public class Mail {
	private String email;		//발신자의 이메일
	private String password;	//발신자의 패스워드
	private String recipient;	//수신자의 이메일
	
	/**
	 * 발신인 이메일, 비밀번호 설정
	 * @param email 발신자의 email
	 * @param password 발신자의 email에 해당하는 password
	 */
	public Mail(String email, String password){
		//1) 발신자의 메일 계정, 비밀번호 설정
		this.email = email;
		this.password = password;
	}
	
	/**
	 * 이메일 보내기
	 */
	public void sendEmail(){
		//2) Properties클래스를 이용하여 SMTP서버 정보 설정
		Properties p = new Properties();
		//mail.smtp.host : 이메일 발송을 처리해줄 STMP서버(gmail : smtp.gmail.com / naver : smtp.naver.com)
		p.put("mail.smtp.host", "smtp.naver.com");
		//mail.smtp.port : SMTP서버와 통신하는 포트(gmail : 465 / naver : 587)
		p.put("mail.smtp.port", 587);
		p.put("mail.smtp.auth", "true");
		//gmail
//		p.put("mail.smtp.ssl.enable", "true");
//		p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		//3) SMTP서버 정보와 사용자 정보를 기반으로 Session클래스의 인스턴스 생성
		Session session = Session.getInstance(p, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(email, password);
			}
		});
		
		//4) Message클래스의 객체를 사용하여 수신자의 메일주소, 내용, 제목 작성
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(email));	// 발신인의 이메일 주소 설정
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));	// 수신인의 이메일 주소 설정
			message.setSubject("제목입니다.");	// 제목 설정
			message.setText("내용입니다."); // 내용 설정
			
			//5) Transport클래스를 사용하여 작성한 메세지 전송
			Transport.send(message);
			System.out.println("이메일이 발송되었습니다.");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 수신자의 이메일 설정
	 * @param email 수신자의 이메일
	 */
	public void setRecipient(String email){
		recipient = email;
	}
}

//참고 : https://ktko.tistory.com/entry/JAVA-SMTP%EC%99%80-Mail-%EB%B0%9C%EC%86%A1%ED%95%98%EA%B8%B0Google-Naver