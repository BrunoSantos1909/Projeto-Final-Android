package bruno.santos.projetofinal.model;

public class Guitarra {

    private String key;
    private String marca;
    private String modelo;
    private double preco;
    private int ano;


    public Guitarra() {
    }

    public Guitarra(String key, String marca, String modelo, double preco, int ano) {
        this.key = key;
        this.marca = marca;
        this.modelo = modelo;
        this.preco = preco;
        this.ano = ano;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "Guitarra{" +
                "key='" + key + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", preco=" + preco +
                ", ano=" + ano +
                '}';
    }
}
