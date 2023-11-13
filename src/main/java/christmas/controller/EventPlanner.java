package christmas.controller;

import static christmas.enums.Event.GIFT_EVENT;
import static christmas.enums.Menu.CHAMPAGNE;

import christmas.enums.Badge;
import christmas.enums.Menu;
import christmas.model.Date;
import christmas.model.Discount;
import christmas.model.Order;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.Map;

public class EventPlanner {
    private static final String GIFT_NAME = CHAMPAGNE.getName();
    private static final int GIFT_AMOUNT = 1;
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
        outputView.printTotal(date.toString());
        outputView.printOrderDetail(order.generateDetail());
        int totalOrderPriceBefore = order.calculateTotalPriceBefore();
        outputView.printTotalPriceBefore(totalOrderPriceBefore);
        printBenefit(totalOrderPriceBefore);
    }

    private void printBenefit(int totalOrderPriceBefore) {
        this.discount = new Discount(date, order, totalOrderPriceBefore);
        int totalDiscount = discount.calculateTotal();
        boolean isGiftEvent = discount.isGiftEvent();
        boolean discountExists = discount.exists();
        printGiftEvent(isGiftEvent);
        printBenefitDetail(discountExists);
        printTotalDiscount(discountExists, totalDiscount);
        printEstimatedPrice(isGiftEvent, totalOrderPriceBefore, totalDiscount);
        printBadgeName(totalDiscount);
    }


    private void printGiftEvent(boolean isGiftEvent) {
        outputView.printGiftEventTitle();
        if (isGiftEvent) {
            outputView.printGiftEvent(GIFT_NAME, GIFT_AMOUNT);
        }
        if (!isGiftEvent) {
            outputView.printNotExist();
        }
    }

    private void printBenefitDetail(boolean discountExists) {
        outputView.printBenefitDetailTitle();
        if (discountExists) {
            outputView.printBenefitDetail(discount.generateDetail());
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

    private void printEstimatedPrice(boolean isGiftEvent, int totalOrderPriceBefore, int totalDiscount) {
        if (isGiftEvent) {
            outputView.printEstimatedPrice(totalOrderPriceBefore - totalDiscount + GIFT_EVENT.getDiscount());
        }
        if (!isGiftEvent) {
            outputView.printEstimatedPrice(totalOrderPriceBefore - totalDiscount);
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
