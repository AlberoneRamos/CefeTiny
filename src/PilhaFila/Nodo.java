 /**
 *
 * @author Henrique
 */
package PilhaFila;

public class Nodo <Tipo>{
      private Tipo Conteudo;
      private Nodo Prox;
      private String Nome;

      
      public Nodo(Tipo Conteudo){
         this(Conteudo,null,"Nodo");
      }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }
 
   
      public Nodo(Tipo Conteudo, Nodo nodo,String Nome){
         this.Conteudo = Conteudo;
         Prox = nodo;
         this.Nome=Nome;
      }
      
      public Tipo getConteudo(){
         return (Tipo) Conteudo;
      }

    public void setConteudo(Tipo Conteudo) {
        this.Conteudo = Conteudo;
    }

    public void setProx(Nodo Prox) {
        this.Prox = Prox;
    }
 
     public Nodo getProx(){
         return Prox;
      }
     
}
