package hust.tuanpq.finalproject.dronecontrol.controller;

import java.util.TimerTask;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;

import hust.tuanpq.finalproject.dronecontrol.entity.Drone;
import hust.tuanpq.finalproject.dronecontrol.entity.Mission;
import hust.tuanpq.finalproject.dronecontrol.service.DroneService;
import hust.tuanpq.finalproject.dronecontrol.service.MissionService;

public class SendLocationTrackingCronTask extends TimerTask {

private int missionId;
	
	private MissionService missionService;
	
	private int count;
	
	private String missionIdentifier, password;
	
	private SimpMessagingTemplate template;
	
	
	public void addCount() {
		count++;
	}
	
	public void subtractCount() {
		count--;
	}
	
	

	

	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public SendLocationTrackingCronTask(int missionId, MissionService missionService, int count, String missionIdentifier,
			String password, SimpMessagingTemplate template) {
		super();
		this.missionId = missionId;
		this.missionService = missionService;
		this.count = count;
		this.missionIdentifier = missionIdentifier;
		this.password = password;
		this.template = template;
	}

	public String getMissionIdentifier() {
		return missionIdentifier;
	}

	public void setMissionIdentifier(String missionIdentifier) {
		this.missionIdentifier = missionIdentifier;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SimpMessagingTemplate getTemplate() {
		return template;
	}


	public void setTemplate(SimpMessagingTemplate template) {
		this.template = template;
	}


	@Override
	public void run() {
		if (count > 0) {
			Mission m = missionService.findByIdentifier(missionIdentifier, password);
			if (m.isTrackingStart())
				template.convertAndSend("/appTracking/locationTracking/"+ missionIdentifier + "." + password , m );
		}
	}


}
