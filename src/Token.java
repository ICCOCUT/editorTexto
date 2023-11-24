public class Token {

    private String tipo;
    private String valor;
    private int posicionInicio;
    private double valorNumerico;

    public Token(String tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
        
        this.valorNumerico = 0;
        
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getPosicionInicio() {
        return posicionInicio;
    }
    public double getValorNumerico() {
        return valorNumerico;
    }

    public void setValorNumerico(double valorNumerico) {
        this.valorNumerico = valorNumerico;
    }
}