package hust.tuanpq.finalproject.dronecontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hust.tuanpq.finalproject.dronecontrol.entity.Mission;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {

	List<Mission> findBySeller_Username(String sellerUsername);

	Mission findByIdAndSeller_Username(int id, String sellerUsername);

	Mission findByMissionIdentifier(String identifier);

}
