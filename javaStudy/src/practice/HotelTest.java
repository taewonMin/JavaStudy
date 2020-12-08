package practice;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
문제) 

호텔 운영을 관리하는 프로그램 작성.(Map이용)
 - 키값은 방번호 

(단, 종료시 파일로 저장하고 프로그램 실행시 파일로부터 데이터를 불러올 수 있도록 처리한다.)
 
실행 예시)

**************************
호텔 문을 열었습니다.
**************************

*******************************************
어떤 업무를 하시겠습니까?
1.체크인  2.체크아웃 3.객실상태 4.업무종료
*******************************************
메뉴선택 => 1 <-- 입력

어느방에 체크인 하시겠습니까?
방번호 입력 => 101 <-- 입력

누구를 체크인 하시겠습니까?
이름 입력 => 홍길동 <-- 입력
체크인 되었습니다.

*******************************************
어떤 업무를 하시겠습니까?
1.체크인  2.체크아웃 3.객실상태 4.업무종료
*******************************************
메뉴선택 => 1 <-- 입력

어느방에 체크인 하시겠습니까?
방번호 입력 => 102 <-- 입력

누구를 체크인 하시겠습니까?
이름 입력 => 성춘향 <-- 입력
체크인 되었습니다

*******************************************
어떤 업무를 하시겠습니까?
1.체크인  2.체크아웃 3.객실상태 4.업무종료
*******************************************
메뉴선택 => 3 <-- 입력

방번호 : 102, 투숙객 : 성춘향
방번호 : 101, 투숙객 : 홍길동

*******************************************
어떤 업무를 하시겠습니까?
1.체크인  2.체크아웃 3.객실상태 4.업무종료
*******************************************
메뉴선택 => 2 <-- 입력

어느방을 체크아웃 하시겠습니까?
방번호 입력 => 101 <-- 입력
체크아웃 되었습니다.

*******************************************
어떤 업무를 하시겠습니까?
1.체크인  2.체크아웃 3.객실상태 4.업무종료
*******************************************
메뉴선택 => 1 <-- 입력

어느방에 체크인 하시겠습니까?
방번호 입력 => 102 <-- 입력

누구를 체크인 하시겠습니까?
이름 입력 => 허준 <-- 입력
102방에는 이미 사람이 있습니다.

*******************************************
어떤 업무를 하시겠습니까?
1.체크인  2.체크아웃 3.객실상태 4.업무종료
*******************************************
메뉴선택 => 2 <-- 입력

어느방을 체크아웃 하시겠습니까?
방번호 입력 => 101 <-- 입력
101방에는 체크인한 사람이 없습니다.

*******************************************
어떤 업무를 하시겠습니까?
1.체크인  2.체크아웃 3.객실상태 4.업무종료
*******************************************
메뉴선택 => 3 <-- 입력

방번호 : 102, 투숙객 : 성춘향

*******************************************
어떤 업무를 하시겠습니까?
1.체크인  2.체크아웃 3.객실상태 4.업무종료
*******************************************
메뉴선택 => 4 <-- 입력

**************************
호텔 문을 닫았습니다.
**************************


 */

public class HotelTest {
	private static Map<Integer, String> guests = new HashMap<>();
	Scanner sc = new Scanner(System.in);
	
	private static ObjectInputStream ois = null;
	private static ObjectOutputStream oos = null;
	
	static {
		File file = new File("d:/D_Other/연습용/hotel.txt");
		
		try {
			// 파일 읽어오기
			if(file.exists()) {
				ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
				try {
					guests = (Map<Integer, String>)ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			// 파일 쓰기 객체 생성
			oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		HotelTest hotel = new HotelTest();
		hotel.enterHotel();
	}
	
	private void printBanner() {
		System.out.println("**************************");
		System.out.println("호텔 문을 열었습니다.");
		System.out.println("**************************");
	}
	
	private void selectWork() {
		System.out.println();
		System.out.println("*******************************************");
		System.out.println("어떤 업무를 하시겠습니까?");
		System.out.println("1.체크인 2.체크아웃 3.객실상태 4.업무종료");
		System.out.println("*******************************************");
		System.out.print("메뉴선택 => ");	
	}
	
	public void enterHotel() {
		printBanner();
		while(true) {
			selectWork();
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			switch (input) {
			case 1:
				checkIn();
				break;
			case 2:
				checkOut();
				break;
			case 3:
				roomCondition();
				break;
			case 4:
				endWork();
				break;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

	/**
	 * 객실에 체크인(추가)하는 메서드
	 */
	private void checkIn() {
		System.out.println("\n어느 방에 체크인 하시겠습니까?");
		int roomNum = 0;
		while(true) {
			System.out.print("방번호 입력 => ");
			try {
				roomNum = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			break;
		}
		System.out.println("\n누구를 체크인 하시겠습니까?");
		System.out.print("이름 입력 => ");
		String name = sc.next();
		
		if(guests.get(roomNum)!=null) {
			System.out.println(roomNum + "번 방에는 이미 사람이 있습니다.");
			return;
		}
		guests.put(roomNum, name);
		
		System.out.println("체크인 되었습니다.");
	}
	
	/**
	 * 객실에서 체크아웃(삭제)하는 메서드
	 */
	private void checkOut() {
		System.out.println("\n어느방을 체크아웃 하시겠습니까?");
		int roomNum = 0;
		while(true) {
			System.out.print("방번호 입력 => ");
			try {
				roomNum = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			break;
		}
		if(guests.remove(roomNum)==null) {
			System.out.println(roomNum + "방에는 체크인한 사람이 없습니다.");
			return;
		}
		System.out.println("체크아웃 되었습니다.");
	}

	/**
	 * 객실 상태 조회 메서드
	 */
	private void roomCondition() {
		Set<Map.Entry<Integer, String>> entrySet = guests.entrySet();
		System.out.println();
		for(Map.Entry<Integer, String> entry : entrySet) {
			System.out.println("방번호 : " + entry.getKey() + ", 투숙객 : " + entry.getValue());
		}
		if(entrySet.size()==0) {
			System.out.println("현재 호텔에 아무도 없습니다.");
		}
	}
	
	/**
	 * 프로그램 종료 메서드
	 */
	private void endWork() {
		try {
			oos.writeObject(guests);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if(ois != null) {
					ois.close();
				}
				if(oos != null) {
					oos.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		sc.close();
		
		System.out.println();
		System.out.println("**************************");
		System.out.println("호텔 문을 닫았습니다.");
		System.out.println("**************************");
		System.exit(0);
	}
}
