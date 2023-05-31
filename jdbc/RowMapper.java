package jdbc;

import annotation.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

@DAO
public interface RowMapper<T> {
	T mapRow(ResultSet rs) throws SQLException;
}
