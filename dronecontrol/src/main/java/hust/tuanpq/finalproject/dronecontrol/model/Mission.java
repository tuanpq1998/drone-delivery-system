package hust.tuanpq.finalproject.dronecontrol.model;

import java.util.ArrayList;
import java.util.List;

public class Mission {

	private List<ExtraDroneInfo> locations = new ArrayList<ExtraDroneInfo>();
	
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

	public List<ExtraDroneInfo> getLocations() {
		return locations;
	}

	public void setLocations(List<ExtraDroneInfo> locations) {
		this.locations = locations;
	}

	public Mission(List<ExtraDroneInfo> locations) {
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
	
	public boolean addLocation(ExtraDroneInfo location) {
		if (this.locations == null)
			this.locations = new ArrayList<ExtraDroneInfo>();
		return this.locations.add(location);
	}
}
