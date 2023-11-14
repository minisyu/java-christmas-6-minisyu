package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.MenuItem;
import christmas.domain.MenuItems;
import christmas.domain.date.VisitDate;
import christmas.domain.event.EventData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountPolicyTest {

    @DisplayName("크리스마스 디데이 할인 적용 여부 확인")
    @Test
    void should_Apply_Christmas_Discount() {
        // given
        VisitDate visitDate = VisitDate.from("17");
        MenuItems menuItems = MenuItems.from("타파스-1,티본스테이크-1,초코케이크-2,제로콜라-2");
        EventData eventData = new EventData(visitDate, menuItems);
        // when
        DiscountPolicy.CHRISTMAS_D_DAY.applyDiscountPolicy(eventData);

        // then
        DiscountStorage discountStorage = eventData.getDiscountStorage();
        int totalDiscountPrice = discountStorage.calculateTotalDiscountPrice();
        int expectedDiscountPrice = visitDate.calculateChristmasDiscount();

        assertThat(totalDiscountPrice).isEqualTo(expectedDiscountPrice);
    }

    @DisplayName("평일 디저트 할인 적용 여부 확인")
    @Test
    void should_Apply_Weekday_Dessert_Discount() {
        // when
        VisitDate visitDate = VisitDate.from("13");
        MenuItems menuItems = MenuItems.from("시저샐러드-1,바비큐립-1,아이스크림-5,제로콜라-2");
        EventData eventData = new EventData(visitDate, menuItems);

        // when
        DiscountPolicy.WEEKDAYS.applyDiscountPolicy(eventData);

        // then
        DiscountStorage discountStorage = eventData.getDiscountStorage();
        int totalDiscountPrice = discountStorage.calculateTotalDiscountPrice();

        assertThat(totalDiscountPrice).isEqualTo(MenuItem.WEEK_DISCOUNT_PRICE * 5);
    }

    @DisplayName("주말 메인 메뉴 할인 적용 여부 확인")
    @Test
    void should_Apply_Weekend_MainDish_Discount() {
        // given
        VisitDate visitDate = VisitDate.from("15");
        MenuItems menuItems = MenuItems.from("시저샐러드-1,바비큐립-1,제로콜라-2");
        EventData eventData = new EventData(visitDate, menuItems);

        // when
        DiscountPolicy.WEEKEND.applyDiscountPolicy(eventData);

        // then
        DiscountStorage discountStorage = eventData.getDiscountStorage();
        int totalDiscountPrice = discountStorage.calculateTotalDiscountPrice();

        assertThat(totalDiscountPrice).isEqualTo(MenuItem.WEEK_DISCOUNT_PRICE);
    }

    @DisplayName("특별 할인 적용 여부 확인")
    @Test
    void should_Apply_Special_Discount() {
        // given
        VisitDate visitDate = VisitDate.from("31");
        MenuItems menuItems = MenuItems.from("양송이수프-1,바비큐립-1,초코케이크-1,레드와인-1");
        EventData eventData = new EventData(visitDate, menuItems);

        // when
        DiscountPolicy.SPECIAL.applyDiscountPolicy(eventData);

        // then
        DiscountStorage discountStorage = eventData.getDiscountStorage();
        int totalDiscountPrice = discountStorage.calculateTotalDiscountPrice();

        assertThat(totalDiscountPrice).isEqualTo(DiscountPolicy.SPECIAL_DISCOUNT_PRICE);
    }
}