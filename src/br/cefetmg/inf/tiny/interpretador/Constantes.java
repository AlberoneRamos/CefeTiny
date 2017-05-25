package br.cefetmg.inf.tiny.interpretador;

import br.cefetmg.inf.tiny.excecoes.ErroSintatico;

public class Constantes {

    public static final int T_BOOLEAN = 0;		// true ou false
    public static final int T_INTEGER = 1;		// números do tipo inteiro
    public static final int T_DOUBLE = 2;		// números do tipo double
    public static final int T_STRING = 3;		// tipo string
    public static final int T_IDENTIFIER = 4;           // variaveis
    public static final int T_CONSTANT = 5;		// tipos constantes
    public static final int RELOP = 6;			// = || < || <= || > || >= || <>
    public static final int ADDOP = 7;			// + || - || or
    public static final int MULOP = 8;			// * || / || mod || div || and
    public static final int UNOP = 9;			// sqrt || not
    public static final int ATRIBOP = 10;		// :=
    public static final int ABREPAR = 11;		// (
    public static final int FECHAPAR = 12;		// )    
    public static final int END = 13;			// comando end
    public static final int PRINT = 14;			// comando print
    public static final int PRINTLN = 15;		// comando println
    public static final int READINT = 16;		// comando readint
    public static final int IF = 17;			// comando if
    public static final int ELSE = 18;			// comando else
    public static final int ENDIF = 19;			// comando endif
    public static final int WHILE = 20;			// comando while
    public static final int ENDWHILE = 21;		// comando endwhile
    public static final int FOR = 22;			// comando for
    public static final int ENDFOR = 23;		// comando endfor
    public static final int PRINTSTR = 24;              // comando printstr
    public static final int BEGIN = 25;                 // comando begin
    public static final int POWOP = 26;                 // ^
    public static final int READFLOAT = 27;             // comando readfloat
    public static final int ABREASPAS = 28;            
    public static final int DO = 29;
    public static final int THEN = 31;
    public static final int PONTOEVIRGULA=30;

    public static String toString(int item) {
        switch (item) {
            case T_BOOLEAN:
                return "Boolean";
            case T_INTEGER:
                return "Integer";
            case T_DOUBLE:
                return "Double";
            case T_STRING:
                return "String";
            case T_IDENTIFIER:
                return "Identificador";
            case T_CONSTANT:
                return "Constante";
            case RELOP:
                return "Operador Relacional";
            case ADDOP:
                return "Operador +|-|or";
            case MULOP:
                return "Operador *|/|and";
            case UNOP:
                return "Operador sqrt|not";
            case POWOP:
                return "Operador ^";
            case ATRIBOP:
                return "Operador de atribuido";
            case ABREPAR:
                return "Abre parenteses";
            case FECHAPAR:
                return "Fecha Parenteses";
            case END:
                return "end";
            case PRINT:
                return "print";
            case PRINTLN:
                return "println";
            case READINT:
                return "readint";
            case READFLOAT:
                return "readfloat";                
            case IF:
                return "if";
            case ELSE:
                return "else";
            case ENDIF:
                return "endif";
            case WHILE:
                return "while";
            case ENDWHILE:
                return "endwhile";
            case FOR:
                return "for";
            case ENDFOR:
                return "endfor";
            case PRINTSTR:
                return "printstr";
            case BEGIN:
                return "begin";
        }

        throw new ErroSintatico("Elemento '" + item + "' nao eh valido.");
    }
}
