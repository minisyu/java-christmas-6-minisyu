package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.exception.MenuInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuItemsTest {

    @DisplayName("중복된 메뉴명 입력 - 실패")
    @Test
    public void should_Throw_Exception_For_Duplicate_MenuNames() {
        // given
        String input = "아이스크림-2,아이스크림-1";

        // when
        // then
        assertThatThrownBy(() -> MenuItems.from(input))
                .isInstanceOf(MenuInputException.class);
    }

    @DisplayName("총 메뉴 개수 20개 초과 - 실패")
    @Test
    public void should_Throw_Exception_For_Total_Menu_Quantity_Exceeding_Limit() {
        // given
        String input = "아이스크림-20,티본스테이크-1";

        // when
        // then
        assertThatThrownBy(() -> MenuItems.from(input))
                .isInstanceOf(MenuInputException.class);
    }

    @DisplayName("모든 주문이 음료인 경우 - 실패")
    @Test
    public void should_Throw_Exception_For_All_Drinks_Ordered() {
        // given
        String input = "제로콜라-2,샴페인-1";

        // when
        // then
        assertThatThrownBy(() -> MenuItems.from(input))
                .isInstanceOf(MenuInputException.class);
    }
}