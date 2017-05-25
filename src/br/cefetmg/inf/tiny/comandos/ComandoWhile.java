/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.inf.tiny.comandos;

import NPR.ExpReader;
import NPR.Infixo2Posfixo;
import PilhaFila.Lista;
import br.cefetmg.inf.tiny.CommandHandler;
import br.cefetmg.inf.tiny.interpretador.Simbolo;

/**
 *
 * @author Henrique
 */
public class ComandoWhile extends Comando{

    private String [] Condicao;
    private Lista<Simbolo> Comandos;

    public ComandoWhile(Lista<Simbolo> Condicao, Lista<Simbolo> Comandos) {
        this.Condicao = Infixo2Posfixo.ParaPosfixo(Condicao);
        this.Comandos = Comandos.clone();
        
    }
    
    
    @Override
    public void executa() {
        Object resultado = ExpReader.LeExpressao(Condicao);
        CommandHandler executaComandos = new CommandHandler(Comandos.clone());
        while(ExpReader.LeExpressao(Condicao).equals(true)){
            executaComandos.run();
        }
        executaComandos = null;
        System.gc();
    }

    
    
}
