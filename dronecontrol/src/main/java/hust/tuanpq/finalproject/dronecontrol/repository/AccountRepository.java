package hust.tuanpq.finalproject.dronecontrol.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hust.tuanpq.finalproject.dronecontrol.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

	Account findByUsername(String username);

}
