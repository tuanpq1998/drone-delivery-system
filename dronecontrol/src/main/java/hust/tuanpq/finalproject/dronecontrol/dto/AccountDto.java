package hust.tuanpq.finalproject.dronecontrol.dto;

import hust.tuanpq.finalproject.dronecontrol.entity.Account;

public class AccountDto {
	private int id;
	private String fullname, address, telephone, email, username, role;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public AccountDto(int id, String fullname, String address, String telephone, String email, String username, String role) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.address = address;
		this.telephone = telephone;
		this.email = email;
		this.username = username;
		this.role = role;
	}
	public AccountDto(Account account) {
		this.id = account.getId();
		this.fullname = account.getFullname();
		this.address = account.getAddress();
		this.telephone = account.getTelephone();
		this.email = account.getEmail();
		this.username = account.getUsername();
		this.role = account.getRole().getName().toUpperCase();
	}
	
}
