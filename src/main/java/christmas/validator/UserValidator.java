package christmas.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserValidator {
    private static final String DATE_ERROR_MESSAGE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private static final String ORDER_ERROR_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String POSITIVE_INTEGER = "^[1-9]\\d*$";
    private static final int LOWEST = 1;
    private static final int HIGHEST_DATE = 31;
    private static final int MAX_MENU_AMOUNT = 20;

    public static void validateDate(String date) {
        if (isNotPositiveInteger(date)) {
            throw new IllegalArgumentException(DATE_ERROR_MESSAGE);
        }
        int dateNumber = Integer.parseInt(date);
        if (!inRange(dateNumber)) {
            throw new IllegalArgumentException(DATE_ERROR_MESSAGE);
        }
    }

    public static void validateMenuNameAndAmounts(List<String> menuNameAndAmounts) {
        if (invalidType(menuNameAndAmounts)) {
            throw new IllegalArgumentException(ORDER_ERROR_MESSAGE);
        }
        List<String> names = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        menuNameAndAmounts
                .forEach(menuAndAmount -> {
                    String[] NameAmountPair = menuAndAmount.split("-");
                    names.add(NameAmountPair[0]);
                    amounts.add(Integer.parseInt(NameAmountPair[1]));
                });
        validateMenuNames(names);
        validateMenuAmounts(amounts);
    }

    private static void validateMenuNames(List<String> names) {

    }

    private static void validateMenuAmounts(List<Integer> amounts) {

    }

    private static boolean isNotPositiveInteger(String input) {
        return !input.matches(POSITIVE_INTEGER);
    }

    private static boolean inRange(int dateNumber) {
        return dateNumber >= LOWEST && dateNumber <= HIGHEST_DATE;
    }

    private static boolean invalidType(List<String> menuAndAmounts) {
        AtomicBoolean invalidType = new AtomicBoolean(true);
        menuAndAmounts
                .forEach(menuAndAmount -> {
                    String[] NameAmountPair = menuAndAmount.split("-");
                    if (NameAmountPair.length != 2 && isNotPositiveInteger(NameAmountPair[1])) {
                        invalidType.set(false);
                    }
                });
        return invalidType.get();
    }
}
