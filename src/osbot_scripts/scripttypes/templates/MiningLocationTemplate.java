package osbot_scripts.scripttypes.templates;

import java.util.ArrayList;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

public interface MiningLocationTemplate {

	public Area getBankAreaLocation();
	
	public ArrayList<Position> gePositionsFromBankToMiningSpot();
	
	public ArrayList<Position> getExactMiningSpotPosition();
	
	public Area exactMiningPositionArea();
	
	public Area getAreaOfMiningLocation();
	
	public Area getAreaOfOperating();
	
	public ArrayList<Position> getPositionsFromMiningSpotToBank();

	public ArrayList<Position> getPositionsFromGeToBank();
	
	public ArrayList<Position> getPositionsFromGeToMiningPosition();
	
	
	
	
}
