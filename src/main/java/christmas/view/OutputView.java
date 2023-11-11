package christmas.view;

import christmas.domain.Badge;
import christmas.domain.Gift;
import christmas.domain.MenuItemDto;
import christmas.domain.ReservationDto;
import java.util.List;

public class OutputView {
    public void printEvent(ReservationDto reservationDto) {
        System.out.println("<주문 메뉴>");
        List<MenuItemDto> menuItemDtos = reservationDto.menuItems();
        for (MenuItemDto menuItemDto : menuItemDtos) {
            System.out.println(menuItemDto.itemName() + " " + menuItemDto.quantity() + "개");
        }

        System.out.println("<할인 전 총주문 금액>");
        int totalPrice = reservationDto.totalPrice();
        System.out.println(totalPrice + "원");

        System.out.println("<증정 메뉴>");
        Gift gift = reservationDto.gift();
        System.out.println(gift.getGiftName());

        System.out.println("<혜택 내역>");
        List<Integer> benefit = reservationDto.benefit();
        System.out.println("크리스마스 디데이 할인: -" + benefit.get(0));
        System.out.println("평일 할인: -" + benefit.get(1));
        System.out.println("특별 할인: -" + benefit.get(2));
        System.out.println("증정 이벤트: -" + benefit.get(3));

        System.out.println("<총혜택 금액>");
        int totalDiscountPrice = reservationDto.totalDiscountPrice();
        System.out.println("-" + totalDiscountPrice);

        System.out.println("<할인 후 예상 결제 금액>");
        int expectedPrice = reservationDto.expectedPrice();
        System.out.println(expectedPrice);

        System.out.println("<12월 이벤트 배지>");
        Badge badge = reservationDto.badge();
        System.out.println(badge.getBadgeName());
    }
}
