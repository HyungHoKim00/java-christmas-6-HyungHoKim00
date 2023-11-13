package christmas;

import static christmas.enums.Badge.SANTA;
import static christmas.enums.Badge.STAR;
import static christmas.enums.Badge.TREE;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.enums.Badge;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BadgeTest {
    @DisplayName("배지 부여 선정")
    @ParameterizedTest(name = "{1} 배지 부여")
    @MethodSource("totalDiscountAndBadge")
    void getBadge(int totalDiscount, Badge badge) {
        Badge result = Badge.determineByPrice(totalDiscount);

        assertThat(result).isEqualTo(badge);
    }

    static Stream<Arguments> totalDiscountAndBadge() {
        return Stream.of(
                Arguments.of(6000, STAR),
                Arguments.of(12000, TREE),
                Arguments.of(24000, SANTA)
        );
    }
}
