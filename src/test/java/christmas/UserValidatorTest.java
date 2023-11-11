package christmas;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.validator.UserValidator;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class UserValidatorTest {
    @DisplayName("날짜 판별")
    @ParameterizedTest(name = "{1}")
    @MethodSource("invalidDate")
    void invalidDateThrowsException(String invalidDate, String reason) {
        assertThatThrownBy(() -> UserValidator.validateDate(invalidDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 판별")
    @ParameterizedTest(name = "{1}")
    @MethodSource("invalidOrder")
    void invalidOrderThrowsException(List<String> invalidOrder, String reason) {
        assertThatThrownBy(() -> UserValidator.validateMenuNameAndAmounts(invalidOrder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidDate() {
        return Stream.of(
                Arguments.of("asd", "날짜가 양의 정수가 아님"),
                Arguments.of("32", "날짜가 범위 밖")
        );
    }

    static Stream<Arguments> invalidOrder() {
        return Stream.of(
                Arguments.of(List.of("티본스테이크--2"), "주문 입력 형식이 예시와 다름"),
                Arguments.of(List.of("스파게티-3"), "메뉴판에 없는 메뉴 입력"),
                Arguments.of(List.of("타파스-2", "타파스-3"), "중복 메뉴 입력"),
                Arguments.of(List.of("제로콜라-2"), "음료만 주문"),
                Arguments.of(List.of("타파스-0"), "개수가 양의 정수가 아님"),
                Arguments.of(List.of("티본스테이크-10,타파스-11"), "총 개수가 범위 밖")
        );
    }
}
