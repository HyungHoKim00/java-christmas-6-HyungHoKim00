package christmas.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class InputValidatorTest {
    @DisplayName("날짜 입력 판별")
    @Test
    void invalidDateThrowsException() {
        assertThatThrownBy(() -> InputValidator.validateDate("asd"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 입력 판별")
    @ParameterizedTest(name = "{1}")
    @MethodSource("invalidInputOrder")
    void invalidOrderThrowsException(String input, String reason) {
        assertThatThrownBy(() -> InputValidator.validateOrder(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidInputOrder() {
        return Stream.of(
                Arguments.of(",티본스테이크-2,타파스-3", "주문 문장 판별"),
                Arguments.of("티본스테이크-2-", "주문 형식 판별"),
                Arguments.of("티본스테이크-a", "주문 형식 판별"),
                Arguments.of("타파스-2,타파스-3", "메뉴 이름 중복")
        );
    }
}
