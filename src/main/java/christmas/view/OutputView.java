package christmas.view;

import java.text.DecimalFormat;
import java.util.Map;

public class OutputView {
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,###");

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
        orderDetail.forEach((menuName, amount) ->
                System.out.println(menuName + " " + amount + "개"));
        System.out.println();
    }

    public void printOrderPriceTotal(int orderPriceTotal) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(MONEY_FORMAT.format(orderPriceTotal) + "원");
        System.out.println();
    }

    public void printGiftEventTitle() {
        System.out.println("<증정 메뉴>");
    }

    public void printGiftEventDetail(String giftName, int giftAmount) {
        System.out.println(giftName + " " + giftAmount + "개");
        System.out.println();
    }

    public void printDiscountDetailTitle() {
        System.out.println("<혜택 내역>");
    }

    public void printDiscountDetail(Map<String, Integer> discountDetail) {
        discountDetail.forEach((eventName, amount) ->
                System.out.println(eventName + ": -" + MONEY_FORMAT.format(amount) + "원"));
        System.out.println();
    }

    public void printDiscountTotalTitle() {
        System.out.println("<총혜택 금액>");
    }

    public void printDiscountTotal(int discountTotal) {
        System.out.println("-" + MONEY_FORMAT.format(discountTotal) + "원");
        System.out.println();
    }

    public void printDiscountTotalIsZero() {
        System.out.println("0원");
        System.out.println();
    }

    public void printPriceEstimated(int priceEstimated) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(MONEY_FORMAT.format(priceEstimated) + "원");
        System.out.println();
    }

    public void printEventBadge(String badgeName) {
        System.out.println("<12월 이벤트 배지>");
        System.out.println(badgeName);
        System.out.println();
    }

    public void printNotExist() {
        System.out.println("없음");
        System.out.println();
    }
}
