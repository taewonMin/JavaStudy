package practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/*
 	문제) Set을 이용하여 숫자 야구 게임 프로그램을 작성하시오.
 		컴퓨터의 숫자는 난수를 이용하여 구한다.
 		(스트라이크는 'S', 볼은 'B'로 출력한다.) 1~9
 		
 	컴퓨터의 난수가 9 5 7 일때 실행 예시)
 	  숫자입력 => 3 5 6		1S 0B
 	  숫자입력 => 7 8 9		0S 2B
 	  		:
 	  숫자입력 => 9 5 7		3S 0B
 	 
 	 5번째 만에 맞췄군요.
 */
public class BaseballTest {
	public static void main(String[] args) {
		Set<Integer> set = new HashSet<>();
		while(set.size()<3) {
			int rand = (int)(Math.random()*9 + 1);
			set.add(rand);
		}
		System.out.println(set);
		List<Integer> list = new ArrayList<>(set);
		Collections.shuffle(list);
		Integer[] answer = list.toArray(new Integer[0]);
		
		Scanner sc = new Scanner(System.in);
		int count = 1;
		while(true) {
			System.out.println("숫자 입력 : ");
			int[] myInput = new int[3];
			myInput[0] = sc.nextInt();
			myInput[1] = sc.nextInt();
			myInput[2] = sc.nextInt();
			if(myInput[0]==myInput[1] || myInput[1]==myInput[2] || myInput[2]==myInput[0]) {
				System.out.println("중복되지 않는 값을 입력하세요.");
				continue;
			}
			
			int strike = 0;
			int ball = 0;
			for(int i=0; i<answer.length; i++) {
				if(answer[i]==myInput[i]) { 	//strike 찾기
					strike++;
				}else if(list.contains(myInput[i])) { // ball 찾기
					ball++;
				}
			}
			if(strike==answer.length) {
				System.out.println(count + "번째 만에 맞췄군요.");
				break;
			}
			System.out.println(strike + "S " + ball + "B");
			count++;
		}
	}
}
