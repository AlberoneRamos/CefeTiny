/*
 * Tipoo change this template, choose Tipoools | Tipoemplates
 * and open the template in the editor.
 */

/**
 *
 * @author Henrique
 */

package PilhaFila;

import br.cefetmg.inf.tiny.interpretador.Simbolo;
import java.util.ArrayList;
import java.util.Arrays;


public class Lista<Tipo> implements Cloneable{
    protected Nodo Primeiro;
    protected Nodo Ultimo;
    private int Tamanho;

      public Lista(){
         Primeiro = Ultimo = null;
      }

     public void addIni(Tipo conteudo,String Nome){
         if (listaVazia()) {
             Primeiro = Ultimo = new Nodo(conteudo,null,Nome);
         }
        else {
             Primeiro = new Nodo(conteudo,Primeiro,Nome);
         }
         Tamanho++;
      }
     
     public Nodo getPrimeiro(){
    	 return Primeiro;
     }

      public void addFim(Tipo conteudo,String Nome){
         if (listaVazia()) {
              Primeiro = Ultimo = new Nodo(conteudo,null,Nome);
          }
         else {
              Ultimo.setProx(new Nodo(conteudo,null,Nome));
              Ultimo=Ultimo.getProx();
          }
         Tamanho++;
      }

      public Tipo removeInicio(){
         if (listaVazia()) {
              return null;
          }
         Tipo ret = (Tipo) Primeiro.getConteudo();
         if ( Primeiro == Ultimo ) {
              Primeiro = Ultimo = null;
              Tamanho--;
          }
         else {
              Primeiro = Primeiro.getProx();
              Tamanho--;
          }
         return ret;
      }
      
    @Override
      public Lista<Tipo> clone(){
        Lista<Tipo> retorno=new Lista<>();
        Nodo atual = Primeiro;
        for(int i=0;atual != null;i++){
           retorno.addFim((Tipo) atual.getConteudo(), "");
           atual = atual.getProx();
        }
        return retorno;
      }

      public Tipo removeFim(){
         if ( listaVazia() ) {
              return null;
          }
        Tipo ret = (Tipo) Ultimo.getConteudo();
        if ( Primeiro == Ultimo ) {
              Primeiro = Ultimo = null;
              Tamanho--;
          }
        else{
           Nodo atual = Primeiro;
           while ( atual.getProx() != Ultimo ) {
                atual = atual.getProx();
            }
           Ultimo = atual;
           atual.setProx(null);
           Tamanho--;
        }
        return ret;
     }

    public Nodo getUltimo() {
        return Ultimo;
    }

    public void setUltimo(Nodo Ultimo) {
        this.Ultimo = Ultimo;
    }

    public int getTamanho() {
        return Tamanho;
    }

    public void setTamanho(int Tamanho) {
        this.Tamanho = Tamanho;
    }

     public boolean listaVazia(){
        return Primeiro == null;
     }

     public Simbolo getSimboloAt(int pos){
         Nodo atual=Primeiro;
         Simbolo atualz=new Simbolo();
         for(int i=0;atual != null && i<pos;i++){
           atualz= (Simbolo) atual.getConteudo();
           atual = atual.getProx();
        }
        return atualz;
     }
     
    @Override
     public String  toString(){
        if ( listaVazia() ){
           return null;
        } // end if
        String retorno="";
        Nodo atual = Primeiro;
        for(int i=0;atual != null;i++){
           Simbolo atualz=new Simbolo();
           atualz= (Simbolo) atual.getConteudo();
           retorno+=atualz.getLexema()+" ";
           atual = atual.getProx();
        }
        return retorno;
     } 
     
     public String [] RetornaVetorString(){
        if ( listaVazia() ){
           return null;
        } // end if
        ArrayList<String> retorno = new ArrayList<String>();
        Nodo atual = Primeiro;
        for(int i=0;atual != null;i++){
           Simbolo atualz=new Simbolo();
           atualz= (Simbolo) atual.getConteudo();
           retorno.add(atualz.getLexema());
           atual = atual.getProx();
        }
        String[] stringArray = Arrays.copyOf(retorno.toArray(), retorno.toArray().length, String[].class);
        return stringArray;
     } 
     
    
}
