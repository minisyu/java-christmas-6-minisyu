package christmas.domain;

import christmas.domain.date.VisitDate;
import christmas.domain.discount.DiscountStorage;
import christmas.domain.discount.Discounter;

// Reserveration - 주문(할인, 증정, 배지 적용 전 ---처리-->  ReservationDto

// Discounter - 할인

// EventManager - 할인은 디스카운터에 위임, 증정 배지는 직접 처리
// 이후 처리된 데이터를 Reservation에 반환

// Reservation에서 ReservationDto 생성


public class Order {
    private final MenuItems menuItems;
    private final VisitDate visitDate;
    private final Discounter discounter;

    public Order(MenuItems menuItems, VisitDate visitDate) {
        this.menuItems = menuItems;
        this.visitDate = visitDate;
        this.discounter = new Discounter(visitDate, menuItems, new DiscountStorage()); // imp.ㅠㅠ 엥?
    }

    /**
     * @return 10, 000원 이상이면 true, 아니면 false
     */
    public boolean canDoEvent() {
        return menuItems.getTotalPrice() > 10_000;
    }

    /**
     * 할인을 적용한다
     */
    public void discount() {
        // 총주문 금액이 10,000원 이상부터 이벤트가 적용되어야 한다
        if (!canDoEvent()) {
            return;
        }
        // 할인 혜택 금액을 DiscountStorage에 저장한다
        discounter.discount();
    }

    // imp. ㅠㅠ
    //  얘는 원래 EventManager에서 하려고 했으나..실패함..
    //  근데 그럼 여기서 차라리 응답 Dto를 만드는게 좋을까?

    /**
     * 예약 확정 내역을 생성한다
     */
    public Confirmation from() {
        DiscountStorage discountStorage = discounter.getDiscountStorage();
        return new Confirmation(menuItems, visitDate, discountStorage);
    }

    // imp. ㅠㅠ
    //  Gift는 어디서 해줘야 하는교..Confirmation? Order?

    /**
     * 증정 이벤트 실행
     */
    public Gift applyGiftEvent() {
        return Gift.from(menuItems);
    }

    public MenuItems getMenuItems() {
        return menuItems;
    }
}
