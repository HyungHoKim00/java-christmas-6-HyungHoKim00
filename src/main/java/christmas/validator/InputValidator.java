package christmas.validator;

import static christmas.enums.ErrorMessage.ERROR_DATE_INVALID;
import static christmas.enums.ErrorMessage.ERROR_ORDER_INVALID;

import christmas.enums.Menu;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class InputValidator {
    private static final String POSITIVE_INTEGER_PATTERN = "^[1-9]\\d*$";

    public static int validateDate(String input) {
        if (isNotPositiveInteger(input)) {
            throw new IllegalArgumentException(ERROR_DATE_INVALID.getMessage());
        }
        return Integer.parseInt(input);
    }

    public static Map<Menu, Integer> validateOrder(String input) {
        List<String> menuNameAndAmounts = validateMenuNameAndAmounts(input);
        String divisor = "-";
        Map<Menu, Integer> order = new EnumMap<>(Menu.class);
        menuNameAndAmounts.forEach(menuNameAndAmount -> {
            String[] nameAmountPair = validateNameAmountPair(menuNameAndAmount, divisor);
            order.put(Menu.determineByName(nameAmountPair[0]), Integer.parseInt(nameAmountPair[1]));
        });
        if (duplicatedMenu(order, menuNameAndAmounts)) {
            throw new IllegalArgumentException(ERROR_ORDER_INVALID.getMessage());
        }
        return order;
    }

    private static List<String> validateMenuNameAndAmounts(String input) {
        String divisor = ",";
        if (divisorInEdge(input, divisor)) {
            throw new IllegalArgumentException(ERROR_ORDER_INVALID.getMessage());
        }
        return List.of(input.split(divisor));
    }

    private static String[] validateNameAmountPair(String menuNameAndAmount, String divisor) {
        if (divisorInEdge(menuNameAndAmount, divisor)) {
            throw new IllegalArgumentException(ERROR_ORDER_INVALID.getMessage());
        }
        String[] nameAmountPair = menuNameAndAmount.split(divisor);
        if (invalidType(nameAmountPair)) {
            throw new IllegalArgumentException(ERROR_ORDER_INVALID.getMessage());
        }
        return nameAmountPair;
    }


    private static boolean isNotPositiveInteger(String input) {
        return !input.matches(POSITIVE_INTEGER_PATTERN);
    }

    private static boolean divisorInEdge(String input, String divisor) {
        return input.startsWith(divisor) || input.endsWith(divisor);
    }

    private static boolean invalidType(String[] nameAmountPair) {
        return nameAmountPair.length != 2 || isNotPositiveInteger(nameAmountPair[1]);
    }

    private static boolean duplicatedMenu(Map<Menu, Integer> order, List<String> menuNameAndAmounts) {
        return order.size() != menuNameAndAmounts.size();
    }
}
