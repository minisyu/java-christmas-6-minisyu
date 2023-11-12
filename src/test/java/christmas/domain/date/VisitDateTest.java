package christmas.domain.date;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.exception.VisitDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class VisitDateTest {
    
    @DisplayName("예약 날짜 범위 검증 - 실패")
    @ParameterizedTest
    @ValueSource(strings = {"0", "32", "-19", "56"})
    public void should_Throw_Exception_For_Invalid_VisitDate(String visitDate) {
        // when
        // then
        assertThatThrownBy(() -> VisitDate.from(visitDate))
                .isInstanceOf(VisitDateException.class);
    }
}