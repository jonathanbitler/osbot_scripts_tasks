package osbot_scripts.database;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.framework.AccountStage;

public class DatabaseTest {
//
//	public static boolean updateAccountStage(MethodProvider api, AccountStage stage, String email) {
//		try {
//			String query = "UPDATE account SET account_stage = ? WHERE email=?";
//			api.log("1 "+DatabaseConnection.getDatabase());
//			api.log("2 "+DatabaseConnection.getDatabase().getConnection(api));
//			PreparedStatement preparedStmt = DatabaseConnection.getDatabase().getConnection(api).prepareStatement(query);
//			preparedStmt.setString(1, stage.name());
//			preparedStmt.setString(2, email);
//
//			// execute the java preparedstatement
//			preparedStmt.executeUpdate();
//			preparedStmt.close();
//
//			api.log("Updated account in database with new stage!");
//
//			return true;
//
//		} catch (Exception e) {
//			StringWriter errors = new StringWriter();
//			e.printStackTrace(new PrintWriter(errors));
//			
//			api.log(errors.toString());
//			
//			e.printStackTrace();
//			return false;
//		}
//	}
	
}
