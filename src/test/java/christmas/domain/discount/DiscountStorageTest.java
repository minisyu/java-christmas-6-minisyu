package christmas.domain.discount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.domain.MenuItem;
import christmas.domain.date.VisitDate;
import christmas.domain.dto.EventDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountStorageTest {
    private DiscountStorage discountStorage;

    @BeforeEach
    void setUp() {
        discountStorage = new DiscountStorage();
    }

    @DisplayName("크리스마스 디데이 할인(12/1 ~ 12/25) - 1,000원으로 시작하여 크리스마스가 다가올수록 날마다 할인 금액이 100원씩 증가")
    @ParameterizedTest
    @ValueSource(strings = {"1", "8", "17", "24", "25"})
    void should_Add_Christmas_Discount_Price(String input) {
        // given
        VisitDate visitDate = VisitDate.from(input);

        // when
        discountStorage.addChristmasDiscountPrice(visitDate);

        // then
        assertEquals(visitDate.calculateChristmasDiscount(),
                discountStorage.calculateTotalDiscountPrice());
    }

    @DisplayName("평일 할인(일요일~목요일) - 평일에는 디저트 메뉴를 메뉴 1개당 2,023원 할인")
    @Test
    void should_Add_WeekDays_Discount_Price() {
        // given
        String input = "아이스크림-4";
        MenuItem menuItem = MenuItem.from(input);

        // when
        discountStorage.addWeekDaysDiscountPrice(menuItem);

        // then
        assertEquals(MenuItem.WEEK_DISCOUNT_PRICE * 4, discountStorage.calculateTotalDiscountPrice());
    }

    @DisplayName("주말 할인(금요일, 토요일) - 주말에는 메인 메뉴를 메뉴 1개당 2,023원 할인")
    @Test
    void should_Add_Weekend_Discount_Price() {
        // given
        String input = "티본스테이크-2";
        MenuItem menuItem = MenuItem.from(input);

        // when
        discountStorage.addWeekendDiscountPrice(menuItem);

        // then
        assertEquals(MenuItem.WEEK_DISCOUNT_PRICE * 2, discountStorage.calculateTotalDiscountPrice());
    }

    @DisplayName("특별 할인 - 이벤트 달력에 별이 있으면 총주문 금액에서 1,000원 할인")
    @ParameterizedTest
    @ValueSource(strings = {"3", "10", "17", "24", "31"})
    void should_Add_Special_DisCount_Price(String input) {
        // given
        VisitDate visitDate = VisitDate.from(input);

        // when
        discountStorage.addSpecialDisCountPrice(visitDate);

        // then
        assertEquals(DiscountStorage.SPECIAL_DISCOUNT_PRICE, discountStorage.calculateTotalDiscountPrice());
    }

    @DisplayName("DiscountStorage에 저장된 데이터로 List<EventDto> 변환")
    @Test
    void should_Convert_To_EventsDto() {
        // given
        String input1 = "3";
        VisitDate visitDate = VisitDate.from(input1);
        discountStorage.addChristmasDiscountPrice(visitDate);
        discountStorage.addSpecialDisCountPrice(visitDate);

        String input2 = "아이스크림-1";
        MenuItem menuItem1 = MenuItem.from(input2);
        discountStorage.addWeekDaysDiscountPrice(menuItem1);

        String input3 = "티본스테이크-2";
        MenuItem menuItem = MenuItem.from(input3);
        discountStorage.addWeekendDiscountPrice(menuItem);

        // when
        List<EventDto> eventDtos = discountStorage.toEventsDto();

        // then
        assertEquals(4, eventDtos.size());
    }
}