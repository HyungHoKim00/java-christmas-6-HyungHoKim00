package christmas.enums;

import static christmas.enums.MenuType.APPETIZER;
import static christmas.enums.MenuType.DESSERT;
import static christmas.enums.MenuType.DRINK;
import static christmas.enums.MenuType.INVALID_TYPE;
import static christmas.enums.MenuType.MAIN;

import java.util.Arrays;

public enum Menu {
    MUSHROOM_SOUP("양송이수프", APPETIZER, 6_000),
    TAPAS("타파스", APPETIZER, 5_500),
    CAESAR_SALAD("시저샐러드", APPETIZER, 8_000),
    T_BONE_STEAK("티본스테이크", MAIN, 55_000),
    BBQ_RIBS("바비큐립", MAIN, 54_000),
    SEAFOOD_PASTA("해산물파스타", MAIN, 35_000),
    CHRISTMAS_PASTA("크리스마스파스타", MAIN, 25_000),
    CHOCOLATE_CAKE("초코케이크", DESSERT, 15_000),
    ICE_CREAM("아이스크림", DESSERT, 5_000),
    ZERO_COKE("제로콜라", DRINK, 3_000),
    RED_WINE("레드와인", DRINK, 60_000),
    CHAMPAGNE("샴페인", DRINK, 25_000),
    INVALID_MENU("", INVALID_TYPE, 0);

    private final String name;
    private final MenuType type;
    private final int price;

    Menu(String name, MenuType type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public static Menu determineByName(String name) {
        return Arrays.stream(values())
                .filter(menu -> menu.name.equals(name))
                .findFirst()
                .orElse(INVALID_MENU);
    }

    public boolean isInvalid() {
        return this.equals(INVALID_MENU);
    }

    public boolean compareType(MenuType type) {
        return this.type.equals(type);
    }
}
