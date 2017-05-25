package br.cefetmg.inf.tiny.interpretador;

// para armazenamento de informacoes sobre um simbolo identificado no codigo
public class Simbolo {

    private int token;		// identificador do simbolo
    private String lexema;   	// lexema do simbolo
    private int tipo;		// tipo do simbolo
    private String valor;	// valor do simbolo

    public Simbolo() {
        this.token = -1;
        this.lexema = "";
        this.tipo = -1;
        this.valor = "";
    }

    public Simbolo(int token, String lexema) {
        this.token = token;
        this.lexema = lexema;
        this.tipo = -1;
        this.valor = "";
    }

    @Override
    public String toString() {
        return "Simbolo{" + "token=" + token + ", lexema=" + lexema + ", tipo=" + tipo + ", valor=" + valor + '}';
    }

    public int getToken() {
        return this.token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getLexema() {
        return this.lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getTipo() {
        return this.tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
