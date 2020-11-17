package yutnori;

public class Yut {
	private boolean backDo;
	
	public Yut(boolean backDo){
		this.backDo = backDo;
	}
	public boolean getBackDo(){
		return backDo;
	}
	
	public void printF(){
		System.out.println("┌─────────────────────┐ ");
		System.out.println("│    Χ     Χ     Χ    │ ");
		System.out.println("└─────────────────────┘ ");
	}
	
	public void printB(){
		System.out.println("┌─────────────────────┐ ");
		System.out.println("│                     │ ");
		System.out.println("└─────────────────────┘ ");
	}
	
	public void printBD(){
		System.out.println("┌─────────────────────┐ ");
		System.out.println("│          ■          │ ");
		System.out.println("└─────────────────────┘ ");
	}
	
	
}
