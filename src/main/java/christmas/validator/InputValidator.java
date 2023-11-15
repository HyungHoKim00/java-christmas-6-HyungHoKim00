package christmas.validator;

import static christmas.enums.ErrorMessage.ERROR_DATE_INVALID;
import static christmas.enums.ErrorMessage.ERROR_ORDER_INVALID;

import christmas.enums.Menu;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputValidator {
    private static final String POSITIVE_INTEGER_PATTERN = "^[1-9]\\d*$";

    public static int validateDate(String date) {
        if (isNotPositiveInteger(date)) {
            throw new IllegalArgumentException(ERROR_DATE_INVALID.getMessage());
        }
        return Integer.parseInt(date);
    }

    private static boolean isNotPositiveInteger(String input) {
        return !input.matches(POSITIVE_INTEGER_PATTERN);
    }


    public static List<String> validateOrderSentence(String input) {
        if (commaDoubledOrInEdge(input)) {
            throw new IllegalArgumentException(ERROR_ORDER_INVALID.getMessage());
        }
        return List.of(input.split(","));
    }

    private static boolean commaDoubledOrInEdge(String input) {
        return input.contains(",,") || input.startsWith(",") || input.endsWith(",");
    }


    public static Map<Menu, Integer> validateMenuNameAndAmounts(List<String> menuNameAndAmounts) {
        if (invalidType(menuNameAndAmounts)) {
            throw new IllegalArgumentException(ERROR_ORDER_INVALID.getMessage());
        }
        if (duplicatedName(menuNameAndAmounts)) {
            throw new IllegalArgumentException(ERROR_ORDER_INVALID.getMessage());
        }
        Map<Menu, Integer> order = new EnumMap<>(Menu.class);
        menuNameAndAmounts.forEach(menuNameAndAmount -> {
            String[] menuAndAmount = menuNameAndAmount.split("-");
            Menu menu = Menu.determineByName(menuAndAmount[0]);
            int amount = Integer.parseInt(menuAndAmount[1]);
            order.put(menu, amount);
        });
        return order;
    }

    private static boolean invalidType(List<String> menuNameAndAmounts) {
        AtomicBoolean invalidType = new AtomicBoolean(false);
        menuNameAndAmounts.forEach(menuNameAndAmount -> {
            String[] NameAmountPair = menuNameAndAmount.split("-");
            if (NameAmountPair.length != 2 || isNotPositiveInteger(NameAmountPair[1])) {
                invalidType.set(true);
            }
        });
        return invalidType.get();
    }

    private static boolean duplicatedName(List<String> menuNameAndAmounts) {
        List<String> names = new ArrayList<>();
        menuNameAndAmounts.forEach(menuNameAndAmount ->
                names.add(menuNameAndAmount.split("-")[0]));
        return names.size() != names.stream().distinct().count();
    }
}
