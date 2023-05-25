package dao;

import customer.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String DB_ADDRESS = "jdbc:mysql://localhost:3306/insurance_system";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    protected Connection connection = null;
    protected Statement statement = null;
    protected ResultSet resultSet = null;

    protected String tableName = null;
    protected String primarykey = null;
    protected final String SELECT = "SELECT";
    protected final String FROM = "FROM";
    protected final String WHERE = "WHERE";
    protected final String INSERT = "INSERT";
    protected final String INTO = "INTO";
    protected final String VALUES = "VALUES";
    protected final String UPDATE = "UPDATE";


    public DAO(String name) {
        if (connect()) System.out.println(name + "가 DB서버와 성공적으로 연결되었습니다.");
        else System.out.println(name + "가 DB서버와 연결에 실패했습니다.");
    }

    public boolean connect() {
        try {
            Class.forName(DRIVER_CLASS);
            connection = DriverManager.getConnection(DB_ADDRESS, USER, PASSWORD);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean create(Object object) {
        return executeUpdate(INSERT + " " + INTO + " " + tableName + " " +
                VALUES + "(" + object.toString() + ")") == 1;
    }

    public Object retrieve(String key) {
        return null;
    }

    public Object retrieveAll() {
        return null;
    }

    public boolean update(Object object) {
        return true;
    }

    public boolean delete(Object object) {
        return true;
    }

    public ResultSet executeQuery(String query) {
        try {
            statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("SQL Exception is occurred");
            e.printStackTrace();
            return null;
        }
    }

    public int executeUpdate(String query) {
        try {
            statement = connection.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("SQL Exception is occurred");
            return -1;
        }
    }

}
