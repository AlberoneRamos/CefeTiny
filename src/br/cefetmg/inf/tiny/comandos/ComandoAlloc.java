/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.inf.tiny.comandos;

import NPR.ExpReader;
import NPR.Infixo2Posfixo;
import PilhaFila.Lista;
import PilhaFila.Variavel;
import br.cefetmg.inf.tiny.CefeTiny;
import br.cefetmg.inf.tiny.interpretador.Simbolo;

/**
 *
 * @author aluno
 */
public class ComandoAlloc extends Comando {
    Variavel Alloc;
    String Lexema;
    String [] Posfix;
    
    public ComandoAlloc(String Lexema,Lista<Simbolo> Atual) {
        Posfix=Infixo2Posfixo.ParaPosfixo(Atual);
        Alloc=new Variavel();
        Alloc.setNome(Lexema);
        this.Lexema=Lexema;
    }
    
    @Override
    public void executa(){
        Alloc.setValor(ExpReader.LeExpressao(Posfix).toString());
        if(CefeTiny.Memoria==null)
          CefeTiny.Memoria.AddIni(Alloc, Lexema);
        else if(CefeTiny.Memoria.procura(Lexema)!=null)
          CefeTiny.Memoria.Modificar(Lexema, Alloc);
        else
          CefeTiny.Memoria.AddIni(Alloc, Lexema);
    }
    
}
