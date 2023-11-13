package christmas.model;

import static christmas.enums.Event.D_DAY_EVENT;
import static christmas.enums.Event.GIFT_EVENT;
import static christmas.enums.Event.NONE;
import static christmas.enums.Event.SPECIAL_EVENT;
import static christmas.enums.Event.WEEKDAY_EVENT;
import static christmas.enums.Event.WEEKEND_EVENT;
import static christmas.enums.MenuType.DESSERT;
import static christmas.enums.MenuType.MAIN;

import christmas.enums.Event;
import christmas.enums.MenuType;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Discount {
    private static final MenuType WEEKDAY_DISCOUNT_TYPE = DESSERT;
    private static final MenuType WEEKEND_DISCOUNT_TYPE = MAIN;
    private static final int LEAST_ORDER_PRICE_FOR_DISCOUNT = 10_000;
    private static final int LEAST_ORDER_PRICE_FOR_GIFT_EVENT = 120_000;
    private static final int WIN_EVENT = 1;
    private final Map<Event, Integer> discount;

    public Discount(Date date, Order order, int totalOrderPrice) {
        this.discount = new EnumMap<>(Event.class);
        if (totalOrderPrice >= LEAST_ORDER_PRICE_FOR_DISCOUNT) {
            putDDayDiscount(discount, date);
            putWeekdayDiscount(discount, date, order);
            putWeekendDiscount(discount, date, order);
            putSpecialDiscount(discount, date);
            putGiftEventDiscount(discount, totalOrderPrice);
            discount.entrySet().removeIf(entry -> entry.getValue() == NONE.getDiscount());
        }
    }

    public boolean contains(Event event) {
        return discount.containsKey(event);
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

    public boolean exists() {
        return !discount.isEmpty();
    }

    public int calculateDiscountAmount(Event event) {
        return event.getDiscount() * discount.get(event);
    }

    private void putDDayDiscount(Map<Event, Integer> discountDetails, Date date) {
        if (date.isBeforeChristmas()) {
            discountDetails.put(D_DAY_EVENT, date.calculateDDayMultiplicand());
        }
    }

    private void putWeekdayDiscount(Map<Event, Integer> discountDetails, Date date, Order order) {
        if (date.isWeekday()) {
            discountDetails.put(WEEKDAY_EVENT, order.generateAmountTypeOf(WEEKDAY_DISCOUNT_TYPE));
        }
    }

    private void putWeekendDiscount(Map<Event, Integer> discountDetails, Date date, Order order) {
        if (date.isWeekend()) {
            discountDetails.put(WEEKEND_EVENT, order.generateAmountTypeOf(WEEKEND_DISCOUNT_TYPE));
        }
    }

    private void putSpecialDiscount(Map<Event, Integer> discountDetails, Date date) {
        if (date.isSpecialDay()) {
            discountDetails.put(SPECIAL_EVENT, WIN_EVENT);
        }
    }

    private void putGiftEventDiscount(Map<Event, Integer> discountDetails, int totalOrderPrice) {
        if (totalOrderPrice >= LEAST_ORDER_PRICE_FOR_GIFT_EVENT) {
            discountDetails.put(GIFT_EVENT, WIN_EVENT);
        }
    }
}
