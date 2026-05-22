package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
    
public List<String> obterDadosParaExportar() throws SQLException {
    List<String> dados = new ArrayList<>();
    String sql = "SELECT id_filme, titulo, status, id_genero, ano_lancamento FROM filmes"; 
    
    try (PreparedStatement stmt = conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            String linha = rs.getInt("id_filme") + "," 
                         + rs.getString("titulo") + "," 
                         + rs.getString("status") + "," 
                         + rs.getInt("id_genero") + "," 
                         + rs.getString("ano_lancamento");
            dados.add(linha);
        }
    }
    return dados;
}

public void importarFilme(int id, String titulo, String status, int idGenero, String ano) throws SQLException {
    String sql = "INSERT INTO filmes (id_filme, titulo, status, id_genero, ano_lancamento, classificacao_etaria) VALUES (?, ?, ?, ?, ?, ?) "
               + "ON DUPLICATE KEY UPDATE titulo = ?, status = ?, id_genero = ?, ano_lancamento = ?";
    
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        // Tratamento para o ano não ir como 'null'
        int anoInt = 0;
        try {
            if (ano != null && !ano.trim().isEmpty() && !ano.equalsIgnoreCase("null")) {
                anoInt = Integer.parseInt(ano.trim());
            }
        } catch (NumberFormatException e) { anoInt = 0; }

        stmt.setInt(1, id);
        stmt.setString(2, titulo);
        stmt.setString(3, status);
        stmt.setInt(4, idGenero);
        stmt.setInt(5, anoInt); // Enviando como Inteiro
        stmt.setString(6, "L");
        
        stmt.setString(7, titulo);
        stmt.setString(8, status);
        stmt.setInt(9, idGenero);
        stmt.setInt(10, anoInt);
        
        stmt.executeUpdate();
    }
}
    
}
