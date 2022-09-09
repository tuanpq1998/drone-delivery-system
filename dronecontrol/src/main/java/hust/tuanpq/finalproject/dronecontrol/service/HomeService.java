package hust.tuanpq.finalproject.dronecontrol.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.tuanpq.finalproject.dronecontrol.entity.Home;
import hust.tuanpq.finalproject.dronecontrol.repository.HomeRepository;

@Service
public class HomeService {

	@Autowired
	private HomeRepository homeRepository;
	
	public Home getHomeLocation() {
		Optional<Home> res = homeRepository.findById(1);
		Home h = null;
		if (res.isPresent())
			h = res.get();
		return h;
	}
}
