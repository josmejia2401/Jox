package com.bay.common.util;

import java.util.Random;

public class Commons {
	private static final String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	public static boolean isValidEmailAddress(String email) {
		if (email != null && !email.isEmpty()) {
			java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
			java.util.regex.Matcher m = p.matcher(email);
			return m.matches();
		} else {
			return false;
		}
	}

	public static int generatedPin() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}
}
