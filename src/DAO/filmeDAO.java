package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import locadora.conexaowork;
import model.filme;
import model.genero;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class filmeDAO {
    
    private Connection conn;
     
      public filmeDAO(){
        this.conn = new locadora.conexaowork().getConnection();
    }
    
      
      // aqui eu criei o método cadastrar, para cadastrar filmes no meu banco de dados
   public void cadastrar(filme f) throws SQLException {
    String sql = "INSERT INTO filmes(titulo, classificacao_etaria, ano_lancamento, status, id_genero) VALUES (?,?,?,?,?)";
    
    try {
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, f.getTitulo());
        stmt.setString(2, f.getClassificacao_etaria());
        stmt.setInt(3, f.getAno_lancamento());
        stmt.setString(4, f.getStatus());
        
        stmt.setInt(5, f.getId_genero().getId_genero()); 
        
        stmt.execute();
        
        stmt.close();
        
        System.out.println("Filme cadastrado com sucesso!");
        
    } catch (SQLException e) {
        throw new RuntimeException("Ocorreu um erro ao cadastrar o filme: " + e.getMessage());
    }
}
   
   public void cadastrargen(genero g) {
       String sql = "INSERT INTO generos(nome) VALUES (?)";
       
       try {
           
           PreparedStatement stmt = conn.prepareStatement(sql);
           
           stmt.setString(1, g.getNome());
           
           stmt.execute();
           stmt.close();
           
           System.out.println("Gênero cadastrado com sucesso!");
           
       } catch (SQLException e) {
        throw new RuntimeException("Ocorreu um erro ao cadastrar o gênero: " + e.getMessage());
    }
       
       
   }


    // aqui eu criei um método para converter a busca por id para nome, por exemplo: eu vou buscar o genero pelo id e setar pro nome.
    
  public int consultarIdGenero(String nome_genero) {
    String sql = "SELECT id_genero FROM generos WHERE nome = ?"; 
    
    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nome_genero);
        ResultSet rs = stmt.executeQuery();
        
        int idEncontrado = -1;
        
        if (rs.next()) {
            idEncontrado = rs.getInt("id_genero");
        }
        
        rs.close();
        stmt.close();
        
        return idEncontrado;
        
    } catch (SQLException e) {
        System.err.println("Erro ao buscar ID do gênero: " + e.getMessage());
        return -1;
    }
}
   
   
   // aqui eu desenvolvi o método listar
   public List<filme> listar() throws SQLException{
    String sql = "SELECT filmes.*, generos.nome AS nome_genero " +
             "FROM filmes LEFT JOIN generos ON filmes.id_genero = generos.id_genero";
    
    List<filme> lista = new ArrayList<>();
    
    try {
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
         while (rs.next()) {
        filme f = new filme();
       
        
                f.setId_filme(rs.getInt("id_filme"));
                f.setTitulo(rs.getString("titulo"));
                f.setClassificacao_etaria(rs.getString("classificacao_etaria"));
                f.setAno_lancamento(rs.getInt("ano_lancamento"));
                f.setStatus(rs.getString("status"));
        
        int idGenero = rs.getInt("id_genero");
        
if (!rs.wasNull()) {
    genero g = new genero();
    g.setId(idGenero);
    g.setNome(rs.getString("nome_genero"));
    
   
    f.setId_genero(g); 
    f.setGenero(g);
}
         lista.add(f);
    }
          stmt.close();
          rs.close();
    } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar: " + e.getMessage());
        }
     
