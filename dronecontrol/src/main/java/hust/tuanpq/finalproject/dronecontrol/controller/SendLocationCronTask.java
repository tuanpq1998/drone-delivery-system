package hust.tuanpq.finalproject.dronecontrol.controller;

import java.util.TimerTask;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;

import hust.tuanpq.finalproject.dronecontrol.service.DroneService;

public class SendLocationCronTask extends TimerTask {

private int droneId;
	
	private DroneService droneService;
	
	private int count;
	
	private SimpMessagingTemplate template;
	
	public int getDroneId() {
		return droneId;
	}

	public void addCount() {
		count++;
	}
	
	public void subtractCount() {
		count--;
	}
	
	public void setDroneId(int droneId) {
		this.droneId = droneId;
	}


	public DroneService getDroneService() {
		return droneService;
	}


	public void setDroneService(DroneService droneService) {
		this.droneService = droneService;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public SendLocationCronTask(int droneId, DroneService droneService, int count, SimpMessagingTemplate template) {
		super();
		this.droneId = droneId;
		this.droneService = droneService;
		this.count = count;
		this.template = template;
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
			template.convertAndSend("/app/location/"+droneId, droneService.getById(droneId) );
		}
	}
}
