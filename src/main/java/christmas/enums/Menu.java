package christmas.enums;

import static christmas.enums.MenuType.APPETIZER;
import static christmas.enums.MenuType.DESSERT;
import static christmas.enums.MenuType.DRINK;
import static christmas.enums.MenuType.MAIN;

import java.util.Arrays;

public enum Menu {
    MUSHROOM_SOUP("양송이수프", APPETIZER.type(), 6_000),
    TAPAS("타파스", APPETIZER.type(), 5_500),
    CAESAR_SALAD("시저샐러드", APPETIZER.type(), 8_000),
    T_BONE_STEAK("티본스테이크", MAIN.type(), 55_000),
    BBQ_RIBS("바비큐립", MAIN.type(), 54_000),
    SEAFOOD_PASTA("해산물파스타", MAIN.type(), 35_000),
    CHRISTMAS_PASTA("크리스마스파스타", MAIN.type(), 25_000),
    CHOCOLATE_CAKE("초코케이크", DESSERT.type(), 15_000),
    ICE_CREAM("아이스크림", DESSERT.type(), 5_000),
    ZERO_COKE("제로콜라", DRINK.type(), 3_000),
    RED_WINE("레드와인", DRINK.type(), 60_000),
    CHAMPAGNE("샴페인", DRINK.type(), 25_000),
    INVALID_MENU("", "", 0);

    private final String inKorean;
    private final String type;
    private final int price;

    Menu(String inKorean, String type, int price) {
        this.inKorean = inKorean;
        this.type = type;
        this.price = price;
    }

    public String inKorean() {
        return inKorean;
    }

    public String type() {
        return type;
    }

    public int price() {
        return price;
    }

    public static Menu determineByName(String name) {
        return Arrays.stream(values())
                .filter(menu -> menu.inKorean().equals(name))
                .findFirst()
                .orElse(INVALID_MENU);
    }
}
