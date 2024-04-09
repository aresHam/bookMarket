package kh.edu.bookmarket.member;

public class User extends Customer{
	public User(String name, int phone) {
		super(name, phone);
	}
	
	public User(String name, int phone, String address) {
		super(name, phone, address);
	}
}
