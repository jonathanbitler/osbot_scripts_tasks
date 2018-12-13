package osbot_scripts.framework;

public enum Pickaxe {
	
	BRONZE(500),
	IRON(2500),
	STEEL(3500),
	MITHRIL(5500),
	ADAMANT(9500),
	RUNE(27500);

	private Pickaxe(int price) {
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
