package jdbc;

import annotation.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@DAO
public interface PreparedStatementSetter {
	void setParameters(PreparedStatement pstmt) throws SQLException;
}
