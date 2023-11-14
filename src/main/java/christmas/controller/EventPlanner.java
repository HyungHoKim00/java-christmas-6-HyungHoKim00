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
        int totalOrderPrice = order.calculateTotalPrice();
        this.discount = new Discount(date, order, totalOrderPrice);
        printInput(totalOrderPrice);
        printBenefit(totalOrderPrice);
    }

    private void printInput(int totalOrderPrice) {
        outputView.printTitle(date.toString());
        outputView.printOrderDetail(order.generateDetail());
        outputView.printTotalOrderPrice(totalOrderPrice);
    }

    private void printBenefit(int totalOrderPrice) {
        int totalDiscount = discount.calculateTotal();
        boolean wonGiftEvent = discount.contains(GIFT_EVENT);
        boolean discountExists = discount.exists();
        printGiftEvent(wonGiftEvent);
        printDiscountDetail(discountExists);
        printTotalDiscount(discountExists, totalDiscount);
        printEstimatedPrice(wonGiftEvent, totalOrderPrice, totalDiscount);
        printBadgeName(totalDiscount);
    }


    private void printGiftEvent(boolean isGiftEvent) {
        outputView.printGiftEventTitle();
        if (isGiftEvent) {
            outputView.printGiftEvent(GIFT_EVENT_GIFT.getName(), GIFT_EVENT_GIFT.getAmount());
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

    private void printTotalDiscount(boolean discountExists, int totalDiscount) {
        outputView.printTotalDiscountTitle();
        if (discountExists) {
            outputView.printTotalDiscount(totalDiscount);
        }
        if (!discountExists) {
            outputView.printTotalDiscountIsZero();
        }
    }

    private void printEstimatedPrice(boolean isGiftEvent, int totalOrderPrice, int totalDiscount) {
        if (isGiftEvent) {
            outputView.printEstimatedPrice(totalOrderPrice - totalDiscount
                    + discount.calculateDiscountAmount(GIFT_EVENT));
        }
        if (!isGiftEvent) {
            outputView.printEstimatedPrice(totalOrderPrice - totalDiscount);
        }
    }

    private void printBadgeName(int totalDiscount) {
        outputView.printEventBadgeTitle();
        outputView.printEventBadge(getBadgeName(totalDiscount));
    }

    public String getBadgeName(int totalDiscount) {
        return Badge.determineByPrice(totalDiscount).getName();
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
