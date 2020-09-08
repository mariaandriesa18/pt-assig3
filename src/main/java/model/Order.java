package model;


public class Order{
	private int id;
	private int cid;
	private int pid;
	private int quantity;
	
	public Order() {
		
	}
	
	public Order(int oid) {
		this.id = oid;
	}
	public Order(int oid, int opid,int ocid, int oq) {
		this.id = oid;
		this.pid= opid;
		this.cid = ocid;
		this.quantity = oq;
	}
	public Order( int opid,int ocid, int oq) {
		this.pid = opid;
		this.cid = ocid;
		this.quantity = oq;
	}
	public int getId() {
		return this.id;
	}
	public int getOpid() {
		return this.pid;
	}
	public int getOcid() {
		return cid;
	}
	public int getOquantity() {
		return this.quantity;
	}
	
	
}
