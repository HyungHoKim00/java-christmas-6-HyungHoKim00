package christmas.enums;

import static christmas.enums.Menu.CHAMPAGNE;

public enum EventGift {
    GIFT_EVENT_GIFT(CHAMPAGNE, 1, 120_000);

    private final Menu menu;
    private final int amount;
    private final int leastCondition;

    EventGift(Menu menu, int amount, int leastCondition) {
        this.menu = menu;
        this.amount = amount;
        this.leastCondition = leastCondition;
    }

    public String getName() {
        return menu.getName();
    }

    public int getAmount() {
        return amount;
    }

    public int getLeastCondition() {
        return leastCondition;
    }

    public int getPrice() {
        return menu.getPrice() * amount;
    }
}
