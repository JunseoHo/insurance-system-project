package dao;

import java.sql.SQLException;
import java.util.List;
import annotation.DAO;
import common.Product;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;

@DAO
public class ProductDAO {
	public boolean addProduct(Product product) {
		JdbcTemplate template = new JdbcTemplate();
		String sql = "insert into products (product_id, product_name, target, compensation_detail, rate, profit_n_loss_analysis, premiums) values(?, ?,?,?,?,?,?)";
		template.executeUpdate(sql,
				product.getId(),
				product.getProductName(),
				product.getTarget(),
				product.getCompensationDetail(),
				product.getRate(),
				product.getProfitNLossAnalysis(),
				product.getPremiums()
				);
		return true;
	    }

	public Product findById(int id) {
		RowMapper<Product> rm = generateCommonProductRowMapper();
		JdbcTemplate template = new JdbcTemplate();
		String sql = "select * from PRODUCTS where id = ?";
		return template.executeQuery(sql, rm, id);
	}

	public Product findByProductId(String productId) {
		RowMapper<Product> rm = generateCommonProductRowMapper();

		JdbcTemplate template = new JdbcTemplate();
		String sql = "select * from Products where product_id = ?";
		return template.executeQuery(sql, rm, productId);
	}

	public void removeProductByProductId(String productId) {
		JdbcTemplate template = new JdbcTemplate();
		String sql = "delete from products where product_id = ?";
		template.executeUpdate(sql, productId);
	}

	public void removeProductById(Long id) {
		JdbcTemplate template = new JdbcTemplate();
		String sql = "delete from products where id = ?";
		template.executeUpdate(sql, id);
	}

	public void updateProduct(Product product) {
	        JdbcTemplate template = new JdbcTemplate();
	        String sql = "UPDATE PRODUCTS SET product_name = ?, target = ?, compensation_detail = ?, rate = ?, profit_n_loss_analysis = ?, premiums = ?  where product_id = ?";
	        template.executeUpdate(sql, product.getProductName(), product.getTarget(), product.getCompensationDetail(), product.getRate(), product.getProfitNLossAnalysis(), product.getPremiums()  
	        );
	    }

	public List<Product> findProducts() {
		RowMapper<Product> rm = generateCommonProductRowMapper();

		JdbcTemplate template = new JdbcTemplate();
		String sql = "select * from products";
		return template.list(sql, rm);
	}

	private RowMapper<Product> generateCommonProductRowMapper() {
		return rs ->
		new Product(rs.getString("product_id"),
		rs.getString("product_name"),
		rs.getString("target"),
		rs.getString("compensation_detail"),
		rs.getInt("rate"),
		rs.getString("profit_n_loss_analysis"),
		rs.getInt("premiums")
		);
	}
	
	private List<Product> findAll() {
		
		return null;
	}
}
