package christmas.domain;

import christmas.domain.date.VisitDate;
import christmas.domain.discount.DiscountStorage;
import christmas.domain.dto.BadgeDto;
import christmas.domain.dto.ConfirmedReservation;
import christmas.domain.dto.EventDto;
import christmas.domain.dto.GiftDto;
import christmas.domain.dto.MenuItemDto;
import christmas.domain.event.EventData;
import christmas.domain.event.EventManager;
import java.util.List;

/**
 * 예약 내역
 * <p>
 * 주문(할인, 증정, 배지 적용 전 ---처리-->  ReservationDto
 */
public class Reservation {
    private final VisitDate visitDate;
    private final MenuItems menuItems;
    private DiscountStorage discountStorage;
    private Gift gift;
    private Badge badge;

    private Reservation(VisitDate visitDate, MenuItems menuItems) {
        this.visitDate = visitDate;
        this.menuItems = menuItems;
    }

    public static Reservation from(VisitDate visitDate, MenuItems menuItems) {
        return new Reservation(visitDate, menuItems);
    }

    /**
     * 이벤트를 적용한다
     */
    public void applyEvents() {
        EventData eventData = new EventData(visitDate, menuItems);
        EventManager eventManager = new EventManager(eventData);
        eventManager.applyDiscountEvent();

        this.discountStorage = eventManager.getDiscountStorage();
        this.gift = eventManager.applyGiftEvent();
        this.badge = eventManager.awardBadge();
    }

    /**
     * @return 할인 후 예상 결제 금액
     */
    private int calculateFinalPrice() {
        return menuItems.calculateMenuItemsTotalPrice() - discountStorage.calculateTotalDiscountPrice();
    }

    /**
     * @return 총 혜택 금액 = 할인 금액의 합계  + 증정 메뉴의 가격
     */
    private int sumTotalDiscountPrice() {
        return discountStorage.calculateTotalDiscountPrice() + gift.getGiftPrice();
    }

    /**
     * List<EventDto> 반환
     * <p>
     * (할인 혜택 + 증정 이벤트)
     */
    private List<EventDto> generateEventsDtoWithDiscountAndGift() {
        List<EventDto> eventsDto = discountStorage.toEventsDto();
        eventsDto.add(gift.toEventDto());
        return eventsDto;
    }

    /**
     * CompletedReservation 생성
     */
    public ConfirmedReservation toConfirmedReservation() {
        // 날짜
        int confirmedVisitDate = visitDate.getVisitDate();

        // 주문 메뉴 및 개수
        List<MenuItemDto> confirmedMenuItems = menuItems.toMenuItemsDto();

        // 할인 전 총주문 금액
        int confirmedMenuItemsTotalPrice = menuItems.calculateMenuItemsTotalPrice();

        // 증정 메뉴
        GiftDto confirmedGift = gift.toGiftDto();

        // 혜택 내역
        List<EventDto> eventsDto = generateEventsDtoWithDiscountAndGift();

        // 총 혜택 금액
        int confirmedTotalDiscountPrice = sumTotalDiscountPrice();

        // 할인 후 예상 결제 금액
        int confirmedFinalPrice = calculateFinalPrice();

        // 배지
        BadgeDto confirmedBadge = badge.toBadgeDto();

        return new ConfirmedReservation(
                confirmedVisitDate,
                confirmedMenuItems,
                confirmedMenuItemsTotalPrice,
                confirmedGift,
                eventsDto,
                confirmedTotalDiscountPrice,
                confirmedFinalPrice,
                confirmedBadge
        );
    }
}
