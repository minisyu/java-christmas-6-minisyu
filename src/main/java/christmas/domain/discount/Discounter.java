package christmas.domain.discount;

import christmas.domain.MenuItem;
import christmas.domain.MenuItems;
import christmas.domain.date.VisitDate;
import java.time.LocalDate;

public class Discounter {
    private static final LocalDate CHRISTMAS_DAY = LocalDate.of(2023, 12, 25);
    private final VisitDate visitDate;
    private final MenuItems menuItems;
    private final DiscountStorage discountStorage;

    public Discounter(VisitDate visitDate, MenuItems menuItems, DiscountStorage discountStorage) {
        this.visitDate = visitDate;
        this.menuItems = menuItems;
        this.discountStorage = discountStorage;
    }

    /**
     * 입력한 날짜에 따라 할인 혜택을 적용
     */
    public void discount() {
        doChristmasDiscount();
        doWeekdayDiscount();
        doWeekendDiscount();
        doSpecialDiscount();
    }

    public DiscountStorage getDiscountStorage() {
        return discountStorage;
    }

    /**
     * 크리스마스 디데이 할인
     */
    private void doChristmasDiscount() {
        if (visitDate.isNotAfter(CHRISTMAS_DAY)) {
            discountStorage.addChristmasDiscountPrice(visitDate);
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
            discountStorage.addWeekDaysDiscountPrice(menuItem);
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
            discountStorage.addWeekendDiscountPrice(menuItem);
        }
    }

    /**
     * 특별 할인
     */
    private void doSpecialDiscount() {
        if (visitDate.isSpecialDay()) {
            discountStorage.addSpecialDisCountPrice(visitDate);
        }
    }
}
