package christmas.domain.date;

import christmas.domain.discount.DiscountPolicy;
import christmas.exception.VisitDateException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * 예약 날짜
 */
public class VisitDate {
    public static final int DEFAULT_YEAR = 2023;
    public static final int DEFAULT_MONTH = 12;
    public static final List<Integer> specialDays = List.of(3, 10, 17, 24, 25, 31);
    private static final int MIN_DAY = 1;
    private static final int MAX_DAY = 31;
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

    private static void validateVisitDateRange(int dayOfMonth) {
        if (dayOfMonth < MIN_DAY || dayOfMonth > MAX_DAY) {
            throw new VisitDateException();
        }
    }

    public boolean isBeforeOrEqual(LocalDate otherDate) {
        return !visitDate.isAfter(otherDate);
    }

    public int calculateChristmasDiscount() {
        int daysUntilChristmas = visitDate.getDayOfMonth() - 1;
        return DiscountPolicy.INITIAL_CHRISTMAS_PRICE +
                (DiscountPolicy.DAILY_CHRISTMAS_DISCOUNT * (daysUntilChristmas));
    }

    public boolean isWeekend() {
        DayOfWeek dayOfWeek = visitDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }

    public boolean isWeekday() {
        return !isWeekend();
    }

    public boolean isSpecialDay() {
        int dayOfMonth = visitDate.getDayOfMonth();
        return specialDays.contains(dayOfMonth);
    }

    public int getVisitDate() {
        return visitDate.getDayOfMonth();
    }
}

