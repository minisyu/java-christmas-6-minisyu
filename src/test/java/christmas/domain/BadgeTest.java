package christmas.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.domain.dto.BadgeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BadgeTest {

    @DisplayName("총혜택 금액에 따라 다른 이벤트 배지를 부여하는지 확인")
    @Test
    void should_Assign_Badge_Based_On_Total_Discount_Price() {
        // given
        int lowDiscount = 4_000;
        int moderateDiscount = 8_000;
        int highDiscount = 15_000;
        int highestDiscount = 25_000;

        // when
        Badge badgeForLowDiscount = Badge.from(lowDiscount);
        Badge badgeForModerateDiscount = Badge.from(moderateDiscount);
        Badge badgeForHighDiscount = Badge.from(highDiscount);
        Badge badgeForHighestDiscount = Badge.from(highestDiscount);

        // then
        assertEquals(Badge.NONE, badgeForLowDiscount);
        assertEquals(Badge.STAR, badgeForModerateDiscount);
        assertEquals(Badge.TREE, badgeForHighDiscount);
        assertEquals(Badge.SANTA, badgeForHighestDiscount);
    }

    @DisplayName("BadgeDto를 생성하는지 확인")
    @Test
    void should_Create_BadgeDto() {
        // given
        Badge none = Badge.NONE;
        Badge star = Badge.STAR;
        Badge tree = Badge.TREE;
        Badge santa = Badge.SANTA;

        // when
        BadgeDto noneBadgeDto = none.toBadgeDto();
        BadgeDto starBadgeDto = star.toBadgeDto();
        BadgeDto treeBadgeDto = tree.toBadgeDto();
        BadgeDto santaBadgeDto = santa.toBadgeDto();

        // then
        assertEquals("없음", noneBadgeDto.badgeName());
        assertEquals("별", starBadgeDto.badgeName());
        assertEquals("트리", treeBadgeDto.badgeName());
        assertEquals("산타", santaBadgeDto.badgeName());
    }
}