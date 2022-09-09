package hust.tuanpq.finalproject.dronecontrol.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private int id;
	
	@Column
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}

	public Role(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Role() {
		super();
	}
	
	
}
