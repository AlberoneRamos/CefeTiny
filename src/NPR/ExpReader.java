/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NPR;

import PilhaFila.Pilha;
import PilhaFila.Variavel;
import br.cefetmg.inf.tiny.CefeTiny;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 *
 * @author Henrique
 */
public class ExpReader {
    private static Pilha<Object> Notacao=new Pilha<>();
    private static Pilha<Boolean> NotacaoBooleana=new Pilha<>();
    public static boolean éOperando(String c){
        return c.equals("+")|| c.equals("*") || c.equals("/") || c.equals("-") || c.equals("^")|| c.equals("=") 
                || c.equals(">=") || c.equals("<=") || c.equals(">") || c.equals("<") || c.equals("!=") || c.equals("mod");
               
    }
    
    public static boolean éOperandoLogico(String c){
       return c.toLowerCase().equals("and") || c.toLowerCase().equals("or");
    }
    
    public static boolean éOperandoUnario(String c){
        return c.toLowerCase().equals("sqrt");
    }
    
    public static boolean éOperandoUnarioLogico(String c){
         return c.toLowerCase().equals("not");
    }
    
   /* public static Object LeExpressao(String [] Eval){
        Object retorno;
        String [] EvalDiv=Eval;
        for(String EvalDiv1:EvalDiv){
            if(éOperandoLogico(EvalDiv1) || éOperandoUnarioLogico(EvalDiv1)){
                retorno=LeExpressaoBooleana(EvalDiv);
                return retorno;
            }
            
        }
        retorno=LeExpressaoNormal(EvalDiv);
        return retorno;
    }*/
    
    public static Object LeExpressao(String [] Eval){
        String [] EvalDiv;
        EvalDiv = Eval;
        for (String EvalDiv1 : EvalDiv) {
            if (!éOperando(EvalDiv1) && !éOperandoUnario(EvalDiv1) && !éOperandoUnarioLogico(EvalDiv1) && !éOperandoLogico(EvalDiv1) ) {
                if (Pattern.matches("[0-9]+", EvalDiv1) == true || EvalDiv1.equals("true")|| EvalDiv1.equals("false")) {
                    Notacao.push(EvalDiv1);
                } else if (Pattern.matches("[a-zA-Z]+", EvalDiv1) == true) {
                    Variavel Aloc = (Variavel) CefeTiny.Memoria.procura(EvalDiv1).getConteudo();
                    Notacao.push(Aloc.getValor().toString());
                }
            } else if(éOperando(EvalDiv1)){
                Double a=Double.parseDouble(Notacao.pop().toString());
                Double b=Double.parseDouble(Notacao.pop().toString());
                Double Emp;
                Boolean Bool;
                switch (EvalDiv1) {
                    case "+":
                        Emp = b+a;
                        Notacao.push(Emp);
                        break;
                    case "-":
                        Emp=(b-a);
                        Notacao.push(Emp);
                        break;
                    case "*":
                        Emp=(b*a);
                        Notacao.push(Emp);
                        break;
                    case "/":
                        Emp=(b/a);
                        Notacao.push(Emp);
                        break;
                    case "mod":
                        Notacao.push(b.intValue()%a.intValue());
                        break;
                    case "^":
                        Emp =Math.pow(b,a);
                        Notacao.push(Emp);
                        break;
                    case "=":
                        Bool=(Objects.equals(b, a));
                        Notacao.push(Bool);
                        break;
                    case "!=":
                        Bool=(!Objects.equals(b, a));
                        Notacao.push(Bool);
                        break;
                    case ">=":
                        Bool =(b>=a);
                        Notacao.push(Bool);
                        break;
                    case "<=":
                        Bool=(b<=a);
                        Notacao.push(Bool);
                        break;
                    case ">":
                        Bool=(b>a);
                        Notacao.push(Bool);
                        break;
                    case "<":
                        Bool=(b<a);
                        Notacao.push(Bool);
                        break;
                        
                }
                
            }
            if(éOperandoUnarioLogico(EvalDiv1)){
                            Boolean a=Boolean.valueOf(Notacao.pop().toString());
                            switch (EvalDiv1) {
                                case "not":
                                    if(Objects.equals(a, Boolean.FALSE)){
                                        Notacao.push(Boolean.TRUE);
                                    }
                                    else{
                                        Notacao.push(Boolean.FALSE);
                                    }
                                    break;
                            }
                        }
                        else if(éOperandoLogico(EvalDiv1)){
                            Boolean b=Boolean.valueOf(Notacao.pop().toString());
                            Boolean a=Boolean.valueOf(Notacao.pop().toString());
                            switch(EvalDiv1){
                                case "and":
                                    Notacao.push(a && b);
                                    break;
                                case "or":
                                    Notacao.push(a || b);
                                    break;
                            }
                        }
            else if(éOperandoUnario(EvalDiv1)){
                Double a=Double.parseDouble(Notacao.pop().toString());
                Double Emp;
                switch (EvalDiv1) {
                    case "sqrt":
                        Emp=(Double)Math.sqrt(a);
                        Notacao.push(Emp);
                        break;
                }
            }
        }
        Object ret=Notacao.pop();
        return ret;
    }
    
}