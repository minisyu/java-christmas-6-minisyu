package christmas.domain.event;

import christmas.domain.MenuItems;
import christmas.domain.date.VisitDate;
import christmas.domain.discount.DiscountPolicy;
import christmas.domain.discount.DiscountStorage;

public class EventData {
    private final VisitDate visitDate;
    private final MenuItems menuItems;
    private final DiscountStorage discountStorage;

    public EventData(VisitDate visitDate, MenuItems menuItems) {
        this.visitDate = visitDate;
        this.menuItems = menuItems;
        this.discountStorage = new DiscountStorage();
    }

    /**
     * @param discountPolicy 할인 혜택
     * @param price          할인 금액
     *                       <p>
     *                       discountStorage에 할인 금액 저장
     */
    public void addDiscountPrice(DiscountPolicy discountPolicy, int price) {
        discountStorage.addDiscountPrice(discountPolicy, price);
    }

    public MenuItems getMenuItems() {
        return menuItems;
    }

    public VisitDate getVisitDate() {
        return visitDate;
    }

    public DiscountStorage getDiscountStorage() {
        return discountStorage;
    }
}