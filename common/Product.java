package common;

import java.io.Serializable;
import annotation.Common;
@Common
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String target;
	private String compensationDetail;
	private int rate;
	private String profitNLossAnalysis;
	private int premiums;
	
	public Product(String id, String name, String target, String compensationDetail, int rate, String profitNLossAnalysis, int premiums){
		this.setId(id);
		this.name = name;
		this.target = target;
		this.compensationDetail = compensationDetail;
		this.rate = rate;
		this.profitNLossAnalysis = profitNLossAnalysis;
		this.premiums = premiums;
	}
	

	public String getProductName() {
		return name;
	}

	public void setProductName(String productName) {
		this.name = productName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getCompensationDetail() {
		return compensationDetail;
	}

	public void setCompensationDetail(String compensationDetail) {
		this.compensationDetail = compensationDetail;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getProfitNLossAnalysis() {
		return profitNLossAnalysis;
	}

	public void setProfitNLossAnalysis(String profitNLossAnalysis) {
		this.profitNLossAnalysis = profitNLossAnalysis;
	}

	public int getPremiums() {
		return premiums;
	}

	public void setPremiums(int premiums) {
		this.premiums = premiums;
	}

	

	public void finalize() throws Throwable {

	}

	public void devleop(){

	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	public String toString() {
        StringBuilder table = new StringBuilder();
        table.append(String.format("| %-10s | %-15s | %-10s | %-20s | %-5s | %-25s | %-10s| \n", this.id , this.name, this.target, this.compensationDetail, this.rate, this.profitNLossAnalysis, this.premiums));
        table.append("--------------------------------------------------------------------------------------------------------------------\n");
        return table.toString();
    }

}
