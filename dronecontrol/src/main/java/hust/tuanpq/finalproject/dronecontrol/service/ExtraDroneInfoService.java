package hust.tuanpq.finalproject.dronecontrol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.tuanpq.finalproject.dronecontrol.model.ExtraDroneInfo;
import hust.tuanpq.finalproject.dronecontrol.repository.LocationRepository;
import io.mavsdk.System;
import io.mavsdk.telemetry.Telemetry;
import io.mavsdk.telemetry.Telemetry.Battery;
import io.mavsdk.telemetry.Telemetry.Odometry;
import io.mavsdk.telemetry.Telemetry.Position;
import io.mavsdk.telemetry.Telemetry.VelocityNed;
import io.reactivex.Flowable;

@Service
public class ExtraDroneInfoService {
	
//	@Autowired
//	private LocationRepository locationRepository;
//	
	public ExtraDroneInfo getCurrentExtraInfo(System drone) {
		Telemetry telemetry = drone.getTelemetry();
		Flowable<Position> altitudeFlowable = telemetry.getPosition();
		Flowable<Battery> batteryFlowable = telemetry.getBattery();
		Flowable<VelocityNed> velocityNedFlowable = telemetry.getVelocityNed();
		
		Position p = altitudeFlowable.blockingFirst();
		Battery b = batteryFlowable.blockingFirst();
		VelocityNed v = velocityNedFlowable.blockingFirst();
		return new ExtraDroneInfo(p.getRelativeAltitudeM(), p.getLatitudeDeg(), p.getLongitudeDeg(), 
				b.getVoltageV(), b.getRemainingPercent(), (float)Math.sqrt(Math.pow(v.getEastMS(), 2)+Math.pow(v.getNorthMS(), 2)), v.getDownMS());
	}

	
	public float[] getBatteryInfo(System drone) {
		Flowable<Battery> batteryFlowable = drone.getTelemetry().getBattery();
		Battery b = batteryFlowable.blockingFirst();
		float[] result = {b.getVoltageV(), b.getRemainingPercent()};
		
		
		return result;
	}
	
	public float[] getOdometryVelocity(System drone) {
		Flowable<Odometry> odometryFlowable = drone.getTelemetry().getOdometry();
		Odometry b = odometryFlowable.blockingFirst();
		float[] result = {b.getVelocityBody().getXMS(), b.getVelocityBody().getYMS(), b.getVelocityBody().getZMS()};
		
		java.lang.System.out.println(result[0] +"|"+  result[1]+"|"+ result[2]);
		return result;
	}
	
}
