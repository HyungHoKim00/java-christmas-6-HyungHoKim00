package christmas;

import static christmas.enums.Event.D_DAY_EVENT;
import static christmas.enums.Event.GIFT_EVENT;
import static christmas.enums.Event.SPECIAL_EVENT;
import static christmas.enums.Event.WEEKDAY_EVENT;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.enums.Menu;
import christmas.model.Date;
import christmas.model.Discount;
import christmas.model.Order;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DiscountTest {
    private static final Date VALID_DATE = new Date(3);
    private static final Order VALID_ORDER = new Order(new EnumMap<>(Map.of(
            Menu.T_BONE_STEAK, 1,
            Menu.BBQ_RIBS, 1,
            Menu.CHOCOLATE_CAKE, 2,
            Menu.ZERO_COKE, 1
    )));
    private static final int VALID_TOTAL_ORDER_PRICE
            = Menu.T_BONE_STEAK.getPrice()
            + Menu.BBQ_RIBS.getPrice()
            + Menu.CHOCOLATE_CAKE.getPrice() * 2
            + Menu.ZERO_COKE.getPrice();
    private static final Discount VALID_DISCOUNT
            = new Discount(VALID_DATE, VALID_ORDER, VALID_TOTAL_ORDER_PRICE);

    @DisplayName("증정 이벤트 당첨 여부 선정")
    @Test
    void wonGiftEvent() {
        boolean result = VALID_DISCOUNT.contains(GIFT_EVENT);
        boolean expected = true;

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("할인 상세 내역 선정")
    @Test
    void generateDiscountDetail() {
        Map<String, Integer> result = VALID_DISCOUNT.generateDetail();
        Map<String, Integer> expected = Map.of(
                D_DAY_EVENT.getName(), D_DAY_EVENT.getDiscount() * VALID_DATE.calculateDDayMultiplicand(),
                WEEKDAY_EVENT.getName(), WEEKDAY_EVENT.getDiscount() * 2,
                SPECIAL_EVENT.getName(), SPECIAL_EVENT.getDiscount(),
                GIFT_EVENT.getName(), GIFT_EVENT.getDiscount()
        );
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("전체 할인 금액 계산")
    @Test
    void calculateTotalDiscount() {
        int result = VALID_DISCOUNT.calculateTotal();
        int expected = D_DAY_EVENT.getDiscount() * VALID_DATE.calculateDDayMultiplicand()
                + WEEKDAY_EVENT.getDiscount() * 2
                + SPECIAL_EVENT.getDiscount()
                + GIFT_EVENT.getDiscount();

        assertThat(result).isEqualTo(expected);
    }
}
