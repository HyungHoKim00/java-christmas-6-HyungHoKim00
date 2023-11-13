package christmas;

import static christmas.enums.Badge.SANTA;
import static christmas.enums.Badge.STAR;
import static christmas.enums.Badge.TREE;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.controller.EventPlanner;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class EventPlannerTest {
    private static final EventPlanner VALID_EVENT_PLANNER = new EventPlanner(new InputView(), new OutputView());

    @DisplayName("배지 부여 선정")
    @ParameterizedTest(name = "{1} 배지 부여")
    @MethodSource("totalDiscountAndBadge")
    void getBadgeName(int totalDiscount, String badgeName) {
        String result = VALID_EVENT_PLANNER.getBadgeName(totalDiscount);

        assertThat(result).isEqualTo(badgeName);
    }

    static Stream<Arguments> totalDiscountAndBadge() {
        return Stream.of(
                Arguments.of(6000, STAR.getName()),
                Arguments.of(12000, TREE.getName()),
                Arguments.of(24000, SANTA.getName())
        );
    }
}
