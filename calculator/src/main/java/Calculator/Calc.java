package Calculator;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.lang.Math;

public class Calc {
    public static void main(String[] args) {
        //creez un obiect Scanner sa citesc date de la tastatura
        Scanner scanner = new Scanner(System.in);

        String expression = scanner.nextLine();
        try {
            List<String> postfix = infixToPostfix(expression);
            double result = evaluatePostfix(postfix);
            System.out.println("The result is: " + result);
        }
        catch(Exception e) {
            System.out.println("Error processing the expression: " + e.getMessage());
        }
        scanner.close();
    }

        private static int precedence(String op)
        {
            switch(op){
                case "+": return 1;
                case "-": return 1;
                case "*": return 2;
                case "/": return 2;
                case "%": return 2;
                case "^": return 3;
                case "sin": return 4;
                case "cos": return 4;
                default: return -1;
            }
        }

        public static List<String> infixToPostfix(String expression)
        {
            List<String> result = new ArrayList<>();
            Stack<String> operatorStack = new Stack<>();

            String[] tokens = expression.split(" ");

            for(String token:tokens)
            {
                if(token.isEmpty()) continue;

                //daca e numar il adaug direct
                if(token.matches("-?\\d+(\\.\\d+)?"))
                {
                    result.add(token);
                }
                else if(token.equals("(")) { //daca e paranteza (
                    operatorStack.push(token);
                }
                else if(token.equals(")")) { //daca e paranteza )
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                        result.add(operatorStack.pop());
                    }
                    operatorStack.pop();
                }
                else
                {
                    while(!operatorStack.isEmpty() && !operatorStack.peek().equals("("))
                    {
                        int precToken = precedence(token);
                        int precTop = precedence(operatorStack.peek());

                        boolean leftAssociativity = !token.equals("^") && !token.equals("sin") && !token.equals("cos");

                        if((leftAssociativity && precToken<=precTop) || (!leftAssociativity && precToken < precTop))
                            result.add(operatorStack.pop());
                        else
                            break;
                    }
                    operatorStack.push(token);
                }
            }

            while(!operatorStack.isEmpty())
            {
                result.add(operatorStack.pop());
            }

            return result;
        }

        public static double evaluatePostfix(List<String> postfix)
        {
            Stack<Double> stack = new Stack<>();

            for(String token:postfix)
            {
                if(token.matches("-?\\d+(\\.\\d+)?"))
                {
                    stack.push(Double.parseDouble(token));
                }
                else
                {
                    if(token.equals("sin"))
                    {
                        double angle = stack.pop();
                        stack.push(calculateSin(angle));
                    }
                    else if(token.equals("cos"))
                    {
                        double angle = stack.pop();
                        double cosValue = Math.cos(Math.toRadians(angle));
                        stack.push(cosValue + 2 * Math.pow(cosValue, 2));
                    }
                    else
                    {
                        double num2 = stack.pop();
                        double num1 = stack.pop();

                        switch(token)
                        {
                            case "+": stack.push(num1 + num2); break;
                            case "-": stack.push(num1 - num2); break;
                            case "*": stack.push(num1 * num2); break;
                            case "/":
                                if(num2 == 0) throw new ArithmeticException("Division by zero");
                                stack.push(num1 / num2); break;
                            case "%":
                                if(num2 == 0) throw new ArithmeticException("Division by zero");
                                stack.push(num1 % num2); break;
                            case "^":
                                stack.push(Math.pow(num1,num2)); break;
                        }
                    }
                }

            }

            return stack.pop();
        }

        public static double calculateSin(double x)
        {
            x = Math.toRadians(x);
            x = x % (2 * Math.PI);
            if(x > Math.PI)
                x -= 2 * Math.PI;
            else if(x < -Math.PI)
                x += 2 * Math.PI;

            double element = x;
            double sum = x;
            int n = 1;

            while(Math.abs(element) > 1e-9)
            {
                element = element * (-1 * x * x) / ((2.0 * n) * (2.0 * n + 1.0));

                //        -x^2
                //     ------------
                //      2n * (2n+1)

                sum += element;
                n++;
            }
            return sum;
        }
}
