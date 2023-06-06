package dao;

import java.util.List;

import annotation.DAO;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import marketing.Board;

@DAO
public class BoardDAO {
    public void addBoard(Board board) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "insert into BOARDS (statement_id, statement_title, statement_content, customer_id, answer, processed) values(?,?,?,?,?,?)";
        template.executeUpdate(sql,
                board.getStatementId(), board.getTitle(), board.getContent(), board.getCustomerId(), board.getAnswer(), board.getProcessed());
    }

    public Board findByBoardId(String boardId) {
        RowMapper<Board> rm = generateCommonBoardRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from BOARDS where board_id = ?";
        return template.executeQuery(sql, rm, boardId);
    }

    public Board findById(Long id) {
        RowMapper<Board> rm = generateCommonBoardRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from BOARDS where id = ?";
        return template.executeQuery(sql, rm, id);
    }

    public void removeBoardByBoardId(String boardId) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "delete from BOARDS where board_id = ?";
        template.executeUpdate(sql, boardId);
    }

    public void updateBoard(Board board) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "update BOARDS set statement_title = ?, statement_content = ?, customer_id = ?, answer = ?, processed = ? where statement_id = '" + board.getStatementId() +"'";
        template.executeUpdate(sql,
                board.getTitle(),
                board.getContent(),
                board.getCustomerId(),
                board.getAnswer(),
                board.getProcessed());

    }

    public List<Board> findAll() {
        RowMapper<Board> rm = generateCommonBoardRowMapper();

        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from BOARDS";
        return template.list(sql, rm);
    }

    private RowMapper<Board> generateCommonBoardRowMapper() {
        return rs ->
                new Board(
                        rs.getString("statement_id"),
                        rs.getString("statement_title"),
                        rs.getString("statement_content"),
                        rs.getString("customer_id"),
                        rs.getString("answer"),
                        rs.getInt("processed")
                );
    }
}