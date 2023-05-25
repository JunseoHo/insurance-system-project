package customer;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer implements Serializable {

    // ids
    private String customerId;
    // personal info
    private String name;
    private boolean gender;
    private String birth;
    private String job;
    // insurance info
    private String bankAccount;
    private String familyHistory;
    private String healthExaminationRecord;

    public Customer(ResultSet resultSet) {
        try {
            setCustomerId(resultSet.getString("customerId"));
            setName(resultSet.getString("name"));
            setGender(resultSet.getString("gender").equals("M"));
            setBirth(resultSet.getString("birth"));
            setJob(resultSet.getString("job"));
            setBankAccount(resultSet.getString("bankAccount"));
            setFamilyHistory(resultSet.getString("familyhistory"));
            setHealthExaminationRecord(resultSet.getString("healthExaminationRecord"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean getGender() {
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getHealthExaminationRecord() {
        return healthExaminationRecord;
    }

    public void setHealthExaminationRecord(String healthExaminationRecord) {
        this.healthExaminationRecord = healthExaminationRecord;
    }

    public boolean createClaim() {
        return false;
    }

    public boolean payment() {
        return false;
    }

    public boolean receiveAnAccident() {
        return false;
    }

    public boolean rewarded() {
        return false;
    }

}