return lista;
      }
   
   public void atualizar(filme filmeAtualizado){
       
       String sql ="update filmes set status = ? where id_filme =  ?";
       try{
           PreparedStatement stmt = conn.prepareStatement(sql);
           
           stmt.setString(1, filmeAtualizado.getStatus());
           stmt.setInt(2, filmeAtualizado.getId_filme());
           
           stmt.execute();
            stmt.close();
            System.out.println(" Status atualizado com sucesso!");
           
           
           
           
       } catch (SQLException e) {
           throw new RuntimeException("Erro ao atualizar filme: " + e.getMessage());
       }
       
       
       
   }
   
   public void deletar (int ID){
       
       String sql = "DELETE FROM filmes WHERE id_filme = ?";
       
       try{
           PreparedStatement stmt = conn.prepareStatement(sql);
           stmt.setInt(1, ID);
           
           stmt.execute();
           stmt.close();
           System.out.println(" O filme foi apagado com sucesso da locadora senacflix");
           
           
       }catch (SQLException e) {
        throw new RuntimeException("Erro ao excluir: " + e.getMessage());
       }
    
    
}
   
   public filme buscaID(int id) throws SQLException {
                String sql = "SELECT * FROM filmes WHERE id_filme = ?";
                filme filme = null;
                try{
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();
                    
                    if (rs.next()){
                        filme = new filme();
                        filme.setId_filme(rs.getInt("id_filme"));
                        filme.setTitulo(rs.getString("titulo"));
                        filme.setAno_lancamento(rs.getObject("ano_lancamento", int.class));
                        filme.setStatus(rs.getString("status"));
                        
                    }
                    stmt.close();
                } catch (SQLException e){
                    throw new RuntimeException("Erro ao buscar ID: " + e.getMessage());
                }
                return filme;
}
   
  public boolean alocacao(int id_filme, String nome_cliente) throws SQLException {
    String sqlalocar = "INSERT INTO emprestimos (id_filme, nome_cliente) VALUES (?, ?)";
    String sqlattfilme = "UPDATE filmes SET status = 'locado' WHERE id_filme = ?";
    
    try {
        conn.setAutoCommit(false); 
        
        PreparedStatement stmt1 = conn.prepareStatement(sqlalocar);
        stmt1.setInt(1, id_filme);
        stmt1.setString(2, nome_cliente); 
        stmt1.executeUpdate();
        stmt1.close();
        
        PreparedStatement stmt2 = conn.prepareStatement(sqlattfilme);
        stmt2.setInt(1, id_filme);
        stmt2.executeUpdate();
        stmt2.close();
        
        conn.commit();
        return true;
        
    } catch (SQLException e) {
        System.out.println(" AVISO: ERRO ENCONTRADO NO DAO: " + e.getMessage());
        conn.rollback();
        return false;
    } finally {
        conn.setAutoCommit(true);
    }
}
   
   public boolean devolver (int id_filme) throws SQLException{
       String sqlDevolver =  "update emprestimos set data_devolucao_real = now() where id_filme = ? and data_devolucao_real is null";
       String sqlUpdate = "update filmes set status = 'disponivel' where id_filme = ?";
       
       try{
           
           conn.setAutoCommit(false);
           
           PreparedStatement stmt0 = conn.prepareStatement(sqlDevolver);
           stmt0.setInt(1, id_filme);
           
           int pendencias = stmt0.executeUpdate();
           
         if(pendencias == 0){
           System.out.println("Aviso: Não há registro de empréstimo em aberto para este livro");
           conn.rollback();
           return false;    
       }  
         
         PreparedStatement stmt2 = conn.prepareStatement(sqlUpdate);
         stmt2.setInt(1, id_filme);
         stmt2.executeUpdate();
         stmt2.close();;
         
         conn.commit();
         return true;
         
         
           
       } catch (SQLException e) {
        try {
            System.out.println("AVISO: ERRO ENCONTRADO: " + e.getMessage());
            conn.rollback();
        } catch (SQLException ex) {
            System.out.println("AVISO: ERRO NO ROLLBACK ENCONTRADO: " + ex.getMessage());
        }
        return false;
    } finally {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("AVISO: ERRO NA RESTAURAÇÃO DO AUTOCOMMIT:: " + e.getMessage());
        }
    }
       
       
   }
   
public List<filme> listarPorGenero(String nomeGen) throws SQLException {
    List<filme> lista = new ArrayList<>();
    String sql = "SELECT f.*, g.nome AS nome_genero FROM filmes f " +
                 "JOIN generos g ON f.id_genero = g.id_genero " +
                 "WHERE g.nome LIKE ?";
    
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
        pst.setString(1, "%" + nomeGen + "%");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            filme f = new filme();
            f.setId_filme(rs.getInt("id_filme"));
            f.setTitulo(rs.getString("titulo"));
            f.setStatus(rs.getString("status"));
            f.setAno_lancamento(rs.getInt("ano_lancamento"));
            f.setClassificacao_etaria(rs.getString("classificacao_etaria"));
            
            genero g = new genero();
            g.setId(rs.getInt("id_genero"));
            g.setNome(rs.getString("nome_genero"));
            
            f.setId_genero(g);
            f.setGenero(g); 
            lista.add(f);
        }
    }
    return lista;
}

public List<filme> listarPorStatus(String status) throws SQLException {
    List<filme> lista = new ArrayList<>();
    String sql = "SELECT f.*, g.nome AS nome_genero FROM filmes f " +
                 "LEFT JOIN generos g ON f.id_genero = g.id_genero " +
                 "WHERE f.status = ?";
    
    try (PreparedStatement pst = conn.prepareStatement(sql)) {
        pst.setString(1, status);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            filme f = new filme();
            f.setId_filme(rs.getInt("id_filme"));
            f.setTitulo(rs.getString("titulo"));
            f.setStatus(rs.getString("status"));
            f.setAno_lancamento(rs.getInt("ano_lancamento"));
            f.setClassificacao_etaria(rs.getString("classificacao_etaria"));
            
            genero g = new genero();
            g.setId(rs.getInt("id_genero"));
            g.setNome(rs.getString("nome_genero"));
            
            f.setId_genero(g);
            f.setGenero(g); 
            lista.add(f);
        }
    }
    return lista;
}

public List<genero> listarGenerosLimpos() {
    List<genero> lista = new ArrayList<>();
    String sql = "SELECT * FROM generos ORDER BY nome ASC";
    
    try {
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            genero g = new genero();
            g.setId(rs.getInt("id_genero"));
            g.nome = rs.getString("nome");
            lista.add(g);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar gêneros: " + e.getMessage());
    }
    return lista;
}

public void deletarGenero(int id) {
    String sql = "DELETE FROM generos WHERE id_genero = ?";
    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao deletar gênero: " + e.getMessage());
    }
}
   

public List<String> listarNomesGeneros() throws SQLException {
    List<String> nomes = new ArrayList<>();
    String sql = "SELECT nome FROM generos ORDER BY nome ASC";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            nomes.add(rs.getString("nome"));
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar nomes dos gêneros: " + e.getMessage());
    }
    return nomes;
}
   
   
}
