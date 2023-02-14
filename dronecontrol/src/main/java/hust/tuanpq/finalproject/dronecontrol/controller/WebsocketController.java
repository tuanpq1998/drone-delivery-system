package hust.tuanpq.finalproject.dronecontrol.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import hust.tuanpq.finalproject.dronecontrol.entity.Drone;
import hust.tuanpq.finalproject.dronecontrol.entity.Mission;
import hust.tuanpq.finalproject.dronecontrol.model.ExtraDroneInfo;
import hust.tuanpq.finalproject.dronecontrol.model.LoginRequest;
import hust.tuanpq.finalproject.dronecontrol.model.SdkServer;
import hust.tuanpq.finalproject.dronecontrol.service.DroneService;
import hust.tuanpq.finalproject.dronecontrol.service.ExtraDroneInfoService;
import hust.tuanpq.finalproject.dronecontrol.service.SdkServerSerivce;
import hust.tuanpq.finalproject.dronecontrol.service.MissionService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class WebsocketController {

	public List<SendLocationCronTask> cronTasks = new ArrayList<SendLocationCronTask>();
	public List<SendLocationTrackingCronTask> cronTrackingTasks = new ArrayList<SendLocationTrackingCronTask>();

	@Autowired
	private DroneService droneService;

	@Autowired
	private MissionService missionService;

//	@EventListener
//	public void handleWebSocketConnectListener(SessionSubscribeEvent event) {
//		System.out.println(event.getUser().getName());
//	}

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

//	private SimpMessagingTemplate template;
//	
//	@Autowired
//    public DroneWebsocketController(SimpMessagingTemplate template) {
//        this.template = template;
//    }

//	@MessageMapping("/room/greeting/{droneId}")
//	public void getLocation(@DestinationVariable int droneId) {
//		//init location ?
//		io.mavsdk.System d = sdkServerService.getSystem(droneId);
//		this.template.convertAndSend("/chat/room/"+droneId, locationService.getCurrentLocation(d));  
//	}

//	
//	@MessageMapping("/start")
//    public void connect(@RequestBody Drone drone) throws IOException, InterruptedException {
//		Drone d = droneService.getById(drone.getId());
//		sdkServerService.initOne(d);
//
////		List<Drone> drones = droneService.getAll();
////		for(Drone _drone : drones) {
////			sdkServerService.initOne(_drone);
////		}
////		List<SdkServer> servers = sdkServerService.getServers();
////		List<Drone> results = new ArrayList<Drone>();
////		for (SdkServer server : servers) {
////			for (Drone _drone : drones) {
////				if (server.getDroneId() == _drone.getId()) {
////					results.add(drone);
////				}
////			}
////		}
////		System.out.println("aaa"+results.size());
//	}
//	
//
//	

	@SubscribeMapping("/locationTracking/{missionIdentifier}.{password}")
	public void subscribeToLocationTracking(@DestinationVariable String missionIdentifier,
			@DestinationVariable String password) {
		boolean hasRun = false;
		// System.out.println("105" + accessor.getDestination());
		Mission mission = missionService.findByIdentifier(missionIdentifier, password);
		if (mission.getDrone() != null) {
			for (SendLocationTrackingCronTask sendLocationCronTask : cronTrackingTasks) {
				if (sendLocationCronTask.getMissionIdentifier() == mission.getMissionIdentifier()) {
					sendLocationCronTask.addCount();
					hasRun = true;
				}
			}
			if (!hasRun) {
				SendLocationTrackingCronTask slct = new SendLocationTrackingCronTask(mission.getId(),
						missionService, 1, missionIdentifier, password, this.simpMessagingTemplate);
				Timer timer = new Timer();
				timer.schedule(slct, 0, 2000);
				cronTrackingTasks.add(slct);
			}
		}

	}

//	@EventListener
//	  public void onApplicationEvent(SessionSubscribeEvent event) {
//		org.springframework.messaging.Message<byte[]> message = event.getMessage();
//	      StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//	      System.out.println(accessor);
//	      StompCommand command = accessor.getCommand();
//	      if (command.equals(StompCommand.SUBSCRIBE)) {
//	          String sessionId = accessor.getSessionId();
//	          String stompSubscriptionId = accessor.getSubscriptionId();
//	          String destination = accessor.getDestination();
//	          // Handle subscription event here
//	          // e.g. send welcome message to *destination*
//	  		throw new IllegalArgumentException("Wrong password!");
//
//	       }
//	  }

	@SubscribeMapping("/location/{droneId}")
	public void subscribeToLocation(@DestinationVariable int droneId) {
//    	System.out.println("hi");
//    	this.simpMessagingTemplate.convertAndSend("/app/location/1", missionService.findById(4));
//
//    	return "hi";
		boolean hasRun = false;
//System.out.println("105" + accessor.getDestination());

		for (SendLocationCronTask sendLocationCronTask : cronTasks) {
			if (sendLocationCronTask.getDroneId() == droneId) {
				sendLocationCronTask.addCount();
				hasRun = true;
			}
		}
		if (!hasRun) {
			SendLocationCronTask slct = new SendLocationCronTask(droneId, droneService, 1, this.simpMessagingTemplate);
			Timer timer = new Timer();
			timer.schedule(slct, 0, 2000);
			cronTasks.add(slct);
		}
	}

	@MessageMapping("/unsubscribeLocation")
	public void unsubscribe(@RequestBody int droneId) {
		for (SendLocationCronTask sendLocationCronTask : cronTasks) {
			if (sendLocationCronTask.getDroneId() == droneId) {
				sendLocationCronTask.subtractCount();
			}
		}
	}
	
	@MessageMapping("/emergencyLanding")
	public void emergencyLanding(@RequestBody int droneId) {
		droneService.stopMissionAndLand(droneId);
	}
	
	@MessageMapping("/emergencyReturnToLand")
	public void emergencyReturnToLand(@RequestBody int droneId) {
		droneService.stopMissionAndReturnToLaunch(droneId);
	}
	
	@MessageMapping("/changeEmergency")
	public void changeEmergencyMode(@RequestBody int droneId) {
		droneService.changeEmergencyMode(droneId);
	}

//	@MessageMapping("/getLocation")
//    @SendTo("/chat/location")
//	public List<Drone> getLocation(@RequestBody List<Integer> droneIds) {
//		List<Drone> result = new ArrayList<Drone>();
//		for (int droneId : droneIds) {
//			Drone d = droneService.getById(droneId);
//			result.add(d);
//		}
//		return result;
////		io.mavsdk.System d = sdkServerService.getSystem(1);
////		return locationService.getCurrentLocation(d);
//	}
//	
//	
//	@MessageMapping("/send")
//    @SendTo("/chat/sendMessage")
//    public Location sendMessage(@RequestBody int droneId) {
//        return getLocation(droneId);
//    }
//	
//	@MessageMapping("/takeoffandland")
//    public void takeoffAndLand(@RequestBody int droneId) throws IOException {
//		io.mavsdk.System d = sdkServerService.getSystem(droneId);
//		droneService.takeoffAndLand(d);
//    }
//	
//	@MessageMapping("/uploadmission")
//    public void uploadMisson(@RequestBody Mission mission) throws Exception {
//		System.out.println(mission);
//		io.mavsdk.System d = sdkServerService.getSystem(mission.getDroneId());
//		missionService.uploadMission(mission, d);
//    }
//	
//	@MessageMapping("/runmission")
//    public void flyMission(@RequestBody int droneId) throws IOException {
//		io.mavsdk.System d = sdkServerService.getSystem(droneId);
//		droneService.runMission(d, false);
//    }
//	
//	@MessageMapping("/runmissionrtl")
//    public void flyMissionRtl(@RequestBody int droneId) throws IOException {
//		io.mavsdk.System d = sdkServerService.getSystem(droneId);
//        droneService.runMission(d, true);
//    }
//	
//	@MessageMapping("/clearmission")
//    public void clearMission(@RequestBody int droneId) throws IOException {
//		io.mavsdk.System d = sdkServerService.getSystem(droneId);
//        missionService.clearMission(d);
//    }
//	
//	@MessageMapping("/disconnect")
//	public void disconnect(@RequestBody int droneId) {
//		io.mavsdk.System d = sdkServerService.getSystem(droneId);
////		droneService.disconnect(d);
//	}
//	
//	@MessageMapping("/stopmission")
//	public void stopMission(@RequestBody int droneId) {
//		io.mavsdk.System d = sdkServerService.getSystem(droneId);
//		droneService.stopMission(d);
//	}

}
