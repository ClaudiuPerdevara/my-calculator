package Calculator;

import java.util.ArrayList;
import java.util.*;

public class Calc {
    public static void main(String[] args) {
        //creez un obiect Scanner sa citesc date de la tastatura
        Scanner scanner = new Scanner(System.in);

        String expresie = scanner.nextLine();
        try {
            List<String> postfix = infixToPostfix(expresie);
            double rezultat = evalPostfix(postfix);
            System.out.println("Rezultatul este: " + rezultat);
        } catch (Exception e) {
            System.out.println("Eroare la procesarea expresiei: " + e.getMessage());
        }

        scanner.close();
    }

        private static int precedenta(String op)
        {
            switch(op){
                case "+": return 1;
                case "-": return 1;
                case "*": return 2;
                case "/": return 2;
                case "%": return 2;
                case "^": return 3;
                default: return -1;
            }
        }

        public static List<String> infixToPostfix(String expresie)
        {
            List<String> rezultat = new ArrayList<>();
            Stack<String> stivaOp=new Stack<>();

            String[] tokens=expresie.split(" ");

            for(String token:tokens)
            {
                if(token.isEmpty()) continue;

                //daca e numar il adaug direct
                if(token.matches("-?\\d+(\\.\\d+)?"))
                {
                    rezultat.add(token);
                }
                else if(token.equals("(")) { //daca e paranteza (
                    stivaOp.push(token);
                }
                else if(token.equals(")")) { //daca e paranteza )
                    while (!stivaOp.isEmpty() && !stivaOp.peek().equals("(")) {
                        rezultat.add(stivaOp.pop());
                    }
                    stivaOp.pop();
                }
                else {
                    while(!stivaOp.isEmpty() && precedenta(token)<=precedenta(stivaOp.peek()))
                    {
                        rezultat.add(stivaOp.pop());
                    }
                    stivaOp.push(token);
                }
            }

            while(!stivaOp.isEmpty())
            {
                rezultat.add(stivaOp.pop());
            }

            return rezultat;
        }

        public static double evalPostfix(List<String> postfix)
        {
            Stack<Double> stiva=new Stack<>();

            for(String token:postfix)
            {
                if(token.matches("-?\\d+(\\.\\d+)?"))
                {
                    stiva.push(Double.parseDouble(token));
                }
                else
                {
                    double num2=stiva.pop();
                    double num1=stiva.pop();

                    switch(token)
                    {
                        case "+": stiva.push(num1+num2); break;
                        case "-": stiva.push(num1-num2); break;
                        case "*": stiva.push(num1*num2); break;
                        case "/":
                            if(num2==0) throw new ArithmeticException("Division by zero");
                            stiva.push(num1/num2); break;
                        case "%":
                            if(num2==0) throw new ArithmeticException("Division by zero");
                            stiva.push(num1%num2); break;
                        case "^":
                            stiva.push(Math.pow(num1,num2)); break;
                    }
                }

            }

            return stiva.pop();
        }
}
