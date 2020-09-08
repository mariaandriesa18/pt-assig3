package presentation;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import business_logic.*;
import model.*;
import presentation.Controller.*;

public class Controller {

	private ClientBLL clientBll = new ClientBLL();
	private ProductBLL productBll = new ProductBLL();
	private OrderBLL orderBll = new OrderBLL();
	private MainView view;
	public Controller(MainView vw) {
		this.view  =vw;
		
		getAllClientsTable();
		getAllProductsTable();
		getAllOrdersTable();
		//view.addTableDeleteActionListener(new TableDeleteListener());
		view.addClientInsActionListener(new ClientInsertionListener());
		view.addClientEditActionListener(new ClientEditListener());
		view.addClientDeleteActionListener(new ClientDeleteListener());
		view.addProductInsActionListener(new ProductInsertListener());
		view.addProductEditActionListener(new ProductEditListener());
		view.addProductDeleteActionListener(new ProductDeleteListener());
		view.addOrderInsActionListener(new OrderInsListener());
		view.addOrderEditActionListener(new OrderEditListener());
		view.addOrderDeleteActionListener(new OrderDeleteListener());
	}
	
	public void getAllClientsTable() {
		Client cl = new Client();
		String[][] clientData = clientBll.selectAll();
		for(int i = 1; i < clientData.length; i++) {
			view.insertClientRow(clientData[i]);
		}
	}
	public void getAllProductsTable() {
	
		String[][] productData = productBll.selectAll();
		 
		for(int i = 1; i < productData.length; i++) {
			view.insertProductRow(productData[i]);
		}
	}
	public void getAllOrdersTable() {
		String[][] orderData = orderBll.selectAll();
		for(int i = 1; i < orderData.length; i++) {
			view.insertOrderRow(orderData[i]);
		}
	}
	
	
	public class ClientInsertionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String cn = view.getClientName();
			String ca = view.getClientAddress();
			Client client1 = new Client(cn,ca);
			
			Integer id = clientBll.insertClient(client1);
			String[] data = new String[3];
			data[0] = id.toString();
			data[1] = cn;
			data[2] = ca;
			
			view.insertClientRow(data);
			view.removeClientTable();
			getAllClientsTable();
			
