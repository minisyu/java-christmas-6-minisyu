package christmas.domain.event;

import christmas.domain.Badge;
import christmas.domain.Gift;
import christmas.domain.MenuItems;
import christmas.domain.date.VisitDate;
import christmas.domain.discount.DiscountManager;
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
public class EventCoordinator {
    private final VisitDate visitDate;
    private final MenuItems menuItems;
    private final DiscountManager discountManager = new DiscountManager();
    //private final DiscountManager discountManager;

    public EventCoordinator(VisitDate visitDate, MenuItems menuItems) {
        this.visitDate = visitDate;
        this.menuItems = menuItems;
    }

    /**
     * 할인 이벤트를 진행한다
     */
    public void applyDiscountEvent() {
        if (isNotEventAvailable()) {
            return;
        }
        discountManager.discount(visitDate, menuItems);
    }

    /**
     * @return 할인 금액을 가져온다
     */
    public DiscountStorage getDiscountStorage() {
        return discountManager.getDiscountStorage();
    }

    /**
     * @return 10, 000원 이상이면 true, 아니면 false
     */
    private boolean isNotEventAvailable() {
        return menuItems.calculateMenuItemsTotalPrice() < 10_000;
    }

    /**
     * 증정 이밴트를 진행한다
     */
    public Gift applyGiftEvent() {
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
        DiscountStorage discountStorage = discountManager.getDiscountStorage();
        Gift gift = applyGiftEvent();
        return discountStorage.calculateTotalDiscountPrice() + gift.getGiftPrice();
    }
}
