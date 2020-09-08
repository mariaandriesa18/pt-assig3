package business_logic;

import data_access.ProductDAO;
import model.Product;

public class ProductBLL {
	
	public String[] findByID(Integer id) {
		ProductDAO product=new ProductDAO();
		return product.findById(id);
	}
	
	public int insertProduct(Product product) {
		ProductDAO ins = new ProductDAO();
		/*for (Validator<Product> v : validators) {
			v.validate(product);
		}*/
		return ins.insert(product).getId();
	}
	
	public Product editProduct(Product product) {
		ProductDAO up = new ProductDAO();
		//TODO validate edit
		return  up.update(product);
	}
	public int deleteProduct(Product product) {
		ProductDAO del = new ProductDAO();
		//TODO validate edit
		return del.delete(product).getId();
	}
	/*
	 * public List<Product> selectAllProducts(){ ProductDAO sel = new ProductDAO();
	 * return sel.findAll(); }
	 */
	public String[][] selectAll(){
		ProductDAO s = new ProductDAO();
		return s.selectAll();
	}
}
