package presentation;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.*;

import data_access.ClientDAO;
import model.Client;
import model.Order;
import model.Product;
import start.Reflection;

public class MainView {

	private JFrame frame;

	private JTable clientTable; 
	private JTable productTable;
	private JTable orderTable;
	private DefaultTableModel clientModel = new DefaultTableModel();
	private DefaultTableModel productModel = new DefaultTableModel();
	private DefaultTableModel orderModel = new DefaultTableModel();
	private JButton icBtn = new JButton("Insert Client");
	private JButton dcBtn = new JButton("Delete Client");
	private JButton ecBtn = new JButton("Edit Client");
	private JButton ipBtn = new JButton("Insert Product");
	private JButton dpBtn = new JButton("Delete Product");
	private JButton epBtn = new JButton("Edit Product");
	private JButton ioBtn = new JButton("Insert Order");
	private JButton eoBtn = new JButton("Edit Order");
	private JButton doBtn = new JButton("Delete Order");
	//private JButton dtBtn = new JButton("Delete Table");
	private JTextField cidTF = new JTextField();
	private JTextField cnameTF = new JTextField();
	private JTextField cadressTF = new JTextField();
	private JTextField pidTF = new JTextField();
	private JTextField prodTF=  new JTextField();
	private JTextField pquantityTF = new JTextField();
	private JTextField ppriceTF = new JTextField();
	private JTextField oidTF = new JTextField();
	private JTextField ocidTF = new JTextField();
	private JTextField opidTF = new JTextField();
	private JTextField oquantityTF = new JTextField();
	
	private JLabel cidLB= new JLabel("Id:");
	private JLabel cnameLB= new JLabel("Name:");
	private JLabel cadressLB= new JLabel("Address:");
	private JLabel  pidLB = new JLabel("Id:");
	private JLabel prodLB = new JLabel("Product:");
	private JLabel pquantityLB = new JLabel("Quantity");
	private JLabel ppriceLB = new JLabel("Price");
	private JLabel oidLB = new JLabel("Order Id:");
	private JLabel ocidLB = new JLabel("Client Id:");
	private JLabel opidLB = new JLabel("Product Id:");
	private JLabel oquantityLB =new JLabel("Quantity:");
	
	public MainView() {
		initialize();
		
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(500, 200, 700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		JTabbedPane tabbedPanel = new JTabbedPane();
		tabbedPanel.setPreferredSize(new Dimension(700, 500));
		
		//Client Tab
		JPanel mainPane1 = new JPanel();
		mainPane1.setLayout(new BorderLayout());
		//String[] clientColumns = {"ClientId", "Name","Address"};
		Client client = new Client();
		String[] clientColumns = Reflection.retrieveProperties(client);
		for(int c = 0 ; c < clientColumns.length; c++) {
			clientModel.addColumn(clientColumns[c]);
		}
		//Object[][] clientData = {};
		clientTable = new JTable( clientModel);
		//clientModel = (DefaultTableModel) clientTable.getModel();
		clientTable.setPreferredScrollableViewportSize(new Dimension(700,300));
		JScrollPane js1 = new JScrollPane(clientTable);
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(2,1));
		JPanel p11 = new JPanel();
		p11.setLayout(new GridLayout(1, 3));
		p11.add(icBtn);
		p11.add(ecBtn);
		p11.add(dcBtn);
		JPanel p12 = new JPanel();
		p12.setLayout(new GridLayout(2, 1));
		//client insertion labels
		JPanel p121 = new JPanel();
		p121.setLayout(new GridLayout(1,3));
		cidTF.setPreferredSize(new Dimension(125,5));
		p121.add(cidLB);
		p121.add(cnameLB);
		p121.add(cadressLB);
		p12.add(p121);
		//client insertion text fields
		JPanel p122 = new JPanel();
		p122.setLayout(new GridLayout(1,3));
		p122.add(cidTF);
		p122.add(cnameTF);
		p122.add(cadressTF);
		p12.add(p122);
		p1.add(p11);
		p1.add(p12);
		/*
		 * JPanel del = new JPanel(); del.setLayout(new FlowLayout()); del.add(dtBtn);
		 */
		mainPane1.add(p1, BorderLayout.PAGE_START);
	//	mainPane1.add(del, BorderLayout.CENTER);
		mainPane1.add(js1, BorderLayout.PAGE_END);
		tabbedPanel.addTab("Clients", mainPane1);
		
		//Product Tab
		JPanel mainPanel2 = new JPanel();
		mainPanel2.setLayout(new BorderLayout());
		//String[] productColumns = {, ,,};
		//Object[][] productData = {};
		Product prod = new Product();
		String[] productColumns = Reflection.retrieveProperties(prod);
		for(int p =0 ; p< productColumns.length ; p++) {
			productModel.addColumn(productColumns[p]);
		}
		productTable = new JTable( productModel);
		//productModel = (DefaultTableModel) productTable.getModel();
		productTable.setPreferredScrollableViewportSize(new Dimension(700,300));
		JScrollPane js2 = new JScrollPane(productTable);
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(2,1));
		JPanel p21 = new JPanel();
		p21.setLayout(new GridLayout(1, 3));
		p21.add(ipBtn);
		p21.add(epBtn);
		p21.add(dpBtn);
		p2.add(p21);
		
