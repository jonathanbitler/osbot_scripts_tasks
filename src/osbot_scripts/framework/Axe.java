package osbot_scripts.framework;

public enum Axe {
	
	BRONZE(300),
	IRON(600),
	STEEL(1500),
	MITHRIL(2000),
	ADAMANT(3000),
	RUNE(8000);

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
