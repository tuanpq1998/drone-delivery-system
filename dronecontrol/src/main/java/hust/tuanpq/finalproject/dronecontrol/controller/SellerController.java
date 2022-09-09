package hust.tuanpq.finalproject.dronecontrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hust.tuanpq.finalproject.dronecontrol.entity.Mission;
import hust.tuanpq.finalproject.dronecontrol.service.MissionService;

@RestController
@RequestMapping("/api/v1/seller")
public class SellerController {

	@Autowired
	private MissionService missionService;
	
	@GetMapping("/missions")
	public List<Mission> getAll(Authentication authentication) {
		String sellerName = authentication.getName();
		return missionService.findAllBySellername(sellerName);
	}
	
	@GetMapping("/missions/{id}")
	public Mission getOne(@PathVariable int id, Authentication authentication) {
		String sellerName = authentication.getName();
		return missionService.findByIdAndSellername(id, sellerName);
	}
	
	@PostMapping("/missions")
	public Mission createNew(@RequestBody Mission mission, Authentication authentication) {
		String sellerName = authentication.getName();
		return missionService.createNewWithSellername(mission, sellerName);
	}
	
	@PutMapping("/missions/{id}")
	public Mission update(@PathVariable int id, @RequestBody Mission mission, Authentication authentication) {
		String sellerName = authentication.getName();
		return missionService.updateWithSellername(id, mission, sellerName);
	}
}
