package christmas.model;

import static christmas.enums.ErrorMessage.DATE_ERROR;

import java.util.List;

public class Date {
    private static final List<Integer> WEEKEND = List.of(1, 2, 8, 9, 15, 16, 22, 23, 29, 30);
    private static final List<Integer> STAR = List.of(3, 10, 17, 24, 25, 31);
    private static final int D_DAY_MULTIPLICAND_DEFAULT = 9;
    private static final int LOWEST = 1;
    private static final int HIGHEST = 31;
    private static final int CHRISTMAS = 25;
    private final int date;

    public Date(int date) {
        this.date = validate(date);
    }

    public String toString() {
        return String.format("12월 %d일", date);
    }

    public int calculateDDayMultiplicand() {
        return date + D_DAY_MULTIPLICAND_DEFAULT;
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


    private int validate(int date) {
        if (outOfRange(date)) {
            throw new IllegalArgumentException(DATE_ERROR.getMessage());
        }
        return date;
    }

    private static boolean outOfRange(int date) {
        return date < LOWEST || date > HIGHEST;
    }
}
