package christmas.enums;

import static christmas.enums.Badge.SANTA;
import static christmas.enums.Badge.STAR;
import static christmas.enums.Badge.TREE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BadgeTest {
    @DisplayName("배지 부여 선정")
    @ParameterizedTest(name = "{1} 배지 부여")
    @MethodSource("discountTotalAndBadge")
    void getBadge(int discountTotal, Badge badge) {
        Badge result = Badge.determineByPrice(discountTotal);

        assertThat(result).isEqualTo(badge);
    }

    static Stream<Arguments> discountTotalAndBadge() {
        return Stream.of(
                Arguments.of(6000, STAR),
                Arguments.of(12000, TREE),
                Arguments.of(24000, SANTA)
        );
    }
}
