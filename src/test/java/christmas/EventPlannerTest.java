package christmas;

import static christmas.enums.Event.D_DAY_BASIC;
import static christmas.enums.Event.D_DAY_BONUS;
import static christmas.enums.Event.GIFT;
import static christmas.enums.Event.SPECIAL;
import static christmas.enums.Event.WEEKDAY;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.controller.EventPlanner;
import christmas.enums.Menu;
import christmas.model.Date;
import christmas.model.Order;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EventPlannerTest {
    private static final EventPlanner VALID_EVENT_PLANNER = new EventPlanner(new InputView(), new OutputView());
    private static final Date VALID_DATE = new Date(3);
    private static final Order VALID_ORDER = new Order(new EnumMap<>(Map.of(
            Menu.T_BONE_STEAK, 1,
            Menu.BBQ_RIBS, 1,
            Menu.CHOCOLATE_CAKE, 2,
            Menu.ZERO_COKE, 1
    )));
    private static final int VALID_TOTAL_ORDER_PRICE_BEFORE
            = Menu.T_BONE_STEAK.getPrice()
            + Menu.BBQ_RIBS.getPrice()
            + Menu.CHOCOLATE_CAKE.getPrice() * 2
            + Menu.ZERO_COKE.getPrice();
    private static final Map<String, Integer> EXPECTED_DISCOUNT_DETAILS
            = Map.of(
            "크리스마스 디데이 할인", D_DAY_BASIC.getDiscount() + D_DAY_BONUS.getDiscount() * 2,
            "평일 할인", WEEKDAY.getDiscount() * 2,
            "특별 할인", SPECIAL.getDiscount(),
            "증정 이벤트", GIFT.getDiscount());
    private static final int EXPECTED_TOTAL_DISCOUNT
            = D_DAY_BASIC.getDiscount() + D_DAY_BONUS.getDiscount() * 2
            + WEEKDAY.getDiscount() * 2
            + SPECIAL.getDiscount()
            + GIFT.getDiscount();

    @DisplayName("증정 이벤트 당첨 여부 선정")
    @Test
    void distinctGiftEvent() {
        boolean result = VALID_EVENT_PLANNER
                .isGiftEvent(
                        Menu.T_BONE_STEAK.getPrice()
                                + Menu.BBQ_RIBS.getPrice()
                                + Menu.CHOCOLATE_CAKE.getPrice() * 2
                                + Menu.ZERO_COKE.getPrice());
        boolean expected = true;

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("전체 할인 내역 선정")
    @Test
    void getDiscountDetails() {
        Map<String, Integer> result = VALID_EVENT_PLANNER
                .getDiscountDetails(VALID_DATE, VALID_ORDER, VALID_TOTAL_ORDER_PRICE_BEFORE);

        assertThat(result).isEqualTo(EXPECTED_DISCOUNT_DETAILS);
    }

    @DisplayName("전체 할인 금액 계산")
    @Test
    void calculateTotalDiscount() {
        int result = VALID_EVENT_PLANNER.getTotalDiscount(EXPECTED_DISCOUNT_DETAILS);

        assertThat(result).isEqualTo(EXPECTED_TOTAL_DISCOUNT);
    }

    @DisplayName("배지 부여 선정")
    @Test
    void getBadgeName() {
        String result = VALID_EVENT_PLANNER.getBadgeName(EXPECTED_TOTAL_DISCOUNT);
        String expected = "산타";

        assertThat(result).isEqualTo(expected);
    }
}
