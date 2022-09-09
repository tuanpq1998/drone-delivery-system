package hust.tuanpq.finalproject.dronecontrol.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;


public class SdkServer {

	private  String mavSdkServerDir;
	
	private String mavSdkServerFilename;
	
	private int droneId;
	
	private String ipDrone;
	
	private int portDrone;
	
	private int portSystem;
		
	private Process process;
	
	public int getDroneId() {
		return droneId;
	}

	public void setDroneId(int droneId) {
		this.droneId = droneId;
	}

	public SdkServer(int droneId, String ipDrone, int portDrone, String mavSdkServerDir, String mavSdkServerFilename) {
		super();
		this.droneId = droneId;
		this.ipDrone = ipDrone;
		this.portDrone = portDrone;
		this.mavSdkServerDir = mavSdkServerDir;
		this.mavSdkServerFilename = mavSdkServerFilename; 
	}

	@Override
	public String toString() {
		return "SdkServer [mavSdkServerDir=" + mavSdkServerDir + ", mavSdkServerFilename=" + mavSdkServerFilename
				+ ", droneId=" + droneId + ", ipDrone=" + ipDrone + ", portDrone=" + portDrone + ", portSystem="
				+ portSystem + ", process=" + process + "]";
	}

	public String getIpDrone() {
		return ipDrone;
	}

	public void setIpDrone(String ipDrone) {
		this.ipDrone = ipDrone;
	}

	public int getPortDrone() {
		return portDrone;
	}

	public void setPortDrone(int portDrone) {
		this.portDrone = portDrone;
	}

	public int getPortSystem() {
		return portSystem;
	}

	public void setPortSystem(int portSystem) {
		this.portSystem = portSystem;
	}

	public void destroy() {
		System.out.println("MAVSDK Server is disposing");
		process.destroy();
	}
	
	public boolean isRunning () {
		return process.isAlive();
	}
	
	public String generateIpDrone() {
		if (getIpDrone().equals("localhost")) {
			return "";
		}
		return getIpDrone();
	}
	
	public String generatePortSystem() {
		int portDroneNumb = getPortDrone();
		setPortSystem(((portDroneNumb - 14540 + 50050)));
		return Integer.toString(getPortSystem());
	}
	
	public void init() throws IOException, InterruptedException {
		List<String> commands = new ArrayList<>();
		commands.add("./"+ this.mavSdkServerFilename); 
		commands.add("udp://"+ generateIpDrone() +":"+ this.portDrone);
		commands.add("-p");
		commands.add(generatePortSystem());

		ProcessBuilder pb = new ProcessBuilder(commands);
        File file = new ClassPathResource(this.mavSdkServerDir, SdkServer.class.getClassLoader()).getFile();

        pb.directory(new File(file.getAbsolutePath()));
        pb.redirectErrorStream(true);
        
        process = pb.start();
         
        System.out.println("Mavsdk server is starting at " + "udp://"+ generateIpDrone() +":"+ this.portDrone );
        // for reading the ouput from stream
//        BufferedReader stdInput = new BufferedReader(new
//                InputStreamReader(process.getInputStream()));
//        String s = null;
//        while ((s = stdInput.readLine()) != null) {
//            System.out.println(s);
//        }
        System.out.println("Done!");
	}
}
