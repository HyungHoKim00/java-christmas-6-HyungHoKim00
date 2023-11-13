package christmas.model;

import static christmas.enums.ErrorMessage.ORDER_ERROR;
import static christmas.enums.MenuType.DRINK;

import christmas.enums.Menu;
import christmas.enums.MenuType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Order {
    private static final int LEAST_AMOUNT = 1;
    private static final int MAX_AMOUNT = 20;
    private final Map<Menu, Integer> order;

    public Order(Map<Menu, Integer> order) {
        this.order = validate(order);
    }

    public Map<String, Integer> generateDetail() {
        Map<String, Integer> orderDetail = new HashMap<>();
        order.keySet()
                .forEach(menu -> {
                    String menuName = menu.getName();
                    int menuAmount = order.get(menu);
                    orderDetail.put(menuName, menuAmount);
                });
        return orderDetail;
    }

    public int calculateTotalPriceBefore() {
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

    private Map<Menu, Integer> validate(Map<Menu, Integer> order) {
        List<Menu> menus = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        order.keySet().forEach(menu -> {
            menus.add(menu);
            amounts.add(order.get(menu));
        });
        validateMenu(menus);
        validateAmount(amounts);
        return order;
    }

    private static void validateMenu(List<Menu> menus) {
        if (nameNotExist(menus)) {
            throw new IllegalArgumentException(ORDER_ERROR.getMessage());
        }
        if (onlyDrinks(menus)) {
            throw new IllegalArgumentException(ORDER_ERROR.getMessage());
        }
    }

    private static boolean nameNotExist(List<Menu> menus) {
        AtomicBoolean nameNotExist = new AtomicBoolean(false);
        menus.forEach(menu -> {
            if (menu.isInvalid()) {
                nameNotExist.set(true);
            }
        });
        return nameNotExist.get();
    }

    private static boolean onlyDrinks(List<Menu> menus) {
        long numberOfDrinks = menus.stream()
                .filter(menu -> menu.compareType(DRINK))
                .count();
        return menus.size() == numberOfDrinks;
    }

    private static void validateAmount(List<Integer> amounts) {
        if (totalAmountOutOfRange(amounts)) {
            throw new IllegalArgumentException(ORDER_ERROR.getMessage());
        }
    }

    private static boolean totalAmountOutOfRange(List<Integer> amounts) {
        int sum = amounts.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return sum > MAX_AMOUNT || sum < LEAST_AMOUNT;
    }
}
