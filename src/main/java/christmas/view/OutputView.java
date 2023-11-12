package christmas.view;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final DecimalFormat MONEY = new DecimalFormat("#,###");

    public void printDateRequestMessage() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
    }

    public void printOrderRequestMessage() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
    }

    public void printTotal(int date) {
        System.out.printf("12월 %d일에 우테코 식당에서 받을 혜택 미리 보기!" + "%n", date);
        System.out.println();
    }

    public void printMenu(List<String> menuDetail) {
        System.out.println("<주문 메뉴>");
        menuDetail.forEach(System.out::println);
        System.out.println();
    }

    public void printTotalPriceBefore(int price) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(MONEY.format(price) + "원");
        System.out.println();
    }

    public void printGiftMenu(boolean giftEvent) {
        System.out.println("<증정 메뉴>");
        if (giftEvent) {
            System.out.println("샴페인 1개");
        }
        if (!giftEvent) {
            printNotExist();
        }
        System.out.println();
    }

    public void printDiscountDetails(Map<String, Integer> discountDetails) {
        System.out.println("<혜택 내역>");
        if (!discountDetails.isEmpty()) {
            discountDetails.keySet()
                    .forEach(discountName -> {
                        String discountAmount = MONEY.format(discountDetails.get(discountName));
                        System.out.println(discountName + ": -" + discountAmount + "원");
                    });
        }
        if (discountDetails.isEmpty()) {
            printNotExist();
        }
        System.out.println();
    }

    public void printTotalDiscount(int discountDetails) {
        System.out.println("<총혜택 금액>");
        System.out.println("-" + MONEY.format(discountDetails) + "원");
        System.out.println();
    }

    public void printEstimatedPrice(int estimatedPrice) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(MONEY.format(estimatedPrice) + "원");
        System.out.println();
    }

    public void printEventBadge(String badge) {
        System.out.println("<12월 이벤트 배지>");
        if (!badge.isEmpty()) {
            System.out.println(badge);
        }
        if (badge.isEmpty()) {
            printNotExist();
        }
        System.out.println();
    }

    private void printNotExist() {
        System.out.println("없음");
    }
}
