package util;

import DAO.filmeDAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import model.filme;
import model.genero;

public class importador {
    
    public void importarFilmes(String caminhoArquivo) {
        
        filmeDAO dao = new filmeDAO();
        int contador = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = br.readLine(); 
            
            linha = br.readLine();
            
            while (linha != null) {
                String[] dados = linha.split(";");
                
                String titulo = dados[1];
                String classificacao = dados[2];
                int anoLancamento = Integer.parseInt(dados[3]);
                int idGeneroInt = Integer.parseInt(dados[4]);
                
                genero gen = new genero();
                gen.setId(idGeneroInt); 
                
                filme novoFilme = new filme(titulo, classificacao, anoLancamento, "disponivel");
                novoFilme.setId_genero(gen); 
                
                dao.cadastrar(novoFilme);
                contador++;
                
                linha = br.readLine();
            }
            
            System.out.println(" Importação concluída! " + contador + " filmes adicionados ao SenacFlix.");
        } catch (IOException e) {
            System.out.println(" Erro ao ler o arquivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("️ Erro! Verifique se o ID do gênero e o ano são números. aviso: " + e.getMessage());
        }
    }
}