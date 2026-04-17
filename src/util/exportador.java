package util;

import DAO.filmeDAO;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.filme;

public class exportador {

    public void exportarFilmes(String caminhoArquivo) throws IOException, SQLException {

        filmeDAO dao = new filmeDAO();
        List<filme> filmes = dao.listar();

        if (filmes.isEmpty()) {
            System.out.println("O catálogo está vazio. Nada para exportar!");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {

            bw.write("ID;Título;Classificação;Ano;Gênero;Status\n");

            for (filme f : filmes) {
                
                // aqui eu vou buscar o genero pelo id do genero, pra não dar conflito na hora de importar
                          int idGen = 0;
                          if (f.getId_genero() != null) {
                          idGen = f.getId_genero().getId_genero(); 
           }

                String linha = f.getId_filme() + ";" +
                               f.getTitulo() + ";" +
                               f.getClassificacao_etaria() + ";" + 
                               f.getAno_lancamento() + ";" +       
                               idGen + ";" +
                               f.getStatus() + "\n";

                bw.write(linha);
            }
            System.out.println("?Backup do catálogo salvo com sucesso em: " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo: " + e.getMessage());
        }
    }
}