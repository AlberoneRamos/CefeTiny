package br.cefetmg.inf.tiny;

import br.cefetmg.inf.tiny.comandos.ComandoIf;
import PilhaFila.Lista;
import java.util.ArrayList;

import br.cefetmg.inf.tiny.comandos.Comando;
import br.cefetmg.inf.tiny.comandos.ComandoAlloc;
import br.cefetmg.inf.tiny.comandos.ComandoPrint;
import br.cefetmg.inf.tiny.comandos.ComandoFor;
import br.cefetmg.inf.tiny.comandos.ComandoPrintStr;
import br.cefetmg.inf.tiny.comandos.ComandoPrintln;
import br.cefetmg.inf.tiny.comandos.ComandoWhile;
import br.cefetmg.inf.tiny.comandos.ReadInt;
import br.cefetmg.inf.tiny.excecoes.ErroSintatico;
import br.cefetmg.inf.tiny.interpretador.Constantes;
import br.cefetmg.inf.tiny.interpretador.Simbolo;

/**
 * Classe meio q copiada da estrutura de execucao da classe CefeTiny
 * , mas que j� que recebe os simbolos no construtor
 * @author digao
 *
 */
public class CommandHandler {
	private Lista<Simbolo> simbolos;
	private Simbolo simboloAtual;
	private ArrayList<Comando> comandos;


	public CommandHandler(Lista<Simbolo> simbolos) {
		this.simbolos = simbolos;
		this.comandos = new ArrayList<>();
	}

	public void run() {
                this.simboloAtual = simbolos.removeInicio();
		 while (this.simboloAtual != null){
			Comando cmd = null;
			switch (this.simboloAtual.getToken()) {
			case Constantes.PRINTSTR:
                            cmd = trataComandoPrintStr();
                            break;
			case Constantes.PRINTLN:
                            cmd = trataComandoPrintln();
                            break;
			case Constantes.T_IDENTIFIER:
                            cmd = trataComandoAlloc();
                            break;
                        case Constantes.WHILE:
                            cmd = trataComandoWhile();
                            break;
			case Constantes.PRINT:
                            cmd = trataComandoPrint(this.simboloAtual.getLexema());
                            break;
                        case Constantes.READINT:
                            cmd = trataComandoReadInt(this.simboloAtual.getLexema(),this.simboloAtual.getValor());
                            break;
                        case Constantes.FOR:
                            cmd = trataComandoFor();
                            break;
                        case Constantes.IF:
                            cmd = trataComandoIf();
                            break;
			default:
			// exiba mensagem de erro sintatico
			throw new ErroSintatico("Comando Invalido na linha: "+CefeTiny.parser.getLineNumber()+" "+this.simboloAtual.getLexema());
			}

			// adicione o comando identificado na lista de comandos
			this.comandos.add(cmd);
			this.simboloAtual = simbolos.removeInicio();
			// enquanto n�o for identificado o fim do arquivo
		}

		// depois que todos os comandos (até primeiro comando end) tiverem sido identificados
		// comece a execucao deles
		this.executa();

	}
	//tratamento do comando Printstr
    private Comando trataComandoPrintStr() {
        //obtem o proximo simbolo
        this.simboloAtual =simbolos.removeInicio();

        //analisa se e abre parentese
        if (this.simboloAtual.getToken() != Constantes.ABREPAR) {
            throw new ErroSintatico("Era esperado um ( depois de printstr");
        }

        //obtem o proximo simbolo
        this.simboloAtual = simbolos.removeInicio();

        //analisa se e uma string
        if (this.simboloAtual.getTipo() != Constantes.T_STRING) {
            throw new ErroSintatico("O comando printstr so aceita String");
        }

        //obtem a string
        String str = this.simboloAtual.getLexema();

        //obtem o proximo simbolo
        this.simboloAtual = simbolos.removeInicio();

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
		Lista<Simbolo> expressao = new Lista<>();
		String nomeVariavel = this.simboloAtual.getLexema();
		this.simboloAtual =simbolos.removeInicio();
		if (this.simboloAtual.getToken() != Constantes.ATRIBOP) {
			throw new ErroSintatico("Era esperado um := depois do nome da variavel");
		}
		simboloAtual = simbolos.removeInicio();
		expressao = getExpressao().clone();
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
		if(simboloAtual!=null)simbolos.addIni(simboloAtual,this.simboloAtual.getLexema());
		return expressao; 
	}

