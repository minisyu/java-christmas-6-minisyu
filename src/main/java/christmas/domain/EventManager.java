package christmas.domain;

import christmas.domain.discount.DiscountStorage;
import christmas.domain.discount.Discounter;

/**
 * 12월 이벤트를 관리한다
 * <p>
 * - 할인을 적용한다
 *  * <p>
 *  * - 증정 이벤트를 적용한다
 *  * <p>
 *  * - 배지를 부여한다
 */
/**
 * imp.ㅠㅠ
 *  위의 역할을 하는 객체를 생성했는데 컨트롤러에서 사용하면 Applicaiton에서 Order, Discounter를 초기화해야 하는데
 *  Order은 사용자 입력값인데 초기화할 수 없지 않나..?
 *  그래서 Order(Reservation)에서 하는 건가..?
 *  오잉..어떻게 역할을 분리해야하는지 통 감이 오지 않는군용...
 */
public class EventManager {
    private final Order order;
    private final Discounter discounter;


    public EventManager(Order order, Discounter discounter) {
        this.order = order;
        this.discounter = discounter;
    }

    public void applyAllEvents() {
        //
        discounter.discount();
    }
    /**
     * 할인을 적용한다
     */
    public void discount() {
        // 총주문 금액이 10,000원 이상부터 이벤트가 적용되어야 한다
        if (order.canDoEvent()) {
            return;
        }
        // 할인 혜택 금액을 DiscountStorage에 저장한다
        discounter.discount();
    }

    // imp. ㅠㅠ
    //  만들었는데 사용을 안 하면 EventManager가 필요 없나..?
    //  할인 적용을 Order(Reservation)이 하도록 만들면 될까...
    //  Confirmation(예약 내역)을 생성해서 여기서 응답 Dto로 바꿔줄까 했는데
    //  Order(Reservation) 여기서 충분히 해도 되는 역할인지 잘 모르겠다...ㅠㅠ

//    /**
//     * 증정 이벤트를 적용한다
//     */
//    public Gift applyGiftEvent() {
//        return order.applyGiftEvent();
//    }
//
//    /**
//     * 총혜택 금액에 따라 다른 이벤트 배지를 부여한다
//     */
//    public Badge awardBadge() {
//        DiscountStorage discountStorage = discounter.getDiscountStorage();
//        return Badge.from(discountStorage);
//    }

    /**
     * 예약 확정 내역을 생성한다
     */
    public Confirmation from() {
        DiscountStorage discountStorage = discounter.getDiscountStorage();
        return new Confirmation(order., discountStorage);
    }
}
