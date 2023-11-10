package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.enums.Menu;
import christmas.validator.UserValidator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class InputView {
    public static int readDate() {
        printDateRequestMessage();
        String date = inputDate();
        return Integer.parseInt(date);
    }

    private static void printDateRequestMessage() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
    }

    private static String inputDate() {
        String input = Console.readLine();
        try {
            UserValidator.validateDate(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            readDate();
        }
        return input;
    }

    public static Map<Menu, Integer> readOrder() {
        printOrderRequestMessage();
        List<String> menuAndAmounts = inputMenuAndAmounts();
        Map<Menu, Integer> order = new EnumMap<>(Menu.class);
        menuAndAmounts
                .forEach(menuAndAmount -> {
                    String[] keyAndValue = menuAndAmount.split("-");
                    Menu key = Menu.determineByName(keyAndValue[0]);
                    int value = Integer.parseInt(keyAndValue[1]);
                    order.put(key, value);
                });
        return order;
    }

    private static void printOrderRequestMessage() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
    }

    private static List<String> inputMenuAndAmounts() {
        String input = Console.readLine();
        List<String> menuAndAmounts = List.of(input.split(","));
        try {
            UserValidator.validateMenuAndAmounts(menuAndAmounts);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            inputMenuAndAmounts();
        }
        return menuAndAmounts;
    }
}
