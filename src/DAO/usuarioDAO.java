package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import util.seguranca;
import java.sql.SQLException;
import java.sql.ResultSet;
import locadora.conexaowork;
import model.usuario;



public class usuarioDAO {
    
    private Connection conn;
    
    public usuarioDAO(){
        this.conn = new conexaowork().getConnection();
    }
    
    public void cadastrouser(usuario u, String senhauser) throws SQLException{
       String sql = "insert into usuarios (login, senha_hash) values (?,?)";
       try{
           PreparedStatement stmt = conn.prepareStatement(sql);
           stmt.setString(1, u.getLogin());
           stmt.setString(2, seguranca.gerarHash(senhauser));
           
           stmt.execute();
           stmt.close();
           
           
       } catch (SQLException e){
           throw new RuntimeException("Erro no cadastro do utilizador: " +e.getMessage());  
       }    
    }
    
    public boolean autenticacao(String login, String senhauser) throws SQLException{
        String sql = "select * from usuarios where login = ? and senha_hash = ?";
        try{
          PreparedStatement stmt = conn.prepareStatement(sql);
          stmt.setString(1, login);
          stmt.setString(2, seguranca.gerarHash(senhauser));
          
          ResultSet rs = stmt.executeQuery();
          
          boolean liberado = rs.next();
          
          stmt.close();
            
            return liberado;
            
        }catch (SQLException e){
            throw new RuntimeException("Erro na autaenticação: " +e.getMessage());  
    }   
}   
}
