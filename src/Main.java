import java.util.Scanner;

public class Main {
    public static void main(String[] Args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String expression = in.nextLine();
        expression = expression.trim();
        if (expression.isEmpty()) {
            throwException("Введена пустая строка.");
        }
        String result = calc(expression);
        System.out.println(result);
    }

    public static String calc(String input){

        String [] operators = {"+", "-", "*", "/"};
        boolean operatorExists = false;
        int answer = 0;

        String[] parts = input.split(" ");
        if (parts.length == 3) {
            for (String operator : operators) {
                if (parts[1].equals(operator)) {
                    operatorExists = true;
                    break;
                }
            }
        }
        if(!operatorExists){
            throwException("Выражение не является математической операцией в заданном формате или пропущен пробел.");
        }

        String a = parts[0];
        String b = parts[2];

        boolean aIsArabic = isArabic(a);
        boolean aIsRoman = isRoman(a);
        boolean bIsArabic = isArabic(b);
        boolean bIsRoman = isRoman(b);

        if (!((aIsArabic || aIsRoman) && (bIsArabic || bIsRoman))) {
            throwException("Операнд(ы) не является числом или не является целым числом или выходит за рамки заданного диапазона.");
        }
        if (aIsRoman^bIsRoman){
            throwException("Одновременно используются разные системы счисления.");
        }
        if (aIsRoman) {
            int operand1 = romanToArabicConversion(a);
            int operand2 = romanToArabicConversion(b);
            String operator = parts[1];
            answer = calculate(operand1, operand2, operator);
            if (answer<=0) {
                throwException("Римские числа: ответ не может быть меньше или равен нулю.");
            }
            return arabicToRomanConversion(answer);
        }
        else {
            int operand1 = Integer.parseInt(a);
            int operand2 = Integer.parseInt(b);
            String operator = parts[1];
            answer = calculate(operand1, operand2, operator);
            return Integer.toString(answer);
        }
    }

    static boolean isArabic(String arg) {
        String [] arabicNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        for (String arabicNumber : arabicNumbers) {
            if (arg.equals(arabicNumber)) {
                return true;
            }
        }
        return false;
    }
    static boolean isRoman(String arg) {
        String [] romanNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (String romanNumber : romanNumbers) {
            if (arg.equals(romanNumber)) {
                return true;
            }
        }
        return false;
    }

    static int calculate(int operand1, int operand2, String operation) {
        int result;
        switch(operation ) {
            case "+" :
                result = operand1+operand2;
                break;
            case "-" :
                result = operand1-operand2;
                break;
            case "*" :
                result = operand1*operand2;
                break;
            case "/" :
                result = operand1/operand2;
                break;
            default: result = 0;
        }
        return result;
    }

    static int romanToArabicConversion(String romanString) {
        return Roman.valueOf(romanString).getConvertToArabic();
    }

    static String arabicToRomanConversion(int x) {
        String answerRoman = "";
        int tens = x / 10;
        int units = x % 10;
        if (tens != 0) {
            answerRoman = arabicToRoman(tens * 10);
        }
        if (units != 0){
        answerRoman = answerRoman + arabicToRoman(units);}
        return answerRoman;
    }

    static String arabicToRoman(int x) {
        return Arabic.valueOf("A"+x).getConvertToRoman();
    }

    static void throwException(String message){
        try {
            throw new Exception();
        } catch (Exception e) {
            System.out.println(message);
            System.exit(0);
        }
    }
}

enum Roman {
    I(1), II(2), III(3), IV(4), V(5),
    VI(6), VII(7), VIII(8), IX(9), X(10);

    int convertToArabic;

    Roman(int convertToArabic) {
        this.convertToArabic = convertToArabic;
    }

    public int getConvertToArabic(){
        return convertToArabic;
    }

}

enum Arabic {
    A1("I"), A2("II"),A3("III"), A4("IV"), A5("V"),
    A6("VI"), A7("VII"), A8("VIII"), A9("IX"),
    A10 ("X"), A20("XX"), A30("XXX"), A40("XL"), A50("L"),
    A60("LX"), A70("LXX"), A80("LXXX"), A90("XC"), A100("C");

    String convertToRoman;

    Arabic(String convertToRoman) {
        this.convertToRoman = convertToRoman;
    }

    public String getConvertToRoman(){
        return convertToRoman;
    }

}
