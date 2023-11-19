public class Token {

    private String tipo;
    private String valor;
    private int posicionInicio;

    public Token(String tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.posicionInicio = posicionInicio;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public int getPosicionInicio() {
        return posicionInicio;
    }
}