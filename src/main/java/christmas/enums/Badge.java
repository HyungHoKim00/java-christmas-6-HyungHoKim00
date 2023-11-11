package christmas.enums;

import java.util.Arrays;

public enum Badge {
    SANTA("산타", 20_000),
    TREE("트리", 10_000),
    STAR("스타", 5_000),
    INVALID_BADGE("", 0);

    private final String inKorean;
    private final int leastCondition;

    Badge(String inKorean, int leastCondition) {
        this.inKorean = inKorean;
        this.leastCondition = leastCondition;
    }

    public String inKorean() {
        return inKorean;
    }

    public int leastCondition() {
        return leastCondition;
    }

    public static Badge determineBadge(int totalDiscountPrice) {
        return Arrays.stream(values())
                .filter(badge -> totalDiscountPrice >= badge.leastCondition())
                .findFirst()
                .orElse(INVALID_BADGE);
    }
}