		JPanel p22 = new JPanel();
		p22.setLayout(new GridLayout(2, 1));
		//product insertion labels
		JPanel p221 = new JPanel();
		p221.setLayout(new GridLayout(1,4));
		cidTF.setPreferredSize(new Dimension(125,5));
		p221.add(pidLB);
		p221.add(prodLB);
		p221.add(pquantityLB);
		p221.add(ppriceLB);
		
		//product insertion text fields
		JPanel p222 = new JPanel();
		p222.setLayout(new GridLayout(1,4));
		p222.add(pidTF);
		p222.add(prodTF);
		p222.add(pquantityTF);
		p222.add(ppriceTF);
		p22.add(p221);
		p22.add(p222);
		p2.add(p22);
		
		mainPanel2.add(p2, BorderLayout.PAGE_START);
		mainPanel2.add(js2, BorderLayout.PAGE_END);
		tabbedPanel.addTab("Products", mainPanel2);
		
		//Order Tab
		JPanel mainPanel3 = new JPanel();
		mainPanel3.setLayout(new BorderLayout());
		//Object[] orderColumns = {, ,,};
		//Object[][] orderData = {};
		Order ord = new Order();
		String[] orderColumns = Reflection.retrieveProperties(ord);
		for( int o = 0; o < orderColumns.length; o++) {
			orderModel.addColumn(orderColumns[o]);
		}
		orderTable = new JTable(orderModel);
		//orderModel =  (DefaultTableModel) orderTable.getModel();
		orderTable.setPreferredScrollableViewportSize(new Dimension(700,300));
		JScrollPane js3 = new JScrollPane(orderTable);
		JPanel p3 = new JPanel();
		p3.setLayout(new GridLayout(2,1));
		JPanel p31 = new JPanel();
		p31.setLayout(new GridLayout(1, 3));
		p31.add(ioBtn);
		p31.add(eoBtn);
		p31.add(doBtn);
		p3.add(p31);
		
		JPanel p32 = new JPanel();
		p32.setLayout(new GridLayout(2, 1));
		//product insertion labels
		JPanel p321 = new JPanel();
		p321.setLayout(new GridLayout(1,4));
		cidTF.setPreferredSize(new Dimension(125,5));
		p321.add(oidLB);
		p321.add(ocidLB);
		p321.add(opidLB);
		p321.add(oquantityLB);
		
		//product insertion text fields
		JPanel p322 = new JPanel();
		p322.setLayout(new GridLayout(1,4));
		p322.add(oidTF);
		p322.add(ocidTF);
		p322.add(opidTF);
		p322.add(oquantityTF);
		
		p32.add(p321);
		p32.add(p322);
		p3.add(p32);
		mainPanel3.add(p3, BorderLayout.PAGE_START);
		mainPanel3.add(js3, BorderLayout.PAGE_END);
		tabbedPanel.addTab("Orders", mainPanel3);
		
