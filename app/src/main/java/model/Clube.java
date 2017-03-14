package model;

/**
 * Created by danilopereira on 02/03/17.
 */

public class Clube {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private int id;
    private String nome;

    public Clube(){

    }

    public Clube(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
