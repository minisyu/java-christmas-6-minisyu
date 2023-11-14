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

    public void printReservation(ConfirmedReservation confirmedReservation) {
        System.out.print(createReservationMessage(confirmedReservation));
    }

    public String createReservationMessage(ConfirmedReservation confirmedReservation) {
        return String.format(
                ViewConstants.RESERVATION_MESSAGE_FRAME,
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

    /**
     * @param confirmedReservation
     * @return 날짜 출력 문자열
     */
    private int formatReservationDate(ConfirmedReservation confirmedReservation) {
        return confirmedReservation.visitDate();
    }

    /**
     * @param confirmedReservation
     * @return 주문 메뉴 출력 문자열
     * <p>
     * return """ 티본스테이크 1개 바비큐립 1개 초코케이크 2개 제로콜라 1개 """;
     */
    private String formatOrderedMenuItems(ConfirmedReservation confirmedReservation) {
        List<MenuItemDto> menuItemsDto = confirmedReservation.menuItems();

        return menuItemsDto.stream()
                .map(menuItemDto -> menuItemDto.menuName() + ViewConstants.SPACE + menuItemDto.quantity()
                        + ViewConstants.COUNT)
                .collect(Collectors.joining(ViewConstants.NEW_LINE));
    }

    /**
     * @param confirmedReservation
     * @return 할인 전 총주문 금액 문자열 출력
     */
    private String formatTotalMenuItemsPrice(ConfirmedReservation confirmedReservation) {
        return formatPrice(confirmedReservation.totalPrice());
    }

    /**
     * @param confirmedReservation
     * @return 증정 메뉴 문자열 출력
     */
    private String formatPromotionalGift(ConfirmedReservation confirmedReservation) {
        GiftDto gift = confirmedReservation.gift();
        int quantity = gift.quantity();

        if (quantity != ViewConstants.ZERO) {
            return gift.giftName() + ViewConstants.SPACE + quantity + ViewConstants.COUNT;
        }
        return gift.giftName();
    }

    /**
     * @param confirmedReservation
     * @return 혜택 내역
     */
    private String formatDiscountEvents(ConfirmedReservation confirmedReservation) {
        List<EventDto> discountEvents = confirmedReservation.events();

        if (areDiscountsZero(discountEvents)) {
            return ViewConstants.NONE;
        }

        return discountEvents.stream()
                .filter(event -> event.price() != ViewConstants.ZERO)
                .map(this::formatSingleDiscountEvent)
                .collect(Collectors.joining(ViewConstants.NEW_LINE));
    }

    /**
     * @param discountEvents
     * @return 혜택 내역 목록이 0원이면 true, 아니면 false
     */
    private boolean areDiscountsZero(List<EventDto> discountEvents) {
        return discountEvents.stream()
                .allMatch(event -> event.price() == ViewConstants.ZERO);
    }

    /**
     * @param discountEvent
     * @return 혜택 내역 문자열
     */
    private String formatSingleDiscountEvent(EventDto discountEvent) {
        return String.format(ViewConstants.LABEL_WITH_NEGATIVE_VALUE_FORMAT,
                discountEvent.eventName(),
                formatPrice(discountEvent.price())
        );
    }

    /**
     * @param confirmedReservation
     * @return 총혜택 금액
     */
    private String formatTotalDiscountPrice(ConfirmedReservation confirmedReservation) {
        int totalDiscountPrice = confirmedReservation.totalDiscountPrice();

        if (totalDiscountPrice == ViewConstants.ZERO) {
            return ViewConstants.ZERO_WON;
        }

        return String.format(ViewConstants.NEGATIVE_VALUE_FORMA, formatPrice(totalDiscountPrice));
    }

    /**
     * @param confirmedReservation
     * @return 할인 후 예상 결제 금액 출력 문자열
     */
    private String formatFinalPrice(ConfirmedReservation confirmedReservation) {
        return formatPrice(confirmedReservation.finalPrice());
    }

    /**
     * @param confirmedReservation
     * @return 12월 이벤트 배지 출력 문자열
     */
    private String formatBadge(ConfirmedReservation confirmedReservation) {
        BadgeDto badge = confirmedReservation.badge();
        return badge.badgeName();
    }

    /**
     * @param price 금액
     * @return 세 자리 숫자를 쉼표로 구분한 문자열
     */
    private String formatPrice(int price) {
        return new DecimalFormat("#,###").format(price) + ViewConstants.WON;
    }

}