		frame.getContentPane().add(tabbedPanel);
		frame.pack();
	
	}
	
	public void addClientInsActionListener(ActionListener e) {
		this.icBtn.addActionListener(e);
	}
	
	public void addClientEditActionListener(ActionListener e) {
		this.ecBtn.addActionListener(e);
	}
	
	public void addClientDeleteActionListener(ActionListener e) {
		this.dcBtn.addActionListener(e);
	}
	
	public void addProductInsActionListener(ActionListener e) {
		this.ipBtn.addActionListener(e);
	}
	
	public void addProductEditActionListener(ActionListener e) {
		this.epBtn.addActionListener(e);
	}
	
	public void addProductDeleteActionListener(ActionListener e) {
		this.dpBtn.addActionListener(e);
	}
	
	public void addOrderInsActionListener(ActionListener e) {
		this.ioBtn.addActionListener(e);
	}
	
	public void addOrderEditActionListener(ActionListener e) {
		this.eoBtn.addActionListener(e);
	}
	
	public void addOrderDeleteActionListener(ActionListener e) {
		this.doBtn.addActionListener(e);
	}

	/*
	 * public void addTableDeleteActionListener(ActionListener e) {
	 * this.dtBtn.addActionListener(e); }
	 */
	public String getClientId() {
		return this.cidTF.getText();
	}
	
	
	public String getClientName() {
		return this.cnameTF.getText();
	}
	
	public String getClientAddress() {
		return this.cadressTF.getText();
	}
	
	public String getProductId() {
		return this.pidTF.getText();
	}
	
	public String getProduct() {
		return this.prodTF.getText();
	}
	
	public String getProductPrice() {
		return this.ppriceTF.getText();
	}
	
	public String getProductQuantity() {
		return this.pquantityTF.getText();
	}
	
	public String getOrderID() {
		return this.oidTF.getText();
	}
	
	public String getOrderClientID() {
		return this.ocidTF.getText();
	}
	
	public String getOrderProductId() {
		return this.opidTF.getText();
	}
	
	public String getOrderQuantity() {
		return this.oquantityTF.getText();
	}
	public DefaultTableModel getClientTable() {
		return this.clientModel;
	}
	
	public DefaultTableModel getProductTable() {
		return this.productModel;
	}
	
	public DefaultTableModel getOrderTable() {
		return this.orderModel;
	}
	
	//update row from CLient
	/*public void updateClientRow(String cid, String[] data) {
	    if (data.length > 2)
	        throw new IllegalArgumentException("data[] is too long");
	    for (int i = 0; i < clientModel.getRowCount(); i++)
	        if (clientModel.getValueAt(i, 0).equals(cid))
	            for (int j = 1; j < data.length+1; j++)
	                clientModel.setValueAt(data[j-1], i, j);
	}*/
	
	public void insertClientRow(String[] data) {
		this.clientModel.addRow(data);
	}
	
	public void insertProductRow(String[] data) {
		this.productModel.addRow(data);
	}
	
	public void insertOrderRow(String[] data) {
		this.orderModel.addRow(data);
	}
	
	//delete selected client
	public Integer removeSelectedClient(){
		   int[] rows = clientTable.getSelectedRows();
		   Integer clientRowId = null;
		   for(int i=0; i< rows.length;i++){
			  clientRowId = Integer.parseInt((String)clientTable.getValueAt(rows[i] -i, 0));
		     clientModel.removeRow(rows[i]-i);
		   }
		   return clientRowId ;
		}
	//delete selected product
	public Integer removeSelectedProduct(){
		   int[] rows = productTable.getSelectedRows();
		   Integer prodRowId = null;
		   for(int i=0;i<rows.length;i++){
			   prodRowId = Integer.parseInt((String)productTable.getValueAt(rows[i] -i, 0));
		     productModel.removeRow(rows[i]-i);
		   }
		   return prodRowId;
		}
	
	//delete selected order
	public Integer removeSelectedOrder(){
		   int[] rows = orderTable.getSelectedRows();
		   Integer orderRowId = null;
		   for(int i=0;i<rows.length;i++){
			   orderRowId =Integer.parseInt((String)orderTable.getValueAt(rows[i] -i, 0));;
		     orderModel.removeRow(rows[i]-i);
		   }
		   return orderRowId;
		}
	
	//delete table
	public void removeClientTable() {
		clientModel.setRowCount(0);
		clientTable.revalidate();
	}
	public void removeProductTable() {
		productModel.setRowCount(0);
		productTable.revalidate();
	}
	public void removeOrderTable() {
		orderModel.setRowCount(0);
		orderTable.revalidate();
	}


	public void clearAllTF() {
		this.cadressTF.setText("");
		this.cnameTF.setText("");
		this.cidTF.setText("");
		this.pidTF.setText("");
		this.ppriceTF.setText("");
		this.prodTF.setText("");
		this.pquantityTF.setText("");
		this.oidTF.setText("");
		this.opidTF.setText("");
		this.ocidTF.setText("");
		this.oquantityTF.setText("");
	}
	
	
}
