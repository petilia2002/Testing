package calculator;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestDataProviders {

    public static Stream<Arguments> additionTestData() {
        return Stream.of(
                arguments("1010", "110", Calculator.NumberSystem.BINARY, "10000"),
                arguments("17", "5", Calculator.NumberSystem.OCTAL, "24"),
                arguments("15", "7", Calculator.NumberSystem.DECIMAL, "22"),
                arguments("A", "5", Calculator.NumberSystem.HEXADECIMAL, "F")
        );
    }
}