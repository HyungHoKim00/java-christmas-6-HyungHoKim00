package christmas.validator;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputValidator {
    private static final String DATE_ERROR_MESSAGE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private static final String ORDER_ERROR_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String POSITIVE_INTEGER_PATTERN = "^\\d+$";


    public static void validateDate(String date) {
        if (isNotPositiveInteger(date)) {
            throw new IllegalArgumentException(DATE_ERROR_MESSAGE);
        }
    }

    private static boolean isNotPositiveInteger(String input) {
        return !input.matches(POSITIVE_INTEGER_PATTERN);
    }


    public static void validateMenuNameAndAmounts(List<String> menuNameAndAmounts) {
        if (invalidType(menuNameAndAmounts)) {
            throw new IllegalArgumentException(ORDER_ERROR_MESSAGE);
        }
    }

    private static boolean invalidType(List<String> menuAndAmounts) {
        AtomicBoolean invalidType = new AtomicBoolean(false);
        menuAndAmounts
                .forEach(menuAndAmount -> {
                    String[] NameAmountPair = menuAndAmount.split("-");
                    if (NameAmountPair.length != 2 || isNotPositiveInteger(NameAmountPair[1])) {
                        invalidType.set(true);
                    }
                });
        return invalidType.get();
    }
}