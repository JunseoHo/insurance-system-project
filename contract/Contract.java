package contract;

import java.io.Serializable;

@annotation.Contracts
public class Contract implements Serializable{
    private String compensationTerms;
    private int fee;
    private int rate;
    private String termsOfSubscription;
    private String customerId;
    private String contractId;
    private int premiums;
    private String productId;
    private boolean isUnderwriting;

    public Contract(
            String compensation_terms,
            int fee,
            int rate,
            String termsOfSubscription,
            String customer_id,
            String contractId,
            int premiums,
            String productId,
            boolean isUnderwriting) {
        this.compensationTerms = compensation_terms;
        this.fee = fee;
        this.rate = rate;
        this.termsOfSubscription = termsOfSubscription;
        this.customerId = customer_id;
        this.contractId = contractId;
        this.premiums = premiums;
        this.productId = productId;
        this.setUnderwriting(isUnderwriting);
    }
    public String getCompensationTerms() {
        return compensationTerms;
    }
    public void setCompensationTerms(String compensationTerms) {
        this.compensationTerms = compensationTerms;
    }
    public int getFee() {
        return fee;
    }
    public void setFee(int fee) {
        this.fee = fee;
    }
    public int getRate() {
        return rate;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }
    public String getTermsOfSubscription() {
        return termsOfSubscription;
    }
    public void setTermsOfSubscription(String termsOfSubscription) {
        this.termsOfSubscription = termsOfSubscription;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getContractId() {
        return contractId;
    }
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
    public int getPremiums() {
        return premiums;
    }
    public void setPremiums(int premiums) {
        this.premiums = premiums;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public boolean getUnderwriting() {
        return isUnderwriting;
    }
    public void setUnderwriting(boolean underwriting) {
        this.isUnderwriting = underwriting;
    }
    public String getClaimPayout() {
        StringBuilder table = new StringBuilder();
        table.append(String.format("| %-10s | %-15s |\n",
                this.contractId, this.fee));
        table.append(
                "--------------------------------\n");
        return table.toString();
    }

}
