package encryption;

import java.io.UnsupportedEncodingException;
import java.security.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import sun.misc.BASE64Encoder;
	/*
		AES256( Advanced Encryption Standard 256bit )
	- 대칭형, 블록 암호화 알고리즘
	- 암호화와 복호화에 비밀키 하나만 사용
	- 속도가 빠르지만 비밀키하나만을 사용하여 암호화를 하기때문에 비밀키 유출시 암호화가 무의미
	
	필요한 정보)
	1. 키의 길이는 128,192,256 bit(16,24,32byte)만 가능
	2. Cipher - 암호화/복호화 클래스 
		(객체 생성)
		Cipher c = Cipher.getInstance("사용할 알고리즘/운용 모드/패딩 방식");
		- 알고리즘 : AES,DES,...
		- 운용모드 : ECB,CBC,...
		- 패딩방식 : 데이터를 특정한 크기로 맞추기위해 특정 크기보다 부족한 부분의 공간을 의미없는 문자로 채워서 비트수를 맞춤
			- PKCS#5 또는 #7 패딩 : 블록 암호화 알고리즘에서 바이트 단위의 블럭의 빈자리를 채우기위해 사용되는 패딩 알고리즘
				예) 블럭의 크기가 128비트(16바이트)이면,
					블럭의 크기(16바이트)의 배수만큼 데이터가 꽉 차야함(16, 32, 48, ...)
					블럭의 크기가 딱 맞아 떨어지는 경우에는 뒷부분의 byte(01, 02 02, ...)가 패딩비트인지 구분하기 어렵기 때문에
					한 블럭만큼 패딩을 추가한다. 
				원본 데이터		01 02 03 04 05 06 07 08 01 02 04 08 10 20 40 80 00 11 22 33 44 55 66 77 88 99 AA
				블럭단위		01 02 03 04 05 06 07 08 01 02 04 08 10 20 40 80 | 00 11 22 33 44 55 66 77 88 99 6바이트만큼 부족
					=> 부족한 바이트수(6)만큼 부족한바이트값(06)으로 채움
				패딩적용		01 02 03 04 05 06 07 08 01 02 04 08 10 20 40 80 | 00 11 22 33 44 55 66 77 88 99 06 06 06 06 06 06
				
				#5방식과 #7방식은 구현 방식이 같지만 #5는 블럭크기가 (8 byte)로 고정
				AES는 128비트(16바이트)블럭 알고리즘이기때문에 #5방식을 쓸 필요가 없다.
				but, java에서 PKCS5Padding표기법만 허용함
			- NoPadding : 패딩방식이 없기때문에 암호화할 입력 문자열의 크기를 16바이트의 배수로 맞추어야함. 
							16바이트의 배수가 아니면  IllegalBlockSizeException 예외 발생
		(초기화)
		c.init(opmode값, key값);
		- opmode
		  : ENCRYPT_MODE - 암호화 모드
		  : DECRYPT_MODE - 복호화 모드
		  
	3. BASE64 인코딩
	  - 8비트의 바이너리 데이터를 ASCII영역의 문자로 변형하는 인코딩 방식
	  - 64진법을 사용(A-Z a-z 0-9 + /)
	  - 특정 문자열을 암호화하여 서버에 전달할 때 #,@같은 특수문자가 생겨 데이터 전송과 연동에 문제가 생길 수 있으므로
	    BASE64를 이용하여 인코딩한 후 디코딩하여 원래의 텍스트로 변환하여 사용한다.
	
	4. 예외
	InvalidKeyException
	: java의 기본 정책으로는 AES128 방식(128bit=16byte)까지만 사용가능하므로
	  AES256 방식으로 암호화를 진행하면 키 값을 초과하여 예외 발생
		-> local_policy.jar, US_export_policy.jar 라이브러리를 추가하여 예외 처리
	
	5. 필요한 라이브러리
	commons-codec-1.10.jar, local_policy.jar, US_export_policy.jar
	
	*/

public class AES256 {
	private static final String ALGO = "AES";	// 암호화방식
	private byte[] keyValue;	// 비밀키를 byte로 바꿔 저장한 원본데이터 배열
	
	/**
	 * 비밀키를 받아 객체를 생성
	 * @param key 비밀키
	 */
	public AES256(String key){
		try {
			keyValue = key.getBytes("UTF-8"); // 문자열 key를 UTF-8로 인코딩한 후 byte로 변환하여 저장
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("지정된 문자의 부호화 형식을 지원하지 않음");
		}
	}
	
	/**
	 * 암호화할 문자열을 받아 AES256방식으로 암호화하는 메서드
	 * @param data 암호화할 문자열
	 * @return 암호화된 문자열
	 */
	public String encrypt(String data){
		Key key = generateKey();	// Key : 인터페이스
		Cipher c;
		String encryptedValue = null;	// 암호화된 문자열
		try {
			c = Cipher.getInstance(ALGO);	//AES의 기본값은 'AES/ECB/PKCS5Padding'
			c.init(Cipher.ENCRYPT_MODE, key);	//key를 사용해 암호 초기화
			byte[] encVal = c.doFinal(data.getBytes()); // 문자열 암호화
			encryptedValue = new BASE64Encoder().encode(encVal);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("현재의 환경에서는 사용할 수 없는 알고리즘");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			System.out.println("현재 환경에서 사용할 수 없는 패딩방식");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			System.out.println("키값이 16또는 24또는 32가 아님");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			System.out.println("16바이트의 배수가 아닌 입력 길이");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			System.out.println("암호화의 비밀키와 복호화의 비밀키가 서로 다름");
		}
		return encryptedValue;
	}
	
	/**
	 * 복호화 메서드
	 * @param encryptedData 암호화된 문자열
	 * @return 복호화된 문자열
	 */
	public String decrypt(String encryptedData) {
		Key key = generateKey();
		Cipher c;
		String decryptedValue = null;
		try {
			c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = new Base64().decode(encryptedData);
			byte[] decVal = c.doFinal(decordedValue);
			decryptedValue = new String(decVal);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decryptedValue;
	}
	
	/**
	 * 비밀키를 이용하여 AES256방식으로 암호화
	 * @return 생성된 Key구현체(SecretKeySpec) 반환
	 */
	private Key generateKey() {
		/*
		  SecretKeySpec 클래스 : Key를 구현한 구현체
		  SecretKeySpec(byte[] key, String algorithm) 생성자
		  - key : 비밀키의 키 데이터
		  - algorithm : 비밀키 알고리즘의 이름
		*/
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}
	
	public static void main(String[] args) {
		AES256 aes = new AES256("asdfasdfasdfasdfasdfasdfasdfasdf");	//가능한 키의 길이 : 16, 24, 32
		String encData = aes.encrypt("안녕하십니까@asdf.com");
		System.out.println(encData);
		String decData = aes.decrypt(encData);
		System.out.println(decData);
	}
}

//참고 : https://www.youtube.com/watch?v=uxyGJMBs2dI
