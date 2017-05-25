package br.cefetmg.inf.tiny.excecoes;

//classe para tratamento de erros sintaticos
public class ErroSintatico extends RuntimeException {

    public ErroSintatico(String msg) {
        super("\n....................Erro sintatico......................\n=>" + msg);
    }
}

