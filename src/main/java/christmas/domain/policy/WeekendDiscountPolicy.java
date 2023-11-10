package christmas.domain.policy;

import christmas.domain.Category;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekendDiscountPolicy implements DiscountPolicy {
    /**
     * @param category 메뉴 카테고리
     * @param date     입력한 날짜
     * @return 주말 할인(금요일, 토요일): 주말에는 메인 메뉴를 메뉴 1개당 2,023원 할인
     */
    @Override
    public int discount(Category category, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek(); // 요일 반환
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY) {
            if (category == Category.MAINDISH) {
                return 2_203;
            }
        }
        return 0;
    }
}
