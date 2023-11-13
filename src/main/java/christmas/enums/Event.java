package christmas.enums;

import static christmas.enums.Menu.CHAMPAGNE;

public enum Event {
    D_DAY_BASIC("크리스마스 디데이 할인", 1_000),
    D_DAY_BONUS("", 100),
    WEEKDAY("평일 할인", 2_023),
    WEEKEND("주말 할인", 2_023),
    SPECIAL("특별 할인", 1_000),
    GIFT("증정 이벤트", CHAMPAGNE.getPrice()),
    NONE("", 0);

    private final String eventName;
    private final int discount;

    Event(String eventName, int discount) {
        this.eventName = eventName;
        this.discount = discount;
    }

    public String getEventName() {
        return eventName;
    }

    public int getDiscount() {
        return discount;
    }
}
