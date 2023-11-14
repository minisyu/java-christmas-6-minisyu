package christmas.domain.dto;

import java.util.List;

public record ConfirmedReservation(
        // 날짜
        int visitDate,

        // 주문 메뉴 및 개수
        List<MenuItemDto> menuItems,

        // 할인 전 총주문 금액
        int totalPrice,

        // 증정 메뉴
        GiftDto gift,

        // 혜택 내역
        List<EventDto> events,

        // 총 혜택 금액
        int totalDiscountPrice,
        
        // 할인 후 예상 결제 금액
        int finalPrice,

        // 12월 이벤트 배지
        BadgeDto badge
) {
}
