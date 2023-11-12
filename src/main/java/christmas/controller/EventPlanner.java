package christmas.controller;

import static christmas.enums.Discount.NONE;

import christmas.enums.Badge;
import christmas.enums.Menu;
import christmas.model.Date;
import christmas.model.Order;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.HashMap;
import java.util.Map;

public class EventPlanner {
    private static final int GIFT_EVENT_CONDITION_PRICE = 120_000;
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
        outputView.printTotal(date.get());
        outputView.printMenu(order.getOrderDetail());
        int totalOrderPriceBefore = order.calculateTotalOrderPriceBefore();
        outputView.printTotalPriceBefore(totalOrderPriceBefore);
        printBenefit(totalOrderPriceBefore);
    }

    private void printBenefit(int totalOrderPriceBefore) {
        outputView.printGiftMenu(isGiftEvent(totalOrderPriceBefore));
        Map<String, Integer> discountDetails = getDiscountDetails(date, order, totalOrderPriceBefore);
        outputView.printDiscountDetails(discountDetails);
        int totalDiscount = getTotalDiscount(discountDetails);
        outputView.printTotalDiscount(totalDiscount);
        outputView.printEstimatedPrice(totalOrderPriceBefore - totalDiscount);
        String badge = getBadgeName(totalDiscount);
        outputView.printEventBadge(badge);
    }

    public boolean isGiftEvent(int totalOrderPriceBefore) {
        return totalOrderPriceBefore >= GIFT_EVENT_CONDITION_PRICE;
    }

    public Map<String, Integer> getDiscountDetails(Date date, Order order, int totalOrderPriceBefore) {
        Map<String, Integer> discountDetails = new HashMap<>();
        if (totalOrderPriceBefore >= LEAST_ORDER_PRICE_FOR_DISCOUNT) {
            putDDayDiscount(discountDetails, date, order);
            putWeekdayDiscount(discountDetails, date, order);
            putWeekendDiscount(discountDetails, date, order);
            putSpecialDiscount(discountDetails, date, order);
            putGiftEventDiscount(discountDetails, order, totalOrderPriceBefore);
            discountDetails.entrySet().removeIf(entry -> entry.getValue() == NONE.discount());
        }
        return discountDetails;
    }

    private void putDDayDiscount(Map<String, Integer> discountDetails, Date date, Order order) {
        if (date.isBeforeChristmas()) {
            discountDetails.put("크리스마스 디데이 할인", order.getDDayDiscount(date.get()));
        }
    }

    private void putWeekdayDiscount(Map<String, Integer> discountDetails, Date date, Order order) {
        if (date.isWeekday()) {
            discountDetails.put("평일 할인", order.getWeekdayDiscount());
        }
    }

    private void putWeekendDiscount(Map<String, Integer> discountDetails, Date date, Order order) {
        if (date.isWeekend()) {
            discountDetails.put("주말 할인", order.getWeekendDiscount());
        }
    }

    private void putSpecialDiscount(Map<String, Integer> discountDetails, Date date, Order order) {
        if (date.isSpecialDay()) {
            discountDetails.put("특별 할인", order.getSpecialDiscount());
        }
    }

    private void putGiftEventDiscount(Map<String, Integer> discountDetails, Order order, int totalOrderPriceBefore) {
        if (isGiftEvent(totalOrderPriceBefore)) {
            discountDetails.put("증정 이벤트", order.getGiftEventDiscount());
        }
    }

    public int getTotalDiscount(Map<String, Integer> discountDetails) {
        return discountDetails.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public String getBadgeName(int totalDiscount) {
        return Badge.determineBadge(totalDiscount).inKorean();
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
