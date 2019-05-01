package ProductsPkg;

import java.util.ArrayList;

public class Product {
	String productId;
	String title;
	ArrayList<ColorsObj> colorSwatches = null;
	String nowPrice;
	String priceLabel;
	
	public Product() {
	}
	
	public Product(String productId, String title, ArrayList<ColorsObj> colorSwatches, String nowPrice,
			String priceLabel) {
		super();
		this.productId = productId;
		this.title = title;
		this.colorSwatches = colorSwatches;
		this.nowPrice = nowPrice;
		this.priceLabel = priceLabel;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<ColorsObj> getColorSwatches() {
		return colorSwatches;
	}
	public void setColorSwatches(ArrayList<ColorsObj> colorSwatches) {
		this.colorSwatches = colorSwatches;
	}
	public String getNowPrice() {
		return nowPrice;
	}
	public void setNowPrice(String nowPrice) {
		this.nowPrice = nowPrice;
	}
	public String getPriceLabel() {
		return priceLabel;
	}
	public void setPriceLabel(String priceLabel) {
		this.priceLabel = priceLabel;
	}
	
}
