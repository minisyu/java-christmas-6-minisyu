package christmas.domain.event;

import christmas.domain.Badge;
import christmas.domain.Gift;
import christmas.domain.MenuItems;
import christmas.domain.date.VisitDate;
import christmas.domain.discount.DiscountPolicy;
import christmas.domain.discount.DiscountStorage;

/**
 * 이벤트를 적용한다
 * <p>
 * 할인은 DiscountManager에 위임
 * <p>
 * 증정 배지는 직접 처리
 * <p>
 * 이후 처리된 데이터를 Reservation에 반환
 */
public class EventManager {
    private final EventData eventData;

    public EventManager(EventData eventData) {
        this.eventData = eventData;
    }

    /**
     * 할인 이벤트를 진행한다
     */
    public void applyDiscountEvent() {
        if (isNotEventAvailable()) {
            return;
        }

        DiscountPolicy.CHRISTMAS_D_DAY.applyDiscountPolicy(eventData);
        DiscountPolicy.WEEKDAYS.applyDiscountPolicy(eventData);
        DiscountPolicy.WEEKEND.applyDiscountPolicy(eventData);
        DiscountPolicy.SPECIAL.applyDiscountPolicy(eventData);
    }

    /**
     * @return eventData에서 할인 금액을 가져온다
     */
    public DiscountStorage getDiscountStorage() {
        return eventData.getDiscountStorage();
    }

    /**
     * @return eventData에서 날짜를 가져온다
     */
    public VisitDate getVisitDate() {
        return eventData.getVisitDate();
    }

    /**
     * @return eventData에서 MenuItems를 가져온다
     */
    public MenuItems getMenuItems() {
        return eventData.getMenuItems();
    }

    /**
     * @return 10, 000원 이상이면 true, 아니면 false
     */
    private boolean isNotEventAvailable() {
        MenuItems menuItems = getMenuItems();
        return menuItems.calculateMenuItemsTotalPrice() < 10_000;
    }

    /**
     * 증정 이밴트를 진행한다
     */
    public Gift applyGiftEvent() {
        MenuItems menuItems = getMenuItems();
        return Gift.from(menuItems);
    }

    /**
     * "총 혜택 금액"에 따라 배지를 부여한다
     * <p>
     */
    public Badge awardBadge() {
        int totalDiscountPrice = sumTotalDiscountPrice();
        return Badge.from(totalDiscountPrice);
    }

    /**
     * @return 총 혜택 금액 = 할인 금액의 합계  + 증정 메뉴의 가격
     */
    public int sumTotalDiscountPrice() {
        DiscountStorage discountStorage = getDiscountStorage();
        Gift gift = applyGiftEvent();
        return discountStorage.calculateTotalDiscountPrice() + gift.getGiftPrice();
    }
}
