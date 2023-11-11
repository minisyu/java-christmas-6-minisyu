package christmas.domain;

import java.util.Arrays;

public enum Badge {
    NONE("없음", 0),
    STAR("별", 5_000),
    TREE("트리", 10_000),
    SANTA("산타", 20_000);

    private final String badgeName;
    private final int threshold;

    Badge(String badgeName, int threshold) {
        this.badgeName = badgeName;
        this.threshold = threshold;
    }

    // fix

    /**
     * @param discountAggregator 총 혜택 금액
     * @return 총혜택 금액에 따라 다른 이벤트 배지를 부여
     * <p>
     * 5천 원 이상: 별
     * <p>
     * 1만 원 이상: 트리
     * <p>
     * 2만 원 이상: 산타
     * <p>
     * 이벤트 배지가 부여되지 않는 경우, "없음"
     */
    public static Badge from(int totalDiscountPrice) {
        return Arrays.stream(Badge.values())
                .filter(badge -> totalDiscountPrice >= badge.threshold)
                .findFirst()
                .orElse(NONE);
    }

    public String getBadgeName() {
        return badgeName;
    }
}
