import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RomanCalculator {

    // Карта для преобразования римских цифр в арабские
    private static final Map<Character, Integer> romanToArabic = new HashMap<>();
    static {
        romanToArabic.put('I', 1);
        romanToArabic.put('V', 5);
        romanToArabic.put('X', 10);
        romanToArabic.put('L', 50);
        romanToArabic.put('C', 100);
        romanToArabic.put('D', 500);
        romanToArabic.put('M', 1000);
    }

    // Карта для преобразования арабских чисел в римские
    private static final String[] romanNumerals = {
        "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII",
        "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX"
    };

    // Преобразование римского числа в арабское
    public static int romanToArabic(String roman) {
        int result = 0;
        roman = roman.toUpperCase();
        for (int i = 0; i < roman.length(); i++) {
            int current = romanToArabic.get(roman.charAt(i));
            if (i + 1 < roman.length() && current < romanToArabic.get(roman.charAt(i + 1))) {
                result -= current;
            } else {
                result += current;
            }
        }
        return result;
    }

    // Преобразование арабского числа в римское
    public static String arabicToRoman(int number) {
        if (number < 1 || number > 20) {
            throw new IllegalArgumentException("Only numbers between 1 and 20 are supported.");
        }
        return romanNumerals[number];
    }

    // Основная функция калькулятора
    public static String calculate(String input) {
        // Разделение на операнды и операторы
        String[] parts = input.split(" ");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid format. Expression must contain two operands and one operator.");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        boolean isOperand1Roman = isRoman(operand1);
        boolean isOperand2Roman = isRoman(operand2);

        // Проверка на смешение систем счисления
        if (isOperand1Roman != isOperand2Roman) {
            throw new IllegalArgumentException("Mixing Roman and Arabic numerals is not allowed.");
        }

        int result = 0;

        if (isOperand1Roman) {
            int num1 = romanToArabic(operand1);
            int num2 = romanToArabic(operand2);

            // Проверка на отрицательные числа для римской системы
            if (num1 - num2 < 0 && operator.equals("-")) {
                return "";  // Возвращаем пустую строку, если результат отрицателен
            }

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) throw new ArithmeticException("Division by zero.");
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator.");
            }

            return arabicToRoman(result);

        } else {
            // Если оба операнда арабские числа
            int num1 = Integer.parseInt(operand1);
            int num2 = Integer.parseInt(operand2);

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) throw new ArithmeticException("Division by zero.");
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator.");
            }

            return String.valueOf(result);
        }
    }

    // Проверка, является ли строка римским числом
    private static boolean isRoman(String s) {
        return s.matches("^[IVXLCDM]+$");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите выражение (например, 'I + II', '1 + 2', 'VII / III'):");

        // Чтение строки ввода от пользователя
        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }
}
