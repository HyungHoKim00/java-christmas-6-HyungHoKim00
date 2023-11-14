package christmas.controller;

import static christmas.enums.Event.GIFT_EVENT;
import static christmas.enums.EventGift.GIFT_EVENT_GIFT;

import christmas.enums.Badge;
import christmas.enums.Menu;
import christmas.model.Date;
import christmas.model.Discount;
import christmas.model.Order;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.Map;

public class EventPlanner {
    private final InputView inputview;
    private final OutputView outputView;
    private Date date;
    private Order order;
    private Discount discount;

    public EventPlanner(InputView inputView, OutputView outputView) {
        this.inputview = inputView;
        this.outputView = outputView;
    }

    public void run() {
        this.date = validateDate();
        this.order = validateOrder();
        int orderPriceTotal = order.calculatePriceTotal();
        this.discount = new Discount(date, order, orderPriceTotal);
        printInput(orderPriceTotal);
        printBenefit(orderPriceTotal);
    }

    private void printInput(int orderPriceTotal) {
        outputView.printTitle(date.toString());
        outputView.printOrderDetail(order.generateDetail());
        outputView.printOrderPriceTotal(orderPriceTotal);
    }

    private void printBenefit(int OrderPriceTotal) {
        int discountTotal = discount.calculateTotal();
        boolean wonGiftEvent = discount.contains(GIFT_EVENT);
        boolean discountExists = discount.exists();
        printGiftEventResult(wonGiftEvent);
        printDiscountDetail(discountExists);
        printDiscountTotal(discountExists, discountTotal);
        printPriceEstimated(OrderPriceTotal, discountTotal);
        printBadgeName(discountTotal);
    }


    private void printGiftEventResult(boolean isGiftEvent) {
        outputView.printGiftEventTitle();
        if (isGiftEvent) {
            outputView.printGiftEventDetail(GIFT_EVENT_GIFT.getName(), GIFT_EVENT_GIFT.getAmount());
        }
        if (!isGiftEvent) {
            outputView.printNotExist();
        }
    }

    private void printDiscountDetail(boolean discountExists) {
        outputView.printDiscountDetailTitle();
        if (discountExists) {
            outputView.printDiscountDetail(discount.generateDetail());
        }
        if (!discountExists) {
            outputView.printNotExist();
        }
    }

    private void printDiscountTotal(boolean discountExists, int discountTotal) {
        outputView.printDiscountTotalTitle();
        if (discountExists) {
            outputView.printDiscountTotal(discountTotal);
        }
        if (!discountExists) {
            outputView.printDiscountTotalIsZero();
        }
    }

    private void printPriceEstimated(int OrderPriceTotal, int discountTotal) {
        outputView.printPriceEstimated(OrderPriceTotal - discountTotal
                + discount.calculateDiscountAmount(GIFT_EVENT));
    }

    private void printBadgeName(int discountTotal) {
        outputView.printEventBadge(getBadgeName(discountTotal));
    }

    public String getBadgeName(int discountTotal) {
        return Badge.determineByPrice(discountTotal).getName();
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
