package model;

public class Product{
	private int id;
	private String product;
	private int price;
	private int quantity;
	
	public Product() {
		
	}
	public Product(String[] prodObj) {
		this.id = Integer.parseInt(prodObj[0]);
		this.product = prodObj[1];
		this.price = Integer.parseInt(prodObj[2]);
		this.quantity = Integer.parseInt(prodObj[3]);
	}
	
	public Product(String pname, int price, int q) {
		this.price= price;
		this.product = pname;
		this.quantity = q;
	}
	public Product(int pid) {
		this.id = pid;
	}
	public Product(int pid, String pname, int price, int quant) {
		this.id = pid;
		this.product = pname;
		this.price = price;
		this.quantity = quant;
	}
	
	public int getId() {
		return this.id;
	}
	public String getProduct() {
		return this.product;
	}
	public int getPrice() {
		return this.price;
	}

	public int getQuantity() {
		return this.quantity;
	}

}
