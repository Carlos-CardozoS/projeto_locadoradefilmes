
package model;

import model.genero;

public class filme {
    
    public int id_filme;
    public String titulo;
    public String classificacao_etaria;
    public int ano_lancamento;
    public String status;
    public genero id_genero;
    private genero genero;

    
public filme() {
    
}
    
    public filme(String titulo, String classificacao_etaria, int ano_lancamento, String status) {
        this.titulo = titulo;
        this.classificacao_etaria = classificacao_etaria;
        this.ano_lancamento = ano_lancamento;
        this.status = status;
    }

  @Override
public String toString() {
    String nomeGenero = (this.id_genero != null) ? this.id_genero.getNome() : "Sem Gênero";
    return 
           "ID: " + id_filme + " | " +
            "titulo: " + titulo + 
           " | faixa etária: " + classificacao_etaria + 
           " | ano de lançamento: " + ano_lancamento + 
           " | gênero: " + nomeGenero + 
           " | status: " + status;
}

    public int getId_filme() {
        return id_filme;
    }

    public void setId_filme(int id_filme) {
        this.id_filme = id_filme;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getClassificacao_etaria() {
        return classificacao_etaria;
    }

    public void setClassificacao_etaria(String classificacao_etaria) {
        this.classificacao_etaria = classificacao_etaria;
    }

    public int getAno_lancamento() {
        return ano_lancamento;
    }

    public void setAno_lancamento(int ano_lancamento) {
        this.ano_lancamento = ano_lancamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public genero getId_genero() {
        return id_genero;
    }

    public void setId_genero(genero id_genero) {
        this.id_genero = id_genero;
    }

    public void setCategoria(genero gen) {
        this.id_genero = gen; 
    }

    public genero getGenero() {
        return genero;
    }

    public void setGenero(genero genero) {
        this.genero = genero;
    }



    
}

