package christmas.model;

import static christmas.enums.ErrorMessage.ERROR_ORDER_INVALID;
import static christmas.enums.MenuType.DRINK;

import christmas.enums.Menu;
import christmas.enums.MenuType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private static final int AMOUNT_MIN = 1;
    private static final int AMOUNT_MAX = 20;
    private final Map<Menu, Integer> order;

    public Order(Map<Menu, Integer> order) {
        this.order = validate(order);
    }

    public int calculatePriceTotal() {
        return order.keySet().stream()
                .mapToInt(menu -> menu.getPrice() * order.get(menu))
                .sum();
    }

    public int generateAmountTypeOf(MenuType type) {
        return order.keySet().stream()
                .filter(menu -> menu.compareType(type))
                .mapToInt(order::get)
                .sum();
    }

    public Map<String, Integer> generateDetail() {
        Map<String, Integer> orderDetail = new HashMap<>();
        order.forEach((menu, amount) -> orderDetail.put(menu.getName(), amount));
        return orderDetail;
    }

    private Map<Menu, Integer> validate(Map<Menu, Integer> order) {
        List<Menu> menus = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        order.forEach((menu, amount) -> {
            menus.add(menu);
            amounts.add(amount);
        });
        validateMenu(menus);
        validateAmount(amounts);
        return order;
    }

    private static void validateMenu(List<Menu> menus) {
        if (nameNotExist(menus)) {
            throw new IllegalArgumentException(ERROR_ORDER_INVALID.getMessage());
        }
        if (onlyDrinks(menus)) {
            throw new IllegalArgumentException(ERROR_ORDER_INVALID.getMessage());
        }
    }

    private static boolean nameNotExist(List<Menu> menus) {
        return menus.stream().anyMatch(Menu::isInvalid);
    }

    private static boolean onlyDrinks(List<Menu> menus) {
        return menus.stream().allMatch(menu -> menu.compareType(DRINK));
    }

    private static void validateAmount(List<Integer> amounts) {
        if (totalAmountOutOfRange(amounts)) {
            throw new IllegalArgumentException(ERROR_ORDER_INVALID.getMessage());
        }
    }

    private static boolean totalAmountOutOfRange(List<Integer> amounts) {
        int sum = amounts.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return sum > AMOUNT_MAX || sum < AMOUNT_MIN;
    }
}
