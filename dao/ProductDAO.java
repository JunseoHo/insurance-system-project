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
        String sql = "INSERT INTO PRODUCTS (product_id, name, target, compensation_detail, rate, profit_n_loss_analysis, premiums) values(?, ?,?,?,?,?,?)";
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

    public Product findByProductId(String productId) {
        RowMapper<Product> rm = generateCommonProductRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "SELECT * FROM PRODUCTS WHERE product_id = ?";
        return template.executeQuery(sql, rm, productId);
    }

    public void removeProductByProductId(String productId) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "DELETE FROM PRODUCTS WHERE product_id = ?";
        template.executeUpdate(sql, productId);
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
        String sql = "SELECT * FROM PRODUCTS";
        return template.list(sql, rm);
    }

    private RowMapper<Product> generateCommonProductRowMapper() {
        return rs ->
                new Product(rs.getString("product_id"),
                        rs.getString("name"),
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
