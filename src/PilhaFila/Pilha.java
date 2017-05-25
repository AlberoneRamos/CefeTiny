/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PilhaFila;

/**
 *
 * @author Henrique
 */
public class Pilha<Simbolo> extends Lista{
    public void push(Simbolo Conteudo){
        addFim(Conteudo,"");
    }
    
    public Simbolo pop(){
        return  (Simbolo) removeFim();
    }
    
    public Nodo peek(){
        return Ultimo;
    }
    
    public boolean isEmpty(){
        return listaVazia();
    }
}
