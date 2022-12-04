package hust.tuanpq.finalproject.dronecontrol.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.tuanpq.finalproject.dronecontrol.entity.Drone;
import hust.tuanpq.finalproject.dronecontrol.entity.Home;
import hust.tuanpq.finalproject.dronecontrol.entity.Mission;
import hust.tuanpq.finalproject.dronecontrol.model.ExtraDroneInfo;
import hust.tuanpq.finalproject.dronecontrol.model.SdkServer;
import hust.tuanpq.finalproject.dronecontrol.repository.DroneRepository;
import hust.tuanpq.finalproject.dronecontrol.repository.MissionRepository;
import io.mavsdk.System;
import io.mavsdk.action.Action;
import io.mavsdk.mission.Mission.MissionItem;
import io.mavsdk.mission.Mission.MissionPlan;
import io.mavsdk.mission.Mission.MissionItem.CameraAction;
import io.reactivex.CompletableSource;

@Service
public class DroneService {

	@Autowired
	private DroneRepository droneRepository;
	
	@Autowired
	private MissionService missionService;
	
	@Autowired
	private SdkServerSerivce sdkServerService;
	
	@Autowired
	private MissionRepository missionRepository;
	
	@Autowired
	private HomeService homeService;
	
//	@PreDestroy
//	public void disconnect(System drone) {
//		drone.dispose();
//	}
	
	public boolean isConnected(System drone) {
		return drone.getCore().getConnectionState().blockingFirst().getIsConnected();
	} 
	
