package br.cefetmg.inf.tiny.comandos;

public class ComandoPrintStr extends Comando {

    String msg = null;

    public ComandoPrintStr(String msg) {
        this.msg = msg;
    }

    @Override
    public void executa() {
        System.out.print(this.msg);
    }
}

