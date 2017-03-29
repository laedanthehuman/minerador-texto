package dao;

import models.StopWord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by manoel on 29/10/15.
 */
public class StopWordDao {

    private Connection conn;
    private static final String LOGMESSAGE = "SQL error";
    private static final Logger LOGGER = Logger.getLogger(StopWordDao.class.getName());

    public StopWordDao(Connection conn) {
        super();
        this.conn = conn;
    }

    public List<StopWord> all() {
        String sql = "select * from  stop_word";
        List<StopWord> stopWords = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement stmt = this.conn.prepareStatement(sql);) {
            rs = stmt.executeQuery();
            while (rs.next()) {
                StopWord stopWord = new StopWord();
                stopWord.setId(rs.getLong("id"));
                stopWord.setNome(rs.getString("nome"));
                stopWords.add(stopWord);
            }
            rs.close();
            return stopWords;
        } catch (SQLException e) {
            LOGGER.severe(LOGMESSAGE + e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOGGER.severe(LOGMESSAGE + e);
                }
            }
        }
        return stopWords;
    }

    public void save(StopWord sW) {
        String sql = "INSERT INTO  stop_word  (nome)" + "VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            // prepared statement para inserção

            // seta os valores
            stmt.setString(1, sW.getNome());
            // executa
            stmt.execute();
        } catch (SQLException e) {
            LOGGER.severe(LOGMESSAGE + e);
        }
    }

    public StopWord find(Long id) {
        String sql = "select * from  stop_word  where id=?";
        StopWord stopWord = new StopWord();
        ResultSet rs = null;
        try (PreparedStatement stmt = this.conn.prepareStatement(sql);) {

            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            rs.next();
            stopWord.setId(rs.getLong("id"));
            stopWord.setNome(rs.getString("nome"));
            return stopWord;
        } catch (SQLException e) {
            LOGGER.severe(LOGMESSAGE + e);
        }finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOGGER.severe(LOGMESSAGE + e);
                }
            }
        }
        return stopWord;

    }

    public void update(StopWord entity) {
        // TODO Auto-generated method stub

    }
}
