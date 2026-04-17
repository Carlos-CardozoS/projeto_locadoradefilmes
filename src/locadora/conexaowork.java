package locadora;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexaowork{

private static Connection conexaoUnica;      
    
public Connection getConnection(){
    try{
        
        // 2. Se a conexão for null (ninguém abriu ainda) ou estiver fechada, nós abrimos
        
        if(conexaoUnica == null || conexaoUnica.isClosed()){
            
        
        
        conexaoUnica = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/locadora", // URL: Onde esta o banco?
        "root", // Usuario
        "root" // Senha
        );
            System.out.println("\n[LOG] Nova conexão com o banco aberta.");
        }
        
        // 3. Se estiver aberta, vamos devolver a que já existe.
        return conexaoUnica; 
        
    }catch(SQLException e){
        throw new RuntimeException(e);
    }
}
    
}

