package christmas.model;

import static christmas.enums.Discount.NONE;
import static christmas.enums.Menu.CHAMPAGNE;

import christmas.domain.UserDomain;
import christmas.enums.Menu;
import java.util.Map;

public class User {
    private final UserDomain userDomain;
    private final Map<Menu, Integer> order;
    private final int date;
    private final int totalOrderPriceBefore;

    public User(int date, Map<Menu, Integer> order) {
        this.userDomain = new UserDomain();
        this.date = date;
        this.order = order;
        this.totalOrderPriceBefore = userDomain.calculateTotalOrderPriceBefore(order);
    }

    public int getTotalOrderPriceBefore() {
        return totalOrderPriceBefore;
    }

    public int getGiftEvent() {
        if (isGiftEvent()) {
            return CHAMPAGNE.price();
        }
        return NONE.discount();
    }

    public boolean isGiftEvent() {
        return userDomain.distinctGiftEvent(totalOrderPriceBefore);
    }

    public int getDDayDiscount() {
        return userDomain.calculateDDayDiscount(date);
    }

    public int getWeekdayDiscount() {
        return userDomain.calculateWeekdayDiscount(date, order);
    }

    public int getWeekendDiscount() {
        return userDomain.calculateWeekendDiscount(date, order);
    }

    public int getSpecialDiscount() {
        return userDomain.calculateSpecialDiscount(date);
    }
}
