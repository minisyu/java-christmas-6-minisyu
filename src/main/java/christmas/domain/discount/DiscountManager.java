package christmas.domain.discount;

import christmas.domain.MenuItem;
import christmas.domain.MenuItems;
import christmas.domain.date.VisitDate;
import java.time.LocalDate;

/**
 * 역할: 할인 관리
 */
public class DiscountManager {
    private static final LocalDate CHRISTMAS_DAY = LocalDate.of(2023, 12, 25);
    private final VisitDate visitDate;
    private final MenuItems menuItems;
    private final DiscountAggregator discountAggregator;

    public DiscountManager(VisitDate visitDate, MenuItems menuItems) {
        this.visitDate = visitDate;
        this.menuItems = menuItems;
        this.discountAggregator = new DiscountAggregator();
    }

    /**
     * 입력한 날짜에 따라 해당되는 기능이 실행된다
     */
    public void discount() {
        doChristmasDiscount();
        doWeekdayDiscount();
        doWeekendDiscount();
        doSpecialDiscount();
    }

    public DiscountAggregator getDiscountAggregator() {
        return discountAggregator;
    }

    /**
     * 크리스마스 디데이 할인
     */
    private void doChristmasDiscount() {
        if (visitDate.isNotAfter(CHRISTMAS_DAY)) {
            discountAggregator.addChristmasDiscount(visitDate);
        }
    }

    /**
     * 평일 할인
     */
    private void doWeekdayDiscount() {
        if (visitDate.isWeekend()) {
            return;
        }

        for (MenuItem menuItem : menuItems.getMenuItems()) {
            discountAggregator.addWeekdayDiscountPrice(menuItem);
        }
    }

    /**
     * 주말 할인
     */
    private void doWeekendDiscount() {
        if (visitDate.isWeekday()) {
            return;
        }

        for (MenuItem menuItem : menuItems.getMenuItems()) {
            discountAggregator.addWeekendDiscountPrice(menuItem);
        }
    }

    /**
     * 특별 할인
     */
    private void doSpecialDiscount() {
        if (visitDate.isSpecialDay()) {
            discountAggregator.addSpecialDisCountPrice(visitDate);
        }
    }
}
