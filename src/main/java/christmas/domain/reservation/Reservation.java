package christmas.domain.reservation;

import christmas.domain.reservation.date.VisitDate;
import christmas.domain.event.discount.DiscountStorage;
import christmas.domain.dto.BadgeDto;
import christmas.domain.dto.ConfirmedReservation;
import christmas.domain.dto.EventDto;
import christmas.domain.dto.GiftDto;
import christmas.domain.dto.MenuItemDto;
import christmas.domain.event.Badge;
import christmas.domain.event.EventData;
import christmas.domain.event.EventManager;
import christmas.domain.event.Gift;
import christmas.domain.reservation.menu.MenuItems;
import java.util.List;

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

    public void applyEvents() {
        EventData eventData = new EventData(visitDate, menuItems);
        EventManager eventManager = new EventManager(eventData);
        eventManager.applyDiscountEvent();

        this.discountStorage = eventManager.getDiscountStorage();
        this.gift = eventManager.applyGiftEvent();
        this.badge = eventManager.awardBadge();
    }

    private int calculateFinalPrice() {
        return menuItems.calculateMenuItemsTotalPrice() - discountStorage.calculateTotalDiscountPrice();
    }

    private int sumTotalDiscountPrice() {
        return discountStorage.calculateTotalDiscountPrice() + gift.getGiftPrice();
    }

    private List<EventDto> generateEventsDtoWithDiscountAndGift() {
        List<EventDto> eventsDto = discountStorage.toEventsDto();
        eventsDto.add(gift.toEventDto());
        return eventsDto;
    }

    public ConfirmedReservation toConfirmedReservation() {
        int confirmedVisitDate = visitDate.getVisitDate();

        List<MenuItemDto> confirmedMenuItems = menuItems.toMenuItemsDto();

        int confirmedMenuItemsTotalPrice = menuItems.calculateMenuItemsTotalPrice();

        GiftDto confirmedGift = gift.toGiftDto();

        List<EventDto> eventsDto = generateEventsDtoWithDiscountAndGift();

        int confirmedTotalDiscountPrice = sumTotalDiscountPrice();

        int confirmedFinalPrice = calculateFinalPrice();

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
