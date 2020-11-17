package yutnori;

public class Board {
	private static Board board = null;
	
	private Board(){
		
	}
	
	public static Board getInstance(){
		if(board==null){
			board = new Board();
		}
		return board;
	}
	
	public void printBorad(){
		System.out.println("┌─────┐    ┌───┐    ┌───┐    ┌───┐    ┌───┐    ┌─────┐");
		System.out.println("│     ├────┤   ├────┤   ├────┤   ├────┤   ├────┤     │");
		System.out.println("└──┬──┘    └───┘    └───┘    └───┘    └───┘    └──┬──┘");
		System.out.println("   │   \\\\                                    //   │   ");
		System.out.println("   │     ┌────┐                        ┌────┐     │   ");
		System.out.println(" ┌─┴─┐   │    │                        │    │   ┌─┴─┐ ");
		System.out.println(" │   │   └────┘                        └────┘   │   │ ");
		System.out.println(" └─┬─┘        \\\\                      //        └─┬─┘ ");
		System.out.println("   │            ┌────┐          ┌────┐            │   ");
		System.out.println("   │            │    │          │    │            │   ");
		System.out.println(" ┌─┴─┐          └────┘          └────┘          ┌─┴─┐ ");
		System.out.println(" │   │               \\\\        //               │   │ ");
		System.out.println(" └─┬─┘                 ┌──────┐                 └─┬─┘ ");
		System.out.println("   │                   │      │                   │   ");
		System.out.println("   │                   │      │                   │   ");
		System.out.println(" ┌─┴─┐                 └──────┘                 ┌─┴─┐ ");
		System.out.println(" │   │               //        \\\\               │   │ ");
		System.out.println(" └─┬─┘          ┌────┐          ┌────┐          └─┬─┘ ");
		System.out.println("   │            │    │          │    │            │   ");
		System.out.println("   │            └────┘          └────┘            │   ");
		System.out.println(" ┌─┴─┐        //                      \\\\        ┌─┴─┐ ");
		System.out.println(" │   │   ┌────┐                        ┌────┐   │   │ ");
		System.out.println(" └─┬─┘   │    │                        │    │   └─┬─┘ ");
		System.out.println("   │     └────┘                        └────┘     │   ");
		System.out.println("   │   //                                    \\\\   │   ");
		System.out.println("┌──┴──┐    ┌───┐    ┌───┐    ┌───┐    ┌───┐    ┌──┴──┐");
		System.out.println("│     ├────┤   ├────┤   ├────┤   ├────┤   ├────┤     │");
		System.out.println("└─────┘    └───┘    └───┘    └───┘    └───┘    └─────┘");
	}
}
