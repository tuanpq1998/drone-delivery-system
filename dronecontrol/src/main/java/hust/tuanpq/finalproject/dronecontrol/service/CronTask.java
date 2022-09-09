package hust.tuanpq.finalproject.dronecontrol.service;

import java.util.TimerTask;

import hust.tuanpq.finalproject.dronecontrol.entity.Drone;
import hust.tuanpq.finalproject.dronecontrol.model.Location;

public class CronTask extends TimerTask {

	private io.mavsdk.System drone;
	private int droneId;
	
	private LocationService locationService;
	
	private DroneService droneService;

	public CronTask() {
		super();
	}


	public CronTask(int droneId, io.mavsdk.System drone, LocationService locationService, DroneService droneService) {
		super();
		this.drone = drone;
		this.droneId = droneId;
		this.locationService = locationService;
		this.droneService = droneService;
	}


	@Override
	public void run() {

		Location l = locationService.getCurrentLocation(drone);
		Drone drone = droneService.getById(droneId);

		System.out.println("" + droneId + l);

		drone.setLocationLatitude(l.getLatitude());
		drone.setLocationAltitude(l.getAltitude());
		drone.setLocationLongitude(l.getLongitude());
		
		droneService.saveDrone(drone);
	}
	
}
