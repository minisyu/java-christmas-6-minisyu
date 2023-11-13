package christmas.domain.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import christmas.domain.Badge;
import christmas.domain.Gift;
import christmas.domain.MenuItems;
import christmas.domain.date.VisitDate;
import christmas.domain.discount.DiscountStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventCoordinatorTest {
    private EventCoordinator eventCoordinator;

    @DisplayName("만원 미만 주문 시 할인 이벤트 미진행")
    @Test
    void should_Not_Apply_Discount_Event() {
        // given
        VisitDate visitDate = VisitDate.from("24");
        MenuItems menuItems = MenuItems.from("양송이수프-1,제로콜라-1");
        eventCoordinator = new EventCoordinator(visitDate, menuItems);

        // when
        eventCoordinator.applyDiscountEvent();

        // then
        DiscountStorage discountStorage = eventCoordinator.getDiscountStorage();
        int discountPrice = discountStorage.calculateTotalDiscountPrice();

        assertEquals(0, discountPrice);
    }

    @DisplayName("만원 이상 주문 시 할인 이벤트 진행")
    @Test
    void should_Apply_Discount_Event() {
        // given
        VisitDate visitDate = VisitDate.from("13");
        MenuItems menuItems = MenuItems.from("타파스-1,티본스테이크-1,초코케이크-2,제로콜라-2");
        eventCoordinator = new EventCoordinator(visitDate, menuItems);

        // when
        eventCoordinator.applyDiscountEvent();

        // then
        DiscountStorage discountStorage = eventCoordinator.getDiscountStorage();
        int discountPrice = discountStorage.calculateTotalDiscountPrice();

        assertTrue(discountPrice > 0);
    }

    @DisplayName("12만원 이상 주문 시 증정품 지급")
    @Test
    void should_Apply_Gift_Event() {
        // given
        VisitDate visitDate = VisitDate.from("13");
        MenuItems menuItems = MenuItems.from("시저샐러드-2,바비큐립-1,아이스크림-2,샴페인-2");
        eventCoordinator = new EventCoordinator(visitDate, menuItems);

        // when
        Gift receivedGift = eventCoordinator.applyGiftEvent();

        // then
        assertTrue(menuItems.calculateMenuItemsTotalPrice() >= 120_000);
        assertEquals(Gift.CHAMPAGNE, receivedGift);
    }

    @DisplayName("12만원 미만 주문 시 증정품 미지급")
    @Test
    void should_Not_Apply_Gift_Event() {
        // given
        VisitDate visitDate = VisitDate.from("13");
        MenuItems menuItems = MenuItems.from("타파스-2,바비큐립-1,제로콜라-1");
        eventCoordinator = new EventCoordinator(visitDate, menuItems);

        // when
        Gift receivedGift = eventCoordinator.applyGiftEvent();

        // then
        assertTrue(menuItems.calculateMenuItemsTotalPrice() < 120_000);
        assertEquals(Gift.NONE, receivedGift);
    }

    @DisplayName("총 혜택 금액을 계산할 수 있는지 확인")
    @Test
    void should_Return_Total_Discount_Price() {
        // given
        VisitDate visitDate = VisitDate.from("25");
        MenuItems menuItems = MenuItems.from("양송이수프-1,제로콜라-1,티본스테이크-2,초코케이크-5");

        eventCoordinator = new EventCoordinator(visitDate, menuItems);
        eventCoordinator.applyDiscountEvent();

        // when
        int totalDiscountPrice = eventCoordinator.sumTotalDiscountPrice();

        // then
        Gift gift = eventCoordinator.applyGiftEvent();
        DiscountStorage discountStorage = eventCoordinator.getDiscountStorage();
        int discountPrice = discountStorage.calculateTotalDiscountPrice();

        assertEquals(gift.getGiftPrice() + discountPrice, totalDiscountPrice);
    }

    @DisplayName("총 혜택 금액에 따라 배지 부여")
    @Test
    void should_Award_Correct_Badge_Based_On_Total_Discount_Price() {
        // given
        VisitDate visitDate = VisitDate.from("25");
        MenuItems menuItems = MenuItems.from("양송이수프-1,제로콜라-1,티본스테이크-2,초코케이크-5");
        eventCoordinator = new EventCoordinator(visitDate, menuItems);

        eventCoordinator.applyDiscountEvent();

        // when
        Badge awardedBadge = eventCoordinator.awardBadge();

        // then
        assertEquals(Badge.SANTA, awardedBadge);
    }
}