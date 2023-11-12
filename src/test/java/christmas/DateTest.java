package christmas;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.model.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateTest {
    @DisplayName("날짜 판별")
    @Test
    void invalidDateThrowsException() {
        assertThatThrownBy(() -> new Date(32))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
