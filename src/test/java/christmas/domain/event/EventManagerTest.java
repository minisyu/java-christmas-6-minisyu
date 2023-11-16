package christmas.domain.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import christmas.domain.reservation.date.VisitDate;
import christmas.domain.event.discount.DiscountStorage;
import christmas.domain.reservation.menu.MenuItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class EventManagerTest {

    private EventManager eventManager;

    @DisplayName("1만원 미만 주문 시 할인 이벤트 미진행")
    @Test
    @Order(1)
    void should_Not_Apply_Discount_Event() {
        // given
        VisitDate visitDate = VisitDate.from("24");
        MenuItems menuItems = MenuItems.from("양송이수프-1,제로콜라-1");
        eventManager = new EventManager(new EventData(visitDate, menuItems));

        // when
        eventManager.applyDiscountEvent();

        // then
        DiscountStorage discountStorage = eventManager.getDiscountStorage();
        int totalDiscountPrice = discountStorage.calculateTotalDiscountPrice();

        assertThat(totalDiscountPrice).isEqualTo(0);
    }

    @DisplayName("1만원 이상 주문 시 할인 이벤트 진행")
    @Test
    @Order(2)
    void should_Apply_Discount_Event() {
        // given
        VisitDate visitDate = VisitDate.from("13");
        MenuItems menuItems = MenuItems.from("타파스-1,티본스테이크-1,초코케이크-2,제로콜라-2");
        eventManager = new EventManager(new EventData(visitDate, menuItems));

        // when
        eventManager.applyDiscountEvent();

        // then
        DiscountStorage discountStorage = eventManager.getDiscountStorage();
        int totalDiscountPrice = discountStorage.calculateTotalDiscountPrice();

        assertTrue(totalDiscountPrice > 0);
    }

    @DisplayName("12만원 이상 주문 시 증정품 지급")
    @Test
    @Order(3)
    void should_Apply_Gift_Event() {
        // given
        VisitDate visitDate = VisitDate.from("13");
        MenuItems menuItems = MenuItems.from("시저샐러드-2,바비큐립-1,아이스크림-2,샴페인-2");
        eventManager = new EventManager(new EventData(visitDate, menuItems));

        // when
        Gift receivedGift = eventManager.applyGiftEvent();

        // then
        assertTrue(menuItems.calculateMenuItemsTotalPrice() >= 120_000);
        assertThat(receivedGift).isEqualTo(Gift.CHAMPAGNE);
    }

    @DisplayName("12만원 미만 주문 시 증정품 미지급")
    @Test
    @Order(4)
    void should_Not_Apply_Gift_Event() {
        // given
        VisitDate visitDate = VisitDate.from("13");
        MenuItems menuItems = MenuItems.from("타파스-2,바비큐립-1,제로콜라-1");
        eventManager = new EventManager(new EventData(visitDate, menuItems));

        // when
        Gift receivedGift = eventManager.applyGiftEvent();

        // then
        assertTrue(menuItems.calculateMenuItemsTotalPrice() < 120_000);
        assertThat(receivedGift).isEqualTo(Gift.NONE);
    }

    @DisplayName("총 혜택 금액을 계산할 수 있는지 확인")
    @Test
    @Order(5)
    void should_Return_Total_Discount_Price() {
        // given
        VisitDate visitDate = VisitDate.from("25");
        MenuItems menuItems = MenuItems.from("양송이수프-1,제로콜라-1,티본스테이크-2,초코케이크-5");

        eventManager = new EventManager(new EventData(visitDate, menuItems));
        eventManager.applyDiscountEvent();

        // when
        int totalDiscountPrice = eventManager.sumTotalDiscountPrice();

        // then
        Gift gift = eventManager.applyGiftEvent();
        DiscountStorage discountStorage = eventManager.getDiscountStorage();
        int discountPrice = discountStorage.calculateTotalDiscountPrice();

        assertThat(totalDiscountPrice).isEqualTo(gift.getGiftPrice() + discountPrice);
    }

    @DisplayName("총 혜택 금액이 2만원 이상이면 산타 배지 부여")
    @Test
    @Order(6)
    void should_Award_Santa_Badge() {
        // given
        VisitDate visitDate = VisitDate.from("25");
        MenuItems menuItems = MenuItems.from("양송이수프-1,제로콜라-1,티본스테이크-2,초코케이크-5");

        eventManager = new EventManager(new EventData(visitDate, menuItems));
        eventManager.applyDiscountEvent();

        // when
        Badge awardedBadge = eventManager.awardBadge();

        // then
        assertThat(awardedBadge).isEqualTo(Badge.SANTA);
    }

    @DisplayName("총 혜택 금액이 1만원 이상이면 트리 배지 부여")
    @Test
    @Order(7)
    void should_Award_Tree_Badge() {
        // given
        VisitDate visitDate = VisitDate.from("10");
        MenuItems menuItems = MenuItems.from("양송이수프-1,제로콜라-1,해산물파스타-1,초코케이크-5");

        eventManager = new EventManager(new EventData(visitDate, menuItems));
        eventManager.applyDiscountEvent();

        // when
        Badge awardedBadge = eventManager.awardBadge();

        // then
        assertThat(awardedBadge).isEqualTo(Badge.TREE);
    }

    @DisplayName("총 혜택 금액이 5천원 이상이면 별 배지 부여")
    @Test
    @Order(8)
    void should_Award_Star_Badge() {
        // given
        VisitDate visitDate = VisitDate.from("8");
        MenuItems menuItems = MenuItems.from("타파스-1,해산물파스타-1,티본스테이크-1");

        eventManager = new EventManager(new EventData(visitDate, menuItems));
        eventManager.applyDiscountEvent();

        // when
        Badge awardedBadge = eventManager.awardBadge();

        // then
        assertThat(awardedBadge).isEqualTo(Badge.STAR);
    }

    @DisplayName("총 혜택 금액이 5천원 미만인 경우 배지를 부여하지 않는다")
    @Test
    @Order(9)
    void should_Not_Award_Badge() {
        // given
        VisitDate visitDate = VisitDate.from("1");
        MenuItems menuItems = MenuItems.from("바비큐립-1,샴페인-1");

        eventManager = new EventManager(new EventData(visitDate, menuItems));
        eventManager.applyDiscountEvent();

        // when
        Badge awardedBadge = eventManager.awardBadge();

        // then
        assertThat(awardedBadge).isEqualTo(Badge.NONE);
    }
}