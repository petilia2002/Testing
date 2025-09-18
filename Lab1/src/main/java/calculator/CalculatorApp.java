package calculator;

import java.util.Scanner;

public class CalculatorApp {
    private static final Calculator calculator = new Calculator();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Калькулятор с поддержкой 4 систем счисления ===");

        while (true) {
            try {
                printMenu();
                int choice = getIntInput("Выберите операцию: ");

                if (choice == 0) {
                    System.out.println("Выход из программы...");
                    break;
                }

                if (choice < 1 || choice > 5) {
                    System.out.println("Неверный выбор! Попробуйте снова.");
                    continue;
                }

                if(choice == 5) {
                    continue;
                }

                Calculator.NumberSystem system = selectNumberSystem();
                String a = getStringInput("Введите первое число: ");
                String b = getStringInput("Введите второе число: ");

                String result = performOperation(choice, a, b, system);
                System.out.println("Результат: " + result);

            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("1. Сложение");
        System.out.println("2. Вычитание");
        System.out.println("3. Умножение");
        System.out.println("4. Деление");
        System.out.println("5. Показать меню again");
        System.out.println("0. Выход");
    }

    private static Calculator.NumberSystem selectNumberSystem() {
        System.out.println("\nВыберите систему счисления:");
        System.out.println("1. Двоичная (BINARY)");
        System.out.println("2. Восьмеричная (OCTAL)");
        System.out.println("3. Десятичная (DECIMAL)");
        System.out.println("4. Шестнадцатеричная (HEXADECIMAL)");

        int choice = getIntInput("Ваш выбор: ");

        switch (choice) {
            case 1: return Calculator.NumberSystem.BINARY;
            case 2: return Calculator.NumberSystem.OCTAL;
            case 3: return Calculator.NumberSystem.DECIMAL;
            case 4: return Calculator.NumberSystem.HEXADECIMAL;
            default:
                System.out.println("Неверный выбор, используем десятичную систему");
                return Calculator.NumberSystem.DECIMAL;
        }
    }

    private static String performOperation(int choice, String a, String b,
                                           Calculator.NumberSystem system) {
        switch (choice) {
            case 1: return calculator.add(a, b, system);
            case 2: return calculator.subtract(a, b, system);
            case 3: return calculator.multiply(a, b, system);
            case 4: return calculator.divide(a, b, system);
            default: throw new IllegalArgumentException("Неизвестная операция");
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Введите число!");
            scanner.next();
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // очистка буфера
        return value;
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim().toUpperCase();
    }
}