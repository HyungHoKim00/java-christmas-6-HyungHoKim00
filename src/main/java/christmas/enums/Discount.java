package christmas.enums;

import static christmas.enums.Menu.CHAMPAGNE;

public enum Discount {
    NONE(0),
    D_DAY_BASIC(1_000),
    D_DAY_BONUS(100),
    WEEKDAY_AND_WEEKEND(2_023),
    SPECIAL(1_000),
    GIFT_EVENT(CHAMPAGNE.price());

    private final int discount;

    Discount(int discount) {
        this.discount = discount;
    }

    public int discount() {
        return discount;
    }
}
