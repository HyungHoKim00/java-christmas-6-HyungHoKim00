package christmas.controller;

import static christmas.enums.Discount.NONE;

import christmas.enums.Badge;
import christmas.enums.Menu;
import christmas.model.User;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.HashMap;
import java.util.Map;

public class EventPlanner {
    private static final int LEAST_ORDER_PRICE_FOR_DISCOUNT = 10_000;
    private final InputView inputview;
    private final OutputView outputView;
    private User user;

    public EventPlanner(InputView inputView, OutputView outputView) {
        this.inputview = inputView;
        this.outputView = outputView;
    }

    public void run() {
        int date = inputview.readDate();
        Map<Menu, Integer> order = inputview.readOrder();
        user = new User(date, order);
        outputView.printTotal(date);
        outputView.printMenu(order);
        int totalOrderPriceBefore = user.getTotalOrderPriceBefore();
        outputView.printTotalPriceBefore(totalOrderPriceBefore);
        printBenefit(totalOrderPriceBefore);
    }

    private void printBenefit(int totalOrderPriceBefore) {
        outputView.printGiftMenu(user.isGiftEvent());
        Map<String, Integer> discountDetails = getDiscountDetails(user, totalOrderPriceBefore);
        outputView.printDiscountDetails(discountDetails);
        int totalDiscount = getTotalDiscount(discountDetails);
        outputView.printTotalDiscount(totalDiscount);
        outputView.printEstimatedPrice(totalOrderPriceBefore - totalDiscount);
        String badge = getBadgeName(totalDiscount);
        outputView.printEventBadge(badge);
    }

    public Map<String, Integer> getDiscountDetails(User user, int totalOrderPriceBefore) {
        Map<String, Integer> discountDetails = new HashMap<>();
        if (totalOrderPriceBefore >= LEAST_ORDER_PRICE_FOR_DISCOUNT) {
            discountDetails.put("크리스마스 디데이 할인", user.getDDayDiscount());
            discountDetails.put("평일 할인", user.getWeekdayDiscount());
            discountDetails.put("주말 할인", user.getWeekendDiscount());
            discountDetails.put("특별 할인", user.getSpecialDiscount());
            discountDetails.put("증정 이벤트", user.getGiftEvent());
            discountDetails.entrySet().removeIf(entry -> entry.getValue() == NONE.discount());
        }
        return discountDetails;
    }

    public int getTotalDiscount(Map<String, Integer> discountDetails) {
        return discountDetails.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public String getBadgeName(int totalDiscount) {
        return Badge.determineBadge(totalDiscount).inKorean();
    }
}
