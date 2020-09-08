package model;

public class Client {
		private int id;
		private String name;
		private String address;
		
		public Client() {
			
		}
		public Client(int id) {
			this.id = id;
		}
		
		public Client(int id, String n, String a) {
			this.id = id;
			this.name= n;
			this.address = a;
		}
		
		public Client(String n, String a) {
			this.address = a;
			this.name = n;
		}
		
		public int getId() {
			return this.id;
		}
		public String getName() {
			return this.name;
		}

		public String getAddress() {
			return this.address;
		}
		
		
		/*
	 * @Override public String toString() { return "Client [id=" + id + ", name=" +
	 * name + ", address=" + address + "]"; }
	 */
}
