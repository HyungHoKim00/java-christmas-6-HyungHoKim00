package christmas.enums;

import static christmas.enums.EventGift.GIFT_EVENT_GIFT;

public enum Event {
    D_DAY_EVENT("크리스마스 디데이 할인", 100),
    WEEKDAY_EVENT("평일 할인", 2_023),
    WEEKEND_EVENT("주말 할인", 2_023),
    SPECIAL_EVENT("특별 할인", 1_000),
    GIFT_EVENT("증정 이벤트", GIFT_EVENT_GIFT.getPrice());

    private final String name;
    private final int discount;

    Event(String name, int discount) {
        this.name = name;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public int getDiscount() {
        return discount;
    }
}
