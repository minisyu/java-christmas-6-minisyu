package christmas.domain.dto;

import christmas.domain.Badge;
import christmas.domain.Gift;
import christmas.domain.MenuItemDto;
import christmas.domain.discount.DiscountType;
import java.util.EnumMap;
import java.util.List;

public record ReservationDto(
        // fix. 날짜
        //  아니 getter로 가져올꺼면 그냥 Order(Reservation)에서 해야하나ㅠㅠ
        int date,

        // 주문 메뉴 및 개수
        List<MenuItemDto> menuItems,

        // 할인 전 총주문 금액
        int totalPrice,

        // 증정 메뉴
        Gift gift,

        // 혜택 내역
        //List<Integer> benefit,
        //DiscountStorage discountStorage,
        EnumMap<DiscountType, Integer> discountMap,

        // 총 혜택 금액
        int totalDiscountPrice,

        // 할인 후 예상 결제 금액
        int expectedPrice,

        // 12월 이벤트 배지
        Badge badge,

        List<EventDto> events
) {
}
