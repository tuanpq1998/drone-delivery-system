package hust.tuanpq.finalproject.dronecontrol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.tuanpq.finalproject.dronecontrol.model.Location;
import hust.tuanpq.finalproject.dronecontrol.repository.LocationRepository;
import io.mavsdk.System;
import io.mavsdk.telemetry.Telemetry.Position;
import io.reactivex.Flowable;

@Service
public class LocationService {
	
//	@Autowired
//	private LocationRepository locationRepository;
//	
	public Location getCurrentLocation(System drone) {
		Flowable<Position> altitudeFlowable = drone.getTelemetry().getPosition();
		Position p = altitudeFlowable.blockingFirst();
		return new Location(p.getRelativeAltitudeM(), p.getLatitudeDeg(), p.getLongitudeDeg());
	}
	
//	public hust.tuanpq.finalproject.dronecontrol.entity.Location transformLocationModelToEntity(Location l) {
//		hust.tuanpq.finalproject.dronecontrol.entity.Location result = new hust.tuanpq.finalproject.dronecontrol.entity.Location();
//		result.setAltitude(l.getAltitude());
//		result.setLatitude(l.getLatitude());
//		result.setLongitude(l.getLongitude());
//
//		return result;
//	}
	
//	public hust.tuanpq.finalproject.dronecontrol.entity.Location save(hust.tuanpq.finalproject.dronecontrol.entity.Location location) {
//		return locationRepository.save(location);
//	}
}
