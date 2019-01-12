package osbot_scripts.scripttypes.templates;

import java.util.ArrayList;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

public interface WoodcuttingLocationTemplate {

	public Area getBankAreaLocation();

	public ArrayList<Position> getPositionsFromBankToWoodcuttingSpot();

	public Area getAreaOfWoodcuttingLocation();

	public Area getAreaOfOperating();

	public ArrayList<Position> getPositionsFromWoodcuttingSpotToBank();

	public ArrayList<Position> getPositionsFromGeToBank();

	public ArrayList<Position> getPositionsFromGeToWoodcuttingPosition();

}
