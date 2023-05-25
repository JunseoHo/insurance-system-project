package compensation;

import annotation.Compensation;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

@Compensation
public class Claim implements Serializable {

    // ids
    private Long id;
    private String claimId;
    private Long customerId;
    private Long employeeId;
    // info
    private String date;
    private String type;
    private String description;
    private String location;
    // review
    private String report;
    private int compensation;
    private Long reviewer;
    private String status;

    public Claim(String[] values) {
        this.claimId = values[0];
        this.customerId = Long.valueOf(values[1]);
        this.employeeId = Long.valueOf(values[2]);
        this.date = values[3];
        this.type = values[4];
        this.description = values[5];
        this.location = values[6];
        this.report = values[7];
        this.compensation = Integer.parseInt(values[8]);
        this.reviewer = Long.valueOf(values[9]);
        this.status = values[10];
    }

    public Claim(Long id, String claimId, Long customerId, Long employeeId, String date, String type, String description,
        String location, String report, int compensation, Long reviewer,
        String status) {
        this.id = id;
        this.claimId = claimId;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.date = date;
        this.type = type;
        this.description = description;
        this.location = location;
        this.report = report;
        this.compensation = compensation;
        this.reviewer = reviewer;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
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

    public Long getReviewer() {
        return reviewer;
    }

    public void setReviewer(Long reviewer) {
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