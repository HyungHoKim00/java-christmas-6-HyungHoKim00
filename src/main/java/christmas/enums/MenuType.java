package christmas.enums;

public enum MenuType {
    APPETIZER("에피타이저"),
    MAIN("메인"),
    DESSERT("디저트"),
    DRINK("음료");

    private final String type;

    MenuType(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
