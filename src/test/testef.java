package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class testef {
    
    // ESSE MÉTODO EU PUXEI DO PROJETO BIBLIOTECH, A IDEIA É APENAS TESTAR MEUS IMPORT E EXPORT
  public static void main(String[] args) throws IOException {
        
        String caminho = "teste_excel.csv";
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))){
            
            bw.write("ID;Titulo;Status\n");
            
            bw.write("1;O luar;disponivel\n");
            bw.write("2;Além da vida;disponivel\n");
            
            System.out.println("Ficheiro CSV gerado com sucesso!");
            
        } catch(IOException e){
            System.out.println("Erro ao escrever o ficheiro " +e.getMessage());
        }
        
    }
    
    
}

