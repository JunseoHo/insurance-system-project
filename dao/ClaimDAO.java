package dao;


import annotation.DAO;
import compensation.Claim;

import java.util.List;

import jdbc.JdbcTemplate;
import jdbc.RowMapper;

@DAO
public class ClaimDAO {
    public void addClaim(Claim claim) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "insert into CLAIMS (claim_id, compensation, customer_id, date, description, employee_id, location, report, reviewer, status) values(?,?,?,?,?,?,?,?,?,?)";
        template.executeUpdate(sql, claim.getClaimId(),
                claim.getCompensation(), claim.getCustomerId(), claim.getDate(), claim.getDescription(),
                claim.getEmployeeId(), claim.getLocation(), claim.getReport(),
                claim.getReviewer(), claim.getStatus()
        );
    }

    public Claim findByClaimId(String claimId) {
        RowMapper<Claim> rm = generateCommonClaimRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from CLAIMS where claim_id = ?";
        return template.executeQuery(sql, rm, claimId);
    }

    public void removeClaimByClaimId(String claimId) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "delete from CLAIMS where claim_id = ?";
        template.executeUpdate(sql, claimId);
    }

    public void updateClaim(Claim claim) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "update CLAIMS set customer_id = ?, employee_id = ?, date = ?, type = ?, description = ?, location = ?, report = ?, compensation = ?, reviewer = ?, status = ? where claim_id = ?";
        template.executeUpdate(sql,
                claim.getCustomerId(), claim.getEmployeeId(), claim.getDate(),
                claim.getType(), claim.getDescription(), claim.getLocation(),
                claim.getReport(), claim.getCompensation(),
                claim.getReviewer(), claim.getStatus(), claim.getClaimId()
        );
    }

    public List<Claim> findAll() {
        RowMapper<Claim> rm = generateCommonClaimRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from CLAIMS";
        return template.list(sql, rm);
    }

    private RowMapper<Claim> generateCommonClaimRowMapper() {
        return rs ->
                new Claim(
                        rs.getString("claim_id"),
                        rs.getString("customer_id"),
                        rs.getString("employee_id"),
                        rs.getString("date"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("report"),
                        rs.getInt("compensation"),
                        rs.getString("reviewer"),
                        rs.getString("status")
                );
    }
}



