package osbot_scripts.framework;

public enum TabWid {

	COMBAT(548, 48, -1),
	QUEST(548, 50, -1),
	INVENTORY(548, 51, -1),
	EQUIPMENT(548, 52, -1),
	PRAYER(548, 53, -1),
	MAGIC(548, 54, -1),
	CLANCHAT(548, 31, -1),
	FRIENDS(548, 33, -1),
	ACCOUNT_MANAGER(548, 32, -1),
	LOGOUT(548, 34, -1),
	SETTINGS(548, 35, -1),
	EMOTES(548, 36, -1),
	MUSIC(548, 37, -1),
	SKILLS(548, 49, -1);

	private TabWid(int main, int sub, int subsub) {
		this.setMain(main);
		this.setSub(sub);
		this.setSubsub(subsub);
	}

	/**
	 * @return the main
	 */
	public int getMain() {
		return main;
	}

	/**
	 * @param main the main to set
	 */
	public void setMain(int main) {
		this.main = main;
	}

	/**
	 * @return the sub
	 */
	public int getSub() {
		return sub;
	}

	/**
	 * @param sub the sub to set
	 */
	public void setSub(int sub) {
		this.sub = sub;
	}

	/**
	 * @return the subsub
	 */
	public int getSubsub() {
		return subsub;
	}

	/**
	 * @param subsub the subsub to set
	 */
	public void setSubsub(int subsub) {
		this.subsub = subsub;
	}

	private int main, sub, subsub;

}
