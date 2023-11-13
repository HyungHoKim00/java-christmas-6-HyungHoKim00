package christmas.view;

import java.text.DecimalFormat;
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

    public void printTitle(String date) {
        System.out.printf("%s에 우테코 식당에서 받을 혜택 미리 보기!" + "%n", date);
        System.out.println();
    }

    public void printOrderDetail(Map<String, Integer> orderDetail) {
        System.out.println("<주문 메뉴>");
        orderDetail.forEach((key, value) -> System.out.println(key + " " + value + "개"));
        System.out.println();
    }

    public void printTotalOrderPrice(int totalOrderPrice) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(MONEY.format(totalOrderPrice) + "원");
        System.out.println();
    }

    public void printGiftEventTitle() {
        System.out.println("<증정 메뉴>");
    }

    public void printGiftEvent(String giftName, int giftAmount) {
        System.out.println(giftName + " " + giftAmount + "개");
        System.out.println();
    }

    public void printBenefitDetailTitle() {
        System.out.println("<혜택 내역>");
    }

    public void printBenefitDetail(Map<String, Integer> benefitDetail) {
        benefitDetail.forEach((key, value) -> {
            String discountAmount = MONEY.format(value);
            System.out.println(key + ": -" + discountAmount + "원");
        });
        System.out.println();
    }

    public void printTotalDiscountTitle() {
        System.out.println("<총혜택 금액>");
    }

    public void printTotalDiscount(int totalDiscount) {
        System.out.println("-" + MONEY.format(totalDiscount) + "원");
        System.out.println();
    }

    public void printTotalDiscountIsZero() {
        System.out.println("0원");
        System.out.println();
    }

    public void printEstimatedPrice(int estimatedPrice) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(MONEY.format(estimatedPrice) + "원");
        System.out.println();
    }

    public void printEventBadgeTitle() {
        System.out.println("<12월 이벤트 배지>");
    }

    public void printEventBadge(String badgeName) {
        System.out.println(badgeName);
        System.out.println();
    }

    public void printNotExist() {
        System.out.println("없음");
        System.out.println();
    }
}
