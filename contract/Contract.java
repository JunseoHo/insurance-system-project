package contract;

import java.io.Serializable;

@annotation.Contracts
public class Contract implements Serializable{
	private String compensation_terms;
	private int fee;
	private int rate;
	private String terms_of_subscription;
	private String customer_id;
	private String contract_id;
	private int premiums;
	private String product_id;
	private boolean is_underwriting;
	
	public Contract(
			String compensation_terms,
			int fee,
			int rate,
			String terms_of_subscription,
			String customer_id,
			String contract_id,
			int premiums,
			String product_id,
			boolean is_underwriting) {
		this.compensation_terms = compensation_terms;
		this.fee = fee;
		this.rate = rate;
		this.terms_of_subscription = terms_of_subscription;
		this.customer_id = customer_id;
		this.contract_id = contract_id;
		this.premiums = premiums;
		this.product_id = product_id;
		this.setIs_underwriting(is_underwriting);
	}
	public String getCompensation_terms() {
		return compensation_terms;
	}
	public void setCompensation_terms(String compensation_terms) {
		this.compensation_terms = compensation_terms;
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
	public String getTerms_of_subscription() {
		return terms_of_subscription;
	}
	public void setTerms_of_subscription(String terms_of_subscription) {
		this.terms_of_subscription = terms_of_subscription;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getContract_id() {
		return contract_id;
	}
	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}
	public int getPremiums() {
		return premiums;
	}
	public void setPremiums(int premiums) {
		this.premiums = premiums;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public boolean getIs_underwriting() {
		return is_underwriting;
	}
	public void setIs_underwriting(boolean is_underwriting) {
		this.is_underwriting = is_underwriting;
	}
	public String getClaimPayout() {
			StringBuilder table = new StringBuilder();
			table.append(String.format("| %-10s | %-15s |\n",
					this.contract_id, this.fee));
			table.append(
					"--------------------------------\n");
			return table.toString();
	}

}
