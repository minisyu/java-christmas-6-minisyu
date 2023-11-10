package christmas.domain.policy;

import christmas.domain.Category;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ChristmasDDayDiscountPolicy implements DiscountPolicy {
    /**
     * @param category 메뉴 카테고리
     * @param date     입력한 날짜
     *                 <p>
     *                 이벤트 기간: 2023.12.1 ~ 2023.12.25
     * @return 1, 000원으로 시작하여 크리스마스가 다가올수록 날마다 할인 금액이 100원씩 증가
     */
    @Override
    public int discount(Category category, LocalDate date) {
        // 크리스마스까지 남은 일수 계산
        LocalDate christmasDay = LocalDate.of(2023, 12, 25);
        // ChronoUnit은 "두 날짜 간 일 수 차이"를 계산할 수 있는 enum
        int dayUtilChristmas = (int) ChronoUnit.DAYS.between(date, christmasDay);
        // 할인금액 계산: 1일 마다 100원 씩 증가 (기본 값은 1000원)
        int discountAmount = 1000 + (25 - dayUtilChristmas) * 100;

        // 크리스마스 디데이 기간에만 할인 적용
        if (dayUtilChristmas >= 0) {
            return discountAmount;
        }
        return 0;
    }
}
