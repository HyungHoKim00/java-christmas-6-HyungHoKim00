package christmas.model;

import static christmas.enums.Event.D_DAY_EVENT;
import static christmas.enums.Event.GIFT_EVENT;
import static christmas.enums.Event.SPECIAL_EVENT;
import static christmas.enums.Event.WEEKDAY_EVENT;
import static christmas.enums.Event.WEEKEND_EVENT;
import static christmas.enums.EventGift.GIFT_EVENT_GIFT;
import static christmas.enums.MenuType.DESSERT;
import static christmas.enums.MenuType.MAIN;

import christmas.enums.Event;
import christmas.enums.MenuType;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Discount {
    private static final MenuType MENU_TYPE_WEEKDAY = DESSERT;
    private static final MenuType MENU_TYPE_WEEKEND = MAIN;
    private static final int ORDER_PRICE_LEAST = 10_000;
    private static final int NONE = 0;
    private static final int EVENT_WON = 1;
    private final Map<Event, Integer> discount;

    public Discount(Date date, Order order, int orderPriceTotal) {
        this.discount = new EnumMap<>(Event.class);
        if (orderPriceTotal >= ORDER_PRICE_LEAST) {
            putDDayDiscount(date);
            putWeekdayDiscount(date, order);
            putWeekendDiscount(date, order);
            putSpecialDiscount(date);
            putGiftEventDiscount(orderPriceTotal);
            discount.values().removeIf(discountAmount -> discountAmount == NONE);
        }
    }

    public int calculateTotal() {
        return discount.keySet().stream()
                .mapToInt(this::calculateDiscountAmount)
                .sum();
    }

    public Map<String, Integer> generateDetail() {
        Map<String, Integer> discountDetail = new HashMap<>();
        discount.keySet()
                .forEach(event -> discountDetail.put(event.getName(), calculateDiscountAmount(event)));
        return discountDetail;
    }

    public int calculateDiscountAmount(Event event) {
        if (contains(event)) {
            return event.getDiscount() * discount.get(event);
        }
        return NONE;
    }

    public boolean contains(Event event) {
        return discount.containsKey(event);
    }

    public boolean exists() {
        return !discount.isEmpty();
    }


    private void putDDayDiscount(Date date) {
        if (date.isChristmasOrBefore()) {
            discount.put(D_DAY_EVENT, date.calculateDDayEventMultiplicand());
        }
    }

    private void putWeekdayDiscount(Date date, Order order) {
        if (date.isWeekday()) {
            discount.put(WEEKDAY_EVENT, order.generateAmountTypeOf(MENU_TYPE_WEEKDAY));
        }
    }

    private void putWeekendDiscount(Date date, Order order) {
        if (date.isWeekend()) {
            discount.put(WEEKEND_EVENT, order.generateAmountTypeOf(MENU_TYPE_WEEKEND));
        }
    }

    private void putSpecialDiscount(Date date) {
        if (date.isSpecialDay()) {
            discount.put(SPECIAL_EVENT, EVENT_WON);
        }
    }

    private void putGiftEventDiscount(int orderPriceTotal) {
        if (orderPriceTotal >= GIFT_EVENT_GIFT.getConditionLeast()) {
            discount.put(GIFT_EVENT, EVENT_WON);
        }
    }
}
