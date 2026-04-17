package model;


public class genero {
    
    public int id_genero;
    public String nome;

    public genero() {
        
    }

    @Override
    public String toString() {
        return "genero:" + "nome: " + nome + '}';
    }

    public genero(String nome) {
        this.nome = nome;
    }
    
    public int getId() {
        return id_genero;
    }

    public void setId(int id) {
        this.id_genero = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId_genero() {
        return id_genero;
    
    }
    
public void setId_genero(int id_genero) {
    this.id_genero = id_genero;
}
    
    
    
}
