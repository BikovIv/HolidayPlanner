package holidayplanner.HolidayPlanner.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordServices {
	public static String hashMe(String word) {

		StringBuilder sb = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(word.getBytes());

			byte[] bytes = md.digest();

			for (int i = 0; i < bytes.length; i++) {
				sb.append((char) bytes[i]);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}	
}
