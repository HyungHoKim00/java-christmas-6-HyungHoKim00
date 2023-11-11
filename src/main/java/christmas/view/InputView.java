package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.enums.Menu;
import christmas.validator.UserValidator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class InputView {
    public int readDate() {
        printDateRequestMessage();
        String date = inputDate();
        return Integer.parseInt(date);
    }

    private void printDateRequestMessage() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
    }

    private String inputDate() {
        String input = Console.readLine();
        try {
            UserValidator.validateDate(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            readDate();
        }
        return input;
    }

    public Map<Menu, Integer> readOrder() {
        printOrderRequestMessage();
        List<String> menuNameAndAmounts = inputMenuNameAndAmounts();
        Map<Menu, Integer> order = new EnumMap<>(Menu.class);
        menuNameAndAmounts
                .forEach(menuAndAmount -> {
                    String[] keyAndValue = menuAndAmount.split("-");
                    Menu key = Menu.determineByName(keyAndValue[0]);
                    int value = Integer.parseInt(keyAndValue[1]);
                    order.put(key, value);
                });
        return order;
    }

    private void printOrderRequestMessage() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
    }

    private List<String> inputMenuNameAndAmounts() {
        String input = Console.readLine();
        List<String> menuNameAndAmounts = List.of(input.split(","));
        try {
            UserValidator.validateMenuNameAndAmounts(menuNameAndAmounts);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            inputMenuNameAndAmounts();
        }
        return menuNameAndAmounts;
    }
}
