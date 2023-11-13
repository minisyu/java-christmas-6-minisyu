package christmas.view;

import christmas.domain.dto.BadgeDto;
import christmas.domain.dto.ConfirmedReservation;
import christmas.domain.dto.EventDto;
import christmas.domain.dto.GiftDto;
import christmas.domain.dto.MenuItemDto;
import christmas.util.ViewConstants;
import java.text.DecimalFormat;
import java.util.List;

public class OutputView {

    public void printEvent(ConfirmedReservation reservationDto) {
        printEventPreView(reservationDto);

        printReservationMenu(reservationDto);

        printTotalReservationPrice(reservationDto);

        printGiftMenu(reservationDto);

        priintEventBenefits(reservationDto);

        printTotalDiscount(reservationDto);

        printFinalPrice(reservationDto);

        printEventBadge(reservationDto);
    }

    /**
     * @param reservationDto 배지 출력
     */
    private void printEventBadge(ConfirmedReservation reservationDto) {
        printHeader(ViewConstants.BADGE_MESSAGE);
        BadgeDto badge = reservationDto.badge();
        System.out.println(badge.badgeName());
    }

    /**
     * @param reservationDto 할인 후 예상 결제 금액 출력
     */
    private void printFinalPrice(ConfirmedReservation reservationDto) {
        printHeader(ViewConstants.EXPECTED_PAYMENT_MESSAGE);
        int expectedPrice = reservationDto.finalPrice();
        System.out.println(expectedPrice + ViewConstants.WON);
        printFooter();
    }

    /**
     * @param reservationDto 총혜택 금액 출력
     */
    private void printTotalDiscount(ConfirmedReservation reservationDto) {
        printHeader(ViewConstants.TOTAL_DISCOUNT_MESSAGE);
        int totalDiscountPrice = reservationDto.totalDiscountPrice();
        printPrice(totalDiscountPrice);
        printFooter();
    }

    /**
     * @param reservationDto 혜택 내역 출력
     */
    private void priintEventBenefits(ConfirmedReservation reservationDto) {
        printHeader(ViewConstants.DISCOUNT_MESSAGE);
        List<EventDto> events = reservationDto.events();

        List<EventDto> nonZeroPriceEvents = events.stream()
                .filter(event -> event.price() != 0)
                .toList();

        if (nonZeroPriceEvents.isEmpty()) {
            System.out.println(ViewConstants.NONE);
        }

        //nonZeroPriceEvents.forEach(event -> printPrice(event.price()));
        nonZeroPriceEvents.forEach(
                event -> System.out.println(event.eventName() + ": -" + event.price() + ViewConstants.WON));
        printFooter();
    }

    /**
     * @param reservationDto 증정 메뉴 출력
     */
    private void printGiftMenu(ConfirmedReservation reservationDto) {
        printHeader(ViewConstants.GIFT_MESSAGE);
        GiftDto gift = reservationDto.gift();
        if (gift.quantity() != 0) {
            System.out.println(gift.giftName() + " " + gift.quantity() + ViewConstants.COUNT);
        }

        if (gift.quantity() == 0) {
            System.out.println(gift.giftName());
        }
        printFooter();
    }

    /**
     * @param reservationDto 할인 전 총주문 금액 출력
     */
    private void printTotalReservationPrice(ConfirmedReservation reservationDto) {
        printHeader(ViewConstants.TOTAL_RESERVATION_PRICE_MESSAGE);
        int totalPrice = reservationDto.totalPrice();
        printPrice(totalPrice);
        printFooter();
    }

    /**
     * @param reservationDto 주문 메뉴 출력
     */
    private void printReservationMenu(ConfirmedReservation reservationDto) {
        printHeader(ViewConstants.RESERVATION_MENU_MESSAGE);
        List<MenuItemDto> menuItemDtos = reservationDto.menuItems();
        for (MenuItemDto menuItemDto : menuItemDtos) {
            System.out.println(menuItemDto.menuName() + " " + menuItemDto.quantity() + ViewConstants.COUNT);
        }
        printFooter();
    }

    /**
     * @param reservationDto 날짜 출력
     */
    private void printEventPreView(ConfirmedReservation reservationDto) {
        System.out.printf(String.format(ViewConstants.START_MESSAGE + "\n", reservationDto.visitDate()));
        printFooter();
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
     * @param price 금액 출력
     */
    private void printPrice(int price) {
        if (price != 0) {
            System.out.println(price + ViewConstants.WON);
        }

        if (price == 0) {
            System.out.println(price + ViewConstants.WON);
        }
    }

    /**
     * 세자리 숫자 쉼표로 구분하여 출력
     */
    private String formatPrice(int price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###원");
        return decimalFormat.format(price);
    }

}
