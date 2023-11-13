package christmas.model;

import static christmas.enums.ErrorMessage.ORDER_ERROR;
import static christmas.enums.Event.SPECIAL;
import static christmas.enums.Event.WEEKDAY;
import static christmas.enums.Event.WEEKEND;
import static christmas.enums.Menu.CHAMPAGNE;
import static christmas.enums.Menu.INVALID_MENU;
import static christmas.enums.MenuType.DESSERT;
import static christmas.enums.MenuType.DRINK;
import static christmas.enums.MenuType.MAIN;

import christmas.enums.Menu;
import christmas.enums.MenuType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private static final int LEAST_AMOUNT = 1;
    private static final int MAX_AMOUNT = 20;
    private static final MenuType WEEKDAY_DISCOUNT_TYPE = DESSERT;
    private static final MenuType WEEKEND_DISCOUNT_TYPE = MAIN;
    private final Map<Menu, Integer> order;

    public Order(Map<Menu, Integer> order) {
        validate(order);
        this.order = order;
    }

    public List<String> getOrderDetail() {
        List<String> orderDetail = new ArrayList<>();
        order.keySet()
                .forEach(menu -> {
                    String menuName = menu.getName();
                    String menuAmount = order.get(menu).toString();
                    orderDetail.add(menuName + " " + menuAmount + "개");
                });
        return orderDetail;
    }

    public int calculateTotalOrderPriceBefore() {
        AtomicInteger totalOrderPrice = new AtomicInteger();
        order.keySet()
                .forEach(key -> totalOrderPrice.addAndGet(key.getPrice() * order.get(key)));
        return totalOrderPrice.get();
    }

    public int getWeekdayDiscount() {
        AtomicInteger discountAmount = new AtomicInteger();
        order.keySet().stream()
                .filter(key -> key.getType().equals(WEEKDAY_DISCOUNT_TYPE))
                .forEach(key -> discountAmount.addAndGet(WEEKDAY.getDiscount() * order.get(key)));
        return discountAmount.get();
    }

    public int getWeekendDiscount() {
        AtomicInteger discountAmount = new AtomicInteger();
        order.keySet().stream()
                .filter(key -> key.getType().equals(WEEKEND_DISCOUNT_TYPE))
                .forEach(key -> discountAmount.addAndGet(WEEKEND.getDiscount() * order.get(key)));
        return discountAmount.get();
    }

    public int getSpecialDiscount() {
        return SPECIAL.getDiscount();
    }

    public int getGiftEventDiscount() {
        return CHAMPAGNE.getPrice();
    }


    private void validate(Map<Menu, Integer> order) {
        List<String> names = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        order.keySet().forEach(name -> {
            names.add(name.getName());
            amounts.add(order.get(name));
        });
        validateMenuNames(names);
        validateMenuAmounts(amounts);
    }

    private static void validateMenuNames(List<String> names) {
        if (nameNotExist(names)) {
            throw new IllegalArgumentException(ORDER_ERROR.getMessage());
        }
        if (onlyDrinks(names)) {
            throw new IllegalArgumentException(ORDER_ERROR.getMessage());
        }
    }

    private static boolean nameNotExist(List<String> names) {
        AtomicBoolean nameNotExist = new AtomicBoolean(false);
        names.forEach(name -> {
            if (Menu.determineByName(name).equals(INVALID_MENU)) {
                nameNotExist.set(true);
            }
        });
        return nameNotExist.get();
    }

    private static boolean onlyDrinks(List<String> names) {
        long numberOfDrinks = names.stream()
                .filter(name -> Menu.determineByName(name).getType().equals(DRINK))
                .count();
        return names.size() == numberOfDrinks;
    }

    private static void validateMenuAmounts(List<Integer> amounts) {
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
