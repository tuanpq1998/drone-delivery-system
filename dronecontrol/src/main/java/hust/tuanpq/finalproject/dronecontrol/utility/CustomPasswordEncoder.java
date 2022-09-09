package hust.tuanpq.finalproject.dronecontrol.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder {

	@Autowired
	private CustomPasswordUtility customPasswordUtility;
	
	public String encode(CharSequence rawPassword) {
	    return customPasswordUtility.encrypt(rawPassword.toString());
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
	    if (rawPassword != null && rawPassword.length() != 0 && encode(rawPassword).equals(encodedPassword)) {
	        return true;
	    }
	    return false;
	}
}
