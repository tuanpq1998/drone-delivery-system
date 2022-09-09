package hust.tuanpq.finalproject.dronecontrol.model;

import java.util.ArrayList;
import java.util.List;

public class Mission {

	private List<Location> locations = new ArrayList<Location>();
	
	private boolean isReturnToLaunch;

	private int droneId;
	
	public int getDroneId() {
		return droneId;
	}

	public void setDroneId(int droneId) {
		this.droneId = droneId;
	}

	public boolean isReturnToLaunch() {
		return isReturnToLaunch;
	}

	public void setReturnToLaunch(boolean isReturnToLaunch) {
		this.isReturnToLaunch = isReturnToLaunch;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public Mission(List<Location> locations) {
		super();
		this.locations = locations;
	}

	public Mission() {
		super();
	}

	@Override
	public String toString() {
		return "Mission [locations=" + locations + "]";
	}
	
	public boolean addLocation(Location location) {
		if (this.locations == null)
			this.locations = new ArrayList<Location>();
		return this.locations.add(location);
	}
}
