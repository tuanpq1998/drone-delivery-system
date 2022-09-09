package hust.tuanpq.finalproject.dronecontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.tuanpq.finalproject.dronecontrol.entity.Drone;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Integer> {

	List<Drone> findByActiveMissionNull();

	
}
