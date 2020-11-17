package yutnori;

import java.util.Scanner;

public class Yutnori {
	private Board board = Board.getInstance();
	private Yut[] yutArr = new Yut[4];
	private Scanner sc = new Scanner(System.in);
	private boolean nextTurn = false;
	
	{
		yutArr[0] = new Yut(false);
		yutArr[1] = new Yut(false);
		yutArr[2] = new Yut(false);
		yutArr[3] = new Yut(true);	//백도
	}
	
	public void startGame(){
		System.out.println("---------------------시작---------------------");
		while(true){
			System.out.println("[1]게임시작 [0]종료");
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
		while(true){
			board.printBorad();
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
					int distance = tossYut();
					//말 이동
					move(distance);
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

	private int tossYut() {
		System.out.println("\n윷 던지기!\n");
		shuffle();
		int distance = 0;
		boolean backDo = false;
		for(int i=0; i<4; i++){
			int random = (int)(Math.random()*2);
			if(random==1){
				yutArr[i].printF();
				distance++;
			}else{
				if(yutArr[i].getBackDo()){
					yutArr[i].printBD();
					backDo = true;
				}else{
					yutArr[i].printB();
				}
			}
		}
		//백도이면
		if(distance==3 && backDo){
			distance = -1;
		}
		return distance;
	}
	
	private void shuffle(){
		int random = (int)(Math.random()*4);
		for(int i=0; i<4; i++){
			Yut temp = yutArr[i];
			yutArr[i] = yutArr[random];
			yutArr[random] = temp;
		}
	}
	
	private void move(int distance){
		switch (distance) {
		case 3:
			//도
			break;
		case 2:
			//개
			break;
		case 1:
			//걸
			break;
		case 0:
			//윷
			break;
		case 4:
			//모
			break;
		default:
			//백도
		}
	}
	
	
	
	
	
}
