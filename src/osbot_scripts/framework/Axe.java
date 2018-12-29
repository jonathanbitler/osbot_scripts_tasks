package osbot_scripts.framework;

public enum Axe {
	
	BRONZE(30),
	IRON(40),
	STEEL(60),
	MITHRIL(150),
	ADAMANT(2000),
	RUNE(5000);

	private Axe(int price) {
		this.setPrice(price);
	}
	
	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	private int price;
}
