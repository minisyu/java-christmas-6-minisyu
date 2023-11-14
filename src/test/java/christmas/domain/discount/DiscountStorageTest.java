package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import christmas.domain.MenuItem;
import christmas.domain.dto.EventDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountStorageTest {
    private DiscountStorage discountStorage;

    @BeforeEach
    void setUp() {
        discountStorage = new DiscountStorage();
    }

    @DisplayName("할인 혜택 금액이 저장되는지 확인")
    @Test
    void should_Store_Correct_Discount_Price() {
        // given
        discountStorage.addDiscountPrice(DiscountPolicy.WEEKDAYS, MenuItem.WEEK_DISCOUNT_PRICE);
        discountStorage.addDiscountPrice(DiscountPolicy.SPECIAL, DiscountPolicy.SPECIAL_DISCOUNT_PRICE);

        // when
        int totalDiscountPrice = discountStorage.calculateTotalDiscountPrice();
        int storedTotalPrice = MenuItem.WEEK_DISCOUNT_PRICE + DiscountPolicy.SPECIAL_DISCOUNT_PRICE;

        // then
        assertThat(totalDiscountPrice).isEqualTo(storedTotalPrice);
    }

    @DisplayName("할인 혜택의 총 금액을 올바르게 계산하는지 확인")
    @Test
    void should_Calculate_Total_Discount_Price() {
        // given
        discountStorage.addDiscountPrice(DiscountPolicy.WEEKDAYS, MenuItem.WEEK_DISCOUNT_PRICE * 2);
        discountStorage.addDiscountPrice(DiscountPolicy.WEEKEND, MenuItem.WEEK_DISCOUNT_PRICE);
        discountStorage.addDiscountPrice(DiscountPolicy.SPECIAL, DiscountPolicy.SPECIAL_DISCOUNT_PRICE * 3);

        // when
        int totalDiscountPrice = discountStorage.calculateTotalDiscountPrice();

        // then
        assertThat(totalDiscountPrice).isEqualTo(9_069);
    }


    @DisplayName("EventDto를 생성하는지 확인")
    @Test
    void should_Create_EventsDto() {
        // given
        discountStorage.addDiscountPrice(DiscountPolicy.CHRISTMAS_D_DAY, 1000);
        discountStorage.addDiscountPrice(DiscountPolicy.WEEKDAYS, MenuItem.WEEK_DISCOUNT_PRICE);
        discountStorage.addDiscountPrice(DiscountPolicy.WEEKEND, MenuItem.WEEK_DISCOUNT_PRICE);
        discountStorage.addDiscountPrice(DiscountPolicy.SPECIAL, DiscountPolicy.SPECIAL_DISCOUNT_PRICE);

        // when
        List<EventDto> eventsDto = discountStorage.toEventsDto();

        // then
        assertThat(eventsDto).hasSize(4)
                .extracting(EventDto::eventName, EventDto::price)
                .containsExactly(
                        tuple("크리스마스 디데이 할인", 1_000),
                        tuple("평일 할인", MenuItem.WEEK_DISCOUNT_PRICE),
                        tuple("주말 할인", MenuItem.WEEK_DISCOUNT_PRICE),
                        tuple("특별 할인", DiscountPolicy.SPECIAL_DISCOUNT_PRICE)
                );
    }
}