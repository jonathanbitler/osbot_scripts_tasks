package osbot_scripts.testscripts;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.database.DatabaseUtilities;

public class Break {

	private static final String LINK = "http://localhost:8000/osbot/api";

	public static void main(String args[]) {
//		DatabaseUtilities.updateAccountBreakTill(null, "alphabearman+433546@protonmail.com", 30);
		
		String date = "2018-11-01 18:30:16";
		
		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.MINUTE, 30);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		try {
			Date date2 = sdf.parse(date);
			calendar.setTime(date2);
			
			System.out.println(sdf.format(calendar.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String dateTime = sdf.format(calendar.getTime());
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(new Date());
		String dateTime2 = sdf.format(calendar.getTime());
		
		System.out.println(calendar2.after(calendar));
	}

	public static boolean updateAccountBreakTill(MethodProvider prov, String email, int minutesBreak) {
		try {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.HOUR, 1);
			
			System.out.println(calendar.getTime().getTime());

//			String urlParameters = "?email=" + URLEncoder.encode(email, "UTF-8") + "&breaktill="
//					+ URLEncoder.encode(Integer.toString(value), "UTF-8");

//			prov.log(LINK + "" + urlParameters);
//			DatabaseUtilities.sendGet(LINK + "" + urlParameters);

			return true;

		} catch (Exception e) {
			prov.log(e);
			e.printStackTrace();
			return false;
		}
	}
}
