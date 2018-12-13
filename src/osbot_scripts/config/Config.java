package osbot_scripts.config;

import org.osbot.rs07.script.MethodProvider;

public class Config {

	public static final boolean NO_BREAK = true;

	public static final boolean TEST = false;

	public static final boolean NO_LOGIN = false;

	public static boolean doesntHaveAnyPickaxe(MethodProvider api) {
		return api.getBank().getAmount("Bronze pickaxe") <= 0 && api.getBank().getAmount("Iron pickaxe") <= 0
				&& api.getBank().getAmount("Steel pickaxe") <= 0 && api.getBank().getAmount("Mithril pickaxe") <= 0
				&& api.getBank().getAmount("Adamant pickaxe") <= 0 && api.getBank().getAmount("Rune pickaxe") <= 0
				&& api.getInventory().getAmount("Bronze pickaxe") <= 0
				&& api.getInventory().getAmount("Iron pickaxe") <= 0
				&& api.getInventory().getAmount("Steel pickaxe") <= 0
				&& api.getInventory().getAmount("Mithril pickaxe") <= 0
				&& api.getInventory().getAmount("Adamant pickaxe") <= 0
				&& api.getInventory().getAmount("Rune pickaxe") <= 0 && (api.getInventory().getItem(1266) == null)
				&& (api.getInventory().getItem(1268) == null) && (api.getInventory().getItem(1270) == null)
				&& (api.getInventory().getItem(1272) == null) && (api.getInventory().getItem(1274) == null)
				&& (api.getInventory().getItem(1276) == null);
	}
}
