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
		try {
			setApi(api);
			if (conn == null || conn.isClosed()) {
				api.log("Trying to connect with DB!");

				conn = connect(login);
			}
			api.log("Returning conneciton: " + conn);
			return conn;
		} catch (SQLException e) {
			api.log(exceptionToString(e));
		}
		return conn;
	}

	public Connection connect(LoginEvent login) {
		String host = "131.153.18.105:3306";
		String db = login.getDbName().replaceAll("%", "_");
		getApi().log("db: " + db);
		String user = login.getDbUsername().replaceAll("%", "_");
		getApi().log("user: " + user);
		String pass = login.getDbPassword().replaceAll("%", "_");
		getApi().log("pass: " + pass);

		String connStr = String.format(
				"jdbc:mysql://%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
				host, db);

		try {
			Connection conn2 = DriverManager.getConnection(connStr, user, pass);
			conn = conn2;
		} catch (SQLException e) {
			api.log(exceptionToString(e));
			api.log("Failed to connect to DB!");
			BotCommands.waitBeforeKill(api, "CANT CONNECT");
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
