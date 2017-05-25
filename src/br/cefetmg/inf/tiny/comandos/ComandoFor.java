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
public class ComandoFor extends Comando {
    
    private String [] Condicao;
    private Lista<Simbolo> IncrementoExpressao;
    private String Incremento;
    private Lista<Simbolo> Comandos;
    private Lista<Simbolo> VariavelIniExpressao;
    private String VariavelIni;
    
    public ComandoFor(String VariavelIni, Lista<Simbolo> VariavelIniExpressao, Lista<Simbolo> condicao, String Incremento,Lista<Simbolo> IncrementoExpressao, Lista<Simbolo> comando) {
        this.VariavelIniExpressao=VariavelIniExpressao.clone();
        this.VariavelIni=VariavelIni;
        Condicao=Infixo2Posfixo.ParaPosfixo(condicao);
        this.Incremento=Incremento;
        this.IncrementoExpressao=IncrementoExpressao.clone();
        if(this.IncrementoExpressao.getSimboloAt(this.IncrementoExpressao.getTamanho()).getLexema().equals(")")){
            this.IncrementoExpressao.removeFim();
        }
        this.Comandos=comando.clone();
    }

    @Override
    public void executa() {
        ComandoAlloc CommIni=new ComandoAlloc(VariavelIni,VariavelIniExpressao.clone());
        
        ComandoAlloc CommIncremento=new ComandoAlloc(Incremento,IncrementoExpressao.clone());
        CommandHandler executaComandos = new CommandHandler(Comandos.clone());
        for(CommIni.executa();ExpReader.LeExpressao(Condicao.clone()).equals(true);CommIncremento.executa()){
            executaComandos.run();
        }
        executaComandos = null;
        System.gc();
    }

   
    
}
