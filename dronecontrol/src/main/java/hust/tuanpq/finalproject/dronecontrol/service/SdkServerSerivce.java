package hust.tuanpq.finalproject.dronecontrol.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import hust.tuanpq.finalproject.dronecontrol.entity.Drone;
import hust.tuanpq.finalproject.dronecontrol.model.SdkServer;

@Service
public class SdkServerSerivce {
	@Value("${sdk-server-dir}")
	private  String sdkServerDir;
	
	@Value("${sdk-server-filename}")
	private String sdkServerFilename;
	
	@Autowired
	private ExtraDroneInfoService extraDroneInfoService;
	
	@Lazy
	@Autowired 
	private DroneService droneService;

	private List<SdkServer> servers = new ArrayList<SdkServer>();
	
	public List<SdkServer> getServers() {
		return servers;
	}
	
	public SdkServer getByDroneId(int droneId) {
		for (SdkServer s : servers) {
			if (s.getDroneId() == droneId) {
				return s;
			}
		}
		return null;
	}
	
	public void initOne(Drone drone) throws IOException, InterruptedException {
		int id = drone.getId();
		if (getByDroneId(id) == null) {
			SdkServer server = new SdkServer(id, drone.getConnectType() ,drone.getConnectIp(), drone.getConnectPort(), sdkServerDir, sdkServerFilename);
			server.init();
			Thread.sleep(2000);
			if (isSystemDroneConnect(new io.mavsdk.System("localhost", server.getPortSystem()))) {
				servers.add(server);
				CronTask cronTask = new CronTask(id, new io.mavsdk.System("localhost", server.getPortSystem()), extraDroneInfoService, droneService);
				Timer timer = new Timer();
				timer.schedule(cronTask, 0, 1000);
//				Thread thread = new Thread(new Runnable() {
//					@Override
//				    public void run() {
//						CronTask cronTask = new CronTask(id, new io.mavsdk.System("localhost", server.getPortSystem()), locationService, droneService);
//						Timer timer = new Timer();
//						timer.schedule(cronTask, 0, 5000);
//				    }
//				});
//				thread.start();
			} else {
				System.out.println("Cannot connect droneId "+ id);
				server.destroy();
			}
		}
	}
	
	public io.mavsdk.System getSystem(int droneId) {
		for (SdkServer s : servers) {
			if (s.getDroneId() == droneId) {
				return new io.mavsdk.System("localhost", s.getPortSystem());
			}
		}
		return null;
	}
	
	public boolean isSystemDroneConnect(io.mavsdk.System drone) {
		try {
			extraDroneInfoService.getCurrentExtraInfo(drone);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void init() {
		
	}
	
	public void destroy() {
		
	}
}
