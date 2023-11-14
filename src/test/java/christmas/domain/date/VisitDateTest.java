package christmas.domain.date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.discount.DiscountPolicy;
import christmas.exception.VisitDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class VisitDateTest {

    @DisplayName("예약 날짜 범위가 1 ~ 31 사이의 숫자인지 검증 - 실패")
    @ParameterizedTest
    @ValueSource(strings = {"0", "32", "-19", "56", "100", "1540", "-245"})
    public void should_Throw_Exception_For_Invalid_VisitDate(String visitDate) {
        // when
        // then
        assertThatThrownBy(() -> VisitDate.from(visitDate))
                .isInstanceOf(VisitDateException.class);
    }

    @DisplayName("지정한 날짜와 동일하거나 그 이전인지 확인")
    @ParameterizedTest
    @ValueSource(strings = {"1", "3", "14", "21", "24", "25"})
    void should_Check_If_Before_Or_Equal_Date(String date) {
        // given
        VisitDate visitDate = VisitDate.from(date);

        // when
        boolean result = visitDate.isBeforeOrEqual(DiscountPolicy.CHRISTMAS_DAY);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("크리스마스 디데이 할인액 계산")
    @ParameterizedTest
    @EnumSource(ChristmasDDay.class)
    void should_Calculate_Christmas_Discount(ChristmasDDay christmasDDay) {
        // given
        VisitDate visitDate = VisitDate.from(christmasDDay.getDay());

        // when
        int discount = visitDate.calculateChristmasDiscount();

        // then
        assertThat(discount).isEqualTo(1000 + 100 * (visitDate.getVisitDate() - 1));
    }

    @DisplayName("주말 여부 확인")
    @ParameterizedTest
    @EnumSource(Weekend.class)
    void should_Check_If_Weekend(Weekend weekend) {
        // given
        VisitDate visitDate = VisitDate.from(weekend.getDay());

        // when
        boolean isWeekend = visitDate.isWeekend();

        // then
        assertThat(isWeekend).isTrue();
    }

    @DisplayName("평일 여부 확인")
    @ParameterizedTest
    @EnumSource(WeekDay.class)
    void should_Check_If_Weekday(WeekDay weekDay) {

        // given
        VisitDate visitDate = VisitDate.from(weekDay.getDay());

        // when
        boolean isWeekDay = visitDate.isWeekday();

        // then
        assertThat(isWeekDay).isTrue();
    }

    @DisplayName("특별 날짜 여부 확인")
    @ParameterizedTest
    @EnumSource(SpecialDay.class)
    void should_Check_If_Special_Day(SpecialDay specialDay) {
        // given
        VisitDate visitDate = VisitDate.from(specialDay.getDay());

        // when
        boolean isSpecialDay = visitDate.isSpecialDay();

        // then
        assertThat(isSpecialDay).isTrue();
    }

}