    private Comando trataComandoPrint(String lexema) {
         this.simboloAtual =simbolos.removeInicio();
        if (this.simboloAtual.getToken() != Constantes.ABREPAR) {
            throw new ErroSintatico("Era esperado um ( depois de Print");
        }
        this.simboloAtual = simbolos.removeInicio();
        String str = this.simboloAtual.getLexema();
        this.simboloAtual = simbolos.removeInicio();
        if (this.simboloAtual.getToken() != Constantes.FECHAPAR) {
            throw new ErroSintatico("Era esperado um ) depois de \"" + str + "\"");
        }
        return new ComandoPrint(str);
    }

    private Comando trataComandoReadInt(String lexema, String valor) {
        this.simboloAtual =simbolos.removeInicio();
        if (this.simboloAtual.getToken() != Constantes.ABREPAR) {
            throw new ErroSintatico("Era esperado um ( depois de readInt");
        }
        this.simboloAtual = simbolos.removeInicio();
        
        String str = this.simboloAtual.getLexema();
        this.simboloAtual = simbolos.removeInicio();
        if (this.simboloAtual.getToken() != Constantes.FECHAPAR) {
            throw new ErroSintatico("Era esperado um ) depois de \"" + str + "\"");
        }
        return new ReadInt(str);
    }
    
private Comando trataComandoWhile(){
        Lista<Simbolo> comando = new Lista<Simbolo>();
        Lista<Simbolo> condicao;
        simboloAtual = simbolos.removeInicio();
        if(simboloAtual.getToken() != Constantes.ABREPAR)
            throw new ErroSintatico("Era esperado um parentese depois de while na linha "+CefeTiny.parser.getLineNumber());
        condicao = getExpressao().clone();
        int ContWhile = 1;
        simboloAtual = simbolos.removeInicio();
        if(simboloAtual.getToken() != Constantes.DO)
            throw new ErroSintatico("while sem DO na linha "+CefeTiny.parser.getLineNumber());
         
        simboloAtual = simbolos.removeInicio();
         
        while( ContWhile >0 ){
            if(simboloAtual == null)
                throw new ErroSintatico("WHILE SEM ENDWHILE");
            if(simboloAtual.getToken() == Constantes.WHILE)
                ContWhile++;

            if(simboloAtual.getToken() == Constantes.ENDWHILE){
                ContWhile--;
            }

            if(simboloAtual.getToken() != Constantes.ENDWHILE 
            || ((simboloAtual.getToken() == Constantes.ENDWHILE) && ContWhile >= 1))
                comando.addFim(simboloAtual,"");

            if ((simboloAtual.getToken() != Constantes.ENDWHILE) || ContWhile != 0)
                simboloAtual = simbolos.removeInicio(); 

        }
         
        return new ComandoWhile(condicao,comando);
        }

