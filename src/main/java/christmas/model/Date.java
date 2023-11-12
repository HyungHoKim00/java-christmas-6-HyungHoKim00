package christmas.model;

import java.util.List;

public class Date {
    private static final String ERROR_MESSAGE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private static final int LOWEST = 1;
    private static final int HIGHEST = 31;
    private final static int CHRISTMAS = 25;
    private static final List<Integer> WEEKEND = List.of(1, 2, 8, 9, 15, 16, 22, 23, 29, 30);
    private static final List<Integer> STAR = List.of(3, 10, 17, 24, 25, 31);
    private int date;

    public Date(int date) {
        validate(date);
        this.date = date;
    }

    public int get() {
        return date;
    }

    public boolean isBeforeChristmas() {
        return date <= CHRISTMAS;
    }

    public boolean isWeekday() {
        return !WEEKEND.contains(date);
    }

    public boolean isWeekend() {
        return WEEKEND.contains(date);
    }

    public boolean isSpecialDay() {
        return STAR.contains(date);
    }


    private void validate(int date) {
        if (outOfDateRange(date)) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    private static boolean outOfDateRange(int date) {
        return date < LOWEST || date > HIGHEST;
    }
}
