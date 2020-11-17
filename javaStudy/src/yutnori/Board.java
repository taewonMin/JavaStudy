package yutnori;

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
	
	
	public void setNewMarkerPosition(Map<String, ?> params) {
		int pos = (int)params.get("pos");
		if(pos==-1) {
			position[19] = (String)params.get("marker");
		}else {
			position[pos] = (String)params.get("marker");
		}
	}
	
	public void printBorad(){
		System.out.println("┌──────┐    ┌────┐    ┌────┐    ┌────┐    ┌────┐    ┌──────┐");
		System.out.println("│  "+position[15]+"  ├────┤ "+position[14]+" ├────┤ "+position[13]+" ├────┤ "+position[12]+" ├────┤ "+position[11]+" ├────┤  "+position[10]+"  │");
		System.out.println("└──┬───┘    └────┘    └────┘    └────┘    └────┘    └───┬──┘");
		System.out.println("   │    \\\\\\                                      ///    │   ");
		System.out.println("   │      ┌────┐                            ┌────┐      │   ");
		System.out.println("┌──┴─┐    │ "+position[24]+" │                            │ "+position[25]+" │    ┌─┴──┐");
		System.out.println("│ "+position[16]+" │    └────┘                            └────┘    │ "+position[9]+" │");
		System.out.println("└──┬─┘          \\\\\\                      ///          └─┬──┘");
		System.out.println("   │              ┌────┐            ┌────┐              │   ");
		System.out.println("   │              │ "+position[23]+" │            │ "+position[26]+" │              │   ");
		System.out.println("┌──┴─┐            └────┘            └────┘            ┌─┴──┐");
		System.out.println("│ "+position[17]+" │                  \\\\\\      ///                  │ "+position[8]+" │");
		System.out.println("└──┬─┘                    ┌──────┐                    └─┬──┘");
		System.out.println("   │                      │      │                      │   ");
		System.out.println("   │                      │  "+position[22]+"  │                      │   ");
		System.out.println("┌──┴─┐                    └──────┘                    ┌─┴──┐");
		System.out.println("│ "+position[18]+" │                  ///      \\\\\\                  │ "+position[7]+" │");
		System.out.println("└──┬─┘            ┌────┐            ┌────┐            └─┬──┘");
		System.out.println("   │              │ "+position[27]+" │            │ "+position[21]+" │              │   ");
		System.out.println("   │              └────┘            └────┘              │   ");
		System.out.println("┌──┴─┐          ///                      \\\\\\          ┌─┴──┐");
		System.out.println("│ "+position[19]+" │    ┌────┐                            ┌────┐    │ "+position[6]+" │");
		System.out.println("└──┬─┘    │ "+position[28]+" │                            │ "+position[20]+" │    └─┬──┘");
		System.out.println("   │      └────┘                            └────┘      │   ");
		System.out.println("   │    ///                                      \\\\\\    │   ");
		System.out.println("┌──┴───┐    ┌────┐    ┌────┐    ┌────┐    ┌────┐    ┌───┴──┐");
		System.out.println("│  "+position[0]+"  ├────┤ "+position[1]+" ├────┤ "+position[2]+" ├────┤ "+position[3]+" ├────┤ "+position[4]+" ├────┤  "+position[5]+"  │");
		System.out.println("└──────┘    └────┘    └────┘    └────┘    └────┘    └──────┘");
	}
}
