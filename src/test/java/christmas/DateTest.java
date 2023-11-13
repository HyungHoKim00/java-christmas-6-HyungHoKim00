package christmas;

import static christmas.enums.Event.D_DAY_BASIC;
import static christmas.enums.Event.D_DAY_BONUS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.model.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateTest {
    private static final Date THIRD_DATE = new Date(3);

    @DisplayName("날짜 판별")
    @Test
    void invalidDateThrowsException() {
        assertThatThrownBy(() -> new Date(32))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("크리스마스 디데이 할인 계산")
    @Test
    void calculateDDayDiscount() {
        int result = THIRD_DATE.getDDayDiscount();
        int expected = D_DAY_BASIC.getDiscount() + D_DAY_BONUS.getDiscount() * 2;

        assertThat(result).isEqualTo(expected);
    }
}
