package osbot_scripts.login;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.events.LoginEvent;

public class LoginHandler {

	public static LoginEvent login(MethodProvider p, String parameters) {
		LoginEvent loginEvent = null;
		
		if (!p.getClient().isLoggedIn()) {
			String username = null;
			String password = null;
			String accountStage = null;
			int pid = 0;
			if (parameters != null) {
				String[] params = parameters.split("_"); // split the _ character!!!!!!
				username = params[0];
				password = params[1];
				pid = Integer.parseInt(params[2]);
				accountStage = params[3];
				p.log(username + " " + password);

			}
			loginEvent = new LoginEvent(username, password, pid, accountStage);
			p.getBot().addLoginListener(loginEvent);
			p.execute(loginEvent);
		}
		return loginEvent;
	}
}
