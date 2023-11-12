package christmas.domain.discount;

import christmas.domain.Category;
import christmas.domain.MenuItem;
import christmas.domain.date.VisitDate;
import christmas.domain.dto.EventDto;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * 역할: 할인액을 저장한다
 */
public class DiscountStorage {
    private final EnumMap<DiscountType, Integer> discountMap;

    private final int a = 1;

    // imp. 할인 금액을 저장하는 곳이니까 초기화할 때 세팅해도 될까?
    // ref. 필드 초기화를 하면 변경이 불가능하므로 생성자에서 초기화 기능을 따로 분리해서 생성자에서 필드를 초기화하자
    public DiscountStorage() {
        discountMap = new EnumMap<>(DiscountType.class);
        for (DiscountType type : DiscountType.values()) {
            discountMap.put(type, 0);
        }
    }

    /**
     * @param discountMap 생성자 인자
     * @return key가 DiscountType, value를 0으로 초기화한 EnumMap을 반환
     */
    private EnumMap<DiscountType, Integer> initialize(EnumMap<DiscountType, Integer> discountMap) {
        discountMap = new EnumMap<>(DiscountType.class);
        for (DiscountType type : DiscountType.values()) {
            discountMap.put(type, 0);
        }
        return discountMap;
    }

    /**
     * @param type  할인 혜택
     * @param price 할인 금액
     *              <p>
     *              EnumMap에 할인 혜택이 적용되면 할인 금액을 저장한다
     */
    private void addDiscountPrice(DiscountType type, int price) {
        discountMap.put(type, discountMap.get(type) + price);
    }

    /**
     * @param visitDate 사용자가 입력한 날짜
     *                  <p>
     *                  12/1 ~ 12/25 크리스마스 디데이 할인 할인
     *                  <p>
     *                  금액은 1000원으로 시작하여 하루마다 100원씩 증가
     */
    public void addChristmasDiscountPrice(VisitDate visitDate) {
        int christmasDiscount = visitDate.sumChristmasDiscount();
        addDiscountPrice(DiscountType.CHRISTMAS_D_DAY, christmasDiscount);
    }

    /**
     * @param menuItem 사용자가 입력한 메뉴와 개수
     *                 <p>
     *                 평일 할인(일요일~목요일): 평일에는 디저트 메뉴를 메뉴 1개당 2,023원 할인
     */
    public void addWeekDaysDiscountPrice(MenuItem menuItem) {
        if (menuItem.isSameCategory(Category.DESSERT)) {
            addDiscountPrice(DiscountType.WEEKDAYS, 2_023 * menuItem.getQuantity()); //ref
        }
    }

    /**
     * @param menuItem 사용자가 입력한 메뉴와 개수
     *                 <p>
     *                 주말 할인(금요일, 토요일): 주말에는 메인 메뉴를 메뉴 1개당 2,023원 할인
     */
    public void addWeekendDiscountPrice(MenuItem menuItem) {
        if (menuItem.isSameCategory(Category.MAINDISH)) {
            addDiscountPrice(DiscountType.WEEKEND, 2_023 * menuItem.getQuantity()); // ref
        }
    }

    /**
     * @param visitDate 사용자가 입력한 날짜
     *                  <p>
     *                  이벤트 달력에 별(⭐)이 있으면 총 주문 금액에서 1,000원 할인
     */
    public void addSpecialDisCountPrice(VisitDate visitDate) {
        if (visitDate.isSpecialDay()) {
            addDiscountPrice(DiscountType.SPECIAL, 1_000);
        }
    }

    /**
     * @return 총 할인 혜택 금액(증점 혜택 제외)
     */
    public int sumtotalDiscountPrice() {
        return discountMap.values().stream()
                .mapToInt(value -> value)
                .sum();
    }

    /**
     * 할인 금액이 0원인 경우 제외하고 discountStorage 반환
     * <p>
     * 출력할 때 사용하려고 했다
     */
    // ref. 출력할 때 0원이 아닌 것만 출력하도록 하자 (복잡성 줄이기)
    public EnumMap<DiscountType, Integer> getDiscountMapExceptZero() {
        EnumMap<DiscountType, Integer> discountEnumMap = new EnumMap<>(DiscountType.class);
        for (Entry<DiscountType, Integer> entry : discountMap.entrySet()) {
            if (entry.getValue() != 0) {
                discountEnumMap.put(entry.getKey(), entry.getValue());
            }
        }
        return discountEnumMap;
    }

    public EnumMap<DiscountType, Integer> getDiscountMap() {
        return discountMap;
    }

    // ref. 혜택 내역 Dto 생성
    public List<EventDto> toEventsDto() {
        return List.of();
    }
}
