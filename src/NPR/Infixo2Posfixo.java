/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NPR;

import PilhaFila.Lista;
import PilhaFila.Pilha;
import br.cefetmg.inf.tiny.interpretador.Simbolo;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Henrique
 */
public class Infixo2Posfixo {
    
   private static boolean éOperador(String c){
       return éOperadorMatematico(c) || éOperadorBooleano(c) || éOperadorComparação(c);
        
    }
    
   private static boolean éOperadorMatematico(String c){
       return "+".equals(c) || "-".equals(c) || "*".equals(c) || "/".equals(c) || "^".equals(c) 
               || "(".equals(c) || ")".equals(c) || c.equals("sqrt")|| c.equals("mod");
   }
   
   private static boolean éOperadorBooleano(String c){
       return  c.equals("and") || c.equals("or") || c.equals("not");
   }
   
   private static boolean éOperadorComparação(String c){
        return c.equals("=") || c.equals(">=") || c.equals("<=") 
               || c.equals(">") || c.equals("<") || c.equals("!=");
    }

    private static boolean PrecedenciaÉMenor(String op1, String op2){
        switch (op1){
            case "!=":
            case ">=":
            case "<=":
            case "<":
            case ">":
            case "=":
                return éOperadorMatematico(op2) ;
            case "or":
                return !(op2.equals("or"));
            case "+":
            case "-":
                return !(op2.equals("+") || op2.equals("-") || éOperadorBooleano(op2) || éOperadorComparação(op2));
            case "and":
                return !(op2.equals("or") || op2.equals("and"));
            case "mod":
            case "*":
            case "/":
                return op2.equals("^")  || op2.equals("(") ;
            case "not":
                return éOperadorComparação(op2) || éOperadorMatematico(op2);
            case "sqrt":
            case "^":
                return op2.equals("(") ;

            case "(":
                return true;

            default:
                return false;
        }
    }

    public static String[] ParaPosfixo(Lista<Simbolo> Infix){
        String [] Infixo=Infix.RetornaVetorString();
    Pilha<String> pilha = new Pilha<>();
        int contElement;
        ArrayList<String> posfixo = new ArrayList<String>();
        String c;
        for (int i = 0; i < Infixo.length; i++){
            c=Infixo[i];
            if (!éOperador(c)){
                posfixo.add(Infixo[i]);
                if (i+1 >= Infixo.length || (éOperador(c))){}
            }
            else{
                if (")".equals(c)){
                    while (!pilha.isEmpty() && !"(".equals(pilha.peek().getConteudo().toString())){
                        posfixo.add((String) pilha.pop());
                    }
                    if (!pilha.isEmpty()){
                        pilha.pop();
                    }
                }

                else{
                    if (!pilha.isEmpty() && !PrecedenciaÉMenor(c, pilha.peek().getConteudo().toString())){
                        pilha.push(c);
                    }
                    else{
                        while (!pilha.isEmpty() && PrecedenciaÉMenor(c, pilha.peek().getConteudo().toString())){
                            String pop = pilha.pop().toString();
                            if (!"(".equals(c)){
                                posfixo.add(pop);
                            } else {
                              c = pop;
                            }
                        }
                        if("(".equals(c)){}
                        else
                            pilha.push(c);
                    }

                }
            }
        }
        while (!pilha.isEmpty()) {
          posfixo.add((String) pilha.pop());
        }
         String[] stringArray = Arrays.copyOf(posfixo.toArray(), posfixo.toArray().length, String[].class);
         return stringArray;
    }
}