    private Comando trataComandoFor() {
        Lista<Simbolo> comando = new Lista<>();
        Lista<Simbolo> condicao=new Lista<>();
        String VariavelIni;
        Lista<Simbolo> VariavelIniExpressao = new Lista<>();
        String Incremento;
        Lista<Simbolo> IncrementoExpressao=new Lista<>();
        simboloAtual = simbolos.removeInicio();
        if(simboloAtual.getToken() != Constantes.ABREPAR)
            throw new ErroSintatico("Era esperado um ( depois de for na linha "+CefeTiny.parser.getLineNumber());
        simboloAtual = simbolos.removeInicio();
        if(simboloAtual.getToken() != Constantes.T_IDENTIFIER)
            throw new ErroSintatico("Era esperado uma variavel depois de ( na linha "+CefeTiny.parser.getLineNumber());
        VariavelIni=simboloAtual.getLexema();
        simboloAtual = simbolos.removeInicio();
        if(simboloAtual.getToken() != Constantes.ATRIBOP)
            throw new ErroSintatico("Era esperado um := depois do nome da variável "+CefeTiny.parser.getLineNumber());
        simboloAtual = simbolos.removeInicio();
        VariavelIniExpressao =getExpressao().clone();
        simboloAtual = simbolos.removeInicio();
        simboloAtual = simbolos.removeInicio();
        condicao=getExpressao().clone();
        simboloAtual = simbolos.removeInicio();
        simboloAtual = simbolos.removeInicio();
        Incremento = simboloAtual.getLexema();
        simboloAtual = simbolos.removeInicio();
        simboloAtual = simbolos.removeInicio();
        IncrementoExpressao=getExpressao().clone();
        int ContFor = 1;
        simboloAtual = simbolos.removeInicio();
        while( ContFor >0 ){
            if(simboloAtual == null)
                throw new ErroSintatico("NÃO FOI ENCONTRADO O FIM DO FOR");
            if(simboloAtual.getToken() == Constantes.FOR)
                ContFor++;

            if(simboloAtual.getToken() == Constantes.ENDFOR){
                ContFor--;
            }

            if(simboloAtual.getToken() != Constantes.ENDFOR 
            || ((simboloAtual.getToken() == Constantes.ENDFOR) && ContFor >= 1))
                comando.addFim(simboloAtual,"");

            if ((simboloAtual.getToken() != Constantes.ENDFOR) || ContFor != 0)
                simboloAtual = simbolos.removeInicio(); 

        }
        return new ComandoFor(VariavelIni,VariavelIniExpressao,condicao,Incremento,IncrementoExpressao,comando);
    }

    private Comando trataComandoIf() {
        Lista<Simbolo> comandosIf = new Lista<Simbolo>();
        Lista<Simbolo> comandosElse = new Lista<Simbolo>();
        Lista<Simbolo> condicao;
        simboloAtual = simbolos.removeInicio();
        if(simboloAtual.getToken() != Constantes.ABREPAR)
            throw new ErroSintatico("Era esperado um parentese depois de while na linha "+CefeTiny.parser.getLineNumber());
        condicao = getExpressao().clone();
        int ContIf = 1;
        simboloAtual = simbolos.removeInicio();
        if(simboloAtual.getToken() != Constantes.THEN)
            throw new ErroSintatico("ELSE sem THEN na linha "+CefeTiny.parser.getLineNumber());
         
        simboloAtual = simbolos.removeInicio();
         
        while( ContIf >0 && simboloAtual.getToken()!=Constantes.ELSE){
            if(simboloAtual == null)
                throw new ErroSintatico("WHILE SEM ENDWHILE");
            if(simboloAtual.getToken() == Constantes.IF)
                ContIf++;

            if(simboloAtual.getToken() == Constantes.ENDIF){
                ContIf--;
            }

            if(simboloAtual.getToken() != Constantes.ENDIF 
            || ((simboloAtual.getToken() == Constantes.ENDIF) && ContIf >= 1))
                comandosIf.addFim(simboloAtual,"");

            if ((simboloAtual.getToken() != Constantes.ENDIF) || ContIf != 0)
                simboloAtual = simbolos.removeInicio(); 

        }
        if(simboloAtual.getToken()==Constantes.ELSE){
            simboloAtual = simbolos.removeInicio(); 
        }
         while( ContIf >0){
            if(simboloAtual == null)
                throw new ErroSintatico("IF SEM ENDIF");
            if(simboloAtual.getToken() == Constantes.IF)
                ContIf++;

            if(simboloAtual.getToken() == Constantes.ENDIF){
                ContIf--;
            }

            if(simboloAtual.getToken() != Constantes.ENDIF 
            || ((simboloAtual.getToken() == Constantes.ENDIF) && ContIf >= 1))
                comandosElse.addFim(simboloAtual,"");

            if ((simboloAtual.getToken() != Constantes.ENDIF) || ContIf != 0)
                simboloAtual = simbolos.removeInicio(); 

        }
        return new ComandoIf(condicao,comandosIf,comandosElse);
        }


}
