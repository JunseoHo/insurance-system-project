package dao;

import java.util.List;
import contract.Contract;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;

public class ContractDAO {
	public boolean addContract(Contract contract) {
		JdbcTemplate template = new JdbcTemplate();
		String sql = "insert into contracts (compensation_terms, fee, rate, terms_of_subscription, underwriting_report, customer_id, contract_id, premiums, product_id) values(?,?,?,?,?,?,?,?,?)";
		template.executeUpdate(sql, contract.getCompensation_terms(), contract.getFee(), contract.getRate(),
				contract.getTerms_of_subscription(), contract.getCustomer_id(),
				contract.getContract_id(), contract.getPremiums(), contract.getProduct_id(), contract.getIs_underwriting());
		return true;
	}

	public Contract findById(int id) {
		RowMapper<Contract> rm = generateCommonContractRowMapper();
		JdbcTemplate template = new JdbcTemplate();
		String sql = "select * from Contracts where id = ?";
		return template.executeQuery(sql, rm, id);
	}

	public Contract findByProductId(String contractId) {
		RowMapper<Contract> rm = generateCommonContractRowMapper();

		JdbcTemplate template = new JdbcTemplate();
		String sql = "select * from Contracts where contract_id = ?";
		return template.executeQuery(sql, rm, contractId);
	}

	public void removeContractByProductId(String contractId) {
		JdbcTemplate template = new JdbcTemplate();
		String sql = "delete from Contracts where contract_id = ?";
		template.executeUpdate(sql, contractId);
	}

	public void removeContractById(Long id) {
		JdbcTemplate template = new JdbcTemplate();
		String sql = "delete from Contracts where id = ?";
		template.executeUpdate(sql, id);
	}
	public void updateContract(Contract contract) {
	        JdbcTemplate template = new JdbcTemplate();
	        int premiums = contract.getPremiums();
	        boolean is_underwriting = true;
	        String sql = "update contracts set premiums = ? , is_underwriting = ? where contract_id = ?";
	        template.executeUpdate(sql, premiums, is_underwriting , contract.getContract_id()
	        );
	    }

	public List<Contract> findContracts() {
		RowMapper<Contract> rm = generateCommonContractRowMapper();

		JdbcTemplate template = new JdbcTemplate();
		String sql = "select * from Contracts";
		return template.list(sql, rm);
	}

	private RowMapper<Contract> generateCommonContractRowMapper() {
		return rs -> new Contract(rs.getString("compensation_terms"), rs.getInt("fee"), rs.getInt("rate"),
				rs.getString("terms_of_subscription"), rs.getString("customer_id"),
				rs.getString("contract_id"),rs.getInt("premiums"), rs.getString("product_id"), rs.getBoolean("is_underwriting"));
	}

	private List<Contract> findAll() {
		return null;
	}

}
