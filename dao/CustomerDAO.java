package dao;

import customer.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DAO {

    public CustomerDAO(String name) {
        super(name);
        tableName = "CUSTOMER";
        primarykey = "customerId";
    }

    @Override
    public Object retrieve(String key) {
        try {
            ResultSet resultSet = executeQuery(SELECT + " * " + FROM + " " + tableName + " " +
                    WHERE + " " + primarykey + " = '" + key + "';");
            if (resultSet == null || !resultSet.next()) return null;
            return new Customer(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object retrieveAll() {
        try {
            ResultSet resultSet = executeQuery(SELECT + " * " + FROM + " " + tableName + ";");
            resultSet.next();
            List<Customer> customers = new ArrayList<>();
            while (!resultSet.isAfterLast()) {
                customers.add(new Customer(resultSet));
                resultSet.next();
            }
            return customers;
        } catch (SQLException e) {
            System.out.println("SQL Exception is occurred");
            return null;
        }
    }


}
