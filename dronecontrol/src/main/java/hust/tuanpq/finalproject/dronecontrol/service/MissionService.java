package hust.tuanpq.finalproject.dronecontrol.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.tuanpq.finalproject.dronecontrol.entity.Account;
import hust.tuanpq.finalproject.dronecontrol.entity.Mission;
import hust.tuanpq.finalproject.dronecontrol.model.ExtraDroneInfo;
import hust.tuanpq.finalproject.dronecontrol.repository.MissionRepository;
import hust.tuanpq.finalproject.dronecontrol.utility.CustomPasswordEncoder;
import hust.tuanpq.finalproject.dronecontrol.utility.CustomPasswordUtility;
import hust.tuanpq.finalproject.dronecontrol.utility.DateTimeHandler;
import hust.tuanpq.finalproject.dronecontrol.utility.StringIdentifierGenerator;
import io.mavsdk.System;
import io.mavsdk.mission.Mission.MissionItem;
import io.mavsdk.mission.Mission.MissionPlan;
import io.mavsdk.mission.Mission.MissionItem.CameraAction;
import io.reactivex.CompletableSource;

@Service
public class MissionService {
	
	public void clearMission(System drone) {
//		CountDownLatch latch = new CountDownLatch(1);
//		drone.getMission().clearMission()
//			.doOnComplete(() -> java.lang.System.out.println("clearing"))
//			.subscribe(() -> java.lang.System.out.println("cleared!"), throwable -> {
//				java.lang.System.out.println("Error: " + throwable.getMessage());
//				latch.countDown();
//			});
//		try {
//	      latch.await();
//	    } catch (InterruptedException ignored) {
//	      // This is expected
//	    }
	}
	
	public void uploadMission(hust.tuanpq.finalproject.dronecontrol.model.Mission mission, System drone) {
		List<ExtraDroneInfo> locations = mission.getLocations();
		List<MissionItem> list = new ArrayList<MissionItem>();

		for(ExtraDroneInfo l : locations) {
			list.add(new MissionItem(l.getLatitude(), l.getLongitude(), 10f, 10f, true, Float.NaN, Float.NaN,
					CameraAction.NONE, Float.NaN, 1.0, Float.NaN, Float.NaN, Float.NaN));
		}
		CountDownLatch latch = new CountDownLatch(1);
		MissionPlan plan = new MissionPlan(list);
		
		drone.getMission().setReturnToLaunchAfterMission(false)
			.andThen(drone.getMission().uploadMission(plan)
					.doOnComplete(() -> java.lang.System.out.println("Upload done!")))
			.andThen(drone.getMission().downloadMission()
					 .doOnSubscribe(disposable -> java.lang.System.out.println("Downloading mission"))
			            .doAfterSuccess(disposable -> java.lang.System.out.println("Mission downloaded")))
			.ignoreElement()
	        .andThen((CompletableSource) cs -> latch.countDown())
	        .subscribe(() -> java.lang.System.out.println("uploaded!"), throwable -> {
				java.lang.System.out.println("Error: " + throwable.getMessage());
				latch.countDown();
			});
		try {
	      latch.await();
	    } catch (InterruptedException ignored) {
	      // This is expected
	    }
		
	}

	@Autowired
	private MissionRepository missionRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CustomPasswordUtility customPasswordUtility;
	
	@Autowired
	private CustomPasswordEncoder customPasswordEncoder;
	
	public List<Mission> findAll() {
		return missionRepository.findAll();
	}

	public Mission findById(int id) {
		Optional<Mission> res = missionRepository.findById(id);
		Mission m = null;
		if (res.isPresent()) {
			m = res.get();
			m.setRawpassword(customPasswordUtility.decrypt(m.getPassword()));
		}
			
		return m;
	}

	public Mission createNew(Mission mission) {
		mission.setId(0);
		return missionRepository.save(mission);
	}

	public Mission update(int id, Mission mission) {
		Mission old = findById(id);
		if (old != null) {
			old.setReceiverLocationLatitude(mission.getReceiverLocationLatitude());
			old.setReceiverLocationLongitude(mission.getReceiverLocationLongitude());
			old.setSenderLocationLatitude(mission.getSenderLocationLatitude());
			old.setSenderLocationLongitude(mission.getReceiverLocationLongitude());
			old.setNote(mission.getNote());
			old.setSize(mission.getSize());
			old.setWeight(mission.getWeight());
			old.setPrice(mission.getPrice());
			old.setStatus(mission.getStatus());
			old.setFlyingAltitude(mission.getFlyingAltitude());
			old.setHoldingTime(mission.getHoldingTime());
			old.setSenderLocationAltitude(mission.getSenderLocationAltitude());
			old.setReceiverLocationAltitude(mission.getReceiverLocationAltitude());
			old.setSpeedMs(mission.getSpeedMs());
			
			return missionRepository.save(old);
		}
		return old;
	}
	
	public Mission addNewLogsAndStatus(Mission m, String statusStr) {
		String newLog = DateTimeHandler.datetimeToString(new Date()) + ":: " +statusStr.trim() + "|";
		m.setStatus(statusStr.trim());
		String oldLogs = m.getLogs();
		m.setLogs((oldLogs==null ? "": oldLogs )+ newLog);
		return m;
	}

	public List<Mission> findAllBySellername(String sellerUsername) {
		return missionRepository.findBySeller_Username(sellerUsername);
	}

	public Mission findByIdAndSellername(int id, String sellerUsername) {
		Mission m = missionRepository.findByIdAndSeller_Username(id, sellerUsername);
		m.setRawpassword(customPasswordUtility.decrypt(m.getPassword()));
		return m;
	}

	public Mission createNewWithSellername(Mission mission, String sellerName) {
		mission.setId(0);
		Account seller = accountService.findByUsername(sellerName);
		mission.setSeller(seller);
		
		mission.setSenderAddress(seller.getAddress());
		mission.setFlyingAltitude(100d);
		mission.setSenderLocationAltitude(50d);
		mission.setMissionIdentifier(StringIdentifierGenerator.getRandom());
		mission.setPassword(customPasswordEncoder.encode(StringIdentifierGenerator.getRandom()));
		
		return missionRepository.save(mission);
	}

	public Mission updateWithSellername(int id, Mission mission, String sellerName) {
		Mission old = findById(id);
		if (old != null && old.getSeller() != null && old.getSeller().getUsername().equals(sellerName)) {
			old.setReceiverLocationLatitude(mission.getReceiverLocationLatitude());
			old.setReceiverLocationLongitude(mission.getReceiverLocationLongitude());
			old.setSenderLocationLatitude(mission.getSenderLocationLatitude());
			old.setSenderLocationLongitude(mission.getReceiverLocationLongitude());
			old.setNote(mission.getNote());
			old.setSize(mission.getSize());
			old.setWeight(mission.getWeight());
			old.setPrice(mission.getPrice());
			old.setStatus(mission.getStatus());
			old.setFlyingAltitude(mission.getFlyingAltitude());
			old.setHoldingTime(mission.getHoldingTime());
			old.setSenderLocationAltitude(mission.getSenderLocationAltitude());
			old.setReceiverLocationAltitude(mission.getReceiverLocationAltitude());
			old.setSpeedMs(mission.getSpeedMs());
			
			return missionRepository.save(old);
		}
		return old;
	}

	public Mission findByIdentifier(String identifier, String password) {
		Mission m = missionRepository.findByMissionIdentifier(identifier);
		if (customPasswordEncoder.matches(password, m.getPassword())) 
			return m;
		return null;
	}

}
