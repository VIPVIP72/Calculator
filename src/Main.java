import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Input:");
        String input = in.nextLine();


        System.out.println("Output:");
        try {
              String result = calc(input);
              System.out.println(result);

             } catch (IOException | IllegalArgumentException e) {
                System.out.println("throws Exception");
            }
        in.close();
    }

    public static String calc(String input) throws IOException {
        StringBuilder number = new StringBuilder();
        char symbol, operator = ' ';
        int a = 0, b, result = 0, indicator = 0;
        boolean arabic = false, roman = false;
        String strRoman = "IVX";

        input = input.replaceAll(" ", "");
        if(input.matches("[\\d+/\\-*]+")) {
            arabic = true;
        } else if(input.matches("[IVX+/\\-*]+")) {
            roman = true;
        } else {
            throw new IOException();
        }
        if (arabic && roman) {
            throw new IOException();
        }
        for (int i = 0; i < input.length(); i++) {
            symbol = input.charAt(i); //запишем текущий символ в переменную

            if (!((Character.isDigit(symbol) && arabic) || (strRoman.indexOf(symbol)!=-1 && roman)) && indicator > 0) { //если символ является не цифрой, то число уже считано и его можно записать в переменную
                if (a == 0) {
                    if (arabic) {
                        a = Integer.parseInt(number.toString());
                    } else {
                        RomanNumeral romanNumeral = RomanNumeral.valueOf(number.toString());
                        a = romanNumeral.getTranslation();
                    }
                } else if (!number.isEmpty()) {
                    throw new IOException();
                }
                operator = symbol;
                indicator = 0;
                number = new StringBuilder();
            } else if (!((Character.isDigit(symbol) && arabic) || (strRoman.indexOf(symbol)!=-1 && roman)) && indicator == 0) {
                throw new IOException();
            }

            if ((Character.isDigit(symbol) && arabic) || (strRoman.indexOf(symbol)!=-1 && roman)) {  //если символ является числом то запишем его в строковую переменную number
                number.append(symbol);
                indicator++;
            }
        }
        if (indicator > 0) {//если фраза заканчивается на число
            if  (arabic) {
                b = Integer.parseInt(number.toString());
            } else {
                RomanNumeral romanNumeral = RomanNumeral.valueOf(number.toString());
                b = romanNumeral.getTranslation();
            }
            if (!((a>0 && a<=10) && (b>0 && b<=10))) {
                throw new IOException();
            }
            switch (operator) {

                case '+':
                    result = a + b;
                    break;

                // performs subtraction between numbers
                case '-':
                    result = a - b;
                    break;

                // performs multiplication between numbers
                case '*':
                    result = a * b;
                    break;

                // performs division between numbers
                case '/':
                    result = a / b;
                    break;

                default:
                    throw new IOException();
            }
        }
        if (result < 1 && roman){
            throw new IOException();
        }
        if  (arabic) {
            return Integer.toString(result);
        } else {
            return convert(result);
        }
    }
    private enum RomanNumeral {
        I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10);

        private final int translation;

        RomanNumeral(int translation){
            this.translation = translation;
        }

        public int getTranslation() {
            return translation;
        }
    }
    private static String convert(int number) {

        String romanOnes = romanDigit(number % 10, "I", "V", "X");
        number /= 10;

        String romanTens = romanDigit(number % 10, "X", "L", "C");
        number /= 10;

        String romanHundreds = romanDigit(number % 10, "C", "D", "M");

        String result = romanHundreds + romanTens + romanOnes;
        return result;
    }
    private static String romanDigit(int n, String one, String five, String ten){

        if(n >= 1)
        {
            if(n == 1)
            {
                return one;
            }
            else if (n == 2)
            {
                return one + one;
            }
            else if (n == 3)
            {
                return one + one + one;
            }
            else if (n == )
            {
                return one + five;
            }
            else if (n == 5)
            {
                return five;
            }
            else if (n == 6)
            {
                return five + one;
            }
            else if (n == 7)
            {
                return five + one + one;
            }
            else if (n == 8)
            {
                return five + one + one + one;
            }
            else if (n == 9)
            {
                return one + ten;
            }

        }
        return "";
    }
}