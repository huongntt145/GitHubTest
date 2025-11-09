package com.github.desktop.auto.test;
import static org.testng.Assert.fail;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
public class Utils {
	public static float maxWatchers = 0;

	/*
	 * convert the string contains character [,] to Integer, example: 3,432 -> 3432,
	 * 10,322 -> 10322. Get issues number of each repository
	 */
	public static int getIssuesNumber(String strNum) {
		if (strNum == null) {
			fail("String Number is null");
		}
		try {
			if (strNum.contains(",")) {
				strNum = strNum.replace(",", "");
				int issueNumber = Integer.parseInt(strNum);
				return issueNumber;
			}
			return Integer.parseInt(strNum);

		} catch (NumberFormatException e) {
			System.out.println("Number format is invalid " + strNum);
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * convert the string contains character [k] to Float, example: 1.3k -> 1300.
	 * Get watchers number of each repository
	 */
	public static float gettWatchersNumber(String strNum) {
		if (strNum == null)
			fail("String Number is null");
		try {
			if (strNum.contains("k")) {
				strNum = strNum.substring(0, strNum.length() - 1);
				float watcherNumber = Float.parseFloat(strNum) * 1000;
				return watcherNumber;
			}
			return Integer.parseInt(strNum);
		} catch (NumberFormatException e) {
			System.out.println("Number format is invalid " + strNum);
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * find out [key] which its [value] is max in HashMap
	 */
	public static String getKeyHasMaxValue(HashMap<String, Float> watcherNumbers) {
		float maxValueInMap = (Collections.max(watcherNumbers.values()));
		for (Entry<String, Float> entry : watcherNumbers.entrySet()) {
			if (entry.getValue() == maxValueInMap) {
				maxWatchers = entry.getValue();
				return entry.getKey();
			}
		}
		return null;
	}

}
