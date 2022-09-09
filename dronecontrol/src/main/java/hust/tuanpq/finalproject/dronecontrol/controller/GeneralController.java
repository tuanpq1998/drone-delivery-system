package hust.tuanpq.finalproject.dronecontrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hust.tuanpq.finalproject.dronecontrol.entity.Drone;
import hust.tuanpq.finalproject.dronecontrol.entity.Home;
import hust.tuanpq.finalproject.dronecontrol.entity.Mission;
import hust.tuanpq.finalproject.dronecontrol.model.LoginRequest;
import hust.tuanpq.finalproject.dronecontrol.service.HomeService;
import hust.tuanpq.finalproject.dronecontrol.service.MissionService;

@RestController
@RequestMapping("/api/v1")
public class GeneralController {
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private MissionService missionService;
	
	@GetMapping("/homeLocation")
	public Home getHomeLocation() {
		return homeService.getHomeLocation();
	}
	
	@PostMapping("/tracking/{identifier}")
	public Mission getMissionByIdentifier(@PathVariable String identifier, @RequestBody LoginRequest request) {
		Mission m = missionService.findByIdentifier(identifier, request.getPassword());
		
		//truncate
		if (!m.isTrackingStart()) {
			Drone d = m.getDrone();
			d.setLocationLatitude(0d);
			d.setLocationLongitude(0d);
			m.setDrone(d);
		}
		return m;
	}
	
}
