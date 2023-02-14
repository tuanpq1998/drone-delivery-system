package hust.tuanpq.finalproject.dronecontrol.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hust.tuanpq.finalproject.dronecontrol.utility.DateTimeHandler;

@Entity
@Table(name = "tbl_drone")
public class Drone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "drone_name")
	private String droneName;

	@Column(name = "serial_no")
	private String serialNo;

	@Column(name = "max_weight_package_delivery")
	private Long maxWeightPackageDelivery;

	@Column(name = "max_speed")
	private Long maxSpeed;

	@Column(name = "max_height")
	private Long maxHeight;
	
	@Column(name = "connect_type")
	private String connectType;
	
	@Column(name = "connect_ip")
	private String connectIp;
	
	@Column(name = "connect_port")
	private int connectPort;
	
	@Column(name = "is_emergency")
	private boolean isEmergency;
	
	@Column
	private String modelName;
	
	@Transient
	private boolean isServerActive;
	
	@Column(name="last_updated")
	private String lastUpdatedAt;
	
	@Column
	private Float batteryVoltage, batteryPercent, velocityHorizontal, velocityVertical;
	
	@Column(updatable = false)
	private String createdAt;
	
	private Double locationAltitude, locationLatitude, locationLongitude;
	
	@OneToOne
	@JoinColumn(name="active_mission_id", referencedColumnName = "id")
	@JsonIgnoreProperties("drone")
	private Mission activeMission;
	
	@Column(name="active_mission_id", insertable = false, updatable = false)
	private Integer activeMissionId;

	public Float getBatteryVoltage() {
		return batteryVoltage;
	}
	
	public boolean isEmergency() {
		return isEmergency;
	}

	public void setEmergency(boolean isEmergency) {
		this.isEmergency = isEmergency;
	}

	public Float getVelocityVertical() {
		return velocityVertical;
	}

	public void setVelocityVertical(Float velocityVertical) {
		this.velocityVertical = velocityVertical;
	}

	public Float getVelocityHorizontal() {
		return velocityHorizontal;
	}

	public void setVelocityHorizontal(Float velocityHorizontal) {
		this.velocityHorizontal = velocityHorizontal;
	}

	public void setBatteryVoltage(float batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public Float getBatteryPercent() {
		return batteryPercent;
	}

	public void setBatteryPercent(float batteryPercent) {
		this.batteryPercent = batteryPercent;
	}

	public Integer getActiveMissionId() {
		return activeMissionId;
	}

	public void setActiveMissionId(int activeMissionId) {
		this.activeMissionId = activeMissionId;
	}

	public String getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(String lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Double getLocationAltitude() {
		return locationAltitude;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void setLocationAltitude(Double locationAltitude) {
		this.locationAltitude = locationAltitude;
	}

	public Double getLocationLatitude() {
		return locationLatitude;
	}

	public void setLocationLatitude(Double locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public Double getLocationLongitude() {
		return locationLongitude;
	}

	public void setLocationLongitude(Double locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

	public Mission getActiveMission() {
		return activeMission;
	}

	public void setActiveMission(Mission activeMission) {
		this.activeMission = activeMission;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDroneName() {
		return droneName;
	}

	public void setDroneName(String droneName) {
		this.droneName = droneName;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Long getMaxWeightPackageDelivery() {
		return maxWeightPackageDelivery;
	}

	public void setMaxWeightPackageDelivery(Long maxWeightPackageDelivery) {
		this.maxWeightPackageDelivery = maxWeightPackageDelivery;
	}

	public Long getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Long maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Long getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(Long maxHeight) {
		this.maxHeight = maxHeight;
	}

	public Drone() {
		super();
	}

	public String getConnectType() {
		return connectType;
	}

	public void setConnectType(String connectType) {
		this.connectType = connectType;
	}

	public String getConnectIp() {
		return connectIp;
	}

	public void setConnectIp(String connectIp) {
		this.connectIp = connectIp;
	}

	public int getConnectPort() {
		return connectPort;
	}

	public void setConnectPort(int connectPort) {
		this.connectPort = connectPort;
	}

	public Drone(int id, String droneName, String serialNo, Long maxWeightPackageDelivery,
			Long maxSpeed, Long maxHeight, String type, String ip, int port) {
		super();
		this.id = id;
		this.droneName = droneName;
		this.serialNo = serialNo;
		this.maxWeightPackageDelivery = maxWeightPackageDelivery;
		this.maxSpeed = maxSpeed;
		this.maxHeight = maxHeight;
		this.connectType = type;
		this.connectIp = ip;
		this.connectPort = port;
	}

	public boolean isServerActive() {
		return isServerActive;
	}

	public void setServerActive(boolean isServerActive) {
		this.isServerActive = isServerActive;
	}
	
	@PreUpdate
	protected void onUpdate() {
		lastUpdatedAt = DateTimeHandler.datetimeToString(new Date());
	}
	
	@PrePersist
	protected void onCreate() {
		createdAt = DateTimeHandler.datetimeToString(new Date());
	}

}
