package christmas.domain.policy;

import christmas.domain.Category;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekDaysDiscountPolicy implements DiscountPolicy {
    /**
     * @param category 메뉴 카테고리명
     * @param date     입력한 날짜
     * @return 평일 할인(일요일~목요일): 평일에는 디저트 메뉴를 메뉴 1개당 2,023원 할인
     */
    @Override
    public int discount(Category category, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek(); // 요일 반환
        if (dayOfWeek != DayOfWeek.FRIDAY && dayOfWeek != DayOfWeek.SATURDAY) {
            if (category == Category.DESSERT) {
                return 2_023;
            }
        }
        return 0;
    }
}
