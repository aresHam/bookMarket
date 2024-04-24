package meejitaek_burger;

public class MyBurger extends Burger{
	
	private int orderNum;

	public MyBurger(int orderNum, int code, String bgName, int price, String info) {
		super(code, bgName, price, info);
		this.orderNum = orderNum;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	
	@Override
	public String toString() {
		return "[주문번호 : " + this.orderNum + ", 버거 : " + super.getBgName() + ", 가격 : " + super.getPrice() + "]";
	}
}
