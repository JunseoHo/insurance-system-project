package dao;

import java.util.List;
import contract.Contract;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;

public class ContractDAO {
    public boolean addContract(Contract contract) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "INSERT INTO CONTRACTS (compensation_terms, fee, rate, terms_of_subscription, customer_id, contract_id, premiums, product_id, is_underwriting) values(?,?,?,?,?,?,?,?,?)";
        template.executeUpdate(sql, contract.getCompensationTerms(), contract.getFee(), contract.getRate(),
                contract.getTermsOfSubscription(), contract.getCustomerId(),
                contract.getContractId(), contract.getPremiums(), contract.getProductId(), contract.getUnderwriting());
        return true;
    }

    public Contract findByProductId(String contractId) {
        RowMapper<Contract> rm = generateCommonContractRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "SELECT * FROM CONTRACTS WHERE contract_id = ?";
        return template.executeQuery(sql, rm, contractId);
    }

    public void removeContractByProductId(String contractId) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "DELETE FROM CONTRACTS WHERE contract_id = ?";
        template.executeUpdate(sql, contractId);
    }

    public void updateContract(Contract contract) {
        JdbcTemplate template = new JdbcTemplate();
        int premiums = contract.getPremiums();
        boolean is_underwriting = true;
        String sql = "UPDATE CONTRACTS SET premiums = ? , is_underwriting = ? WHERE contract_id = ?";
        template.executeUpdate(sql, premiums, is_underwriting , contract.getContractId()
        );
    }

    public List<Contract> findContracts() {
        RowMapper<Contract> rm = generateCommonContractRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "SELECT * FROM CONTRACTS";
        return template.list(sql, rm);
    }

    private RowMapper<Contract> generateCommonContractRowMapper() {
        return rs -> new Contract(rs.getString("compensation_terms"), rs.getInt("fee"), rs.getInt("rate"),
                rs.getString("terms_of_subscription"), rs.getString("customer_id"),
                rs.getString("contract_id"),rs.getInt("premiums"), rs.getString("product_id"), rs.getBoolean("is_underwriting"));
    }

}