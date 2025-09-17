package calculator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Nested
    @DisplayName("Addition Tests")
    class AdditionTests {

        @ParameterizedTest(name = "[{index}] {0} + {1} в {2} системе = {3}")
        @CsvSource({
                "1010, 110, BINARY, 10000",
                "17, 5, OCTAL, 24",
                "15, 7, DECIMAL, 22",
                "A, 5, HEXADECIMAL, F"
        })
        @DisplayName("Addition with CSV source")
        void testAddition(String a, String b, Calculator.NumberSystem system, String expected) {
            String result = calculator.add(a, b, system);
            assertEquals(expected, result,
                    String.format("%s + %s in %s should equal %s", a, b, system, expected));
        }

        @ParameterizedTest()
        @CsvFileSource(resources = "/test_data.csv", numLinesToSkip = 1)
        @DisplayName("Addition with CSV file source")
        void testAdditionFromCsvFile(String operation, String a, String b,
                                     String systemStr, String expected) {
            if ("ADD".equals(operation)) {
                Calculator.NumberSystem system = Calculator.NumberSystem.valueOf(systemStr);
                String result = calculator.add(a, b, system);
                assertEquals(expected, result);
            }
        }

        @ParameterizedTest(name = "[{index}] {0} + {1} в {2} = {3}")
        @MethodSource("calculator.TestDataProviders#additionTestData")
        @DisplayName("Addition with External Method Source")
        void testAdditionWithExternalMethodSource(String a, String b,
                                                  Calculator.NumberSystem system,
                                                  String expected) {
            String result = calculator.add(a, b, system);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Subtraction Tests")
    class SubtractionTests {

        @ParameterizedTest
        @CsvSource({
                "1010, 110, BINARY, 100",
                "17, 5, OCTAL, 12",
                "15, 7, DECIMAL, 8",
                "A, 5, HEXADECIMAL, 5"
        })
        @DisplayName("Subtraction with CSV source")
        void testSubtraction(String a, String b, Calculator.NumberSystem system, String expected) {
            String result = calculator.subtract(a, b, system);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Multiplication Tests")
    class MultiplicationTests {

        @ParameterizedTest
        @CsvSource({
                "1010, 11, BINARY, 11110",
                "7, 5, OCTAL, 43",
                "15, 3, DECIMAL, 45",
                "A, 5, HEXADECIMAL, 32"
        })
        @DisplayName("Multiplication with CSV source")
        void testMultiplication(String a, String b, Calculator.NumberSystem system, String expected) {
            String result = calculator.multiply(a, b, system);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Division Tests")
    class DivisionTests {

        @ParameterizedTest
        @CsvSource({
                "1010, 10, BINARY, 101",
                "24, 3, OCTAL, 6",
                "15, 3, DECIMAL, 5",
                "1E, 3, HEXADECIMAL, A"
        })
        @DisplayName("Division with CSV source")
        void testDivision(String a, String b, Calculator.NumberSystem system, String expected) {
            String result = calculator.divide(a, b, system);
            assertEquals(expected, result);
        }

        @ParameterizedTest
        @EnumSource(Calculator.NumberSystem.class)
        @DisplayName("Division by zero should throw exception")
        void testDivisionByZero(Calculator.NumberSystem system) {
            assertThrows(ArithmeticException.class, () -> {
                calculator.divide("10", "0", system);
            });
        }
    }

    @Nested
    @DisplayName("Dynamic Tests from CSV")
    class DynamicTests {

        @TestFactory
        @DisplayName("Dynamic tests for all operations")
        Stream<DynamicTest> dynamicTestsFromCsvFile() {
            List<String[]> testData = Arrays.asList(
                    new String[]{"ADD", "1010", "110", "BINARY", "10000", "Binary Addition"},
                    new String[]{"ADD", "17", "5", "OCTAL", "24", "Octal Addition"},
                    new String[]{"ADD", "15", "7", "DECIMAL", "22", "Decimal Addition"},
                    new String[]{"ADD", "A", "5", "HEXADECIMAL", "F", "Hexadecimal Addition"},
                    new String[]{"SUBTRACT", "1010", "110", "BINARY", "100", "Binary Subtraction"},
                    new String[]{"SUBTRACT", "17", "5", "OCTAL", "12", "Octal Subtraction"}
            );

            return testData.stream()
                    .map(data -> DynamicTest.dynamicTest(data[5], () -> {
                        Calculator.NumberSystem system = Calculator.NumberSystem.valueOf(data[3]);
                        String result;

                        switch (data[0]) {
                            case "ADD":
                                result = calculator.add(data[1], data[2], system);
                                break;
                            case "SUBTRACT":
                                result = calculator.subtract(data[1], data[2], system);
                                break;
                            case "MULTIPLY":
                                result = calculator.multiply(data[1], data[2], system);
                                break;
                            case "DIVIDE":
                                result = calculator.divide(data[1], data[2], system);
                                break;
                            default:
                                throw new IllegalArgumentException("Unknown operation: " + data[0]);
                        }

                        assertEquals(data[4], result);
                    }));
        }
    }
}