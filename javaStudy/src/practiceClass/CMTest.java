package practiceClass;

import java.util.Vector;

public class CMTest {
	public static void main(String[] args) {
      //dp
      NoteBook nb = new NoteBook("Mac", 250);
      
      PS5 p5 = new PS5("SONY", 50);
      
      Styler st = new Styler("LG", 150);
      
      Buyer b = new Buyer("할로윈", 1000);
      Buyer b1 = new Buyer("안녕", 10000);
      Buyer b2 = new Buyer("하세용", 5000);
      
      Customer c = new Customer();
      c.getBuyer();
      
      b.buy(nb);
      b.buy(nb);
      b.buy(st);
      b.buy(st);
      b.buy(p5);
      b.buy(nb);
      b1.buy(nb);
      b1.buy(st);
      b1.buy(nb);
      b2.buy(p5);
      b2.buy(st);
      
      c.getBuyer();
      c.deleteBuyer(b2);
      c.getBuyer();
      
      b.summary();
      
      b.refund(nb);
      c.getBuyer();
      
      b.summary();
   }
}

class Product{
   static final int PRODUCT_QTY = 3;		//초기 제품수량(객체 생성시)
   static Vector product = new Vector();	//제품 목록
   static Vector stock = new Vector();		//제품 수량
   
   String name;
   int price;
   int mileage;
   
   public Product(String name, int price) {
      this.name = name;
      this.price = price;
      mileage = price/100;
      stock.add(PRODUCT_QTY);
   }
}

class NoteBook extends Product{
   NoteBook(String name, int price){
      super(name, price);
      product.add(this);
   }

   @Override
   public String toString() {
      return "NoteBook("+name+")";
   }
}


class PS5 extends Product{
   PS5(String name, int price){
      super(name, price);
      product.add(this);
   }

   @Override
   public String toString() {
      return "PS5("+name+")";
   }
}


class Styler extends Product{
   Styler(String name, int price){
      super(name, price);
      product.add(this);
   }

   @Override
   public String toString() {
      return "Styler("+name+")";
   }
}

class Buyer extends Customer{
   String name;
   int money;
   int mileage;
   
   Vector items = new Vector();		// 장바구니 제품목록
   Vector itemsNum = new Vector();	// 장바구니 제품수량
   
   public Buyer(String name, int money){
      this.name = name;
      this.money = money;
      createBuyer(this);
   }
   
//구매   
   void buy(Product p){
      if(money < p.price){
         System.out.println(name+"님의 잔액이 부족합니다.");
         return;
      }
      //수량 관리
      if(prodManage(p,1)) {	// 재고 체크
    	  money -= p.price;
    	  mileage += p.mileage;
    	  
    	  System.out.println(name+"님이 "+p+"를 구매하셨습니다.");
      }
   }   
   
// 영수증 출력
   void summary(){
	      System.out.println("\n\t영\t수\t증\n");
	      System.out.println("구매목록");
	      int sum = 0;
	      for(int i=0; i<items.size(); i++){
	         Product p = (Product)items.get(i);
	         System.out.println("\t"+p+"*"+(int)itemsNum.get(i)+"\t"+p.price*(int)itemsNum.get(i)+"만원");
	         sum+=p.price*(int)itemsNum.get(i);
	      }
	      System.out.println("\n\t합계\t"+sum+"만원\n");
	      System.out.println(name+" 고객님의 남은 돈은 "+money+"만원 이고 사용할 수 있는\n마일리지는 "+mileage+"만원 입니다.\n고객님 오늘도 이용해주셔서 감사합니다.\n");
   }
   
//환불
   void refund(Product p){
	   if(items.isEmpty()){   // 구매물품이 없는 경우
		   System.out.println("구매물품이 없습니다.");
	   }
	   if(items.contains(p)){	// 내가 산 물건이면
		   prodManage(p,0);
    	   money += p.price;
       }else{
     	   System.out.println("너의 물건이 아닙니다.");
       }
   }
   
   //1. 추가문제 - 클래스재구성
   //제품의 수량을 관리할 수 있도록 만들어주세요.
   /**
    * 재고 관리 메서드
    * @param p
    * @param buyOrRefund	buy: 1 / refund: not 1
    * @return
    */
   boolean prodManage(Product p, int buyOrRefund) {
	   int prodIdx = Product.product.indexOf(p);
	   int itemIdx = items.indexOf(p);
	   int stock = (int)Product.stock.get(prodIdx);	//재고 수량
	   //buy
	   if(buyOrRefund==1) {
		   if(stock>0) {	//재고가 있으면
			   if(itemIdx==-1) {   // 장바구니에 없는 물건이면
				   items.add(p);
				   itemsNum.add(1);
			   }else {
				   itemsNum.set(itemIdx, (int)itemsNum.get(itemIdx)+1);
			   }
			   Product.stock.set(prodIdx, --stock);
		   }else {
			   System.out.println(p+"의 재고가 없습니다.");
			   System.out.println("------------------재고목록------------------");
			   System.out.println(Product.product);
			   System.out.println("수량: "+Product.stock);
			   System.out.println("------------------------------------------\n");
			   return false;
		   }
	   }
	   //refund
	   else {
		   if((int)itemsNum.get(itemIdx)==1) {	// 장바구니 수량이 1개 남은 경우
			   items.remove(p);
			   itemsNum.remove(itemIdx);
    	   }else {
    		   itemsNum.set(itemIdx, (int)itemsNum.get(itemIdx)-1);
    	   }
		   Product.stock.set(prodIdx, ++stock);
		   System.out.println("환불되었습니다.");
		   System.out.println("------------------재고목록------------------");
		   System.out.println(Product.product);
		   System.out.println("수량: "+Product.stock);
		   System.out.println("------------------------------------------\n");
	   }
	   return true;
   }
}

//2. 추가문제   누가 뭐를 몇개 삿는지 저장 - 새로운 클래스
// C R U D
// 사람들을 관리할 수 있도록 만들어주세요.
class Customer{
	private static Vector ctm = new Vector();
	
	void createBuyer(Buyer b){	// 고객 추가
		ctm.add(b);
	}
	
	void getBuyer() {	// 고객 정보 반환
		String str = "\n고객정보\nname\tmoney\tmileage\t\titems\t\titem_qty\n";
		for(int i=0; i<ctm.size(); i++) {
			Buyer b = (Buyer)ctm.get(i);
			str += b.name + "\t" + b.money + "\t" + b.mileage + "\t\t";
			for(int j=0; j<b.items.size(); j++) {
				str += b.items.get(j)+"\t"+b.itemsNum.get(j);
				if(j<b.items.size()-1)
					str+="\n\t\t\t\t";
			}
			str += "\n";
		}
		System.out.println(str);
	}
	
	boolean deleteBuyer(Buyer b) {	// 고객 삭제
		return ctm.remove(b);
	}
}

