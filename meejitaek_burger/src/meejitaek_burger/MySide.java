package meejitaek_burger;

public class MySide extends Side{
	
	private int orderNum;

	public MySide(int orderNum, int code, String sdName, int price) {
		super(code, sdName, price);
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
		return "[주문번호 : " + orderNum + ", 사이드 : " + getSdName() + ", 가격 : " + getPrice() + "]";
	}
}
