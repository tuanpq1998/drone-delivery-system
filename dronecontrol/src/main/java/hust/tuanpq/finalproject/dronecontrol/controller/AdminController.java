package hust.tuanpq.finalproject.dronecontrol.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hust.tuanpq.finalproject.dronecontrol.entity.Drone;
import hust.tuanpq.finalproject.dronecontrol.entity.Mission;
import hust.tuanpq.finalproject.dronecontrol.service.DroneService;
import hust.tuanpq.finalproject.dronecontrol.service.MissionService;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
public class AdminController {

	@Autowired
	private DroneService droneService;
	
	
	@Autowired
	private MissionService missionService;
	
	@GetMapping("/drones")
	public List<Drone> getAll () throws IOException, InterruptedException {
		return droneService.addActiveProperty(droneService.getAll());
	}
	
	@GetMapping("/drones/{id}")
	public Drone getOne(@PathVariable int id) {
		return droneService.getById(id);
	}
	
	@PostMapping("/drones")
	public Drone createNew (@RequestBody Drone drone) {
		return droneService.addNew(drone);
	}
	
	@GetMapping("/drones/getAssignable")
	public List<Drone> getAssignableDrone() {
		return droneService.getAssignable();
	}
	
	@PutMapping("/drones/{id}")
	public Drone update(@PathVariable int id, @RequestBody Drone drone) {
		return droneService.update(id, drone);
	}
	
	@PostMapping("/drones/{droneId}/assignMission")
	public Drone assignMission(@PathVariable int droneId, @RequestBody Mission mission) {
		System.out.println(droneId);
		return droneService.assgin(droneId, mission);
	}
	
	@GetMapping("/drones/{droneId}/startMission")
	public Mission startMission(@PathVariable int droneId) {
		return droneService.startMission(droneId);
	}
	
	@GetMapping("/missions")
	public List<Mission> getAllMissions() {
		return missionService.findAll();
	}
	
	@GetMapping("/missions/{id}")
	public Mission getOneMission(@PathVariable int id) {
		return missionService.findById(id);
	}
	
	@PutMapping("/missions/{id}")
	public Mission updateMission(@PathVariable int id, @RequestBody Mission mission) {
		if (id != mission.getId())
			return null;
		return missionService.update(id, mission);
	}
}
