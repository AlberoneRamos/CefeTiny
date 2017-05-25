package br.cefetmg.inf.tiny.interpretador;

import PilhaFila.UberLista;
import br.cefetmg.inf.tiny.excecoes.ErroArquivo;
import br.cefetmg.inf.tiny.excecoes.ErroSintatico;
import java.io.FileNotFoundException;

public class Interpretador {

    private LeitorArquivo entrada = null;       // para leitura do arquivo de entrada
    private Simbolo simboloAtual = null;        // para armazenamento do simbolo atual
    private boolean usarUltimoSimbolo = false;  // para retornar ultimo simbolo lido
    private int linhaAvaliada = 1;              // linha sendo avaliada
    // construtor padrao
    public Interpretador(String nomeArquivo) throws FileNotFoundException {
        // cria arquivo para leitura
        this.entrada = new LeitorArquivo(nomeArquivo);
    }

    // retorna o proximo simbolo do arquivo fonte
    public Simbolo obterSimbolo() {
        String lexema = "";		// lexema lido
        Simbolo simb = new Simbolo();	// simbolo indentificado

        // se nao for o ultimo simbolo lido
        if (this.usarUltimoSimbolo == false) {
            try {
                int s = 0;		// para controle do estado corrente do automato
                char ch;		// simbolo lido do arquivo

                // enquanto nao for estado de aceitacao
                while (s != -1) {
                    ch = this.entrada.getItem(); // leia um caractere do arquivo

                    switch (s) { // selecione o estado atual
                        case 0:	// se estado inicial
                            // ignore espacos e quebras de linha
                            if ((ch == ' ') || (ch == '\r')) {
                                s = 0;
                            } else if (ch == '\n') {
                                this.linhaAvaliada++;
                                s = 0;
                            } else if (isAlpha(ch)) {	// se ch for uma letra do alfabeto
                                lexema += ch;
                                s = 1;
                            } else if (isNumeric(ch)) {	// se ch for um n�mero de 0 a 9
                                lexema += ch;
                                s = 2;
                            } else if (ch == ':') {	// se ch for operador de atribuicao
                                lexema += ch;
                                s = 4;
                            } else if (ch == '<') {	// se ch for operador logico menor
                                lexema += ch;
                                s = 5;
                            } else if (ch == '>') {	// se for operador logico maior
                                lexema += ch;
                                s = 6;
                            } // se for operador + ou -
                            else if ((ch == '+') || (ch == '-')) {
                                lexema += ch;
                                simb.setToken(Constantes.ADDOP);
                                s = -1;
                            } // se for operador * ou /
                            else if ((ch == '*') || (ch == '/')) {
                                lexema += ch;
                                simb.setToken(Constantes.MULOP);
                                s = -1;
                            } // se for operador ^
                            else if (ch == '^') {
                                lexema += ch;
                                simb.setToken(Constantes.POWOP);
                                s = -1;
                            } // se for operador =
                            else if (ch == '=') {
                                lexema += ch;
                                simb.setToken(Constantes.RELOP);
                                s = -1;
                            } // se for abertura de parentese
                            else if (ch == '(') {
                                lexema += ch;
                                simb.setToken(Constantes.ABREPAR);
                                s = -1;
                            } // se for fechamento de parentese
                            else if (ch == ')') {
                                lexema += ch;
                                simb.setToken(Constantes.FECHAPAR);
                                s = -1;
                            
                            } 
                            else if (ch == ';') {
                                lexema += ch;
                                simb.setToken(Constantes.PONTOEVIRGULA);
                                s = -1;
                            }
                            else if (ch == '\'') {
                                s = 7;
                            } else {            // senao; o lexema nao pode ser identificado
                                lexema += ch;
                                // exiba mensagem de erro sintatico
                                throw new ErroSintatico("Simbolo '" + lexema + "' invalido na linha " + this.linhaAvaliada);
                            }
                            break;
                        case 1: // se estado 1
                            // se caractere for uma letra ou um d�gito
                            if (isAlpha(ch) || isNumeric(ch))
                                lexema += ch;
                            else {
                                simb.setToken(Constantes.T_IDENTIFIER);
                                this.entrada.ungetItem();
                                s = -1; // sinalize estado final
                            }
                            break;
                        case 2: // se estado 2
                            if (isNumeric(ch)) // se caractere for um d�gito
                                lexema += ch;
                            else if (ch == '.') {
                                lexema += ch;
                                s = 3; // sinalize estado final
                            } else {
                                simb.setToken(Constantes.T_CONSTANT);
                                simb.setTipo(Constantes.T_INTEGER);
                                this.entrada.ungetItem();
                                s = -1; // sinalize estado final
                            }
                            break;
                        case 3:
                            if (isNumeric(ch))
                                lexema += ch;
                            else {
                                simb.setToken(Constantes.T_CONSTANT);
                                simb.setTipo(Constantes.T_DOUBLE);
                                this.entrada.ungetItem();
                                s = -1; // sinalize estado final
                            }
                            break;
                        case 4:
                            if (ch == '=') {
                                lexema += ch;
                                simb.setToken(Constantes.ATRIBOP);
                                s = -1;               // sinalize estado final
                            } else // senao
                                throw new ErroSintatico("Simbolo invalido '" + ch + "' na linha " + this.linhaAvaliada + ": era esperado '='");
                            break;
                        case 5:                   // se estado 5
                            simb.setToken(Constantes.RELOP);
                            if ((ch == '>') || (ch == '='))
                                lexema += ch;
                            else
                                this.entrada.ungetItem();

                            s = -1;               // sinalize estado final
                            break;
                        case 6:                   // se estado 6
                            simb.setToken(Constantes.RELOP);
                            if (ch == '=') // se caractere for =
                                lexema += ch;
                            else
                                this.entrada.ungetItem();

                            s = -1;                 // sinalize estado final
                            break;
                        case 7:
                            if (ch != '\'')
                                lexema += ch;
                            else {
                                simb.setToken(Constantes.T_CONSTANT);
                                simb.setTipo(Constantes.T_STRING);
                                s = -1;
                            }

                            break;
                    }
                }
            } catch (ErroArquivo e) {
                
            }

            lexema = lexema.toLowerCase();
            simb.setLexema(lexema);

            switch (lexema) {
                case "begin":
                    simb.setToken(Constantes.BEGIN);
                    break;
                case "true":
                case "false":
                    simb.setToken(Constantes.T_CONSTANT);
                    simb.setTipo(Constantes.T_BOOLEAN);
                    break;
                case "div":
                case "mod":
                case "and":
                    simb.setToken(Constantes.MULOP);
                    break;
                case "not":
                case "sqrt":
                    simb.setToken(Constantes.UNOP);
                    break;
                case "or":
                    simb.setToken(Constantes.ADDOP);
                    break;
                case "print": 
                    simb.setToken(Constantes.PRINT);
                    break;
                case "println":
                    simb.setToken(Constantes.PRINTLN);
                    break;
                case "readint":
                    simb.setToken(Constantes.READINT);
                    break;
                case "readfloat":
                    simb.setToken(Constantes.READFLOAT);
                    break;
                case "if":
                    simb.setToken(Constantes.IF);
                    break;
                case "else":
                    simb.setToken(Constantes.ELSE);
                    break;
                case "endif":
                    simb.setToken(Constantes.ENDIF);
                    break;
                case "while":
                    simb.setToken(Constantes.WHILE);
                    break;
                case "endwhile":
                    simb.setToken(Constantes.ENDWHILE);
                    break;
                case "for":
                    simb.setToken(Constantes.FOR);
                    break;
                case "do":
                    simb.setToken(Constantes.DO);
                    break;
                case "then":
                    simb.setToken(Constantes.THEN);
                    break;
                case "endfor":
                    simb.setToken(Constantes.ENDFOR);
                    break;
                case "end":
                    simb.setToken(Constantes.END);
                    break;
                case "printstr":
                    simb.setToken(Constantes.PRINTSTR);
                    break;
            }                                        

            this.simboloAtual = simb;
        } else
            this.usarUltimoSimbolo = false;

        return this.simboloAtual;
    }

    public void ungetSimbol() {
        this.usarUltimoSimbolo = true;
    }

    private boolean isNumeric(char c) {
        String aux = "" + c;
        return ("0123456789".contains(aux));
    }

    private boolean isAlpha(char c) {
        String aux = "" + c;
        return ("abcdefghijklmnopqrstuvwxyz".contains(aux.toLowerCase()));
    }

    public int getLineNumber() {
        return this.linhaAvaliada;
    }
}

