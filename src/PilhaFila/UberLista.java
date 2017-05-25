/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PilhaFila;

import br.cefetmg.inf.tiny.interpretador.Simbolo;


/**
 *
 * @author Henrique
 */
public class UberLista {
    Lista Simbolos = new Lista<>();
    
    public void AddFim(Variavel adicionar,String Nome){
        Simbolos.addFim(adicionar,Nome);
    }
    
    public Nodo procura(String Nome){
        Nodo atual = Simbolos.getPrimeiro();
        while(atual != null){
            if(atual.getNome().equals(Nome)){
            	return atual;
            }
            atual=atual.getProx();
        }
            return null;
    }
    
    public void Modificar(String Nome, Object Valor){
        Nodo atual=Simbolos.getPrimeiro();
        while( atual!=null ){
            if(atual.getNome().equals(Nome)){
            	atual.setConteudo(Valor);
            	return;
            }
            atual=atual.getProx();           
        }
            System.out.print("Variável não encontrada!!\n");
    }

	public void AddIni(Variavel alloc, String lexema) {
		Simbolos.addIni(alloc, lexema);
		
	}
}
