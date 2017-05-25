package PilhaFila;

import br.cefetmg.inf.tiny.interpretador.Constantes;


public class Variavel {
	private Object valor;
	private String nome;
	private int TIPO;
	
	public Variavel(Object valor, String nome, int TIPO) {
		super();
		this.valor = valor;
		this.nome = nome;
		this.TIPO = TIPO;
	}

	public Variavel() {
		super();
		this.valor = null;
		this.nome = null;
		this.TIPO = Constantes.ENDWHILE;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTIPO() {
		return TIPO;
	}

	public void setTIPO(int tIPO) {
		TIPO = tIPO;
	}
	
	
	
}
