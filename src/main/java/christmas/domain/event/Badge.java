package christmas.domain.event;

import christmas.domain.dto.BadgeDto;
import java.util.Arrays;
import java.util.Comparator;

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

    public static Badge from(int totalDiscountPrice) {
        return Arrays.stream(Badge.values())
                .filter(badge -> badge.threshold <= totalDiscountPrice)
                .max(Comparator.naturalOrder())
                .orElse(NONE);
    }

    public BadgeDto toBadgeDto() {
        return new BadgeDto(badgeName);
    }
}
