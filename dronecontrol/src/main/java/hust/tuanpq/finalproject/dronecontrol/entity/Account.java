package hust.tuanpq.finalproject.dronecontrol.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="tbl_account")
public class Account{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private int id;
	
	@Column
	private String fullname, address, telephone, email, username;
	
	@Column
	@JsonIgnore
	private String password;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "role_id")
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", fullname=" + fullname + ", address=" + address + ", telephone=" + telephone
				+ ", email=" + email + ", username=" + username + ", password=" + password + "]";
	}

	public Account(int id, String fullname, String address, String telephone, String email, String username,
			String password) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.address = address;
		this.telephone = telephone;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public Account() {
		super();
	}

}
