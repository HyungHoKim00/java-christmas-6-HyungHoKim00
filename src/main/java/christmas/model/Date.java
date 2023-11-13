package christmas.model;

import static christmas.enums.ErrorMessage.DATE_ERROR;
import static christmas.enums.Event.D_DAY_BASIC;
import static christmas.enums.Event.D_DAY_BONUS;

import java.util.List;

public class Date {
    private static final int LOWEST = 1;
    private static final int HIGHEST = 31;
    private static final int CHRISTMAS = 25;
    private static final List<Integer> WEEKEND = List.of(1, 2, 8, 9, 15, 16, 22, 23, 29, 30);
    private static final List<Integer> STAR = List.of(3, 10, 17, 24, 25, 31);
    private final int date;

    public Date(int date) {
        validate(date);
        this.date = date;
    }

    public String toString() {
        return String.format("12월 %d일", date);
    }

    public int getDDayDiscount() {
        return D_DAY_BASIC.getDiscount() + (date - 1) * D_DAY_BONUS.getDiscount();
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
            throw new IllegalArgumentException(DATE_ERROR.getMessage());
        }
    }

    private static boolean outOfDateRange(int date) {
        return date < LOWEST || date > HIGHEST;
    }
}
