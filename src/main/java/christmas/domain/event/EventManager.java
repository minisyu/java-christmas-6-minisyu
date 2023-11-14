package christmas.domain.event;

import christmas.domain.Badge;
import christmas.domain.Gift;
import christmas.domain.MenuItems;
import christmas.domain.discount.DiscountPolicy;
import christmas.domain.discount.DiscountStorage;

public class EventManager {
    private final EventData eventData;

    public EventManager(EventData eventData) {
        this.eventData = eventData;
    }

    public void applyDiscountEvent() {
        if (isNotEventAvailable()) {
            return;
        }

        DiscountPolicy.CHRISTMAS_D_DAY.applyDiscountPolicy(eventData);
        DiscountPolicy.WEEKDAYS.applyDiscountPolicy(eventData);
        DiscountPolicy.WEEKEND.applyDiscountPolicy(eventData);
        DiscountPolicy.SPECIAL.applyDiscountPolicy(eventData);
    }

    public DiscountStorage getDiscountStorage() {
        return eventData.getDiscountStorage();
    }

    public MenuItems getMenuItems() {
        return eventData.getMenuItems();
    }

    private boolean isNotEventAvailable() {
        MenuItems menuItems = getMenuItems();
        return menuItems.calculateMenuItemsTotalPrice() < 10_000;
    }

    public Gift applyGiftEvent() {
        MenuItems menuItems = getMenuItems();
        return Gift.from(menuItems);
    }

    public Badge awardBadge() {
        int totalDiscountPrice = sumTotalDiscountPrice();
        return Badge.from(totalDiscountPrice);
    }
    
    public int sumTotalDiscountPrice() {
        DiscountStorage discountStorage = getDiscountStorage();
        Gift gift = applyGiftEvent();
        return discountStorage.calculateTotalDiscountPrice() + gift.getGiftPrice();
    }
}
