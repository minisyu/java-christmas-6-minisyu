package christmas.domain.event;

import christmas.domain.reservation.date.VisitDate;
import christmas.domain.event.discount.DiscountPolicy;
import christmas.domain.event.discount.DiscountStorage;
import christmas.domain.reservation.menu.MenuItems;

public class EventData {
    private final VisitDate visitDate;
    private final MenuItems menuItems;
    private final DiscountStorage discountStorage;

    public EventData(VisitDate visitDate, MenuItems menuItems) {
        this.visitDate = visitDate;
        this.menuItems = menuItems;
        this.discountStorage = new DiscountStorage();
    }

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
