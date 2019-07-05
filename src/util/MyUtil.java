package util;

import java.util.List;

public class MyUtil {
	
	public static void logMessage(Object obj, String str) {
		
		System.out.println("<<" + obj.getClass().getName() + ">> " + str);
		
	}
	
	public static void printList(Object obj, List list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println("-----" + i + "번째 요소 = " + list.get(i) + "-----");
		}
	}
	
	public static boolean isNumeric(String str) {
		
		try {
			Integer.parseInt(str);
		} catch(NumberFormatException e) {
			return false;
		} catch(NullPointerException e) {
			return false;
		}
		
		return true;
	}

}
