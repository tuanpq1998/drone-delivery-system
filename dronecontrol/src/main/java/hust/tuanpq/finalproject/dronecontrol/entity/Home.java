package hust.tuanpq.finalproject.dronecontrol.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_home")
public class Home {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private int id;
	
	private Double altitude, latitude, longitude;
	
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Home(int id, Double altitude, Double latitude, Double longitude) {
		super();
		this.id = id;
		this.altitude = altitude;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Home() {
		super();
	}

	@Override
	public String toString() {
		return "Home [id=" + id + ", altitude=" + altitude + ", latitude=" + latitude + ", longitude=" + longitude
				+ "]";
	}

	
}
