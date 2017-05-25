package br.cefetmg.inf.tiny.excecoes;

// classe para tratamento de erros semanticos
public class ErroSemantico extends RuntimeException {

    public ErroSemantico(String msg) {
        super("\n..........................Erro semantico............................\n=>"
                + msg);
    }
}
