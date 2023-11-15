package christmas.model;

import static christmas.enums.Menu.BBQ_RIBS;
import static christmas.enums.Menu.CHOCOLATE_CAKE;
import static christmas.enums.Menu.INVALID_MENU;
import static christmas.enums.Menu.TAPAS;
import static christmas.enums.Menu.T_BONE_STEAK;
import static christmas.enums.Menu.ZERO_COKE;
import static christmas.enums.MenuType.MAIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.enums.Menu;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderTest {
    private static final Order VALID_ORDER = new Order(new EnumMap<>(Map.of(
            T_BONE_STEAK, 1,
            BBQ_RIBS, 1,
            CHOCOLATE_CAKE, 2,
            ZERO_COKE, 1
    )));

    @DisplayName("할인 전 총 주문 금액 계산")
    @Test
    void calculateOrderPriceTotal() {
        int result = VALID_ORDER.calculatePriceTotal();
        int expected = T_BONE_STEAK.getPrice()
                + BBQ_RIBS.getPrice()
                + CHOCOLATE_CAKE.getPrice() * 2
                + ZERO_COKE.getPrice();

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("어떤 종류의 메뉴 개수 계산")
    @Test
    void generateAmountOfTypeMain() {
        int result = VALID_ORDER.generateAmountTypeOf(MAIN);
        int expected = 2;

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("주문 판별")
    @ParameterizedTest(name = "{1}")
    @MethodSource("invalidOrder")
    void invalidOrderThrowsException(Map<Menu, Integer> invalidOrder, String reason) {
        assertThatThrownBy(() -> new Order(invalidOrder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidOrder() {
        return Stream.of(
                Arguments.of(new EnumMap<>(Map.of(INVALID_MENU, 3)), "메뉴판에 없는 메뉴 입력"),
                Arguments.of(new EnumMap<>(Map.of(ZERO_COKE, 2)), "음료만 주문"),
                Arguments.of(new EnumMap<>(Map.of(TAPAS, 0)), "개수가 양의 정수가 아님"),
                Arguments.of(new EnumMap<>(Map.of(T_BONE_STEAK, 10, TAPAS, 11)), "총 개수가 범위 밖")
        );
    }
}
