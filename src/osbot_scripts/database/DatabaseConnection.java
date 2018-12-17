package osbot_scripts.database;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.events.LoginEvent;

public class DatabaseConnection {

	private MethodProvider api;

	public static DatabaseConnection con = new DatabaseConnection();

	public static DatabaseConnection getDatabase() {
		return con;
	}

	public Connection conn;

	public Connection getConnection(MethodProvider api, LoginEvent login) {
		// if (conn == null || conn.isClosed()) {
		try {
			DatabaseConnection con = new DatabaseConnection();
			con.setApi(api);
			return conn = con.connect(login);
		} catch (Exception e) {
			api.log(exceptionToString(e));
			BotCommands.waitBeforeKill(api, "CANT MAKE CONNECTION");
		}
		return null;
	}

	// public ResultSet getResult(String query) {
	// try {
	// PreparedStatement statement = getConnection().prepareStatement(query);
	//
	// ResultSet resultSet = statement.executeQuery(query);
	// return resultSet;
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	// connect database
	public Connection connect(LoginEvent login) {
		if (conn == null) {
			String host = "131.153.18.105:3306";
			String db = login.getDbName().replaceAll("%", "_");
			getApi().log("db: " + db);
			// "saluss1q_001_dragon";
			String user = login.getDbUsername().replaceAll("%", "_");
			getApi().log("user: " + user);
			// "saluss1q_osbot";
			String pass = login.getDbPassword().replaceAll("%", "_");
			getApi().log("pass: " + pass);
			// "pB4864EnHMcVJt9PiycLYq6U7e6ZUjc5VYn1vrBe";

			String connStr = String.format(
					"jdbc:mysql://%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					host, db);

			try {
				Connection conn2 = DriverManager.getConnection(connStr, user, pass);
				conn = conn2;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				api.log(exceptionToString(e));
				BotCommands.waitBeforeKill(api, "CANT CONNECT");
			}
			// Class.forName(DATABASE_DRIVER);
			// connection = DriverManager.getConnection(DATABASE_URL, getProperties());
		}
		return conn;
	}

	public String exceptionToString(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	// disconnect database
	public void disconnect() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				api.log(exceptionToString(e));
			}
		}
	}

	public static void main(String args[]) {

	}

	/**
	 * @return the api
	 */
	public MethodProvider getApi() {
		return api;
	}

	/**
	 * @param api
	 *            the api to set
	 */
	public void setApi(MethodProvider api) {
		this.api = api;
	}

}
