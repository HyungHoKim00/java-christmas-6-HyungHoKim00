package christmas.enums;

import java.util.Arrays;

public enum Badge {
    SANTA("산타", 20_000),
    TREE("트리", 10_000),
    STAR("별", 5_000),
    NOT_EXIST("없음", 0);

    private final String name;
    private final int leastCondition;

    Badge(String name, int leastCondition) {
        this.name = name;
        this.leastCondition = leastCondition;
    }

    public String getName() {
        return name;
    }

    public static Badge determineByPrice(int discountPriceTotal) {
        return Arrays.stream(values())
                .filter(badge -> discountPriceTotal >= badge.leastCondition)
                .findFirst()
                .orElse(NOT_EXIST);
    }
}
