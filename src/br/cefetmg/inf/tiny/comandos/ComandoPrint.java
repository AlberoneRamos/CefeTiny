/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.inf.tiny.comandos;

import PilhaFila.Nodo;
import PilhaFila.Variavel;
import br.cefetmg.inf.tiny.CefeTiny;

/**
 *
 * @author aluno
 */
public class ComandoPrint extends Comando{
    String Lex;
    
    public ComandoPrint(String lexema) {
        Lex=lexema;
    }

    /**
     *
     */
    @Override
    public void executa() {
            char a=Lex.charAt(0);
                Nodo achado=CefeTiny.Memoria.procura(Lex);
                if(achado==null){
                    System.out.print(Lex);
                }
                else{
                    Variavel var=(Variavel)achado.getConteudo();
                    System.out.print(var.getValor());
                }
            
    }
    
}
