package hust.tuanpq.finalproject.dronecontrol.service;

import java.util.TimerTask;

import hust.tuanpq.finalproject.dronecontrol.entity.Drone;
import hust.tuanpq.finalproject.dronecontrol.model.ExtraDroneInfo;

public class CronTask extends TimerTask {

	private io.mavsdk.System drone;
	private int droneId;
	
	private ExtraDroneInfoService extraDroneInfoService;
	
	private DroneService droneService;

	public CronTask() {
		super();
	}


	public CronTask(int droneId, io.mavsdk.System drone, ExtraDroneInfoService extraDroneInfoService, DroneService droneService) {
		super();
		this.drone = drone;
		this.droneId = droneId;
		this.extraDroneInfoService = extraDroneInfoService;
		this.droneService = droneService;
	}


	@Override
	public void run() {

		ExtraDroneInfo extraDroneInfo = extraDroneInfoService.getCurrentExtraInfo(drone);
		
		Drone drone = droneService.getById(droneId);

		System.out.println("Drone id: " + droneId + " " + extraDroneInfo);
//		System.out.println(batteryInfo[0] + ", " + batteryInfo[1]);

		drone.setLocationLatitude(extraDroneInfo.getLatitude());
		drone.setLocationAltitude(extraDroneInfo.getAltitude());
		drone.setLocationLongitude(extraDroneInfo.getLongitude());
		
		drone.setBatteryVoltage(extraDroneInfo.getBatteryVoltage());
		drone.setBatteryPercent(extraDroneInfo.getBatteryPercent());
		
		drone.setVelocityHorizontal(extraDroneInfo.getVelocityHorizontal());
		drone.setVelocityVertical(extraDroneInfo.getVelocityVertical());
		
		droneService.saveDrone(drone);
	}
	
}
