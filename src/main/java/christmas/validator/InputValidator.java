package christmas.validator;

import static christmas.enums.ErrorMessage.DATE_ERROR;
import static christmas.enums.ErrorMessage.ORDER_ERROR;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputValidator {
    private static final String POSITIVE_INTEGER_PATTERN = "^[1-9]\\d*$";

    public static void validateDate(String date) {
        if (isNotPositiveInteger(date)) {
            throw new IllegalArgumentException(DATE_ERROR.getMessage());
        }
    }

    private static boolean isNotPositiveInteger(String input) {
        return !input.matches(POSITIVE_INTEGER_PATTERN);
    }


    public static void validateMenuNameAndAmounts(List<String> menuNameAndAmounts) {
        if (invalidType(menuNameAndAmounts)) {
            throw new IllegalArgumentException(ORDER_ERROR.getMessage());
        }
        if (duplicatedName(menuNameAndAmounts)) {
            throw new IllegalArgumentException(ORDER_ERROR.getMessage());
        }
    }

    private static boolean invalidType(List<String> menuNameAndAmounts) {
        AtomicBoolean invalidType = new AtomicBoolean(false);
        menuNameAndAmounts
                .forEach(menuNameAndAmount -> {
                    String[] NameAmountPair = menuNameAndAmount.split("-");
                    if (NameAmountPair.length != 2 || isNotPositiveInteger(NameAmountPair[1])) {
                        invalidType.set(true);
                    }
                });
        return invalidType.get();
    }

    private static boolean duplicatedName(List<String> menuNameAndAmounts) {
        List<String> names = new ArrayList<>();
        menuNameAndAmounts
                .forEach(menuNameAndAmount -> names.add(menuNameAndAmount.split("-")[0]));

        return names.size() != names.stream().distinct().count();
    }
}
