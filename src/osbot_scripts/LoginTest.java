package osbot_scripts;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.events.LoginEvent;

@ScriptManifest(author = "Explv", name = "LoginExample", info = "", logo = "", version = 0.1)
public class LoginTest extends Script {
    
    private LoginEvent loginEvent;
    
    public void onStart() {
    	String username = null;
    	String password = null;
    	if (getParameters() != null) {
            String[] params = getParameters().split("_"); //split the _ character!!!!!!
            username = params[0];
            password = params[1];
            log(username+" "+password);
            
    	}
        loginEvent = new LoginEvent(username, password);
        getBot().addLoginListener(loginEvent);
        execute(loginEvent);
    }

    public int onLoop() throws InterruptedException {
        return 0;
    }
}