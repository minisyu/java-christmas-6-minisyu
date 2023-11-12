package christmas.domain.discount;

import christmas.domain.MenuItem;
import christmas.domain.MenuItems;
import christmas.domain.date.VisitDate;
import java.time.LocalDate;

/*
날짜별로 할인을 관리한다
 */
public class DiscountManager {
    public static final LocalDate CHRISTMAS_DAY = LocalDate.of(VisitDate.DEFAULT_YEAR, VisitDate.DEFAULT_MONTH, 25);
    private final DiscountStorage discountStorage = new DiscountStorage();

    public DiscountManager() {
    }

    /**
     * 입력한 날짜에 따라 할인 혜택을 적용
     */
    public void discount(VisitDate visitDate, MenuItems menuItems) {
        applyChristmasDiscount(visitDate);
        applyWeekdayDiscount(visitDate, menuItems);
        applyWeekendDiscount(visitDate, menuItems);
        applySpecialDiscount(visitDate);
    }

    public DiscountStorage getDiscountStorage() {
        return discountStorage;
    }

    /**
     * 크리스마스 디데이 할인
     */
    private void applyChristmasDiscount(VisitDate visitDate) {
        if (visitDate.isNotAfter(CHRISTMAS_DAY)) {
            discountStorage.addChristmasDiscountPrice(visitDate);
        }
    }

    /**
     * 평일 할인
     */
    private void applyWeekdayDiscount(VisitDate visitDate, MenuItems menuItems) {
        if (visitDate.isWeekend()) {
            return;
        }

        for (MenuItem menuItem : menuItems.getMenuItems()) {
            discountStorage.addWeekDaysDiscountPrice(menuItem);
        }
    }

    /**
     * 주말 할인
     */
    private void applyWeekendDiscount(VisitDate visitDate, MenuItems menuItems) {
        if (visitDate.isWeekday()) {
            return;
        }

        for (MenuItem menuItem : menuItems.getMenuItems()) {
            discountStorage.addWeekendDiscountPrice(menuItem);
        }
    }

    /**
     * 특별 할인
     */
    private void applySpecialDiscount(VisitDate visitDate) {
        if (visitDate.isSpecialDay()) {
            discountStorage.addSpecialDisCountPrice(visitDate);
        }
    }
}
