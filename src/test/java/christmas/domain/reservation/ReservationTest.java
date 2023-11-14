package christmas.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.reservation.Reservation;
import christmas.domain.reservation.date.VisitDate;
import christmas.domain.dto.BadgeDto;
import christmas.domain.dto.ConfirmedReservation;
import christmas.domain.dto.EventDto;
import christmas.domain.dto.GiftDto;
import christmas.domain.dto.MenuItemDto;
import christmas.domain.reservation.menu.Menu;
import christmas.domain.reservation.menu.MenuItems;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        VisitDate visitDate = VisitDate.from("25");
        MenuItems menuItems = MenuItems.from("양송이수프-2,티본스테이크-1,아이스크림-2,레드와인-1");

        reservation = Reservation.from(visitDate, menuItems);
    }

    @DisplayName("Reservation에서 ConfirmedReservationDto로 변환할 수 있는지 확인")
    @Test
    void should_Convert_To_Confirmed_Reservation_Dto() {
        // given
        reservation.applyEvents();

        // when
        ConfirmedReservation confirmedReservation = reservation.toConfirmedReservation();

        // then
        assertThat(confirmedReservation).isNotNull();

        // 날짜
        assertThat(confirmedReservation.visitDate()).isEqualTo(25);

        // 주문 메뉴 및 개수
        List<MenuItemDto> expectedMenuItems = createMenuItemsDto();
        assertThat(confirmedReservation.menuItems()).isEqualTo(expectedMenuItems);

        // 할인 전 총주문 금액
        int totalMenuItemsPrice = getTotalMenuItemsPrice();
        assertThat(confirmedReservation.totalPrice()).isEqualTo(totalMenuItemsPrice);

        // 증정 메뉴
        GiftDto expectedGift = new GiftDto("샴페인", 1);
        assertThat(confirmedReservation.gift()).isEqualTo(expectedGift);

        // 혜택 내역
        List<EventDto> expectedEvents = createEventsDto();
        assertThat(confirmedReservation.events()).isEqualTo(expectedEvents);

        // 총 혜택 금액
        assertThat(confirmedReservation.totalDiscountPrice()).isEqualTo(33_446);

        // 할인 후 예상 결제 금액
        assertThat(confirmedReservation.finalPrice()).isEqualTo(128_554);

        // 배지
        BadgeDto expectedBadge = new BadgeDto("산타");
        assertThat(confirmedReservation.badge()).isEqualTo(expectedBadge);
    }

    private List<MenuItemDto> createMenuItemsDto() {
        return List.of(
                new MenuItemDto("양송이수프", 2),
                new MenuItemDto("티본스테이크", 1),
                new MenuItemDto("아이스크림", 2),
                new MenuItemDto("레드와인", 1)
        );
    }

    private int getTotalMenuItemsPrice() {
        return Menu.MUSHROOM_SOUP.getFoodPrice() * 2 +
                Menu.T_BONE_STEAK.getFoodPrice() +
                Menu.ICE_CREAM.getFoodPrice() * 2 +
                Menu.RED_WINE.getFoodPrice();
    }

    private List<EventDto> createEventsDto() {
        return List.of(
                new EventDto("크리스마스 디데이 할인", 3_400),
                new EventDto("평일 할인", 4_046),
                new EventDto("주말 할인", 0),
                new EventDto("특별 할인", 1_000),
                new EventDto("증정 이벤트", 25_000)
        );
    }
}
