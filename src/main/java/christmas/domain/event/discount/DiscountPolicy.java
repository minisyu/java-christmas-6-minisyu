package christmas.domain.event.discount;

import christmas.domain.reservation.menu.Category;
import christmas.domain.reservation.date.VisitDate;
import christmas.domain.event.EventData;
import christmas.domain.reservation.menu.MenuItem;
import christmas.domain.reservation.menu.MenuItems;
import java.time.LocalDate;

public enum DiscountPolicy {

    CHRISTMAS_D_DAY("크리스마스 디데이 할인") {
        @Override
        public void applyDiscountPolicy(EventData eventData) {
            VisitDate visitDate = eventData.getVisitDate();

            if (visitDate.isBeforeOrEqual(CHRISTMAS_DAY)) {
                int christmasDiscount = visitDate.calculateChristmasDiscount();
                eventData.addDiscountPrice(CHRISTMAS_D_DAY, christmasDiscount);
            }
        }
    },

    WEEKDAYS("평일 할인") {
        @Override
        public void applyDiscountPolicy(EventData eventData) {
            VisitDate visitDate = eventData.getVisitDate();
            MenuItems menuItems = eventData.getMenuItems();

            if (visitDate.isWeekend()) {
                return;
            }

            for (MenuItem menuItem : menuItems.getMenuItems()) {
                if (menuItem.isSameCategory(Category.DESSERT)) {
                    eventData.addDiscountPrice(WEEKDAYS, menuItem.calculateWeekDiscountPrice());
                }
            }
        }
    },

    WEEKEND("주말 할인") {
        @Override
        public void applyDiscountPolicy(EventData eventData) {
            VisitDate visitDate = eventData.getVisitDate();
            MenuItems menuItems = eventData.getMenuItems();

            if (visitDate.isWeekday()) {
                return;
            }

            for (MenuItem menuItem : menuItems.getMenuItems()) {
                if (menuItem.isSameCategory(Category.MAINDISH)) {
                    eventData.addDiscountPrice(WEEKEND, menuItem.calculateWeekDiscountPrice());
                }
            }
        }
    },

    SPECIAL("특별 할인") {
        @Override
        public void applyDiscountPolicy(EventData eventData) {
            VisitDate visitDate = eventData.getVisitDate();

            if (visitDate.isSpecialDay()) {
                eventData.addDiscountPrice(SPECIAL, SPECIAL_DISCOUNT_PRICE);
            }
        }
    };

    public static final LocalDate CHRISTMAS_DAY = LocalDate.of(VisitDate.DEFAULT_YEAR, VisitDate.DEFAULT_MONTH, 25);
    public static final int INITIAL_CHRISTMAS_PRICE = 1_000;
    public static final int DAILY_CHRISTMAS_DISCOUNT = 100;
    public final static int SPECIAL_DISCOUNT_PRICE = 1_000;

    private final String discountName;

    DiscountPolicy(String discountName) {
        this.discountName = discountName;
    }

    public abstract void applyDiscountPolicy(EventData eventData);

    public String getDiscountName() {
        return discountName;
    }
}
