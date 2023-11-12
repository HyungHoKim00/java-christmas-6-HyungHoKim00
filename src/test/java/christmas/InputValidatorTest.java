package christmas;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.validator.InputValidator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InputValidatorTest {
    @DisplayName("날짜 판별")
    @Test
    void invalidDateThrowsException() {
        assertThatThrownBy(() -> InputValidator.validateDate("asd"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 형식 판별")
    @Test
    void invalidMenuAndAmountsThrowsException() {
        assertThatThrownBy(() -> InputValidator
                .validateMenuNameAndAmounts(List.of("티본스테이크--2")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 이름 중복 판별")
    @Test
    void duplicatedMenuName() {
        assertThatThrownBy(() -> InputValidator
                .validateMenuNameAndAmounts(List.of("타파스-2", "타파스-3")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
