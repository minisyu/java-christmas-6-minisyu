package christmas.domain;

import christmas.domain.date.VisitDate;
import christmas.domain.discount.DiscountStorage;
import christmas.domain.discount.DiscountType;
import christmas.domain.dto.ReservationDto;
import java.util.EnumMap;
import java.util.List;

// ref. 삭제
public class Confirmation {
    private final MenuItems menuItems;
    private final VisitDate visitDate;
    private final DiscountStorage discountStorage;

    public Confirmation(MenuItems menuItems, VisitDate visitDate, DiscountStorage discountStorage) {
        this.menuItems = menuItems;
        this.visitDate = visitDate;
        this.discountStorage = discountStorage;
    }

    /**
     * 증정 이벤트를 적용한다
     */
    private Gift applyGiftEvent() {
        return Gift.from(menuItems);
    }

    // imp. ㅠㅠ
    //  증정 이벤트는 어디에 있어야 할까? DiscountStorage? MenuItems? Order?
    //  와..꼬였다...어떻게 가져오지...EnumMap으로 했는데 Gift는 DiscountType enum에 없어서 못 함
    //  근데 Gift가 할인 혜택인가?
    /**
     * 증정 이벤트 혜택 내역을 discountStorage에 저장한다
     */

    /**
     * 할인 전 총주문 금액
     */
    private int getTotalPrice() {
        return menuItems.getTotalPrice();
    }

    /**
     * 할인 후 에상 결제 금액
     */
    private int getExpectedPrice() {
        int totalPrice = getTotalPrice();
        int totalDiscountPrice = discountStorage.sumtotalDiscountPrice();
        return totalPrice - totalDiscountPrice;
    }

    /**
     * @return 응답 Dto 생성
     */
    public ReservationDto toReservationDto() {
        // 날짜
        int date = visitDate.getDate();

        // 주문 메뉴
        List<MenuItemDto> menuItemsDto = menuItems.toMenuItemsDto();

        // 할인 전 총주문 금액
        int totalPrice = getTotalPrice();

        // 증정 메뉴
        Gift gift = applyGiftEvent();

        // 혜택 내역 ref. 할인 금액이 0원인 경우 제외
        // fix. 증정 이벤트를 못 가져온다!!!!!!!!!!!!!!!!!ㅠㅠㅠㅠㅠㅠㅠ
        //  어.. 위의 증정 메뉴에서 빼오면 되려나? .....
        //DiscountStorage discountStorage = this.discountStorage;
        EnumMap<DiscountType, Integer> discountMapExceptZero = discountStorage.getDiscountMapExceptZero();

        // fix. 총혜택 금액
        //  아니 그럼 이거도 변경되는뎁..? 증정 메뉴 빼서 넘겨야 하는건가.......? 머여...
        int totalDiscountPrice = discountStorage.sumtotalDiscountPrice();
        int giftPrice = gift.getGiftPrice();
        int discountPrice = totalDiscountPrice + giftPrice;

        // 할인 후 예상 결제 금액
        int expectedPrice = getExpectedPrice();

        // 12월 이벤트 배지
        // fix. 엥...? 그럼 이것도 변경되는뎁..? 어...? 왜..?
        //  총혜택 금액에 따라  배지 다르니까 int를 넘겨? 어...?
        //  아니 혜택 내역에 증정 이벤트도 추가해야 하는건가??? 어떻게 하지??
        //Badge badge = Badge.from(discountStorage);
        Badge badge = Badge.from(discountPrice);

        return new ReservationDto(
                date,
                menuItemsDto,
                totalPrice,
                gift,
                discountMapExceptZero,
                discountPrice,
                expectedPrice,
                badge
        );
    }
}
