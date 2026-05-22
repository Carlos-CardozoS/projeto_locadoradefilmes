package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import locadora.conexaowork;
import model.usuario;
import util.seguranca;

public class usuarioDAO {
    
    private Connection conn;
    
    public usuarioDAO(){
        this.conn = new conexaowork().getConnection();
    }

    public boolean criarLogin(String usuario, String senhaCriptografada) throws SQLException {
        String sql = "INSERT INTO usuarios (login, senha_hash) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, senhaCriptografada);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar na tabela usuarios: " + e.getMessage());
            return false;
        }
    }
    
    public boolean autenticacao(String login, String senhaCriptografada) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha_hash = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senhaCriptografada);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); 
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro na autenticação: " + e.getMessage());
        }
    }
    
    public usuario buscarPorLogin(String login) {
    String sql = "SELECT id_user, login FROM usuarios WHERE login = ?";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, login);
        
                try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                usuario user = new usuario();
                user.setId(rs.getInt("id_user"));
                user.setLogin(rs.getString("login"));
                
                return user; 
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
    }
    return null; 
}
    
}