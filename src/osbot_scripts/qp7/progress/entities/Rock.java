package osbot_scripts.qp7.progress.entities;

import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.MethodProvider;

public enum Rock {
	CLAY(new short[] { 6705 }), COPPER(new short[] { 4645, 4510 }), TIN(new short[] { 53 }), IRON(
			new short[] { 2576 }), SILVER(new short[] { 74 }), COAL(new short[] { 10508 }), GOLD(
					new short[] { 8885 }), MITHRIL(
							new short[] { -22239 }), ADAMANTITE(new short[] { 21662 }), RUNITE(new short[] { -31437 });

	private short[] colours;

	Rock(final short[] colours) {
		this.colours = colours;
	}
	

	public boolean hasOre(final Entity rockEntity, MethodProvider api) {
		if (rockEntity.getDefinition() == null || rockEntity == null) {
			return false;
		}

		short[] colours = rockEntity.getDefinition().getModifiedModelColors();

		if (colours == null) {
			return false;
		}
		
		for (short rockColour : this.colours) {
			for (short entityColour : colours) {
				if (rockColour == entityColour) {
					return true;
				}
			}
		}
		return false;
	}
}