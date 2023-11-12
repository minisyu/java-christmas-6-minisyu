package christmas.domain;

import christmas.domain.date.VisitDate;
import christmas.domain.discount.DiscountAggregator;
import christmas.domain.discount.DiscountManager;
import christmas.domain.dto.EventDto;
import java.util.HashMap;
import java.util.List;
import org.w3c.dom.events.EventException;

/**
 * "사용자가 입력한 날짜, 메뉴와 개수, 할인액"을 지닌다
 */
public class Reservation {
    private final MenuItems menuItems;
    private final VisitDate visitDate;
    private final Gift gift;
    private DiscountAggregator discountAggregator;

    private Reservation(MenuItems menuItems, VisitDate visitDate) {
        this.menuItems = menuItems;
        this.visitDate = visitDate;
        this.gift = Gift.from(menuItems);
    }

    public static Reservation from(MenuItems menuItems, VisitDate visitDate) {
        return new Reservation(menuItems, visitDate);
    }

    /**
     * 날짜 별 할인 혜택 적용
     */
    public void discount() {
        // 총주문 금액이 10,000원 이상부터 이벤트가 적용되어야 한다
        if (!canDoEvent()) {
            return;
        }
        // 날짜와 메뉴 및 개수를 전달하여 할인액을 Reservation의 discountAggregator에 저장한다
        DiscountManager discountManager = new DiscountManager(visitDate, menuItems);
        discountManager.discount();
        this.discountAggregator = discountManager.getDiscountAggregator();
    }

    public void doEvents() {
        EventManager eventManager = new EventManager(, );
        eventManager.applyAllEvents();
        // ref. Reservaiton 필드 추가(OutputView에서 출력할 것들), 이벤트 적용 후 필드 초기화
        this.discountData= eventManager.getDiscountData();
        this.gift= eventManager.getGift();
        this.benefitdata = eventManager.getBenefit();


        // ref. 혜택 내역 Dto에 증정 이벤트도 추가(EventManager에서 해도 될 듯?)
        HashMap<Menu, Integer> discountMap = new HashMap<>();
        int totalEventPrice = 25000;
        List<EventDto> eventDtos = eventManager.getDiscountStorage().toEventDtos;
        int eventPrice = eventManager.getEventPrice();
        EventDto event = new EventDto("증정 이벤트", eventPrice);
        eventDtos.add(event);
    }

    /**
     * @return 10, 000원 이상이면 true, 아니면 false
     */
    private boolean canDoEvent() {
        return menuItems.getTotalPrice() > 10_000;
    }

    /**
     * @return 총 혜택 금액(할인 혜택 + 증정품 금액)
     */
    public int sumTotalDiscountPrice() {
        return discountAggregator.sumAllDiscountPrice() + gift.getGiftPrice();
    }

    /**
     * @return 혜택 내역(증정 이벤트 포함)
     */
    public List<Integer> getBenefit() {
        List<Integer> benefitHistory = discountAggregator.getBenefitHistory();
        benefitHistory.add(benefitHistory.size() - 1, gift.getGiftPrice());
        return benefitHistory;
    }

    // fix. 이게 맞아..?
//    public ReservationDto toReservationDto() {
//        // 1. <주문 메뉴>에 해당하는 Dto 값
//        List<MenuItemDto> menuItemsDto = menuItems.toMenuItemsDto();
//        // 2. <할인 전 총주문 금액>에 해당하는 Dto 값
//        int totalPrice = menuItems.getTotalPrice();
//        // 3. <증정 메뉴>에 해당하는 Dto 값
//        Gift gift = this.gift;
//        // 4. <혜택 내역>에 해당하는 Dto 값
//        List<Integer> benefit = getBenefit();
//        // 5. <총혜택 금액>에 해당하는 Dto 값
//        int totalDiscountPrice = sumTotalDiscountPrice();
//        // 5. <할인 후 예상 결제 금액>에 해당하는 Dto 값
//        int expectedPrice = menuItems.getTotalPrice() - sumTotalDiscountPrice();
//        // 6. <12월 이벤트 배지>에 해당하는 Dto 값
//        Badge badge = Badge.from(totalDiscountPrice);
//        return new ReservationDto(
//                menuItemsDto,
//                totalPrice,
//                gift,
//                benefit,
//                totalDiscountPrice,
//                expectedPrice,
//                badge
//        );
//      }
}

