package hust.tuanpq.finalproject.dronecontrol.model;

public class Location {

	private double altitude, latitude, longitude;

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Location [altitude=" + altitude + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	public Location(double altitude, double latitude, double longitude) {
		super();
		this.altitude = altitude;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Location() {
		super();
	}
	
	
}
