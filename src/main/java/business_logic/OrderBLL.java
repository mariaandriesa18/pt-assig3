package business_logic;

import java.util.List;

import data_access.AbstractDAO;
import data_access.ClientDAO;
import data_access.OrderDAO;
import data_access.ProductDAO;
import model.Client;
import model.Order;

public class OrderBLL {
	public int insertOrder(Order order) {
		OrderDAO ins = new OrderDAO();
		/*for (Validator<Order> v : validators) {
			v.validate(order);
		}*/
		return ((Order) ins.insert(order)).getId();
	}
	
	public int editOrder(Order order) {
		OrderDAO up = new OrderDAO();
		//TODO validate edit
		return  ((Order)up.update(order)).getId();
	}
	
	public int deleteOrder(Order order) {
		OrderDAO del = new OrderDAO();
		//TODO validate edit
		return  ((Order) del.delete(order)).getId();
	}
	
	/*
	 * public List<Order> selectAllOrders(){ OrderDAO findAll = new OrderDAO();
	 * return findAll.findAll(); }
	 */
	
	public String[][] selectAll(){
		OrderDAO s = new OrderDAO();
		return s.selectAll();
	}
}
