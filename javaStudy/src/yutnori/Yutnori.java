package yutnori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Yutnori {
	private Board board = Board.getInstance();
	private Yut[] yutArr = new Yut[4];	//윷 세트(4개)
	
	private User me = new User(4,"O");
	private User com = new User(4,"X");
	private User player = null;
	private User enemy = null;
	private List<Integer> myDistance = new ArrayList<Integer>(); //윷을 던져 나온 눈 저장 배열
	private boolean changeTurn = false;	//player차례
	
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
				System.out.println("플레이어1 이름을 입력해주세요.");
				me.setName(sc.next());
				System.out.println("플레이어2 이름을 입력해주세요.");
				com.setName(sc.next());
				player = me;
				enemy = com;
				homeView();
				System.out.println("\n게임을 종료합니다.");
				return;
			case 2:
				documentView();
				break;
			case 0:
				System.out.println("\n게임을 종료합니다.");
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	private void changePlayer(){
		User temp = player;
		player = enemy;
		enemy = temp; 
	}

	/**
	 * 게임 메인 화면
	 */
	private void homeView() {
		board.printBorad();
		while(true){
			if(changeTurn){
				changePlayer();
			}
			System.out.println(player.getName()+"님 차례입니다.\n");
			System.out.println("내 말 : "+player.marker+"\t\t남은 말의 수 : "+player.markerNum+"\t승점 : "+player.finMarker);
			System.out.println("상대 말 : "+enemy.marker+"\t남은 말의 수 : "+enemy.markerNum+"\t승점 : "+enemy.finMarker);
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
					changeTurn = true;
					//윷 던지기
					tossYut();
					//말 이동
					while(myDistance.size()>0) {
						System.out.println("----------------------------------------------------------");
						System.out.println("이동가능한 거리 : "+myDistance);
						System.out.println("남은 나의 말의 수 : "+player.markerNum);
						System.out.println("[1]새 말 놓기 [2]기존 말 이동");
						if(!choiceMarker()){	//말 선택 취소
							continue;
						}
						if(player.finMarker==4){
							System.out.println("\n승리!!!\n");
							return;
						}
						board.printBorad();
					}
					break;
				case 0:
					System.out.println("\n패배..\n");
					return;
				default:
					System.out.println("잘못된 입력입니다.");
					continue;
				}
				break;
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
	
	private boolean choiceMarker(){
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
				if(player.markerNum==0) {
					System.out.println("더 이상 추가할 말이 없습니다.");
					continue;
				}else {
					//새 말 놓기 메서드
					return newMarker();
				}
			case 2:
				if(player.markerNum==4){
					System.out.println("놓여있는 나의 말이 없습니다.");
					continue;
				}else{
					//기존의 말 이동 메서드
					orgMarker();
				}
				return true;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	private boolean newMarker() {
		boolean result = moveMarker(-1);
		if(result){
			player.markerNum--;
		}
		return result;
	}
	
	private void orgMarker(){
		while(true){
			board.printBorad();
			List<Integer> markerList = board.getMarkerList(player.marker);
			System.out.println("놓여있는 나의 말의 수 : " + markerList.size());
			System.out.println("내 말의 위치 : " + markerList);
			System.out.println("움직일 말의 현재 위치를 선택하세요.");
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			if(markerList.contains((Integer)input)){
				if(moveMarker(input)){
					break;
				}
			}else{
				System.out.println("내 말의 위치가 아닙니다.");
			}
		}
	}
	
	private boolean moveMarker(int nowPos) {
		boolean result = true;
		System.out.println("몇 칸을 전진할지 선택하세요.");
		System.out.println("이동가능한 거리 : "+myDistance);
		while(true) {
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			//내 이동수치 리스트에 있는지 확인
			if(myDistance.contains(input)) {
				//이동할 말 조회하기
				String moveMarker = player.marker+1;
				if(nowPos!=-1){
					Map<String, Object> myMarker = new HashMap<>();
					myMarker.put("pos", nowPos);
					myMarker.put("nowPos", 0);
					moveMarker = board.getMarker(myMarker);
				}					
				
				Map<String, Object> params = new HashMap<>();
				params.put("pos", input);	//이동 거리
				params.put("nowPos", nowPos);	//현재 위치
				params.put("marker", moveMarker);	//나의 말
				
				//이동할 위치에 다른 말이 있는지 확인
				String marker = board.getMarker(params);	// 이동할 위치
				if(!marker.equals("  ")){
					System.out.println("빈여백 두자리와 같지 않아?!?"+marker);
					//내 말이면
					if(marker.charAt(0)==player.marker.charAt(0)){
						System.out.println("이미 나의 말이 존재합니다. 업고 가시겠습니까?(Y/N)");
						String choice = sc.next().toUpperCase();
						if("Y".equals(choice)){
							marker = String.valueOf(marker.charAt(0)) + (Integer.parseInt(marker.substring(1))+1);
							params.put("marker", marker);
						}else{
							result = false;
						}
					}
					//상대말이면
					else{	
						System.out.println("\n상대말을 잡았습니다!\n");
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						//한번더 하기
						if(input!=5 && input!=4){	//모나 윷이면 한번더 안함
							changeTurn = false;
						}else{
							System.out.println("모 또는 윷으로 잡았을 경우에는 추가 기회를 얻을 수 없습니다.");
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						//상대말 되돌리기
						enemy.markerNum += Integer.parseInt(marker.substring(1));
					}
				}
				//게임판에서 해당 거리만큼 이동
				if(result){
					myDistance.remove((Integer)input);
					player.finMarker += board.setMarkerPos(params);
				}
				return result;
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
		System.out.println("│ 6. 새로운 말을 백도로 움직이면 앞으로 한칸 움직인다.");
		System.out.println("└─────────────────────────────────────────────────────┘");
		while(true) {
			System.out.println("[0]뒤로");
			int input = 0;
			try {
				input = sc.nextInt();
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요.");
				sc = new Scanner(System.in);
				continue;
			}
			switch (input) {
			case 0:
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
}

class User{
	int markerNum;
	int finMarker = 0;
	String marker;
	String name;
	
	User(int markerNum, String marker){
		this.markerNum = markerNum;
		this.marker = marker;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}