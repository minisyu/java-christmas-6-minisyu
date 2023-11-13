package christmas.domain;

import christmas.domain.date.VisitDate;
import christmas.domain.discount.DiscountStorage;
import christmas.domain.dto.BadgeDto;
import christmas.domain.dto.ConfirmedReservation;
import christmas.domain.dto.EventDto;
import christmas.domain.dto.GiftDto;
import christmas.domain.dto.MenuItemDto;
import christmas.domain.event.EventCoordinator;
import java.util.List;

/**
 * 예약 내역
 * <p>
 * 주문(할인, 증정, 배지 적용 전 ---처리-->  ReservationDto
 */
public class Reservation {
    private final VisitDate visitDate;
    private final MenuItems menuItems;
    private final EventCoordinator eventCoordinator;
    // imp. EventCoordinator가 있으면 3개의 필드가 필요하지 않을 것 같기도 하다
    private DiscountStorage discountStorage;
    private Gift gift;
    private Badge badge;

    public Reservation(VisitDate visitDate, MenuItems menuItems) {
        this.visitDate = visitDate;
        this.menuItems = menuItems;
        // imp. 날짜와 메뉴 및 개수에 따라 적용된 이벤트가 정해지니까 EventCoordinator를 생성자에서 만들어주는게 맞다고 생각한다
        //  그러면, Gift와 Badge를 생성하는건 EventCoordinator이니까 Reservation의 필드로 지정하지 말고,
        //  EventCoordinator에서 가져오는게 맞을까?
        this.eventCoordinator = new EventCoordinator(visitDate, menuItems);
    }

    public static Reservation from(VisitDate visitDate, MenuItems menuItems) {
        return new Reservation(visitDate, menuItems);
    }

    /**
     * 이벤트를 적용한다
     */
    public void applyEvents() {
        // EventManager에게 이벤트 적용 위임
        eventCoordinator.applyDiscountEvent();
        // 필드 초기화
        this.discountStorage = eventCoordinator.getDiscountStorage();
        this.gift = eventCoordinator.applyGiftEvent();
        this.badge = eventCoordinator.awardBadge();
    }

    /**
     * @return 할인 후 예상 결제 금액
     */
    public int calculateFinalPrice() {
        return menuItems.calculateMenuItemsTotalPrice() - discountStorage.calculateTotalDiscountPrice();
    }

    /**
     * List<EventDto> 반환
     * <p>
     * (할인 혜택 + 증정 이벤트)
     */
    public List<EventDto> generateEventsDtoWithDiscountAndGift() {
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
        int confirmedTotalDiscountPrice = eventCoordinator.sumTotalDiscountPrice();

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
