package christmas.domain.dto;

import java.util.List;

public record ConfirmedReservation(
        int visitDate,
        List<MenuItemDto> menuItems,
        int totalPrice,
        GiftDto gift,
        List<EventDto> events,
        int totalDiscountPrice,
        int finalPrice,
        BadgeDto badge
) {
}
