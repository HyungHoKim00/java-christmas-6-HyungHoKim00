package christmas.domain;

import static christmas.enums.Discount.D_DAY_BASIC;
import static christmas.enums.Discount.D_DAY_BONUS;
import static christmas.enums.Discount.NONE;
import static christmas.enums.Discount.SPECIAL;
import static christmas.enums.Discount.WEEKDAY_AND_WEEKEND;

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
    private static final int GIFT_EVENT_CONDITION_PRICE = 120_000;

    public int calculateTotalOrderPriceBeforeSale(Map<Menu, Integer> order) {
        AtomicInteger totalOrderPrice = new AtomicInteger();
        order.keySet()
                .forEach(key -> totalOrderPrice.addAndGet(key.price() * order.get(key)));
        return totalOrderPrice.get();
    }

    public int calculateDDayDiscount(int date) {
        if (date <= CHRISTMAS_DATE) {
            return D_DAY_BASIC.discount() + (date - 1) * D_DAY_BONUS.discount();
        }
        return NONE.discount();
    }

    public int calculateWeekdayDiscount(int date, Map<Menu, Integer> order) {
        if (!WEEKEND.contains(date)) {
            AtomicInteger discountAmount = new AtomicInteger();
            order.keySet().stream()
                    .filter(key -> Objects.equals(key.type(), WEEKDAY_DISCOUNT_TYPE))
                    .forEach(key -> discountAmount.addAndGet(WEEKDAY_AND_WEEKEND.discount() * order.get(key)));
            return discountAmount.get();
        }
        return NONE.discount();
    }

    public int calculateWeekendDiscount(int date, Map<Menu, Integer> order) {
        if (WEEKEND.contains(date)) {
            AtomicInteger discountAmount = new AtomicInteger();
            order.keySet().stream()
                    .filter(key -> Objects.equals(key.type(), WEEKEND_DISCOUNT_TYPE))
                    .forEach(key -> discountAmount.addAndGet(WEEKDAY_AND_WEEKEND.discount() * order.get(key)));
            return discountAmount.get();
        }
        return NONE.discount();
    }

    public int calculateSpecialDiscount(int date) {
        if (STAR_DATE.contains(date)) {
            return SPECIAL.discount();
        }
        return NONE.discount();
    }

    public boolean distinctGiftEvent(int totalOrderPriceBefore) {
        return totalOrderPriceBefore >= GIFT_EVENT_CONDITION_PRICE;
    }
}