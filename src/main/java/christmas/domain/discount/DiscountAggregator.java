package christmas.domain.discount;

import christmas.domain.Category;
import christmas.domain.MenuItem;
import christmas.domain.date.VisitDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 역할: 할인액을 저장
 */
public class DiscountAggregator {
    // ref. 할인액을 저장하는 클래스를 만들어볼까?
    private int christmasDiscountPrice = 0;
    private int weekdayDiscountPrice = 0;
    private int weekendDiscountPrice = 0;
    private int specialDiscountPrice = 0;

    public DiscountAggregator() {
    }

    /**
     * @param visitDate 사용자가 입력한 날짜
     *                  <p>
     *                  12/1 ~ 12/25 크리스마스 디데이 할인 할인
     *                  <p>
     *                  금액은 1000원으로 시작하여 하루마다 100원씩 증가
     */
    public void addChristmasDiscount(VisitDate visitDate) {
        int discountPrice = visitDate.sumChristmasDiscount();
        christmasDiscountPrice += discountPrice;
    }

    /**
     * @param menuItem 사용자가 입력한 메뉴와 개수
     *                 <p>
     *                 주말 할인(금요일, 토요일): 주말에는 메인 메뉴를 메뉴 1개당 2,023원 할인
     */
    public void addWeekendDiscountPrice(MenuItem menuItem) {
        if (menuItem.isSameCategory(Category.MAINDISH)) {
            weekendDiscountPrice += 2023;
        }
    }

    /**
     * @param menuItem 사용자가 입력한 메뉴와 개수
     *                 <p>
     *                 평일 할인(일요일~목요일): 평일에는 디저트 메뉴를 메뉴 1개당 2,023원 할인
     */
    public void addWeekdayDiscountPrice(MenuItem menuItem) {
        if (menuItem.isSameCategory(Category.DESSERT)) {
            weekdayDiscountPrice += 2023;
        }
    }

    /**
     * @param visitDate 사용자가 입력한 날짜
     *                  <p>
     *                  이벤트 달력에 별(⭐)이 있으면 총 주문 금액에서 1,000원 할인
     */
    public void addSpecialDisCountPrice(VisitDate visitDate) {
        if (visitDate.isSpecialDay()) {
            specialDiscountPrice += 1000;
        }
    }

    /**
     * @return 총 혜택 금액
     */
    public int sumAllDiscountPrice() {
        return christmasDiscountPrice +
                weekdayDiscountPrice +
                weekendDiscountPrice +
                specialDiscountPrice;
    }

    /**
     * @return 혜택 내역을 반환 (증정 이벤트 혜택 제외)
     */
    public List<Integer> getBenefitHistory() {
        List<Integer> benefitHistory = new ArrayList<>();
        benefitHistory.add(christmasDiscountPrice);
        benefitHistory.add(weekdayDiscountPrice);
        benefitHistory.add(weekendDiscountPrice);
        benefitHistory.add(specialDiscountPrice);
        return benefitHistory;
    }

}
