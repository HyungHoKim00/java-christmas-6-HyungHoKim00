package christmas.domain;

import christmas.enums.Menu;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDomain {
    private static final List<Integer> WEEKEND = List.of(1, 2, 8, 9, 15, 16, 22, 23, 29, 30);
    private static final List<Integer> STAR_DATE = List.of(3, 10, 17, 24, 25, 31);
    private static final String WEEKDAY_DISCOUNT_TYPE = "디저트";
    private static final String WEEKEND_DISCOUNT_TYPE = "메인";
    private static final int CHRISTMAS_DATE = 25;
    private static final int NO_DISCOUNT = 0;
    private static final int D_DAY_BASIC_DISCOUNT = 900;
    private static final int D_DAY_BONUS_DISCOUNT = 100;
    private static final int YEAR_DISCOUNT = 2023;
    private static final int SPECIAL_DISCOUNT = 1000;
    private static final int GIFT_EVENT_CONDITION_PRICE = 120000;

    public static int calculateTotalOrderPrice(Map<Menu, Integer> order) {
        AtomicInteger totalOrderPrice = new AtomicInteger();
        order.keySet()
                .forEach(key -> totalOrderPrice.addAndGet(key.price() * order.get(key)));
        return totalOrderPrice.get();
    }

    public static int getDDayDiscount(int date) {
        if (date <= CHRISTMAS_DATE) {
            return D_DAY_BASIC_DISCOUNT + date * D_DAY_BONUS_DISCOUNT;
        }
        return NO_DISCOUNT;
    }

    public static int getWeekdayDiscount(int date, Map<Menu, Integer> order) {
        if (!WEEKEND.contains(date)) {
            AtomicInteger discountAmount = new AtomicInteger();
            order.keySet().stream()
                    .filter(key -> Objects.equals(key.type(), WEEKDAY_DISCOUNT_TYPE))
                    .forEach(key -> discountAmount.addAndGet(YEAR_DISCOUNT));
            return discountAmount.get();
        }
        return NO_DISCOUNT;
    }

    public static int getWeekendDiscount(int date, Map<Menu, Integer> order) {
        if (WEEKEND.contains(date)) {
            AtomicInteger discountAmount = new AtomicInteger();
            order.keySet().stream()
                    .filter(key -> Objects.equals(key.type(), WEEKEND_DISCOUNT_TYPE))
                    .forEach(key -> discountAmount.addAndGet(SPECIAL_DISCOUNT));
            return discountAmount.get();
        }
        return NO_DISCOUNT;
    }

    public static int getSpecialDiscount(int date) {
        if (STAR_DATE.contains(date)) {
            return SPECIAL_DISCOUNT;
        }
        return NO_DISCOUNT;
    }

    public static boolean isGiftEvent(int totalOrderPriceBefore) {
        return totalOrderPriceBefore >= GIFT_EVENT_CONDITION_PRICE;
    }

}