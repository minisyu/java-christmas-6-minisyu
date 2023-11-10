package christmas.domain.policy;

import christmas.domain.Category;
import java.time.LocalDate;

public class SpeicalDiscountPolicy implements DiscountPolicy {
    /**
     * @param category 메뉴 카테고리
     * @param date     입력한 날짜
     * @return 특별 할인: 이벤트 달력에 별이 있으면 총주문 금액에서 1,000원 할인
     */
    @Override
    public int discount(Category category, LocalDate date) {
        // 별이 있는 특별한 날에는 1000원 할인
        if (SpecialDate.isSameAs(date)) {
            return 1_000;
        }
        return 0;
    }
}
