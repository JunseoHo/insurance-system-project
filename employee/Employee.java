package employee;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee implements Serializable {

    // ids
    private String employeeId;
    // personal info
    private String name;
    private boolean gender;
    private String birth;
    // insurance info
    private String department;

    public Employee(ResultSet resultSet) {
        try {
            setEmployeeId(resultSet.getString("employeeId"));
            setName(resultSet.getString("name"));
            setGender(resultSet.getString("gender").equals("M"));
            setBirth(resultSet.getString("birth"));
            setDepartment(resultSet.getString("department"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void print() {
        System.out.println("*** Employee Info ***");
        System.out.println("Employee ID : " + employeeId);
        System.out.println("Employee Name : " + name);
    }

}