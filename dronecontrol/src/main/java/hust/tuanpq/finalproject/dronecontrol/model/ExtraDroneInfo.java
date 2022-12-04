package hust.tuanpq.finalproject.dronecontrol.model;

public class ExtraDroneInfo {

	private double altitude, latitude, longitude;
	
	private float batteryVoltage, batteryPercent, velocityHorizontal, velocityVertical;

	public float getBatteryVoltage() {
		return batteryVoltage;
	}

	public void setBatteryVoltage(float batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public float getBatteryPercent() {
		return batteryPercent;
	}

	public void setBatteryPercent(float batteryPercent) {
		this.batteryPercent = batteryPercent;
	}

	public float getVelocityHorizontal() {
		return velocityHorizontal;
	}

	public void setVelocityHorizontal(float velocityHorizontal) {
		this.velocityHorizontal = velocityHorizontal;
	}

	public float getVelocityVertical() {
		return velocityVertical;
	}

	public void setVelocityVertical(float velocityVertical) {
		this.velocityVertical = velocityVertical;
	}

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
		return "ExtraDroneInfo [altitude=" + altitude + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", batteryVoltage=" + batteryVoltage + ", batteryPercent=" + batteryPercent + ", velocityHorizontal="
				+ velocityHorizontal + ", velocityVertical=" + velocityVertical + "]";
	}

	public ExtraDroneInfo(double altitude, double latitude, double longitude, float batteryVoltage,
			float batteryPercent, float velocityHorizontal, float velocityVertical) {
		super();
		this.altitude = altitude;
		this.latitude = latitude;
		this.longitude = longitude;
		this.batteryVoltage = batteryVoltage;
		this.batteryPercent = batteryPercent;
		this.velocityHorizontal = velocityHorizontal;
		this.velocityVertical = velocityVertical;
	}

	public ExtraDroneInfo() {
		super();
	}
	
	
}
