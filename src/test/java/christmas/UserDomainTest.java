package christmas;

import static christmas.enums.Discount.D_DAY_BASIC;
import static christmas.enums.Discount.SPECIAL;
import static christmas.enums.Discount.WEEKDAY_AND_WEEKEND;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.UserDomain;
import christmas.enums.Menu;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserDomainTest {
    private static final Map<Menu, Integer> VALID_ORDER = new EnumMap<>(Map.of(
            Menu.T_BONE_STEAK, 1,
            Menu.BBQ_RIBS, 1,
            Menu.CHOCOLATE_CAKE, 2,
            Menu.ZERO_COKE, 1
    ));
    private static final int WEEKEND = 2;
    private static final int WEEKDAY = 4;
    private static final int SPECIAL_DAY = 3;
    private static final int FIRST_DAY = 1;
    private final UserDomain userDomain = new UserDomain();

    @DisplayName("할인 전 총 주문 금액 계산")
    @Test
    void calculateTotalOrderPriceBeforeSale() {
        int result = userDomain.calculateTotalOrderPriceBeforeSale(VALID_ORDER);
        int expected = Menu.T_BONE_STEAK.price()
                + Menu.BBQ_RIBS.price()
                + Menu.CHOCOLATE_CAKE.price() * 2
                + Menu.ZERO_COKE.price();

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("크리스마스 디데이 할인 계산")
    @Test
    void calculateDDayDiscount() {
        int result = userDomain.calculateDDayDiscount(FIRST_DAY);
        int expected = D_DAY_BASIC.discount();

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("평일 할인 계산")
    @Test
    void calculateWeekdayDiscount() {
        int result = userDomain.calculateWeekdayDiscount(WEEKDAY, VALID_ORDER);
        int expected = WEEKDAY_AND_WEEKEND.discount() * 2;

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("주말 할인 계산")
    @Test
    void calculateWeekendDiscount() {
        int result = userDomain.calculateWeekendDiscount(WEEKEND, VALID_ORDER);
        int expected = WEEKDAY_AND_WEEKEND.discount() * 2;

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("특별 할인 계산")
    @Test
    void calculateSpecialDiscount() {
        int result = userDomain.calculateSpecialDiscount(SPECIAL_DAY);
        int expected = SPECIAL.discount();

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("증정 이벤트 당첨 여부 선정")
    @Test
    void distinctGiftEvent() {
        boolean result = userDomain.distinctGiftEvent(
                Menu.T_BONE_STEAK.price()
                        + Menu.BBQ_RIBS.price()
                        + Menu.CHOCOLATE_CAKE.price() * 2
                        + Menu.ZERO_COKE.price());
        boolean expected = true;

        assertThat(result).isEqualTo(expected);
    }

}
