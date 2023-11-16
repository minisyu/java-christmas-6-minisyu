package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.reservation.date.VisitDate;
import christmas.domain.event.discount.DiscountPolicy;
import christmas.domain.event.discount.DiscountStorage;
import christmas.domain.reservation.menu.MenuItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventDataTest {

    @DisplayName("할인 금액을 저장하는지 확인")
    @Test
    void should_Add_Discount_Price() {
        // given
        VisitDate visitDate = VisitDate.from("14");
        MenuItems menuItems = MenuItems.from("해산물파스타-2,아이스크림-2,샴페인-1");
        EventData eventData = new EventData(visitDate, menuItems);

        // when
        eventData.addDiscountPrice(DiscountPolicy.CHRISTMAS_D_DAY, 2_400);
        eventData.addDiscountPrice(DiscountPolicy.WEEKDAYS, 2_023 * 2);
        eventData.addDiscountPrice(DiscountPolicy.WEEKEND, 2_023 * 2);

        // then
        DiscountStorage discountStorage = eventData.getDiscountStorage();
        int totalDiscountPrice = discountStorage.calculateTotalDiscountPrice();

        assertThat(totalDiscountPrice).isEqualTo(10_492);
    }
}