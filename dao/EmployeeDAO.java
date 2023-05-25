package dao;

import customer.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO extends DAO {

    public EmployeeDAO(String name) {
        super(name);
        tableName = "EMPLOYEE";
        tableName = "employeeId";
    }


}
