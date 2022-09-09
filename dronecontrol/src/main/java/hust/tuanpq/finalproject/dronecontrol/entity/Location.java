package hust.tuanpq.finalproject.dronecontrol.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import hust.tuanpq.finalproject.dronecontrol.utility.DateTimeHandler;

//@Entity
//@Table(name="tbl_location")
public class Location {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name="id")
//	private int id;
//	
//	@Column
//	private double altitude, latitude, longitude;
//	
//	@Column(name="update_at")
//	private String updateAt;
//	
//	@OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
//	@JoinColumn(name="drone_id", referencedColumnName = "id")
//	private Drone drone;
//	
//	public Drone getDrone() {
//		return drone;
//	}
//
//	public void setDrone(Drone drone) {
//		this.drone = drone;
//	}
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public double getAltitude() {
//		return altitude;
//	}
//
//	public void setAltitude(double altitude) {
//		this.altitude = altitude;
//	}
//
//	public double getLatitude() {
//		return latitude;
//	}
//
//	public void setLatitude(double latitude) {
//		this.latitude = latitude;
//	}
//
//	public double getLongitude() {
//		return longitude;
//	}
//
//	public void setLongitude(double longitude) {
//		this.longitude = longitude;
//	}
//
//	public String getUpdateAt() {
//		return updateAt;
//	}
//
//	public void setUpdateAt(String updateAt) {
//		this.updateAt = updateAt;
//	}
//
//	public Location(int id, double altitude, double latitude, double longitude) {
//		super();
//		this.id = id;
//		this.altitude = altitude;
//		this.latitude = latitude;
//		this.longitude = longitude;
//	}
//
//	public Location() {
//		super();
//	}
//
//	@Override
//	public String toString() {
//		return "Location [id=" + id + ", altitude=" + altitude + ", latitude=" + latitude + ", longitude=" + longitude
//				+ ", updateAt=" + updateAt + "]";
//	}
//	
//	@PreUpdate
//	protected void onUpdate() {
//		updateAt = DateTimeHandler.datetimeToString(new Date());
//	}
//	
//	
}
