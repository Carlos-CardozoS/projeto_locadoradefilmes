package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import locadora.conexaowork;

public class relatorioDAO {
    
    private Connection conexao;

    public relatorioDAO() throws SQLException {
        this.conexao = new conexaowork().getConnection();
    }

    public int getTotalFilmes() throws SQLException {
        String sql = "SELECT COUNT(*) FROM filmes";
        try (PreparedStatement pst = conexao.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getQtdPorStatus(String status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM filmes WHERE status = ?";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setString(1, status);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}
