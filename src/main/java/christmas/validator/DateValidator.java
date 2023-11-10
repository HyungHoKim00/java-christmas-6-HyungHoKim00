package christmas.validator;

public class DateValidator {
    private static final String ERROR_MESSAGE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private static final String POSITIVE_INTEGER = "^[1-9]\\d*$";
    private static final int LOWEST = 1;
    private static final int HIGHEST = 31;

    public static void validateDate(String date) {
        if (!isPositiveInteger(date)) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        int dateNumber = Integer.parseInt(date);
        if (!inRange(dateNumber)) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    private static boolean isPositiveInteger(String date) {
        return date.matches(POSITIVE_INTEGER);
    }

    private static boolean inRange(int dateNumber) {
        return dateNumber >= LOWEST && dateNumber <= HIGHEST;
    }
}
