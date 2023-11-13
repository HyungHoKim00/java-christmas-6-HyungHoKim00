package christmas.controller;

import static christmas.enums.Event.D_DAY_BASIC;
import static christmas.enums.Event.GIFT;
import static christmas.enums.Event.NONE;
import static christmas.enums.Event.SPECIAL;
import static christmas.enums.Event.WEEKDAY;
import static christmas.enums.Event.WEEKEND;
import static christmas.enums.Menu.CHAMPAGNE;

import christmas.enums.Badge;
import christmas.enums.Menu;
import christmas.model.Date;
import christmas.model.Order;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.HashMap;
import java.util.Map;

public class EventPlanner {
    private static final String GIFT_NAME = CHAMPAGNE.name();
    private static final int GIFT_AMOUNT = 1;
    private static final int LEAST_ORDER_PRICE_FOR_GIFT_EVENT = 120_000;
    private static final int LEAST_ORDER_PRICE_FOR_DISCOUNT = 10_000;
    private final InputView inputview;
    private final OutputView outputView;
    private Date date;
    private Order order;

    public EventPlanner(InputView inputView, OutputView outputView) {
        this.inputview = inputView;
        this.outputView = outputView;
    }

    public void run() {
        this.date = validateDate();
        this.order = validateOrder();
        outputView.printTotal(date.toString());
        outputView.printMenu(order.getOrderDetail());
        int totalOrderPriceBefore = order.calculateTotalOrderPriceBefore();
        outputView.printTotalPriceBefore(totalOrderPriceBefore);
        printBenefit(totalOrderPriceBefore);
    }

    private void printBenefit(int totalOrderPriceBefore) {
        printGiftEvent(totalOrderPriceBefore);
        Map<String, Integer> discountDetails = getDiscountDetails(date, order, totalOrderPriceBefore);
        int totalDiscount = getTotalDiscount(discountDetails);
        printDiscountDetails(discountDetails, totalDiscount);
        printTotalDiscount(totalDiscount);
        outputView.printEstimatedPrice(totalOrderPriceBefore - totalDiscount);
        printBadgeName(totalDiscount);
    }


    public void printGiftEvent(int totalOrderPriceBefore) {
        outputView.printGiftEventTitle();
        if (isGiftEvent(totalOrderPriceBefore)) {
            outputView.printGiftEvent(GIFT_NAME, GIFT_AMOUNT);
        }
        if (!isGiftEvent(totalOrderPriceBefore)) {
            outputView.printNotExist();
        }
    }

    public boolean isGiftEvent(int totalOrderPriceBefore) {
        return totalOrderPriceBefore >= LEAST_ORDER_PRICE_FOR_GIFT_EVENT;
    }

    public Map<String, Integer> getDiscountDetails(Date date, Order order, int totalOrderPriceBefore) {
        Map<String, Integer> discountDetails = new HashMap<>();
        if (totalOrderPriceBefore >= LEAST_ORDER_PRICE_FOR_DISCOUNT) {
            putDDayDiscount(discountDetails, date);
            putWeekdayDiscount(discountDetails, date, order);
            putWeekendDiscount(discountDetails, date, order);
            putSpecialDiscount(discountDetails, date, order);
            putGiftEventDiscount(discountDetails, order, totalOrderPriceBefore);
            discountDetails.entrySet().removeIf(entry -> entry.getValue() == NONE.getDiscount());
        }
        return discountDetails;
    }

    private void putDDayDiscount(Map<String, Integer> discountDetails, Date date) {
        if (date.isBeforeChristmas()) {
            discountDetails.put(D_DAY_BASIC.getEventName(), date.getDDayDiscount());
        }
    }

    private void putWeekdayDiscount(Map<String, Integer> discountDetails, Date date, Order order) {
        if (date.isWeekday()) {
            discountDetails.put(WEEKDAY.getEventName(), order.getWeekdayDiscount());
        }
    }

    private void putWeekendDiscount(Map<String, Integer> discountDetails, Date date, Order order) {
        if (date.isWeekend()) {
            discountDetails.put(WEEKEND.getEventName(), order.getWeekendDiscount());
        }
    }

    private void putSpecialDiscount(Map<String, Integer> discountDetails, Date date, Order order) {
        if (date.isSpecialDay()) {
            discountDetails.put(SPECIAL.getEventName(), order.getSpecialDiscount());
        }
    }

    private void putGiftEventDiscount(Map<String, Integer> discountDetails, Order order, int totalOrderPriceBefore) {
        if (isGiftEvent(totalOrderPriceBefore)) {
            discountDetails.put(GIFT.getEventName(), order.getGiftEventDiscount());
        }
    }

    public int getTotalDiscount(Map<String, Integer> discountDetails) {
        return discountDetails.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private void printDiscountDetails(Map<String, Integer> discountDetails, int totalDiscount) {
        outputView.printDiscountDetailsTitle();
        if (totalDiscount != NONE.getDiscount()) {
            outputView.printDiscountDetails(discountDetails);
        }
        if (totalDiscount == NONE.getDiscount()) {
            outputView.printNotExist();
        }
    }

    private void printTotalDiscount(int totalDiscount) {
        outputView.printTotalDiscountTitle();
        if (totalDiscount != NONE.getDiscount()) {
            outputView.printTotalDiscount(totalDiscount);
        }
        if (totalDiscount == NONE.getDiscount()) {
            outputView.printTotalDiscountWhenZero();
        }
    }

    private void printBadgeName(int totalDiscount) {
        outputView.printEventBadgeTitle();
        if (totalDiscount != NONE.getDiscount()) {
            outputView.printEventBadge(getBadgeName(totalDiscount));
        }
        if (totalDiscount == NONE.getDiscount()) {
            outputView.printNotExist();
        }
    }

    public String getBadgeName(int totalDiscount) {
        return Badge.determineBadge(totalDiscount).getName();
    }


    private Date validateDate() {
        outputView.printDateRequestMessage();
        int date;
        while (true) {
            try {
                date = inputview.readDate();
                return new Date(date);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Order validateOrder() {
        outputView.printOrderRequestMessage();
        Map<Menu, Integer> order;
        while (true) {
            try {
                order = inputview.readOrder();
                return new Order(order);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
