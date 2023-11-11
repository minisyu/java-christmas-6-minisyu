package christmas.domain;

import java.util.List;

public record ReservationDto(
        // 주문 메뉴 및 개수
        List<MenuItemDto> menuItems,
        // 할인 전 총주문 금액
        int totalPrice,
        // 증정 메뉴
        Gift gift,
        // 혜택 내역
        List<Integer> benefit,
        // 총 혜택 금액
        int totalDiscountPrice,
        // 할인 후 예상 결제 금액
        int expectedPrice,
        // 12월 이벤트 배지
        Badge badge

) {
}
