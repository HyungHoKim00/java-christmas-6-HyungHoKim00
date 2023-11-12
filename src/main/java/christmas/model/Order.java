package christmas.model;

import static christmas.enums.Discount.D_DAY_BASIC;
import static christmas.enums.Discount.D_DAY_BONUS;
import static christmas.enums.Discount.SPECIAL;
import static christmas.enums.Discount.WEEKDAY_AND_WEEKEND;
import static christmas.enums.Menu.CHAMPAGNE;
import static christmas.enums.MenuType.DESSERT;
import static christmas.enums.MenuType.DRINK;
import static christmas.enums.MenuType.MAIN;

import christmas.enums.Menu;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private static final String ORDER_ERROR_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final int LEAST_MENU_AMOUNT = 1;
    private static final int MAX_MENU_AMOUNT = 20;
    private static final String WEEKDAY_DISCOUNT_TYPE = DESSERT.type();
    private static final String WEEKEND_DISCOUNT_TYPE = MAIN.type();
    private static final String DRINK_TYPE = DRINK.type();
    private final Map<Menu, Integer> order;

    public Order(Map<Menu, Integer> order) {
        validate(order);
        this.order = order;
    }

    public List<String> getOrderDetail() {
        List<String> orderDetail = new ArrayList<>();
        order.keySet()
                .forEach(menu -> {
                    String menuName = menu.inKorean();
                    String menuAmount = order.get(menu).toString();
                    orderDetail.add(menuName + " " + menuAmount + "개");
                });
        return orderDetail;
    }

    public int calculateTotalOrderPriceBefore() {
        AtomicInteger totalOrderPrice = new AtomicInteger();
        order.keySet()
                .forEach(key -> totalOrderPrice.addAndGet(key.price() * order.get(key)));
        return totalOrderPrice.get();
    }

    public int getDDayDiscount(int date) {
        return D_DAY_BASIC.discount() + (date - 1) * D_DAY_BONUS.discount();
    }

    public int getWeekdayDiscount() {
        AtomicInteger discountAmount = new AtomicInteger();
        order.keySet().stream()
                .filter(key -> Objects.equals(key.type(), WEEKDAY_DISCOUNT_TYPE))
                .forEach(key -> discountAmount.addAndGet(WEEKDAY_AND_WEEKEND.discount() * order.get(key)));
        return discountAmount.get();
    }

    public int getWeekendDiscount() {
        AtomicInteger discountAmount = new AtomicInteger();
        order.keySet().stream()
                .filter(key -> Objects.equals(key.type(), WEEKEND_DISCOUNT_TYPE))
                .forEach(key -> discountAmount.addAndGet(WEEKDAY_AND_WEEKEND.discount() * order.get(key)));
        return discountAmount.get();
    }

    public int getSpecialDiscount() {
        return SPECIAL.discount();
    }

    public int getGiftEventDiscount() {
        return CHAMPAGNE.price();
    }


    private void validate(Map<Menu, Integer> order) {
        List<String> names = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        order.keySet().forEach(name -> {
            names.add(name.inKorean());
            amounts.add(order.get(name));
        });
        validateMenuNames(names);
        validateMenuAmounts(amounts);
    }

    private static void validateMenuNames(List<String> names) {
        if (nameNotExist(names)) {
            throw new IllegalArgumentException(ORDER_ERROR_MESSAGE);
        }
        if (onlyDrinks(names)) {
            throw new IllegalArgumentException(ORDER_ERROR_MESSAGE);
        }
    }

    private static boolean nameNotExist(List<String> names) {
        AtomicBoolean nameNotExist = new AtomicBoolean(false);
        names.forEach(name -> {
            if (Objects.equals(Menu.determineByName(name).inKorean(), "")) {
                nameNotExist.set(true);
            }
        });
        return nameNotExist.get();
    }

    private static boolean onlyDrinks(List<String> names) {
        long numberOfDrinks = names.stream()
                .filter(name -> Objects.equals(Menu.determineByName(name).type(), DRINK_TYPE))
                .count();
        return names.size() == numberOfDrinks;
    }

    private static void validateMenuAmounts(List<Integer> amounts) {
        if (amountNotPositive(amounts)) {
            throw new IllegalArgumentException(ORDER_ERROR_MESSAGE);
        }
        if (totalAmountOutOfRange(amounts)) {
            throw new IllegalArgumentException(ORDER_ERROR_MESSAGE);
        }
    }

    private static boolean amountNotPositive(List<Integer> amounts) {
        AtomicBoolean isNotPositive = new AtomicBoolean(false);
        amounts.forEach(amount -> {
            isNotPositive.set(amount < LEAST_MENU_AMOUNT);
        });
        return isNotPositive.get();
    }

    private static boolean totalAmountOutOfRange(List<Integer> amounts) {
        int sum = amounts.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return sum > MAX_MENU_AMOUNT || sum < LEAST_MENU_AMOUNT;
    }
}
