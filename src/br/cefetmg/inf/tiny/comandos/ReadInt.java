/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.inf.tiny.comandos;

import NPR.ExpReader;
import NPR.Infixo2Posfixo;
import PilhaFila.Variavel;
import br.cefetmg.inf.tiny.CefeTiny;
import java.util.Scanner;

/**
 *
 * @author Henrique
 */
public class ReadInt extends Comando{
    private String Nome;
    
    public ReadInt(String Nome) {
        this.Nome=Nome;
    }

    
    
    @Override
    public void executa() {
        Scanner input=new Scanner(System.in);
        Variavel Alloc=new Variavel();
        Alloc=new Variavel();
        Alloc.setNome(Nome);
        Alloc.setValor(input.nextDouble());
        if(CefeTiny.Memoria.procura(Nome)==null){
            CefeTiny.Memoria.AddFim(Alloc, Nome);
        }
        else
            CefeTiny.Memoria.Modificar(Nome,Alloc);
    }
    
}
