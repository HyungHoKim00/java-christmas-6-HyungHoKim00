package christmas.view;

import christmas.enums.Menu;
import java.util.Map;

public class OutputView {
    public void printTotal(int date) {
        System.out.printf("12월 %d일에 우테코 식당에서 받을 혜택 미리 보기!" + "%n", date);
        System.out.println();
    }

    public void printMenu(Map<Menu, Integer> order) {
        System.out.println("<주문 메뉴>");
        order.keySet()
                .forEach(menu -> {
                    String menuName = menu.inKorean();
                    String menuAmount = order.get(menu).toString();
                    System.out.println(menuName + " " + menuAmount + "개");
                });
    }

    public void printTotalPriceBefore(int price) {
        System.out.println("<할인 전 총 주문 금액>");
        System.out.println(price + "원");
    }

    public void printGiftMenu(boolean giftEvent) {
        System.out.println("<증정 메뉴>");
        if (giftEvent) {
            System.out.println("샴페인 1개");
        }
        if (!giftEvent) {
            printNotExist();
        }
    }

    public void printDiscountDetails(Map<String, Integer> discountDetails) {
        if (!discountDetails.isEmpty()) {
            discountDetails.keySet()
                    .forEach(discountName -> {
                        String discountAmount = discountDetails.get(discountName).toString();
                        System.out.println(discountName + ": -" + discountAmount + "원");
                    });
        }
        if (discountDetails.isEmpty()) {
            printNotExist();
        }
    }

    public void printTotalDiscount(int discountDetails) {
        System.out.println("<총 혜택 금액>");
        System.out.println("-" + discountDetails + "원");
    }

    public void printEstimatedPrice(int estimatedPrice) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(estimatedPrice + "원");
    }

    public void printEventBadge(int totalDiscount) {

    }

    private void printNotExist() {
        System.out.println("없음");
    }
}
