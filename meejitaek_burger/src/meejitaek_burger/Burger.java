package meejitaek_burger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Burger implements Comparable<Burger>, Serializable{
	//멤버변수
	private int code;
	private String bgName;
	private int price;
	private String info;
	//생성자
	public Burger(int code, String bgName, int price, String info) {
		super();
		this.code = code;
		this.bgName = bgName;
		this.price = price;
		this.info = info;
	}
	//멤버함수
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getBgName() {
		return bgName;
	}

	public void setBgName(String bgName) {
		this.bgName = bgName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	//멤버함수 - 계산
	public void sizeUp() {						//큰 사이즈 주문
		this.price += 2000;
	}
	
	public void set() {							//세트메뉴 주문
		this.price = (int)(price * 1.3);
	}
	
	public void sizeUpNSet() {					//세트메뉴 + 큰사이즈 버거
		this.price = (int)(price * 1.3)+2000;
	}
	

	//오버라이드
	@Override
	public int compareTo(Burger o) {
		return this.bgName.compareToIgnoreCase(o.bgName);
	}
	@Override
	public int hashCode() {
		return Objects.hash(code);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Burger other = (Burger) obj;
		return code == other.code;
	}
	@Override
	public String toString() {
		return "[상품코드 : " + code + " | 버거 : " + bgName + " | 가격 : " + price + " | 상품정보 : " + info + "]";
	}
}
