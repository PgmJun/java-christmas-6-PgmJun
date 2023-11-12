package christmas.domain;

import java.util.Arrays;

public enum Badge {

    NONE("없음", 0, 4999),
    STAR("별", 5000, 9999),
    TREE("트리", 10000, 19999),
    SANTA("산타", 20000, Integer.MAX_VALUE),
    ;

    private final String name;
    private final int minMatchingCondition;
    private final int maxMatchingCondition;

    Badge(String name, int minMatchingCondition, int maxMatchingCondition) {
        this.name = name;
        this.minMatchingCondition = minMatchingCondition;
        this.maxMatchingCondition = maxMatchingCondition;
    }

    public static Badge issue(int totalBenefitPrice) {
        return Arrays.stream(values())
                .filter(badge -> totalBenefitPrice >= badge.minMatchingCondition)
                .filter(badge -> totalBenefitPrice <= badge.maxMatchingCondition)
                .findFirst()
                .get();
    }

    public String getName() {
        return name;
    }
}
