package dao;

import annotation.DAO;
import common.Customer;

import java.util.List;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;

@DAO
public class CustomerDAO {
    public void addCustomer(Customer customer) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "insert into CUSTOMERS (customer_id, bank_account, birth, family_history, gender, health_examination_record, job, name) values(?, ?,?,?,?,?,?,?,?)";
        template.executeUpdate(sql, customer.getCustomerId(),customer.getBankAccount(), customer.getBirth(), customer.getFamilyHistory(), customer.getGender(), customer.getHealthExaminationRecord(), customer.getJob(), customer.getName());
    }

    public Customer findById(int id) {
        RowMapper<Customer> rm = generateCommonCustomerRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from CUSTOMERS where id = ?";
        return template.executeQuery(sql, rm, id);
    }

    public Customer findByCustomerId(String customerId) {
        RowMapper<Customer> rm = generateCommonCustomerRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from CUSTOMERS where customer_id = ?";
        return template.executeQuery(sql, rm, customerId);
    }

    public void removeCustomerByCustomerId(String customerId) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "delete from CUSTOMERS where customer_id = ?";
        template.executeUpdate(sql, customerId);
    }

    public void removeCustomerById(Long id) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "delete from CUSTOMERS where id = ?";
        template.executeUpdate(sql, id);
    }

    public void updateCustomer(Customer customer) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "update CUSTOMERS set name = ?, gender = ?, birth = ?, job = ?, bank_account = ?, family_history = ?, health_examination_record = ? where id = ?";
        template.executeUpdate(sql,
            customer.getName(), customer.getBirth(), customer.getJob(),
            customer.getBankAccount(), customer.getFamilyHistory(), customer.getHealthExaminationRecord(),
            customer.getId()
        );
    }

    public List<Customer> findCustomers() {
        RowMapper<Customer> rm = generateCommonCustomerRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from CUSTOMERS";
        return template.list(sql, rm);
    }

    private RowMapper<Customer> generateCommonCustomerRowMapper() {
        return rs ->
            new Customer(
                rs.getLong("id"),
                rs.getString("customer_id"),
                rs.getString("name"),
                rs.getBoolean("gender"),
                rs.getString("birth"),
                rs.getString("job"),
                rs.getString("bank_account"),
                rs.getString("family_history"),
                rs.getString("health_examination_record")
            );
    }
}


