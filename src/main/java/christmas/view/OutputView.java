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


    public void printDiscountDetails(Map<String, Integer> discountDetails) {
    }

    public void printTotalDiscount(int discountDetails) {
    }

    public void printEstimatedPrice(int i, int totalDiscount) {

    }

    public void printEventBadge(int totalDiscount) {
    }
}
