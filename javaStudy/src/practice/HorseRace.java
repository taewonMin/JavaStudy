package practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 10마리의 말들이 경주하는 경마 프로그램 작성하기
 * 
 * 말은 Horse라는 이름의 클래스로 구성하고, 이 클래스는 말이름(String), 등수(int)를 멤버변수로 갖는다. 그리고, 이
 * 클래스에는 등수를 오름차순으로 처리할 수 있는 기능이 있다.( Comparable 인터페이스 구현)
 * 
 * 경기 구간은 1~50구간으로 되어 있다.
 * 
 * 경기 중 중간중간에 각 말들의 위치를 >로 나타내시오.
예)
1번말		--->------------------------------------
2번말		----->----------------------------------
...
 * 
 * 경기가 끝나면 등수를 기준으로 정렬하여 출력한다.
 * 
 */
public class HorseRace {
	private static final int HORSE_NUM = 10;	// 경주말의 수
	private static Horse myHorse;
	private static int myDonation;
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		List<Horse> horses = new ArrayList<>();		// 말 저장 list
		MyThread[] tArr = new MyThread[HORSE_NUM];	// 쓰레드 저장 배열

		// 객체 생성
		for(int i=0; i<HORSE_NUM; i++){
			// 말 객체 생성
			String name = (i+1) + "번말";
			Horse horse = new Horse(name);
			horses.add(horse);
			
			// 쓰레드 생성 후 우선순위 랜덤으로 부여
			tArr[i] = new MyThread(horse);
			
			int random = (int)(Math.random()*10+1);
			tArr[i].setPriority(random);
		}
		
		// 도박
		System.out.println("응원할 말을 선택하세요.(1~10)번말");
		System.out.print("=> ");
		
		int num = 0;
		while(true) {
			try {
				num = sc.nextInt();
			}catch(Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			break;
		}
		for(Horse h : horses) {
			if(h.getName().equals(num+"번말")) {
				myHorse = h;
				break;
			}
		}
		
		System.out.println("액수를 입력하세요.");
		System.out.println("=== 배당률 ===\n1등:\tx4\n2등:\tx3\n3등:\tx2\n4~5등:\tx1\nelse:\tx0");
		System.out.print("=> ");
		
		while(true) {
			try {
				myDonation = sc.nextInt();
			}catch(Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			break;
		}
		
		// 경기 준비
		Thread raceReady = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=5; i>0; i--) {
					if(i==5) {
						System.out.println("경기 준비..");
					}else if(i==1){
						System.out.println("시작!!");
					}else {
						System.out.println(i-1);
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		raceReady.start();
		
		try {
			raceReady.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		// 달리기 시작(쓰레드 시작)
		for(int i=0; i<HORSE_NUM; i++) {
			tArr[i].start();
		}
		
		// 현재 경주 상황 출력
		int endCnt = 0;
		while(true) {
			// 경주 상황 출력
			for(int i=0; i<HORSE_NUM; i++) {
				System.out.print(horses.get(i).getName() + "\t");	// 말 이름
				tArr[i].printTrack();	// 말 위치
			}
			System.out.println();
			
			// 0.2초 간격으로 출력
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 10마리가 모두 들어왔으면 종료
			if(endCnt != 10) {
				endCnt = 0;
				for(int i=0; i<HORSE_NUM; i++) {
					if(tArr[i].getState() != Thread.State.TERMINATED) {
						break;
					}
					endCnt++;
				}
			}else {
				break;
			}
		}
		
		// 정렬 후 결과 표시
		Collections.sort(horses);
		System.out.println("=== 경기 결과 ===");
		for(int i=0; i<HORSE_NUM; i++) {
			System.out.print(horses.get(i).getRank() + "등 : " + horses.get(i).getName());
			switch(i) {
			case 0:
				System.out.println(" 🥇");
				break;
			case 1:
				System.out.println(" 🥈");
				break;
			case 2:
				System.out.println(" 🥉");
				break;
			default:
				System.out.println();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		
		// 환급금
		for(int i=0; i<HORSE_NUM; i++) {
			if(horses.get(i) == myHorse) {
				switch(i) {
				case 0:
					myDonation *= 4;
					break;
				case 1:
					myDonation *= 3;
					break;
				case 2:
					myDonation *= 2;
					break;
				default:
					myDonation = 0;
				}
				break;
			}
		}
		System.out.printf("나의 환급금 : %,d원", myDonation);
	}
}

class MyThread extends Thread {
	private String track = "🐎-------------------------------------------------";
	private Horse horse;
	private volatile static int rank = 1;
	
	public MyThread(Horse horse) {
		this.horse = horse;
	}
	
	@Override
	public void run() {
		int len = track.length(); // 길이 : 51
		for(int i=0; i<len-1; i++) {
			
			// 결승점에 도착하면
			if(i == len-2) {
				track += "\t" + rank;
				horse.setRank(rank++);
				break;
			}
			track = track.substring(0, i) + "-🐎" + track.substring(i+3);
			
			// 말의 스피드
			int random = (int)(Math.random()*11)*30;
			try {
				Thread.sleep(random);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void printTrack() {
		System.out.println(track);
	}
}

class Horse implements Comparable<Horse> {
	private String name;
	private int rank;

	public Horse(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public int compareTo(Horse o) {
		return new Integer(getRank()).compareTo(o.getRank());
	}
}