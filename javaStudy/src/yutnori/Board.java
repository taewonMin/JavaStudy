package yutnori;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {
	private static Board board = null;
	private static String[] position = new String[31];
	
	private Board(){
		
	}
	
	public static Board getInstance(){
		if(board==null){
			board = new Board();
		}
		return board;
	}
	
	{
		for(int i=0; i<position.length; i++) {
			position[i] = "  ";
		}
	}
	
	/**
	 * 말 이동 위치 설정
	 * @param params
	 * @return 완주하면 완주한 말의 수 반환, 아니면 0
	 */
	public int setMarkerPos(Map<String, Object> params) {
		int finish = 0;
		int pos = (int)params.get("pos");
		int nowPos = (int)params.get("nowPos");
		String marker = (String)params.get("marker");
		if(pos == -1){//뒷도
			if(nowPos==-1){	//새로운 말이면
				pos = 1;
			}else if(nowPos==0){
				finish = Integer.parseInt(marker.substring(1));
				pos = 19;
			}else if(nowPos==20){
				pos = 5;
			}else if(nowPos==25){
				pos = 10;
			}else if(nowPos==28){
				pos = 22;
			}else{	//둘레
				pos += nowPos;
			}
		}else{
			if(nowPos!=-1){	//새로운 말이 아니면
				if(nowPos==0){	// 시작점이면
					finish = Integer.parseInt(marker.substring(1));
				}else if(nowPos==5){	//5번 위치에서 시작
					pos += 19;
				}else if(nowPos==10){ //10번 위치에서 시작
					pos += 24;
					if(pos==27){	//가운데(22)
						pos = 22;
					}
				}else if(nowPos==22){ //22번 위치에서 시작
					pos += 27;
					if(pos==30){	//22->시작점
						pos = 0;
					}else if(pos>30){	//22->완주
						finish = Integer.parseInt(marker.substring(1));
					}
				}else if(19<nowPos && nowPos<25 && nowPos+pos>24){ //24->15로 가는 경우
					pos += nowPos-10;
				}else if(24<nowPos && nowPos<30){	//25~29
					pos += nowPos;
					if(pos==27){
						pos = 22;
					}else if(pos==30){
						pos = 0;
					}else if(pos>30){
						finish = Integer.parseInt(marker.substring(1));
					}
				}else if(14<nowPos && nowPos<20){	//15~19
					pos += nowPos;
					if(pos==20){
						pos = 0;
					}else if(pos>20){
						finish = Integer.parseInt(marker.substring(1));
					}
				}else{	// 둘레
					pos += nowPos;
					System.out.println(pos);
				}
			}
		}
		if(finish==0){	// 완주가 아니면 위치이동
			position[pos] = marker;
		}
		if(nowPos!=-1){	// 새로운 말이 아니면
			position[nowPos] = "  ";
		}
		return finish;
	}
	
	/**
	 * 해당 위치에 있는 값 찾기
	 * @param params "pos":목적지의 위치, "nowPos":현재 위치
	 * @return 목적지에 저장된 값
	 */
	public String getMarker(Map<String, Object> params){
		int pos = (int)params.get("pos");
		int nowPos = (int)params.get("nowPos");
		int dtn = pos;
		//백도이면
		if(dtn == 0){	//이동할 말의 현재 위치
			dtn = nowPos;
		}else if(dtn == -1){
			if(nowPos==-1){	//새로운 말이면
				dtn = 1;
			}else if(nowPos==0){
				dtn = 19;
			}else if(nowPos==20){
				dtn = 5;
			}else if(nowPos==25){
				dtn = 10;
			}else if(nowPos==28){
				dtn = 22;
			}else{	//둘레
				dtn += nowPos;
			}
		}else{
			if(nowPos==0){	// 시작점이면
				return "  ";
			}else if(nowPos==-1){	// 새말 출발
			}else if(nowPos==5){	//5번 위치에서 시작
				dtn += 19;
			}else if(nowPos==10){ //10번 위치에서 시작
				dtn += 24;
				if(dtn==27){	//가운데(22)
					dtn = 22;
				}
			}else if(nowPos==22){ //22번 위치에서 시작
				dtn += 27;
				if(dtn==30){	//22->시작점
					dtn = 0;
				}else if(dtn>30){	//22->완주
					return "  ";
				}
			}else if(19<nowPos && nowPos<25 && nowPos+dtn>24){ //24->15로 가는 경우
				dtn += nowPos-10;
			}else if(24<nowPos && nowPos<30){	//25~29
				dtn += nowPos;
				if(dtn==27){
					dtn = 22;
				}else if(dtn==30){
					dtn = 0;
				}else if(dtn>30){
					return "  ";
				}
			}else if(14<nowPos && nowPos<20){	//15~19
				dtn += nowPos;
				if(dtn==20){
					dtn = 0;
				}else if(dtn>20){
					return "  ";
				}
			}else{	// 둘레
				dtn += nowPos;
			}
		}
		return position[dtn];
	}
	
	
	
	/**
	 * 유저의 말 위치 찾기 메서드
	 * @param marker 찾을 유저의 말 이름
	 * @return 말 위치를 리스트로 반환
	 */
	public List<Integer> getMarkerList(String marker){
		List<Integer> markerPosList = new ArrayList<>();
		for(int i=0; i<position.length; i++){
			if(position[i].substring(0,1).equals(marker)){
				markerPosList.add(i);
			}
		}
		return markerPosList;
	}
	
	public void printBorad(){
		System.out.println("  15          14        13        12        11          10  ");
		System.out.println("┌──────┐    ┌────┐    ┌────┐    ┌────┐    ┌────┐    ┌──────┐");
		System.out.println("│  "+position[15]+"  ├────┤ "+position[14]+" ├────┤ "+position[13]+" ├────┤ "+position[12]+" ├────┤ "+position[11]+" ├────┤  "+position[10]+"  │");
		System.out.println("└──┬───┘    └────┘    └────┘    └────┘    └────┘    └───┬──┘");
		System.out.println("   │    \\\\\\ 24                                25 ///    │   ");
		System.out.println("16 │      ┌────┐                            ┌────┐      │ 9 ");
		System.out.println("┌──┴─┐    │ "+position[24]+" │                            │ "+position[25]+" │    ┌─┴──┐");
		System.out.println("│ "+position[16]+" │    └────┘                            └────┘    │ "+position[9]+" │");
		System.out.println("└──┬─┘          \\\\\\ 23                26 ///          └─┬──┘");
		System.out.println("   │              ┌────┐            ┌────┐              │   ");
		System.out.println("17 │              │ "+position[23]+" │            │ "+position[26]+" │              │ 8 ");
		System.out.println("┌──┴─┐            └────┘            └────┘            ┌─┴──┐");
		System.out.println("│ "+position[17]+" │                  \\\\\\  22  ///                  │ "+position[8]+" │");
		System.out.println("└──┬─┘                    ┌──────┐                    └─┬──┘");
		System.out.println("   │                      │      │                      │   ");
		System.out.println("18 │                      │  "+position[22]+"  │                      │ 7 ");
		System.out.println("┌──┴─┐                    └──────┘                    ┌─┴──┐");
		System.out.println("│ "+position[18]+" │              28  ///      \\\\\\  21              │ "+position[7]+" │");
		System.out.println("└──┬─┘            ┌────┐            ┌────┐            └─┬──┘");
		System.out.println("   │              │ "+position[28]+" │            │ "+position[21]+" │              │   ");
		System.out.println("19 │              └────┘            └────┘              │ 6 ");
		System.out.println("┌──┴─┐      29  ///                      \\\\\\  20      ┌─┴──┐");
		System.out.println("│ "+position[19]+" │    ┌────┐                            ┌────┐    │ "+position[6]+" │");
		System.out.println("└──┬─┘    │ "+position[29]+" │                            │ "+position[20]+" │    └─┬──┘");
		System.out.println("   │      └────┘                            └────┘      │   ");
		System.out.println(" 0 │    ///   1         2         3         4    \\\\\\    │ 5 ");
		System.out.println("┌──┴───┐    ┌────┐    ┌────┐    ┌────┐    ┌────┐    ┌───┴──┐");
		System.out.println("│  "+position[0]+"  ├────┤ "+position[1]+" ├────┤ "+position[2]+" ├────┤ "+position[3]+" ├────┤ "+position[4]+" ├────┤  "+position[5]+"  │");
		System.out.println("└──────┘    └────┘    └────┘    └────┘    └────┘    └──────┘");
	}
}
