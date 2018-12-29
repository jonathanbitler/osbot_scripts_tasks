package osbot_scripts.ge.api;

import java.io.IOException;

import osbot_scripts.framework.GEPrice;

public class ApiTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		GeApi api = new GeApi();
//		
//		api.getExchangeItem("Clay").ifPresent(System.out::println);
		
//		ItemResource itemResource = new ItemResource(434);
//		System.out.println(itemResource.getPrice());
		

		GEPrice price = new GEPrice();
		try {
			System.out.println(price.getBuyingPrice(434));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
