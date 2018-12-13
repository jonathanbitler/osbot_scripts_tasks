package osbot_scripts.login;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.database.DatabaseTest;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.qp7.progress.ThreadDemo;

public class LoginHandler {

	private static ThreadDemo demo;

	public static LoginEvent login(MethodProvider p, String parameters) {
		LoginEvent loginEvent = null;

		if (!p.getClient().isLoggedIn()) {
			String username = null;
			String password = null;
			String accountStage = null;
			String tradeWith = null;
			String emailTradeWith = null;
			String actualUsername = null;

			String dbUsername = null, dbName = null, dbPassword = null;
			int pid = 0;
			if (parameters != null) {
				String[] params = parameters.split("_"); // split the _ character!!!!!!
				
				//Email and password
				username = params[0];
				password = params[1];
				
				//Pid
				pid = Integer.parseInt(params[2]);
				
				//Account stage
				accountStage = params[3];
				
				//Username and NOT e-mail
				actualUsername = params[4];

				//DB
				dbUsername = params[5];
				dbName = params[6];
				dbPassword = params[7];

				//For muling, person to trade with
				if (params.length >= 9) {
					//DB
					dbUsername = params[4];
					dbName = params[5];
					dbPassword = params[6];
					
					//Mule trading
					tradeWith = params[7];
					emailTradeWith = params[8];
				}
				p.log(username + " " + password);

			}
			loginEvent = new LoginEvent(username, password, pid, accountStage, p);
			
			//Database settings
			loginEvent.setDbName(dbName);
			loginEvent.setDbPassword(dbPassword);
			loginEvent.setDbUsername(dbUsername);
			
			if (actualUsername != null) {
				loginEvent.setActualUsername(actualUsername);
			}
			if (tradeWith != null) {
				loginEvent.setTradeWith(tradeWith);
				loginEvent.setEmailTradeWith(emailTradeWith);
			}

			p.getBot().addLoginListener(loginEvent);
			p.execute(loginEvent);
		}

		demo = new ThreadDemo();
		demo.exchangeContext(p.getBot());
		demo.setLoginEvent(loginEvent);
		demo.paramaters = parameters;

		new Thread(demo).start();

		return loginEvent;
	}
}
