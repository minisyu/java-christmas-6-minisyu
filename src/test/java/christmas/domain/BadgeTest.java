package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(badgeForLowDiscount).isEqualTo(Badge.NONE);
        assertThat(badgeForModerateDiscount).isEqualTo(Badge.STAR);
        assertThat(badgeForHighDiscount).isEqualTo(Badge.TREE);
        assertThat(badgeForHighestDiscount).isEqualTo(Badge.SANTA);
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
        assertThat(noneBadgeDto.badgeName()).isEqualTo("없음");
        assertThat(starBadgeDto.badgeName()).isEqualTo("별");
        assertThat(treeBadgeDto.badgeName()).isEqualTo("트리");
        assertThat(santaBadgeDto.badgeName()).isEqualTo("산타");
    }
}