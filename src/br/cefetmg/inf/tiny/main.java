package br.cefetmg.inf.tiny;

import br.cefetmg.inf.tiny.excecoes.ErroSintatico;

import java.io.IOException;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

       try {
           Scanner input=new Scanner(System.in);
                String titulo=  "                                            \n"+
                                "                   69MM    MMMMMMMMMM 68b                      \n"+
                                "                  6M'          MM     Y89                      \n"+
                                "                  MM           MM                          \n"+
                                " 6MMMMb.  6MMMMb MMMMM6MMMMb   MM     `MM `MM 6MMb `MM(    )M' \n"+
                                "6M'   Mb 6M'  `Mb MM 6M'  `Mb  MM      MM  MMM9 `Mb `Mb    d'  \n"+
                                "MM    `' MM    MM MM MM    MM  MM      MM  MM'   MM  YM.  ,P   \n"+
                                "MM       MMMMMMMM MM MMMMMMMM  MM      MM  MM    MM   MM  M    \n"+
                                "MM       MM       MM MM        MM      MM  MM    MM   `Mbd'    \n"+
                                "YM.   d9 YM    d9 MM YM    d9  MM      MM  MM    MM    YMP     \n"+
                                " YMMMM9   YMMMM9  MM YMMMM9//  MM      MM  MM    MM     M      \n"+
                                "                                                       d'      \n"+
                                "                                                   (8),P       \n"+
                                "                                                    YMM          \n";                                                              
                                                               

                                                               
           System.out.print(titulo);                                                 
           System.out.print("Digite o nome do arquivo (Com extensão, precisa estar na pasta exemplos):");
            String fileName = "./src/exemplos/";
            fileName+=input.next();
            CefeTiny cefeTiny = new CefeTiny(fileName);
            // interpreta e executa arquivo de entrada
            cefeTiny.run();

        } catch (ErroSintatico | IOException e) {
            e.printStackTrace();
        }
    }
}