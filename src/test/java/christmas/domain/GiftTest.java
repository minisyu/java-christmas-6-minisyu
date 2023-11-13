package christmas.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import christmas.domain.dto.EventDto;
import christmas.domain.dto.GiftDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GiftTest {

    @DisplayName("할인 전 총 주문 금액이 12만원 이상인 경우에만 샴페인 증정")
    @Test
    void should_Decide_Gift_Based_On_Total_MenuItems_Price() {
        // given
        String input1 = "티본스테이크-1,바비큐립-1,시저샐러드-2,아이스크림-1,샴페인-1";
        MenuItems menuItems1 = MenuItems.from(input1);

        String input2 = "시저샐러드-2,크리스마스파스타-2,제로콜라-2";
        MenuItems menuItems2 = MenuItems.from(input2);

        // when
        Gift giftForHighPrice = Gift.from(menuItems1);
        Gift giftForLowPrice = Gift.from(menuItems2);

        // then
        assertEquals(giftForHighPrice, Gift.CHAMPAGNE);
        assertEquals(giftForLowPrice, Gift.NONE);
    }


    @DisplayName("GiftDto를 생성하는지 확인")
    @Test
    void should_Create_GiftDto() {
        // given
        Gift none = Gift.NONE;
        Gift champagne = Gift.CHAMPAGNE;

        // when
        GiftDto noneGiftDto = none.toGiftDto();
        GiftDto champagneGiftDto = champagne.toGiftDto();

        // then
        assertNotNull(noneGiftDto);
        assertEquals("없음", noneGiftDto.giftName());
        assertEquals(0, noneGiftDto.quantity());

        assertNotNull(champagneGiftDto);
        assertEquals("샴페인", champagneGiftDto.giftName());
        assertEquals(1, champagneGiftDto.quantity());
    }

    @DisplayName("EventDto를 생성하는지 확인")
    @Test
    void shouldConvertGiftToEventDto() {
        // given
        Gift none = Gift.NONE;
        Gift champagne = Gift.CHAMPAGNE;

        // when
        EventDto noneEventDto = none.toEventDto();
        EventDto champagneEventDto = champagne.toEventDto();

        // then
        assertNotNull(noneEventDto);
        assertEquals("없음", noneEventDto.eventName());
        assertEquals(0, noneEventDto.price());

        assertNotNull(champagneEventDto);
        assertEquals("샴페인", champagneEventDto.eventName());
        assertEquals(25_000, champagneEventDto.price());
    }
}