			view.clearAllTF();
		}
	}
	public class ClientEditListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String cn = view.getClientName();
			String ca = view.getClientAddress();
			
			if(view.getClientId().contentEquals("") || view.getClientAddress().contentEquals("") || view.getClientName().contentEquals("")) {
					JOptionPane.showMessageDialog(null, "Empty fields not allowed!");
			}else {
				Integer cid = Integer.parseInt(view.getClientId());
				clientBll.editClient(new Client(cid, cn, ca));
			}
			view.removeClientTable();
			getAllClientsTable();
			view.clearAllTF();
		}

	}
	
	public class ClientDeleteListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(!view.getClientId().contentEquals("")) {
				int cid = Integer.parseInt(view.getClientId());
				Client client3 = new Client(cid);
				clientBll.deleteClient(client3);
			}else {
				Integer selectedId = view.removeSelectedClient();
				if(selectedId != null) {
					Client client4 = new Client(selectedId);
					clientBll.deleteClient(client4);
				}else {
					System.out.println("SELECTED ID IS NULL");
				}
			}
			
			view.removeClientTable();
			getAllClientsTable();
			view.clearAllTF();
		}

	}
	public class OrderInsListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Integer opid = Integer.parseInt(view.getOrderProductId());
			Integer ocid = Integer.parseInt(view.getOrderClientID());
			Integer oquant = Integer.parseInt(view.getOrderQuantity());
			Order order1 = new Order(opid, ocid,oquant);
			String[] client = clientBll.findById(ocid);
			if(client == null) {
				JOptionPane.showMessageDialog(null, "The requested client doesn't exist!");
			}else if(productBll.findByID(opid) == null){
				JOptionPane.showMessageDialog(null, "The requested product doesn't exist!");
			}else {
				String[] prod = productBll.findByID(opid);
				Integer newPQ = Integer.parseInt(prod[3]) - oquant;
				if(newPQ < 0) {
					JOptionPane.showMessageDialog(null, "Not enough quantity of product to make an order!");
				}else {
					prod[3] = newPQ.toString();
					Product newp = new Product(prod);	
					productBll.editProduct(newp);
					Integer id =  orderBll.insertOrder(order1);
					String[] data = new String[4];
					data[0] = id.toString();
					data[1] = opid.toString();
					data[2] = ocid.toString();
					data[3] = oquant.toString();
					view.insertOrderRow(data);
					//generate the bill
					StringBuilder sb = new StringBuilder();
					sb.append("******* BILL ********\n");
					sb.append("Client : " + client[1] + "\n");
					sb.append("********************\n");
					sb.append("Product: " + prod[1]+ "\n");
					sb.append("Quantity : " + oquant + "\n");
					sb.append("Price/ piece: " + prod[2] + "\n");
					sb.append("********** TOTAL : " + Integer.parseInt(prod[2]) * oquant);
					FileWriter fstream;
					try {
						fstream = new FileWriter("bill.txt");
						BufferedWriter info = new BufferedWriter(fstream);
						info.write(sb.toString());
						info.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}			
			view.removeProductTable();
			getAllProductsTable();
			view.removeOrderTable();
			getAllOrdersTable();
			view.clearAllTF();
		}

	}
	public class OrderEditListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(view.getOrderID().contentEquals("") || view.getOrderProductId().contentEquals("") || view.getOrderClientID().contentEquals("") || view.getOrderQuantity().contentEquals("") ) {
				JOptionPane.showMessageDialog(null, "Empty fields are not allowed!");
			}else{
				int oid = Integer.parseInt(view.getOrderID());
				Integer opid =  Integer.parseInt(view.getOrderProductId());
				Integer ocid =  Integer.parseInt(view.getOrderClientID());
				Integer oquant =  Integer.parseInt(view.getOrderQuantity());
				Order order2 = new Order(oid,opid,ocid,oquant);
				orderBll.editOrder(order2);
			}
			
			view.removeOrderTable();
			getAllOrdersTable();
			view.clearAllTF();
		}

	}
	public class OrderDeleteListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
	
			if(!view.getOrderID().toString().contentEquals("")) {
			int oid = Integer.parseInt(view.getOrderID());
			Order ord3 = new Order(oid);
			orderBll.deleteOrder(ord3);
			}else {
				Integer selId = view.removeSelectedOrder();
				if(selId != null) {
					orderBll.deleteOrder(new Order(selId));			
				}else {
					System.out.println("SELECTED ID IS NULL");
				}
			}			
			view.removeOrderTable();
			getAllOrdersTable();
			view.clearAllTF();
		}

	}
	public class ProductDeleteListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(!view.getProductId().toString().contentEquals("")) {
			int pid = Integer.parseInt(view.getProductId());
			Product prod3 = new Product(pid);
			productBll.deleteProduct(prod3);
			}else {
				Integer selectedId = view.removeSelectedProduct();
				if(selectedId != null) {
					productBll.deleteProduct(new Product(selectedId));
				}else {
					System.out.println("SELECTED ID IS NULL");
				}
			}
			view.removeProductTable();
			getAllProductsTable();
			view.clearAllTF();
		}

	}
	public class ProductEditListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(view.getProductId().contentEquals("") || view.getProduct().contentEquals("") || view.getProductPrice().contentEquals("") || view.getProductQuantity().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "Empty fields not allowed!");
			}else{
				int pid = Integer.parseInt(view.getProductId());
				String prod = view.getProduct();
				Integer price =  Integer.parseInt(view.getProductPrice());
				Integer quant =  Integer.parseInt(view.getProductQuantity());
				Product prod2 = new Product(pid,prod,price,quant);
				 productBll.editProduct(prod2);
			}
		
			view.removeProductTable();
			getAllProductsTable();
			view.clearAllTF();
		}

	}
	public class ProductInsertListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			String prod = view.getProduct();
			Integer price = Integer.parseInt(view.getProductPrice());
			Integer quant = Integer.parseInt(view.getProductQuantity());
			Product prod1 = new Product(prod,price,quant);
			

			@SuppressWarnings("unused")
			Integer id = productBll.insertProduct(prod1);
			String[] data = new String[4];
			data[0] = id.toString();
			data[1] = prod;
			data[2] = price.toString();
			data[3] = quant.toString();
			view.insertProductRow(data);
			view.removeProductTable();
			getAllProductsTable();
			view.clearAllTF();
		}

	}
}
