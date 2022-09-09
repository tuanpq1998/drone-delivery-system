package hust.tuanpq.finalproject.dronecontrol.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hust.tuanpq.finalproject.dronecontrol.utility.DateTimeHandler;

@Entity
@Table(name="tbl_mission")
public class Mission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private int id;
	
	@Column
	private String packageName, size, weight, price, receiverName, receiverAddress, receiverTel, note, status, logs;
	
	@Column
	@JsonIgnore
	private String password;
	
	@Transient
	private String rawpassword;
	
	@Column
	private Double receiverLocationLatitude, receiverLocationLongitude, senderLocationLatitude, senderLocationLongitude;
	
	@Column(updatable = false)
	private String createdAt;
	
	@Column
	private String missionIdentifier;
	
	@Column
	private String senderAddress;
	
	@Column
	private String updatedAt;
	
	@Column
	private float speedMs, holdingTime;
	
	@Column
	private boolean isStarted, isFinished;
	
	@Column
	private boolean trackingStart;
	

	@Column
	private Double flyingAltitude, receiverLocationAltitude, senderLocationAltitude;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "drone_id", nullable = true)
	@JsonIgnoreProperties("activeMission")
	private Drone drone;
	
	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Account seller;
	
	public Account getSeller() {
		return seller;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getFlyingAltitude() {
		return flyingAltitude;
	}

	public String getLogs() {
		return logs;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}

	public void setFlyingAltitude(Double flyingAltitude) {
		this.flyingAltitude = flyingAltitude;
	}

	public Double getReceiverLocationAltitude() {
		return receiverLocationAltitude;
	}

	public String getRawpassword() {
		return rawpassword;
	}

	public void setRawpassword(String rawpassword) {
		this.rawpassword = rawpassword;
	}

	public void setReceiverLocationAltitude(Double receiverLocationAltitude) {
		this.receiverLocationAltitude = receiverLocationAltitude;
	}

	public Double getSenderLocationAltitude() {
		return senderLocationAltitude;
	}

	public void setSenderLocationAltitude(Double senderLocationAltitude) {
		this.senderLocationAltitude = senderLocationAltitude;
	}

	public void setSeller(Account seller) {
		this.seller = seller;
	}

	public float getSpeedMs() {
		return speedMs;
	}

	public void setSpeedMs(float speedMs) {
		this.speedMs = speedMs;
	}

	public float getHoldingTime() {
		return holdingTime;
	}

	public void setHoldingTime(float holdingTime) {
		this.holdingTime = holdingTime;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public boolean isTrackingStart() {
		return trackingStart;
	}

	public void setTrackingStart(boolean trackingStart) {
		this.trackingStart = trackingStart;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getMissionIdentifier() {
		return missionIdentifier;
	}

	public void setMissionIdentifier(String missionIdentifier) {
		this.missionIdentifier = missionIdentifier;
	}

	public Drone getDrone() {
		return drone;
	}

	public void setDrone(Drone drone) {
		this.drone = drone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverTel() {
		return receiverTel;
	}

	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Double getReceiverLocationLatitude() {
		return receiverLocationLatitude;
	}

	public void setReceiverLocationLatitude(Double receiverLocationLatitude) {
		this.receiverLocationLatitude = receiverLocationLatitude;
	}

	public Double getReceiverLocationLongitude() {
		return receiverLocationLongitude;
	}

	public void setReceiverLocationLongitude(Double receiverLocationLongitude) {
		this.receiverLocationLongitude = receiverLocationLongitude;
	}

	public Double getSenderLocationLatitude() {
		return senderLocationLatitude;
	}

	public void setSenderLocationLatitude(Double senderLocationLatitude) {
		this.senderLocationLatitude = senderLocationLatitude;
	}

	public Double getSenderLocationLongitude() {
		return senderLocationLongitude;
	}

	public void setSenderLocationLongitude(Double senderLocationLongitude) {
		this.senderLocationLongitude = senderLocationLongitude;
	}

	public Mission(int id, String packgeName, String size, String weight, String price, String receiverName,
			String receiverAddress, String receiverTel, String note, String status) {
		super();
		this.id = id;
		this.packageName = packgeName;
		this.size = size;
		this.weight = weight;
		this.price = price;
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;
		this.receiverTel = receiverTel;
		this.note = note;
		this.status = status;
	}

	public Mission() {
		super();
	}

	@Override
	public String toString() {
		return "Mission [id=" + id + ", packageName=" + packageName + ", size=" + size + ", weight=" + weight + ", price="
				+ price + ", receiverName=" + receiverName + ", receiverAddress=" + receiverAddress + ", receiverTel="
				+ receiverTel + ", note=" + note + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = DateTimeHandler.datetimeToString(new Date());
	}

	@PrePersist
	protected void onCreate() {
		createdAt = DateTimeHandler.datetimeToString(new Date());
	}
}

