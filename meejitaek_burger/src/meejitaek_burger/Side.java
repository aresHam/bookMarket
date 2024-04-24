package meejitaek_burger;

import java.io.Serializable;
import java.util.Objects;

public class Side implements Comparable<Side>, Serializable{
	//멤버변수
	private int code;
	private String sdName;
	private int price;
	
	//생성자
	public Side(int code, String sdName, int price) {
		super();
		this.code = code;
		this.sdName = sdName;
		this.price = price;
	}
	//멤버함수
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSdName() {
		return sdName;
	}

	public void setSdName(String bgName) {
		this.sdName = bgName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


	//오버라이드
	@Override
	public int compareTo(Side o) {
		return this.sdName.compareToIgnoreCase(o.sdName);
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
		Side other = (Side) obj;
		return code == other.code;
	}
	@Override
	public String toString() {
		return "[상품코드 : " + code + " | 상품명 : " + sdName + " | 가격 : " + price + "]";
	}
}
