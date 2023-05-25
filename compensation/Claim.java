package compensation;

import annotation.Compensation;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

@Compensation
public class Claim implements Serializable {

    // ids
    private String claimId;
    private String customerId;
    private String employeeId;
    // info
    private String date;
    private String type;
    private String description;
    private String location;
    // review
    private String report;
    private int compensation;
    private String reviewer;
    private String status;

    public Claim(ResultSet resultSet) {
        try {
            setClaimId(resultSet.getString("claimId"));
            setCustomerId(resultSet.getString("customerId"));
            setEmployeeId(resultSet.getString("employeeId"));
            setDate(resultSet.getString("date"));
            setType(resultSet.getString("type"));
            setDescription(resultSet.getString("description"));
            setLocation(resultSet.getString("location"));
            setReport(resultSet.getString("report"));
            setCompensation(resultSet.getInt("compensation"));
            setReviewer(resultSet.getString("reviewer"));
            setStatus(resultSet.getString("status"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Claim(String[] values) {
        this.claimId = values[0];
        this.customerId = values[1];
        this.employeeId = values[2];
        this.date = values[3];
        this.type = values[4];
        this.description = values[5];
        this.location = values[6];
        this.report = values[7];
        this.compensation = Integer.parseInt(values[8]);
        this.reviewer = values[9];
        this.status = values[10];
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public int getCompensation() {
        return compensation;
    }

    public void setCompensation(int compensation) {
        this.compensation = compensation;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }


    @Override
    public String toString() {
        return "'" + claimId + "','" + customerId + "','" + employeeId + "','" + date + "','"
                + type + "','" + description + "','" + location + "','"
                + report + "'," + compensation + ",'" + reviewer + "','"
                + status + "'";
    }

    public void print() {
        System.out.println("*** Claim Contents ***");
        System.out.println("Customer ID : " + customerId);
        System.out.println("Employee ID : " + employeeId);
        System.out.println("Date : " + date);
        System.out.println("Claim type : " + type);
        System.out.println("Location : " + location);
        System.out.println("Description : " + description);
    }

}