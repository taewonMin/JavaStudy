package practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/*
 	 로또를 구매하는 프로그램 작성하기
 
 사용자는 로또를 구매할 때 구매할 금액을 입력하고
 입력한 금액에 맞게 로또번호를 출력한다.
 (단, 로또 한장의 금액은 1000원이고 거스름돈도 계산하여
      출력한다.)

	==========================
         Lotto 프로그램
	--------------------------
	 1. Lotto 구입
	  2. 프로그램 종료
	==========================		 
	메뉴선택 : 1  <-- 입력
			
	 Lotto 구입 시작
		 
	(1000원에 로또번호 하나입니다.)
	금액 입력 : 2500  <-- 입력
			
	행운의 로또번호는 아래와 같습니다.
	로또번호1 : 2,3,4,5,6,7
	로또번호2 : 20,21,22,23,24,25
			
	받은 금액은 2500원이고 거스름돈은 500원입니다.
			
   	 ==========================
         Lotto 프로그램
	--------------------------
	  1. Lotto 구입
	  2. 프로그램 종료
	==========================		 
	메뉴선택 : 2  <-- 입력
		
	감사합니다
 */

public class LottoTest {
	public static void main(String[] args) {
		Lotto lot = new Lotto();
		lot.startLotto();
	}
}

class Lotto{
	private static final int BALL_COUNT = 6;
	private static final int RANGE_OF_NUMBER = 45;
	Scanner sc = new Scanner(System.in);
	
	public void startLotto() {
		int input = 0;
		while(true) {
			printBanner();
			System.out.print("메뉴 선택 : ");
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			switch (input) {
			case 1:
				buyLotto();
				break;
			case 2:
				System.out.println("\n감사합니다.");
				System.exit(0);
				break;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	private void printBanner() {
		System.out.println("==========================");
		System.out.println("Lotto 프로그램");
		System.out.println("--------------------------");
		System.out.println("1. Lotto 구입");
		System.out.println("2. 프로그램 종료");
		System.out.println("==========================");
	}
	
	private void buyLotto() {
		System.out.println("\nLotto 구입 시작\n");
		System.out.println("(1000원에 로또번호 하나입니다.)");
		int money = 0;
		while(true) {
			System.out.print("금액 입력 : ");
			try {
				money = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			break;
		}
		printLottoNum(money);
	}
	
	private void printLottoNum(int money) {
		System.out.println("\n행운의 로또번호는 아래와 같습니다.");
		
		for(int i=0; i<money/1000; i++) {
			Set<Integer> lottoSet = new TreeSet<>();
			while(lottoSet.size()<BALL_COUNT) {
				int rand = (int)(Math.random()*RANGE_OF_NUMBER+1);
				lottoSet.add(rand);
			}
			
			List<Integer> lottoList = new ArrayList<>(lottoSet);
			Collections.sort(lottoList);
			
			System.out.print("로또번호"+(i+1)+" : ");
			for(int j=0; j<BALL_COUNT; j++) {
				System.out.print(lottoList.get(j));
				if(j!=BALL_COUNT-1) {
					System.out.print(", ");
				}
			}
			System.out.println();
		}
		System.out.println("\n받은 금액은 "+money+"원이고 거스름돈은 "+money%1000+"원입니다.\n");
	}
}