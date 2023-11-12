package christmas.domain.discount;

import christmas.domain.Category;
import christmas.domain.MenuItem;
import christmas.domain.date.VisitDate;
import christmas.domain.dto.EventDto;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * 할인 혜택 금액을 저장한다
 */
public class DiscountStorage {
    private final static int SPECIAL_DISCOUNT_PRICE = 1_000;
    private final EnumMap<DiscountType, Integer> discountStorage;

    public DiscountStorage() {
        discountStorage = initializeDiscountStorage();
    }

    /**
     * @return key가 DiscountType, value를 0으로 초기화된 EnumMap
     */
    private EnumMap<DiscountType, Integer> initializeDiscountStorage() {
        EnumMap<DiscountType, Integer> discountStorage = new EnumMap<>(DiscountType.class);
        for (DiscountType discountType : DiscountType.values()) {
            discountStorage.put(discountType, 0);
        }
        return discountStorage;
    }

    /**
     * @param type  할인 혜택
     * @param price 할인 금액
     *              <p>
     *              EnumMap에 할인 혜택이 적용되면 할인 금액을 저장한다
     */
    private void addDiscountPrice(DiscountType type, int price) {
        discountStorage.put(type, discountStorage.get(type) + price);
    }

    /**
     * @param visitDate 사용자가 입력한 날짜
     *                  <p>
     *                  12/1 ~ 12/25 크리스마스 디데이 할인 할인
     *                  <p>
     *                  금액은 1000원으로 시작하여 하루마다 100원씩 증가
     */
    public void addChristmasDiscountPrice(VisitDate visitDate) {
        int christmasDiscount = visitDate.calculateChristmasDiscount();
        addDiscountPrice(DiscountType.CHRISTMAS_D_DAY, christmasDiscount);
    }

    /**
     * @param menuItem 사용자가 입력한 메뉴와 개수
     *                 <p>
     *                 평일 할인(일요일~목요일): 평일에는 디저트 메뉴를 메뉴 1개당 2,023원 할인
     */
    public void addWeekDaysDiscountPrice(MenuItem menuItem) {
        if (menuItem.isSameCategory(Category.DESSERT)) {
            addDiscountPrice(DiscountType.WEEKDAYS, menuItem.calculateWeekDiscountPrice());
        }
    }

    /**
     * @param menuItem 사용자가 입력한 메뉴와 개수
     *                 <p>
     *                 주말 할인(금요일, 토요일): 주말에는 메인 메뉴를 메뉴 1개당 2,023원 할인
     */
    public void addWeekendDiscountPrice(MenuItem menuItem) {
        if (menuItem.isSameCategory(Category.MAINDISH)) {
            addDiscountPrice(DiscountType.WEEKEND, menuItem.calculateWeekDiscountPrice());
        }
    }

    /**
     * @param visitDate 사용자가 입력한 날짜
     *                  <p>
     *                  이벤트 달력에 별(⭐)이 있으면 총 주문 금액에서 1,000원 할인
     */
    public void addSpecialDisCountPrice(VisitDate visitDate) {
        if (visitDate.isSpecialDay()) {
            addDiscountPrice(DiscountType.SPECIAL, SPECIAL_DISCOUNT_PRICE);
        }
    }

    /**
     * @return 총 할인 혜택 금액(증점 혜택 제외)
     */
    public int calculateTotalDiscountPrice() {
        return discountStorage.values().stream()
                .mapToInt(value -> value)
                .sum();
    }

    /**
     * List<EventDto> 생성
     */
    public List<EventDto> toEventsDto() {
        List<EventDto> eventsDto = new ArrayList<>();

        discountStorage.forEach((discountType, value) -> {
            EventDto eventDto = new EventDto(discountType.getDiscountName(), value);
            eventsDto.add(eventDto);
        });

        return eventsDto;
    }
}
