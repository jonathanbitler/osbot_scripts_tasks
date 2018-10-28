package osbot_scripts.database;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osbot.rs07.script.MethodProvider;

public class DatabaseUtilities {

	private static final String API_KEY = "SDFJNLKDASNFJK798283423NJASKF";

	private static final String LINK = "http://localhost:8000/osbot/api";

	/**
	 * Updates the account status (banned, locked etc) in the database
	 * 
	 * @param newPassword
	 * @param accountId
	 * @return
	 */
	public static boolean updateAccountStatusInDatabase(MethodProvider prov, String status, String email) {
		try {

			String urlParameters = "?status=" + URLEncoder.encode(status, "UTF-8") + "&email="
					+ URLEncoder.encode(email, "UTF-8") + "&qp="
					+ URLEncoder.encode("" + prov.getQuests().getQuestPoints(), "UTF-8");

			prov.log(LINK + "" + urlParameters);
			sendGet(LINK + "" + urlParameters);

			return true;

		} catch (Exception e) {
			prov.log(e);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Updates stage progress for quests etc.
	 * 
	 * @param prov
	 * @param accountStatus
	 * @param number
	 * @param email
	 * @return
	 */
	public static boolean updateStageProgress(MethodProvider prov, String accountStatus, int number, String email) {
		try {
			if (prov.getQuests().getQuestPoints() >= 7) {
				accountStatus = "UNKNOWN";
			}
			String urlParameters = "?accountStage=" + URLEncoder.encode(accountStatus, "UTF-8") + "&email="
					+ URLEncoder.encode(email, "UTF-8") + "&number=" + URLEncoder.encode("" + number, "UTF-8");

			prov.log(LINK + "" + urlParameters);
			sendGet(LINK + "" + urlParameters);

			return true;

		} catch (Exception e) {
			prov.log(e);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param prov
	 * @param email
	 * @return
	 */
	public static String getQuestProgress(MethodProvider prov, String email) {
		try {

			String urlParameters = "?email=" + URLEncoder.encode(email, "UTF-8");

			prov.log(LINK + "/quest" + "" + urlParameters);

			StringBuilder name = new StringBuilder();

			name.append(sendGet(LINK + "/quest" + "" + urlParameters));

			return name.toString();
		} catch (Exception e) {
			prov.log(e);
			e.printStackTrace();
			return null;
		}
	}

	private static String sendGet(String url) throws Exception {
		String USER_AGENT = "Mozilla/5.0";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		return response.toString();

	}

	public static String executePost(String targetURL, String urlParameters) {
		HttpURLConnection connection = null;

		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
