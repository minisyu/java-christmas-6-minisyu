package christmas.domain.date;

import christmas.exception.VisitDateException;
import java.time.LocalDate;

/**
 * 예약 날짜
 */
public class VisitDate {
    private static final int MIN_DAY = 1;
    private static final int MAX_DAY = 31;
    private static final int DEFAULT_YEAR = 2023;
    private static final int DEFAULT_MONTH = 12;
    
    private final LocalDate visitDate;

    private VisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public static VisitDate from(String input) {
        int dayOfMonth = Integer.parseInt(input);
        validateVisitDateRange(dayOfMonth);
        LocalDate visitDate = LocalDate.of(DEFAULT_YEAR, DEFAULT_MONTH, dayOfMonth);
        return new VisitDate(visitDate);
    }

    /**
     * @param dayOfMonth 사용자가 입력한 날짜
     *                   <p>
     *                   1 ~ 31 안의 숫자가 아니면 예외 발생
     */
    private static void validateVisitDateRange(int dayOfMonth) {
        if (dayOfMonth < MIN_DAY || dayOfMonth > MAX_DAY) {
            throw new VisitDateException();
        }
    }
}

