package dao;

import annotation.DAO;
import common.Employee;

import java.util.List;

import compensation.Claim;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;

@DAO
public class EmployeeDAO {
    public void addEmployee(Employee employee) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "insert into EMPLOYEES (employee_id, birth, department, gender, name) values(?, ?,?,?,?)";
        template.executeUpdate(sql, employee.getEmployeeId(), employee.getBirth(), employee.getDepartment(), employee.isGender(), employee.getName());
    }

    public Employee findByEmployeeId(String employeeId) {
        RowMapper<Employee> rm = generateCommonEmployeeRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from EMPLOYEES where employee_id = ?";
        return template.executeQuery(sql, rm, employeeId);
    }

    public Employee findById(Long id) {
        RowMapper<Employee> rm = generateCommonEmployeeRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from EMPLOYEES where id = ?";
        return template.executeQuery(sql, rm, id);
    }

    public void removeEmployeeByEmployeeId(String employeeId) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "delete from EMPLOYEES where employee_id = ?";
        template.executeUpdate(sql, employeeId);
    }

//    public void updateEmployee(Employee employee) {
//        JdbcTemplate template = new JdbcTemplate();
//        String sql = "update EMPLOYEES set birth = ?, department = ?, gender = ?, name = ? where id = ?";
//        template.executeUpdate(sql,
//                employee.getBirth(), employee.getDepartment(), employee.isGender(), employee.getName(), employee.getId()
//        );
//    }

    public List<Employee> findAll() {
        RowMapper<Employee> rm = generateCommonEmployeeRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from EMPLOYEES";
        return template.list(sql, rm);
    }

    private RowMapper<Employee> generateCommonEmployeeRowMapper() {
        return rs ->
                new Employee(
                        rs.getString("employee_id"),
                        rs.getString("name"),
                        rs.getBoolean("gender"),
                        rs.getString("birth"),
                        rs.getString("department")
                );
    }
}

