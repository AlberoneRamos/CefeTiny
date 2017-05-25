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
import br.cefetmg.inf.tiny.comandos.Comando;
import br.cefetmg.inf.tiny.interpretador.Simbolo;

/**
 *
 * @author Henrique
 */
public class ComandoIf extends Comando {

    private String [] condicao;
    private Lista<Simbolo> comandosIf;
    private Lista<Simbolo> comandosElse;
            
            
    public ComandoIf(Lista<Simbolo> condicao, Lista<Simbolo> comandosIf, Lista<Simbolo> comandosElse) {
        this.condicao = Infixo2Posfixo.ParaPosfixo(condicao);
        this.comandosIf=comandosIf.clone();
        if(comandosElse!=null)
            this.comandosElse=comandosElse.clone();
    }

    @Override
    public void executa() {
        Object resultado = ExpReader.LeExpressao(condicao);
        CommandHandler executaComandosIf = new CommandHandler(comandosIf.clone());
        if(ExpReader.LeExpressao(condicao).equals(true)){
            executaComandosIf.run();
        }
        else{
            if(this.comandosElse!=null){
                CommandHandler executaComandosElse = new CommandHandler(comandosElse.clone());
                executaComandosElse.run();
            }
            else{}
        }
        executaComandosIf = null;
        System.gc();
    }
    
}
