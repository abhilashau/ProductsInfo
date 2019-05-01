package ProductsPkg;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("/product")
public class ConsumeProductsWebService {

	public static ArrayList<Product> productList = new ArrayList<Product>();
	public enum Colors {
	    RED, GREEN, BLUE;  
	}
	public ArrayList<Product> getProductsList() {
		try {
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(
					"https://jl-nonprod-syst.apigee.net/v1/categories/600001506/products?key=2ALHCAAs6ikGRBoy6eTHA58RaG097Fma");
			String responseAsString = target.request(MediaType.APPLICATION_JSON).get(String.class);
			JSONParser parse = new JSONParser();
			JSONObject jobj = (JSONObject) parse.parse(responseAsString);

			JSONArray jsonarr_1 = (JSONArray) jobj.get("products");
			  for(int i=0;i<jsonarr_1.size();i++) { //Store the JSON objects in an array
				  productList.clear();
				  jsonarr_1.forEach(prod -> parseTestData((JSONObject) prod));
			  }
			  System.out.println("productList : "+productList.size());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return productList;
	}

	private void parseTestData(JSONObject product) {
		// Get product object within list
		Product productInfo = new Product();
		String productId = (String) product.get("productId");
		productInfo.setProductId(productId);

		String title = (String) product.get("title");
		productInfo.setTitle(title);

		JSONObject price = (JSONObject) product.get("price");
		String was = (String) price.get("was");
		String then1 = (String) price.get("then1");
		String then2 = (String) price.get("then2");
		Object now = (Object) price.get("now");
		String currency = (String) price.get("currency");
		Float newNow = (float) 0;
		String nowPriceCalc = null;
		String nowPrice = null;
		int reduction = 0;
		if(currency.equalsIgnoreCase("GBP")) {
			currency = "£";
		}
		if (now != null) {
			if (now.toString().contains("to")) {
				nowPrice = now.toString().substring(now.toString().lastIndexOf(":") + 2,
						now.toString().lastIndexOf("\""));
			} else {
				nowPrice = now.toString();
			}
			newNow = new Float(nowPrice);
			if (nowPrice.indexOf(".") > 1 && newNow > 10) {
				nowPriceCalc = nowPrice.substring(0, nowPrice.indexOf("."));
			} else {
				nowPriceCalc = nowPrice;
			}
		}
		if (was != null && !was.equals("")) {
			String wasVal = was.substring(0, was.indexOf("."));
			String nowVal = nowPrice.substring(0, nowPrice.indexOf("."));
			if (Integer.parseInt(wasVal) > Integer.parseInt(nowVal)) {
				reduction = Integer.parseInt(wasVal) - Integer.parseInt(nowVal);
			}
		}
		nowPriceCalc = currency + nowPriceCalc;

		String priceLabel = getLabelType(was, currency, nowPrice, then1, then2);

		String hexColor = null;
		JSONArray colorSwatches = (JSONArray) product.get("colorSwatches");
		ArrayList<ColorsObj> colorsList = new ArrayList<ColorsObj>();
		ColorsObj colorsVal = new ColorsObj();
		for (int k = 0; k < colorSwatches.size(); k++) {
			JSONObject swatches = (JSONObject) colorSwatches.get(k);
			String color = (String) swatches.get("basicColor");
			Color colors = null;
			for (Colors colour : Colors.values()) {
				if (colour.name().equalsIgnoreCase(color)) {
					if ("RED".equalsIgnoreCase(colour.name()))
						colors = Color.RED;
					if ("GREEN".equalsIgnoreCase(colour.name()))
						colors = Color.GREEN;
					if ("BLUE".equalsIgnoreCase(colour.name()))
						colors = Color.BLUE;

					hexColor = getRGBHexadecimal(colors);
				}
			}
			Object skuidObj = (Object) swatches.get("skuid");
			String skuid = null;
			if (skuidObj != null) {
				skuid = skuidObj.toString();
			}
			colorsVal.setColor(color);
			colorsVal.setRbgColor(hexColor);
			colorsVal.setSkuid(skuid);
			colorsList.add(colorsVal);
		}
		productInfo.setColorSwatches(colorsList);
		productInfo.setNowPrice(nowPriceCalc);
		productInfo.setPriceLabel(priceLabel);
		if (reduction > 0) {
			productList.add(productInfo);
		}
	}
	
	static String getRGBHexadecimal(Color color) {
		String hex = "#"+Integer.toHexString(color.getRGB()).substring(2);
		return hex;
	}
	 static String getLabelType(String was,String currency,String nowPriceCalc,String then1,String then2) {
		 String priceLabel =  null; 
		if(nowPriceCalc != null && was != null && (then1.equals("")) && (then2.equals(""))) {
			priceLabel = "Was "+currency+was+", now "+currency+nowPriceCalc;
		}
		else if(nowPriceCalc != null && was != null && !(then1.equals("") || then2.equals(""))) {
			priceLabel = "Was "+currency+was+", now "+currency+nowPriceCalc+", then "+currency+then2 != null ? then2 : then1;
		}
		else {
			priceLabel = "Was "+currency+was+", now "+currency+nowPriceCalc;
		}
		return priceLabel;
	}
	 
	
	  @GET
	  
	  @Produces({ MediaType.APPLICATION_JSON }) 
	  public List<Product> getProducts(){ 
		  ArrayList<Product> productList = getProductsList();
	      System.out.println("Successful!!!!!!!!!!!!!!!!"); 
	      return productList; 
	  }
	 
}
