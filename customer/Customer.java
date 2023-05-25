package customer;


import java.io.Serializable;

public class Customer implements Serializable {

    // ids
    private Long id;
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

    public Customer(Long id, String customerId, String name, boolean gender, String birth,
        String job,
        String bankAccount, String familyHistory, String healthExaminationRecord) {
        this.id = id;
        this.customerId = customerId;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.job = job;
        this.bankAccount = bankAccount;
        this.familyHistory = familyHistory;
        this.healthExaminationRecord = healthExaminationRecord;
    }

    public Long getId() {
        return id;
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