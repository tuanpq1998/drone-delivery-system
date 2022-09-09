package hust.tuanpq.finalproject.dronecontrol.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hust.tuanpq.finalproject.dronecontrol.entity.Home;

@Repository
public interface HomeRepository extends CrudRepository<Home, Integer> {

}
