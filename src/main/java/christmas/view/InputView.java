package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.enums.Menu;
import christmas.validator.InputValidator;
import java.util.Map;

public class InputView {
    public int readDate() {
        String input = Console.readLine();
        return InputValidator.validateDate(input);
    }

    public Map<Menu, Integer> readOrder() {
        String input = Console.readLine();
        return InputValidator.validateOrder(input);
    }
}
