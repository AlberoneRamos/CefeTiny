package br.cefetmg.inf.tiny;					// pacote ao qual este objeto pertence

import PilhaFila.Lista;
import PilhaFila.UberLista;
import br.cefetmg.inf.tiny.comandos.Comando;
import br.cefetmg.inf.tiny.excecoes.ErroSintatico;
import br.cefetmg.inf.tiny.interpretador.Constantes;
import br.cefetmg.inf.tiny.interpretador.Interpretador;
import br.cefetmg.inf.tiny.interpretador.Simbolo;
import java.util.ArrayList;

public class CefeTiny {

    public static Interpretador parser;           // para leitura dos simbolos do arquivo
    private ArrayList<Comando> comandos;    // para armazenamento dos comandos
    private Simbolo simboloAtual;           // para leitura do arquivo de entrada
    public static UberLista Memoria=new UberLista();
    private Lista<Simbolo> simbolos=new Lista<>();

    // construtor da classe CefeTiny --------------------------------------
    public CefeTiny(String fileName) throws java.io.FileNotFoundException {
        CefeTiny.parser = new Interpretador(fileName);      // crie o analisador lexico do arquivo
        this.comandos = new ArrayList<>();              // crie a lista de comandos
    }

    // inicia interpretador de comandos
    public void run() {
    	Simbolo auxiliarCopia = null;
    	do {
    		auxiliarCopia = parser.obterSimbolo();
    		simbolos.addFim(auxiliarCopia,"");
    	}
    	while(auxiliarCopia.getToken() != Constantes.END);
    	
    	Simbolo ultimoComando = this.simbolos.removeFim();
    	
        this.simboloAtual = simbolos.removeInicio();        

        if(this.simboloAtual.getToken() != Constantes.BEGIN)
            throw new ErroSintatico("Erro: bloco de comando invalido na linha "
                        + CefeTiny.parser.getLineNumber() + ": era esperado begin");
        CommandHandler Commands = new CommandHandler(simbolos);
        Commands.run();
        if(ultimoComando.getToken() != Constantes.END){
        	System.err.println("SEU BURRO FAZ DENOVO");
        	System.exit(4564);
        }
    }
        /*do {
            // obtenha o proximo simbolo
            this.simboloAtual = this.parser.obterSimbolo();
            
            // auxiliar para referenciar comando
            Comando cmd = null;

            // selecione o comando
            switch (this.simboloAtual.getToken()) {
                case Constantes.PRINTSTR:
                    cmd = trataComandoPrintStr();
                    break;
                case Constantes.PRINTLN:
                    cmd = trataComandoPrintln();
                    break;
                case Constantes.END:
                    cmd = trataComandoEnd();
                    break;
                case Constantes.T_IDENTIFIER:
                    cmd = trataComandoAlloc();
                    break;
                case Constantes.PRINT:
                    cmd = trataComandoPrint(this.simboloAtual.getLexema());
                    break;
                case Constantes.READINT:
                    cmd = trataComandoReadInt(this.simboloAtual.getLexema(),this.simboloAtual.getValor());
                    break;
                default:						// nao eh um comando valido
                    // exiba mensagem de erro sintatico
                    throw new ErroSintatico("Erro: bloco de comando invalido na linha "
                            + this.parser.getLineNumber() + ": foi encontrado '"
                            + this.simboloAtual.getLexema() + "' ");
            }

            // adicione o comando identificado na lista de comandos
            this.comandos.add(cmd);

            // enquanto n�o for identificado o fim do arquivo
        } while (this.simboloAtual.getToken() != Constantes.END);

        // depois que todos os comandos (até primeiro comando end) tiverem sido identificados
        // comece a execucao deles
        this.executa();
        
    }

    // tratamento do comando End
    private Comando trataComandoEnd() {
        
        return new ComandoEnd();	// cria o comando End
    }

    //tratamento do comando Printstr
    private Comando trataComandoPrintStr() {
        //obtem o proximo simbolo
        this.simboloAtual =this.parser.obterSimbolo();

        //analisa se e abre parentese
        if (this.simboloAtual.getToken() != Constantes.ABREPAR) {
            throw new ErroSintatico("Era esperado um ( depois de printstr");
        }

        //obtem o proximo simbolo
        this.simboloAtual = this.parser.obterSimbolo();

        //analisa se e uma string
        if (this.simboloAtual.getTipo() != Constantes.T_STRING) {
            throw new ErroSintatico("O comando printstr so aceita String");
        }

        //obtem a string
        String str = this.simboloAtual.getLexema();

        //obtem o proximo simbolo
        this.simboloAtual = this.parser.obterSimbolo();

        //analisa se e fecha parentese
        if (this.simboloAtual.getToken() != Constantes.FECHAPAR) {
            throw new ErroSintatico("Era esperado um ) depois de \"" + str + "\"");
        }

        //cria o comando Printstr
        return new ComandoPrintStr(str);
    }

    //tratamento do comando Println
    private Comando trataComandoPrintln() {
        //cria o comando Println
        return new ComandoPrintln();
    }
    
    // Execucao dos comandos lidos do arquivo
    private void executa() {
        for(Comando comando:this.comandos){
            comando.executa();
        }
    }

    private Comando trataComandoAlloc() {
		Lista<Simbolo> expressao = new Lista<Simbolo>();
		String nomeVariavel = this.simboloAtual.getLexema();
		this.simboloAtual =simbolos.removeInicio();
		if (this.simboloAtual.getToken() != Constantes.ATRIBOP) {
			throw new ErroSintatico("Era esperado um := depois do nome da variavel");
		}
		simboloAtual = simbolos.removeInicio();
		expressao = getExpressao();
		return new ComandoAlloc(nomeVariavel,expressao);
			
	}
    
    private Lista<Simbolo> getExpressao(){
		Lista<Simbolo> expressao = new Lista<>();
		while(this.simboloAtual != null && (
				this.simboloAtual.getToken() == Constantes.ABREPAR ||
				this.simboloAtual.getToken() == Constantes.FECHAPAR ||
				this.simboloAtual.getToken() == Constantes.T_CONSTANT ||
				this.simboloAtual.getToken() == Constantes.UNOP ||
				this.simboloAtual.getToken() == Constantes.ADDOP ||
				this.simboloAtual.getToken() == Constantes.MULOP ||
				this.simboloAtual.getToken() == Constantes.POWOP ||
				this.simboloAtual.getToken() == Constantes.RELOP ||
				this.simboloAtual.getToken() == Constantes.T_IDENTIFIER 
				)
				// this.simboloAtual.getToken() == Constantes.ATRIBOP
				)
		{
			if(this.simboloAtual.getToken() == Constantes.T_IDENTIFIER){
				Simbolo bufferAuxiliar = simbolos.removeInicio();
				if(bufferAuxiliar != null && bufferAuxiliar.getToken() == Constantes.ATRIBOP){
					simbolos.addIni(bufferAuxiliar,this.simboloAtual.getLexema());
					simbolos.addIni(simboloAtual,this.simboloAtual.getLexema());
					return expressao;
				}
				simbolos.addIni(bufferAuxiliar,this.simboloAtual.getLexema());
			}
			expressao.addFim(simboloAtual,this.simboloAtual.getLexema()); 
			simboloAtual = simbolos.removeInicio();
		}
		simbolos.addIni(this.simboloAtual,this.simboloAtual.getLexema());
		return expressao; 
	}

    private Comando trataComandoPrint(String lexema) {
         this.simboloAtual =this.parser.obterSimbolo();
        if (this.simboloAtual.getToken() != Constantes.ABREPAR) {
            throw new ErroSintatico("Era esperado um ( depois de Print");
        }
        this.simboloAtual = this.parser.obterSimbolo();
        String str = this.simboloAtual.getLexema();
        this.simboloAtual = this.parser.obterSimbolo();
        if (this.simboloAtual.getToken() != Constantes.FECHAPAR) {
            throw new ErroSintatico("Era esperado um ) depois de \"" + str + "\"");
        }
        return new ComandoPrint(str);
    }

    private Comando trataComandoReadInt(String lexema, String valor) {
        this.simboloAtual =this.parser.obterSimbolo();
        if (this.simboloAtual.getToken() != Constantes.ABREPAR) {
            throw new ErroSintatico("Era esperado um ( depois de readInt");
        }
        this.simboloAtual = this.parser.obterSimbolo();
        
        String str = this.simboloAtual.getLexema();
        this.simboloAtual = this.parser.obterSimbolo();
        if (this.simboloAtual.getToken() != Constantes.FECHAPAR) {
            throw new ErroSintatico("Era esperado um ) depois de \"" + str + "\"");
        }
        return new ReadInt(str);
    }*/
}