	public List<Drone> addActiveProperty(List<Drone> drones) throws IOException, InterruptedException {
		List<Thread> threads = new ArrayList<Thread>();
		for(Drone _drone : drones) {
			Thread thread = new Thread(new Runnable() {
			    @Override
			    public void run() {
			    	try {
						sdkServerService.initOne(_drone);
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			});
			thread.start();
			threads.add(thread);
		}
		
		for (Thread thread : threads) {
		    thread.join();
		}
		
		List<SdkServer> servers = sdkServerService.getServers();
		for (SdkServer server : servers) {
			for (Drone _drone : drones) {
				if (server.getDroneId() == _drone.getId()) {
					_drone.setServerActive(true);
				}
			}
		}
		return drones;
	}

	public void takeoffAndLand(System drone) {
		CountDownLatch latch = new CountDownLatch(1);
		drone.getAction().arm().doOnComplete(() -> java.lang.System.out.println("Arming..."))
				.doOnError(throwable -> java.lang.System.out
						.println("Failed to arm: " + ((Action.ActionException) throwable).getCode()))
				.andThen(drone.getAction().takeoff().delay(15, TimeUnit.SECONDS)
						.doOnComplete(() -> java.lang.System.out.println("Taking off..."))
						.doOnError(throwable -> java.lang.System.out
								.println("Failed to take off: " + ((Action.ActionException) throwable).getCode())))

				.andThen(drone.getAction().land().doOnComplete(() -> java.lang.System.out.println("Landing..."))
						.doOnError(throwable -> java.lang.System.out
								.println("Failed to land: " + ((Action.ActionException) throwable).getCode())))
				.subscribe(latch::countDown, throwable -> latch.countDown());

		try {
			latch.await();
		} catch (InterruptedException ignored) {
			// This is expected
		}
	}

	public void runMission(System drone, boolean isReturnToLaunch) {
		CountDownLatch latch = new CountDownLatch(1);

		drone.getMission().downloadMission().subscribe((plan) -> 
			drone.getMission().clearMission()
				.andThen(drone.getMission().setReturnToLaunchAfterMission(isReturnToLaunch).doOnComplete(() -> java.lang.System.out.println("set rtl " + isReturnToLaunch)))
				.andThen(drone.getMission().uploadMission(plan))
				.andThen(drone.getAction().arm().onErrorComplete())
				.andThen(drone.getMission().startMission().doOnComplete(() -> java.lang.System.out.println("running mission!")))
				.andThen(drone.getMission().getMissionProgress()).subscribe(next -> {
					java.lang.System.out.println("Progress " + next.getCurrent() + "/" + next.getTotal());
					if (next.getCurrent() == next.getTotal()) {
						if (isReturnToLaunch) {
							java.lang.System.out.println("Done!");
							latch.countDown();
						} else 
							drone.getAction().land()
								.doOnComplete(() -> java.lang.System.out.println("Landing..."))
								.doOnError(throwable -> {})
							.subscribe(() -> {
								java.lang.System.out.println("Done!");
								latch.countDown();
							
						});
						
					} 
			}, throwable -> {
				java.lang.System.out.println("Error: " + throwable.getMessage());
				latch.countDown();
			}, () -> {
				java.lang.System.out.println("Successfully!");
				latch.countDown();
			})
				, throwable -> {
				java.lang.System.out.println("Error: " + throwable.getMessage());
				latch.countDown();
			}
		);
		
		

		try {
			latch.await();
		} catch (InterruptedException ignored) {
			// This is expected
		}
	}

	public List<Drone> getAll() {
		return droneRepository.findAll();
	}

	public Drone getById(int id) {
		Optional<Drone> res = droneRepository.findById(id);
		Drone d = null;
		if (res.isPresent())
			d = res.get();
		return d;
	}

	public Drone addNew(Drone drone) {
		drone.setId(0);
		Drone newDrone = droneRepository.save(drone);
		return newDrone;
	}

	public Drone update(int id, Drone drone) {
		Drone old = getById(id);
		if (old != null) {
			drone.setId(id);
			return droneRepository.save(drone);
		}
		return old;
	}
	
	public Drone saveDrone(Drone drone) {
		return droneRepository.save(drone);
	}

	public Drone assgin(int droneId, Mission mission) {
		Drone drone = getById(droneId);
		Mission dbMission = missionService.findById(mission.getId());
		if (drone == null || dbMission == null || dbMission.getDrone() != null || drone.getActiveMission() != null) 
			return null;
		
		dbMission.setFlyingAltitude(mission.getFlyingAltitude());
		dbMission.setSenderLocationAltitude(mission.getSenderLocationAltitude());
		dbMission.setReceiverLocationAltitude(mission.getReceiverLocationAltitude());
		dbMission.setSpeedMs(mission.getSpeedMs());
		dbMission.setHoldingTime(mission.getHoldingTime());
		
		dbMission.setDrone(drone);
		drone.setActiveMission(dbMission);
		missionRepository.save(dbMission);
		return droneRepository.save(drone);
	}

	public void stopMission(System d) {
		CountDownLatch latch = new CountDownLatch(1);
		
		d.getMission().pauseMission().subscribe(latch::countDown, throwable -> latch.countDown());
		try {
			latch.await();
		} catch (InterruptedException ignored) {
			// This is expected
		}
	}

	public List<Drone> getAssignable()  {
		List<Drone> freeDrones = droneRepository.findByActiveMissionNull();
		java.lang.System.out.println(freeDrones);
		List<Drone> res = new ArrayList<Drone>();
		List<Drone> freeAndActiveDrone;
		try {
			freeAndActiveDrone = addActiveProperty(freeDrones);
			
			for (Drone drone : freeAndActiveDrone) {
				if (drone.isServerActive())
					res.add(drone);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public void uploadAndStart(int droneId, Mission m) {
		
		Home home = homeService.getHomeLocation();
		
		List<MissionItem> list = new ArrayList<MissionItem>();
		list.add(new MissionItem(home.getLatitude(), home.getLongitude(), m.getFlyingAltitude().floatValue(), m.getSpeedMs(), true, Float.NaN, Float.NaN,
				CameraAction.NONE, Float.NaN, 1.0, Float.NaN, Float.NaN, Float.NaN));
		
		list.add(new MissionItem(m.getSenderLocationLatitude(), m.getSenderLocationLongitude(), m.getFlyingAltitude().floatValue(), m.getSpeedMs(), true, Float.NaN, Float.NaN,
					CameraAction.NONE, Float.NaN, 1.0, Float.NaN, Float.NaN, Float.NaN));
		list.add(new MissionItem(m.getSenderLocationLatitude(), m.getSenderLocationLongitude(), m.getSenderLocationAltitude().floatValue(), m.getSpeedMs(), false, Float.NaN, Float.NaN,
				CameraAction.NONE, m.getHoldingTime(), 1.0, Float.NaN, Float.NaN, Float.NaN));
		list.add(new MissionItem(m.getSenderLocationLatitude(), m.getSenderLocationLongitude(), m.getFlyingAltitude().floatValue(), m.getSpeedMs(), true, Float.NaN, Float.NaN,
				CameraAction.NONE, Float.NaN, 1.0, Float.NaN, Float.NaN, Float.NaN));
		
		list.add(new MissionItem(m.getReceiverLocationLatitude(), m.getReceiverLocationLongitude(), m.getFlyingAltitude().floatValue(), m.getSpeedMs(), true, Float.NaN, Float.NaN,
				CameraAction.NONE, Float.NaN, 1.0, Float.NaN, Float.NaN, Float.NaN));
		list.add(new MissionItem(m.getReceiverLocationLatitude(), m.getReceiverLocationLongitude(), m.getReceiverLocationAltitude().floatValue(), m.getSpeedMs(), false, Float.NaN, Float.NaN,
				CameraAction.NONE, m.getHoldingTime(), 1.0, Float.NaN, Float.NaN, Float.NaN));
		list.add(new MissionItem(m.getReceiverLocationLatitude(), m.getReceiverLocationLongitude(), m.getFlyingAltitude().floatValue(), m.getSpeedMs(), true, Float.NaN, Float.NaN,
				CameraAction.NONE, Float.NaN, 1.0, Float.NaN, Float.NaN, Float.NaN));
		
		list.add(new MissionItem(home.getLatitude(), home.getLongitude(), m.getFlyingAltitude().floatValue(), m.getSpeedMs(), true, Float.NaN, Float.NaN,
				CameraAction.NONE, Float.NaN, 1.0, Float.NaN, Float.NaN, Float.NaN));
		
		list.add(new MissionItem(home.getLatitude(), home.getLongitude(), 0f, m.getSpeedMs(), false, Float.NaN, Float.NaN,
			CameraAction.NONE, 1f, 1.0, Float.NaN, Float.NaN, Float.NaN));
		
		System drone = sdkServerService.getSystem(droneId);
		CountDownLatch latch = new CountDownLatch(1);
		MissionPlan plan = new MissionPlan(list);
		
		drone.getMission().clearMission()
		.andThen(drone.getMission().setReturnToLaunchAfterMission(false))
		.andThen(drone.getMission().uploadMission(plan)
			.doOnComplete(() -> java.lang.System.out.println("Upload done!")))
		
			.andThen(drone.getAction().arm().onErrorComplete())
			.andThen(drone.getMission().startMission().doOnComplete(() -> java.lang.System.out.println("running mission!")))
			.andThen(drone.getMission().getMissionProgress()).subscribe(next -> {
				java.lang.System.out.println("Progress " + next.getCurrent() + "/" + next.getTotal());
				if (next.getCurrent() == 0) {
					missionRepository.save(missionService.addNewLogsAndStatus(m, "Starting ..."));
				} else if (next.getCurrent() == 1) {
					missionRepository.save(missionService.addNewLogsAndStatus(m, "Take off from home, going to sender."));
				} else if (next.getCurrent() == 2) {
					missionRepository.save(missionService.addNewLogsAndStatus(m, "Arrived at sender, getting the package."));
				} else if (next.getCurrent() == 3) {
					m.setTrackingStart(true);
					missionRepository.save(m);
				} else if (next.getCurrent() == 4) {
					missionRepository.save(missionService.addNewLogsAndStatus(m, "Got the package, going to receiver."));
				} else if (next.getCurrent() == 5) {
					missionRepository.save(missionService.addNewLogsAndStatus(m, "Arrived at receiver, dropping package."));
				} else if (next.getCurrent() == 6) {
					m.setTrackingStart(false);
					missionRepository.save(m);
				} else if (next.getCurrent() == 7) {
					missionRepository.save(missionService.addNewLogsAndStatus(m, "Mission done, returning to home..."));
				} 
				else if (next.getCurrent() == 9) {
					Mission newMission = missionService.addNewLogsAndStatus(m, "Drone returned.");
					newMission.setFinished(true);
					missionRepository.save(newMission);
					Drone d = getById(droneId);
					d.setActiveMission(null);
					saveDrone(d);
					drone.getAction().land().doOnComplete(() -> {
						java.lang.System.out.println("returned");
					}).subscribe(latch::countDown, throwable -> latch.countDown());
				} 
		}, throwable -> {
			java.lang.System.out.println("Error: " + throwable.getMessage());
//			latch.countDown();
		}, () -> {
			java.lang.System.out.println("Successfully!");
//			latch.countDown();
		});
		try {
	      latch.await();
	    } catch (InterruptedException ignored) {
	      // This is expected
	    }
	}
	

	public Mission startMission(int droneId) {
		Drone d = getById(droneId);
		if (d == null) return null;
		Mission m = d.getActiveMission();
		if (m == null || m.isStarted() || m.isFinished()) return null;
		Thread thread = new Thread(new Runnable() {
			@Override
		    public void run() {
				uploadAndStart(droneId, m);
		    }
		});
		thread.start();
		
		m.setStarted(true);
		
		return missionRepository.save(m);
		
	}
	
}
