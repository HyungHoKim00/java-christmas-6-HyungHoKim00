package christmas;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.controller.EventPlanner;
import christmas.enums.Menu;
import christmas.model.User;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EventPlannerTest {
    private static final Map<Menu, Integer> VALID_ORDER = new EnumMap<>(Map.of(
            Menu.T_BONE_STEAK, 1,
            Menu.BBQ_RIBS, 1,
            Menu.CHOCOLATE_CAKE, 2,
            Menu.ZERO_COKE, 1
    ));
    private static final User VALID_USER = new User(3, VALID_ORDER);
    private static final Map<String, Integer> EXPECTED_DISCOUNT_DETAILS
            = Map.of("크리스마스 디데이 할인", 1_200,
            "평일 할인", 4_046,
            "특별 할인", 1_000,
            "증정 이벤트", 25_000);
    private static final int EXPECTED_TOTAL_DISCOUNT = 31_246;
    private static final int VALID_TOTAL_ORDER_PRICE_BEFORE =
            Menu.T_BONE_STEAK.price() + Menu.BBQ_RIBS.price()
                    + Menu.CHOCOLATE_CAKE.price() * 2
                    + Menu.ZERO_COKE.price();
    private static final int VALID_DATE = 3;
    private final EventPlanner eventPlanner = new EventPlanner(new InputView(), new OutputView());

    @DisplayName("전체 할인 내역 선정")
    @Test
    void getDiscountDetails() {
        Map<String, Integer> result = eventPlanner
                .getDiscountDetails(VALID_USER, VALID_TOTAL_ORDER_PRICE_BEFORE);

        assertThat(result).isEqualTo(EXPECTED_DISCOUNT_DETAILS);
    }

    @DisplayName("전체 할인 금액 계산")
    @Test
    void calculateTotalDiscount() {
        int result = eventPlanner.getTotalDiscount(EXPECTED_DISCOUNT_DETAILS);

        assertThat(result).isEqualTo(EXPECTED_TOTAL_DISCOUNT);
    }

    @DisplayName("배지 부여 선정")
    @Test
    void getBadgeName() {
        String result = eventPlanner.getBadgeName(EXPECTED_TOTAL_DISCOUNT);
        String expected = "산타";

        assertThat(result).isEqualTo(expected);
    }
}
