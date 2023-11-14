package christmas.domain.discount;

import christmas.domain.dto.EventDto;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * 할인 혜택 금액을 저장한다
 */
public class DiscountStorage {
    private final EnumMap<DiscountPolicy, Integer> discountStorage;

    public DiscountStorage() {
        discountStorage = initializeDiscountStorage();
    }

    /**
     * @return key가 DiscountType, value를 0으로 초기화된 EnumMap
     */
    private EnumMap<DiscountPolicy, Integer> initializeDiscountStorage() {
        EnumMap<DiscountPolicy, Integer> discountStorage = new EnumMap<>(DiscountPolicy.class);
        for (DiscountPolicy discountPolicy : DiscountPolicy.values()) {
            discountStorage.put(discountPolicy, 0);
        }
        return discountStorage;
    }

    /**
     * @param discountPolicy 할인 혜택
     * @param price          할인 금액
     *                       <p>
     *                       EnumMap에 할인 혜택이 적용되면 할인 금액을 저장한다
     */
    public void addDiscountPrice(DiscountPolicy discountPolicy, int price) {
        discountStorage.put(discountPolicy, discountStorage.get(discountPolicy) + price);
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
