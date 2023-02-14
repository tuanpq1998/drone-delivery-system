package hust.tuanpq.finalproject.dronecontrol.model;

public class MissionThread {

	private int missionId, droneId;
	private Thread thread;
	
	
	public int getMissionId() {
		return missionId;
	}
	public void setMissionId(int missionId) {
		this.missionId = missionId;
	}
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	public MissionThread(int missionId, int droneId,Thread thread) {
		super();
		this.missionId = missionId;
		this.droneId = droneId;
		this.thread = thread;
	}
	public int getDroneId() {
		return droneId;
	}
	public void setDroneId(int droneId) {
		this.droneId = droneId;
	}
	
	
}
