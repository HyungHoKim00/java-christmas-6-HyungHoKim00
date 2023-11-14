package christmas.model;

import static christmas.enums.ErrorMessage.ERROR_DATE_INVALID;

import java.util.List;

public class Date {
    private static final List<Integer> WEEKENDS = List.of(1, 2, 8, 9, 15, 16, 22, 23, 29, 30);
    private static final List<Integer> STAR_DAYS = List.of(3, 10, 17, 24, 25, 31);
    private static final int D_DAY_EVENT_MULTIPLICAND_DEFAULT = 9;
    private static final int MIN = 1;
    private static final int MAX = 31;
    private static final int CHRISTMAS = 25;
    private final int date;

    public Date(int date) {
        this.date = validate(date);
    }

    public int calculateDDayEventMultiplicand() {
        return date + D_DAY_EVENT_MULTIPLICAND_DEFAULT;
    }

    public boolean isChristmasOrBefore() {
        return date <= CHRISTMAS;
    }

    public boolean isWeekday() {
        return !WEEKENDS.contains(date);
    }

    public boolean isWeekend() {
        return WEEKENDS.contains(date);
    }

    public boolean isSpecialDay() {
        return STAR_DAYS.contains(date);
    }

    public String toString() {
        return String.format("12월 %d일", date);
    }


    private int validate(int date) {
        if (outOfRange(date)) {
            throw new IllegalArgumentException(ERROR_DATE_INVALID.getMessage());
        }
        return date;
    }

    private static boolean outOfRange(int date) {
        return date < MIN || date > MAX;
    }
}
