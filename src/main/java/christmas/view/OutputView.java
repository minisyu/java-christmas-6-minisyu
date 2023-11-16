package christmas.view;

import christmas.domain.dto.BadgeDto;
import christmas.domain.dto.ConfirmedReservation;
import christmas.domain.dto.EventDto;
import christmas.domain.dto.GiftDto;
import christmas.domain.dto.MenuItemDto;
import christmas.util.ViewConstants;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {
    public void printBenefitEvents(ConfirmedReservation confirmedReservation) {
        System.out.print(createReservationMessage(confirmedReservation));
    }

    public String createReservationMessage(ConfirmedReservation confirmedReservation) {
        return String.format(
                ViewConstants.EVENT_RESERVATION_FRAME,
                formatReservationDate(confirmedReservation),
                formatOrderedMenuItems(confirmedReservation),
                formatTotalMenuItemsPrice(confirmedReservation),
                formatPromotionalGift(confirmedReservation),
                formatDiscountEvents(confirmedReservation),
                formatTotalDiscountPrice(confirmedReservation),
                formatFinalPrice(confirmedReservation),
                formatBadge(confirmedReservation)
        );
    }

    private int formatReservationDate(ConfirmedReservation confirmedReservation) {
        return confirmedReservation.visitDate();
    }

    private String formatOrderedMenuItems(ConfirmedReservation confirmedReservation) {
        List<MenuItemDto> menuItemsDto = confirmedReservation.menuItems();

        return menuItemsDto.stream()
                .map(menuItemDto -> menuItemDto.menuName() + ViewConstants.SPACE + menuItemDto.quantity()
                        + ViewConstants.COUNT)
                .collect(Collectors.joining(ViewConstants.NEW_LINE));
    }

    private String formatTotalMenuItemsPrice(ConfirmedReservation confirmedReservation) {
        return formatPrice(confirmedReservation.totalPrice());
    }

    private String formatPromotionalGift(ConfirmedReservation confirmedReservation) {
        GiftDto gift = confirmedReservation.gift();
        int quantity = gift.quantity();

        if (quantity != ViewConstants.ZERO_AMOUNT) {
            return gift.giftName() + ViewConstants.SPACE + quantity + ViewConstants.COUNT;
        }
        return gift.giftName();
    }

    private String formatDiscountEvents(ConfirmedReservation confirmedReservation) {
        List<EventDto> discountEvents = confirmedReservation.events();

        if (areDiscountsZero(discountEvents)) {
            return ViewConstants.NONE;
        }

        return discountEvents.stream()
                .filter(event -> event.price() != ViewConstants.ZERO_AMOUNT)
                .map(this::formatSingleDiscountEvent)
                .collect(Collectors.joining(ViewConstants.NEW_LINE));
    }

    private boolean areDiscountsZero(List<EventDto> discountEvents) {
        return discountEvents.stream()
                .allMatch(event -> event.price() == ViewConstants.ZERO_AMOUNT);
    }

    private String formatSingleDiscountEvent(EventDto discountEvent) {
        return String.format(ViewConstants.LABEL_WITH_NEGATIVE_NUMBER_FORMAT,
                discountEvent.eventName(),
                formatPrice(discountEvent.price())
        );
    }

    private String formatTotalDiscountPrice(ConfirmedReservation confirmedReservation) {
        int totalDiscountPrice = confirmedReservation.totalDiscountPrice();

        if (totalDiscountPrice == ViewConstants.ZERO_AMOUNT) {
            return ViewConstants.ZERO_WON;
        }

        return String.format(ViewConstants.NEGATIVE_NUMBER_FORMAT, formatPrice(totalDiscountPrice));
    }

    private String formatFinalPrice(ConfirmedReservation confirmedReservation) {
        return formatPrice(confirmedReservation.finalPrice());
    }

    private String formatBadge(ConfirmedReservation confirmedReservation) {
        BadgeDto badge = confirmedReservation.badge();
        return badge.badgeName();
    }

    private String formatPrice(int price) {
        return new DecimalFormat("#,###").format(price) + ViewConstants.WON;
    }

}
