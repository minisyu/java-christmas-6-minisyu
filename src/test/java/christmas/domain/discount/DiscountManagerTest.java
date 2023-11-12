package christmas.domain.discount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.domain.MenuItems;
import christmas.domain.date.VisitDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountManagerTest {
    private DiscountManager discountManager;

    @BeforeEach
    void setUp() {
        discountManager = new DiscountManager();
    }

    @DisplayName("날짜에 따라 할인 혜택이 다르게 적용된다")
    @Test
    void should_Apply_Discount_Events() {
        // given
        String date = "24";
        VisitDate visitDate = VisitDate.from(date);

        String menus = "티본스테이크-1,제로콜라-2,양송이수프-2";
        MenuItems menuItems = MenuItems.from(menus);

        // when
        discountManager.discount(visitDate, menuItems);

        // then
        DiscountStorage discountStorage = discountManager.getDiscountStorage();
        int totalDiscount = discountStorage.calculateTotalDiscountPrice();
        
        assertEquals(4300, totalDiscount);
    }
}