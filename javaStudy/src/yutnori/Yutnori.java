package yutnori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Yutnori {
	private Board board = Board.getInstance();
	private Yut[] yutArr = new Yut[4];	//윷 세트(4개)
	
//	private String[] position = new String[20];	// 말의 위치 배열 - 19->0
	private int myMarker = 4;	// 내 말의 수
	private int comMarker = 4;	// 상대방의 말의 수
	private List<Integer> myDistance = new ArrayList<Integer>(); //윷을 던져 나온 눈 저장 배열
	private boolean nextTurn = false;
	
	private Scanner sc = new Scanner(System.in);
	
	{
		//윷 초기화
		yutArr[0] = new Yut(false);
		yutArr[1] = new Yut(false);
		yutArr[2] = new Yut(false);
		yutArr[3] = new Yut(true);	//백도
	}
	
	public void startGame(){
		System.out.println("---------------------시작---------------------");
		while(true){
			System.out.println("[1]게임 시작 [2]게임 규칙 [0]종료");
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc=  new Scanner(System.in);
				continue;
			}
			
			switch (input) {
			case 1:
				homeView();
				break;
			case 2:
				documentView();
				break;
			case 0:
				endProgram();
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

	private void endProgram(){
		System.out.println("게임을 종료합니다.");
		System.exit(0);
	}
	
	/**
	 * 게임 메인 화면
	 */
	private void homeView() {
		board.printBorad();
		while(true){
			System.out.println("내 말 : O\t\t남은 말의 수 : "+myMarker);
			System.out.println("상대 말 : X\t남은 말의 수 : "+comMarker);
			System.out.println("----------------------------------------------------------");
			System.out.println("[1]윷 던지기 [0]포기");
			while(true){
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
					//윷 던지기
					tossYut();
					//말 이동
					while(myDistance.size()>0) {
						System.out.println("----------------------------------------------------------");
						System.out.println("나의 이동가능 수치 : "+myDistance);
						System.out.println("남은 나의 말의 수 : "+myMarker);
						System.out.println("[1]새 말 놓기 [2]기존 말 이동");
						choiceMarker();
						board.printBorad();
					}
					nextTurn = true;
					break;
				case 0:
					System.out.println("\n패배..\n");
					endProgram();
				default:
					System.out.println("잘못된 입력입니다.");
				}
				if(nextTurn){
					break;
				}
			}
		}
	}

	private void tossYut() {
		while(true) {
			System.out.println("\n윷 던지기!\n");
			shuffle();
			int distance = 0;
			boolean backDo = false;
			for(int i=0; i<4; i++){
				int random = (int)(Math.random()*2);
				if(random==1){
					yutArr[i].printF();
				}else{
					if(yutArr[i].getBackDo()){
						yutArr[i].printBD();
						backDo = true;
					}else{
						yutArr[i].printB();
					}
					distance++;
				}
			}
			//백도이면
			if(distance==1 && backDo){
				distance = -1;
			}else if(distance==0) {
				distance = 5;
			}
			//수치만큼 저장
			myDistance.add(distance);
			//윷 또는 모 이면 한번더
			if(distance==4 || distance==5) {
				System.out.println("\n한번더!!\n");
			}else {
				break;
			}
		}
	}
	
	private void shuffle(){
		int random = (int)(Math.random()*4);
		for(int i=0; i<4; i++){
			Yut temp = yutArr[i];
			yutArr[i] = yutArr[random];
			yutArr[random] = temp;
		}
	}
	
	private void choiceMarker(){
		while(true) {
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
				//새 말 놓기 메서드
				if(myMarker==0) {
					System.out.println("더 이상 추가할 말이 없습니다.");
					continue;
				}else {
					newMarker();
					return;
				}
			case 2:
				//기존의 말 이동 메서드
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	private void newMarker() {
		System.out.println("몇 칸을 전진할지 선택하세요.");
		System.out.println("나의 이동가능 수치 : "+myDistance);
		while(true) {
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			//내 리스트에 있는지 확인
			if(myDistance.remove((Integer)input)) {
				//있으면 새 말을 추가하여 게임판에서 해당 거리만큼 이동
				Map<String, Object> params = new HashMap<>();
				params.put("marker", "O1");
				params.put("pos", input);
				board.setNewMarkerPosition(params);
				
				myMarker--;
				break;
			}else {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	private void documentView(){
		System.out.println("┌─────────────────────────────────────────────────────┐");
		System.out.println("│ -규칙-");
		System.out.println("│ 1. 말 4개가 모두 완주하면 승리한다.");
		System.out.println("│ 2. 코너에서 멈추면 무조건 가운데로 가야한다.(좌상단의 경우 제외)");
		System.out.println("│ 3. 윷이나 모로 상대방의 말을 먹어도 기회는 중복되지 않는다.");
		System.out.println("│ 4. 한바퀴를 돌아 집위치에 멈춘 경우 한 칸을 더 가야 완주로 간주한다.");
		System.out.println("│ 5. 도->백도->백도이면 한바퀴를 돈것으로 간주한다.");
		System.out.println("└─────────────────────────────────────────────────────┘");
		while(true) {
			System.out.println("[1]게임 시작 [0]뒤로");
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
				homeView();
				break;
			case 0:
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
}
