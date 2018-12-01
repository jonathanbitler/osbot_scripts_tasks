package osbot_scripts.login;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.events.LoginEvent;
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
			int pid = 0;
			if (parameters != null) {
				String[] params = parameters.split("_"); // split the _ character!!!!!!
				username = params[0];
				password = params[1];
				pid = Integer.parseInt(params[2]);
				accountStage = params[3];
				actualUsername = params[4];
				if (params.length > 5) {
					tradeWith = params[4];
					emailTradeWith = params[5];
				}
				p.log(username + " " + password);

			}
			loginEvent = new LoginEvent(username, password, pid, accountStage);
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
		new Thread(demo).start();
		
		return loginEvent;
	}
}
