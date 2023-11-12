package christmas.view;

import christmas.domain.Badge;
import christmas.domain.Gift;
import christmas.domain.MenuItemDto;
import christmas.domain.discount.DiscountType;
import christmas.domain.dto.ReservationDto;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

public class OutputView {
    public void printEvent(ReservationDto reservationDto) {
        System.out.printf("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!%n", reservationDto.date());

        printHeader("주문 메뉴");
        List<MenuItemDto> menuItemDtos = reservationDto.menuItems();
        for (MenuItemDto menuItemDto : menuItemDtos) {
            System.out.println(menuItemDto.itemName() + " " + menuItemDto.quantity() + "개");
        }
        System.out.println();

        printHeader("할인 전 총주문 금액");
        int totalPrice = reservationDto.totalPrice();
        System.out.println(totalPrice + "원");
        System.out.println();

        printHeader("증정 메뉴");
        Gift gift = reservationDto.gift();
        System.out.println(gift.getGiftName());
        System.out.println();

        // fix. 증정 이벤트는 어떻게 출력하지..오잉..하하하하
        printHeader("혜택 내역");
        EnumMap<DiscountType, Integer> discountStorage = reservationDto.discountMap();
        for (Entry<DiscountType, Integer> entry : discountStorage.entrySet()) {
            System.out.println(entry.getKey().getDiscountName() + ": -" + entry.getValue());
        }
        System.out.println("증정 이벤트: -" + gift.getGiftPrice());
        System.out.println();

        printHeader("총혜택 금액");
        int totalDiscountPrice = reservationDto.totalDiscountPrice();
        System.out.println("-" + totalDiscountPrice);
        System.out.println();

        printHeader("할인 후 예상 결제 금액");
        int expectedPrice = reservationDto.expectedPrice();
        System.out.println(expectedPrice);
        System.out.println();

        // fix. 배지 안 나옴!! 맨날 없대!!
        printHeader("12월 이벤트 배지");
        Badge badge = reservationDto.badge();
        System.out.println(badge.getBadgeName());
    }

    /**
     * @param message 출력 메시지
     *                <p>
     *                첫 번째 메시지를 보여준다
     */
    private void printHeader(String message) {
        System.out.printf("<%s>%n", message);
    }

    /**
     * 개행
     */
    private void printFooter() {
        System.out.println();
    }

    /**
     * @param price 금액이 0이 아닌 경우, "-"를 붙여서 출력
     */
    private void printPrice(int price) {
        if (price != 0) {
            System.out.println("-" + price);
        }
        System.out.println(price);
    }

    /**
     * 세자리 숫자 쉼표로 구분하여 출력
     */
}
