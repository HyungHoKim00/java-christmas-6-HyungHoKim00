package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.enums.Menu;
import christmas.validator.InputValidator;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class InputView {
    public int readDate() {
        String input = Console.readLine();
        InputValidator.validateDate(input);
        return Integer.parseInt(input);
    }

    public Map<Menu, Integer> readOrder() {
        String input = Console.readLine();
        InputValidator.validateOrderSentence(input);
        List<String> menuNameAndAmounts = new ArrayList<>(List.of(input.split(",")));
        InputValidator.validateMenuNameAndAmounts(menuNameAndAmounts);
        Map<Menu, Integer> order = new EnumMap<>(Menu.class);
        menuNameAndAmounts
                .forEach(menuNameAndAmount -> {
                    String[] menuAndAmount = menuNameAndAmount.split("-");
                    Menu menu = Menu.determineByName(menuAndAmount[0]);
                    int amount = Integer.parseInt(menuAndAmount[1]);
                    order.put(menu, amount);
                });
        return order;
    }
}
