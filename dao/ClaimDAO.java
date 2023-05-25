package dao;


import java.sql.SQLException;

public class ClaimDAO extends DAO {

    public ClaimDAO(String name) {
        super(name);
        tableName = "CLAIM";
        primarykey = "claimId";
    }


}
