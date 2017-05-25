package br.cefetmg.inf.tiny.comandos;

//classe para tratamento do comando println
public class ComandoPrintln extends Comando {

    //contrutor padrao
    public ComandoPrintln() {

    }

    //metodo que executa o comando println
    @Override
    public void executa() {
        //imprime uma linha em branco
        System.out.println();
    }
}

