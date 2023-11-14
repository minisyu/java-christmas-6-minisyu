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

    private EnumMap<DiscountPolicy, Integer> initializeDiscountStorage() {
        EnumMap<DiscountPolicy, Integer> discountStorage = new EnumMap<>(DiscountPolicy.class);
        for (DiscountPolicy discountPolicy : DiscountPolicy.values()) {
            discountStorage.put(discountPolicy, 0);
        }
        return discountStorage;
    }

    public void addDiscountPrice(DiscountPolicy discountPolicy, int price) {
        discountStorage.put(discountPolicy, discountStorage.get(discountPolicy) + price);
    }

    public int calculateTotalDiscountPrice() {
        return discountStorage.values().stream()
                .mapToInt(value -> value)
                .sum();
    }
    
    public List<EventDto> toEventsDto() {
        List<EventDto> eventsDto = new ArrayList<>();

        discountStorage.forEach((discountType, value) -> {
            EventDto eventDto = new EventDto(discountType.getDiscountName(), value);
            eventsDto.add(eventDto);
        });

        return eventsDto;
    }
}
