package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import christmas.exception.MenuInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuItemTest {

    @DisplayName("사용자 입력으로부터 MenuItem 생성")
    @Test
    public void should_Create_MenuItem_From_Input() {
        // given
        String input = "아이스크림-2";

        // when
        MenuItem menuItem = MenuItem.from(input);

        // then
        assertEquals("아이스크림", menuItem.getFoodName());
        assertEquals(2, menuItem.getQuantity());
    }

    @DisplayName("메뉴의 개수 검증 - 실패")
    @Test
    public void should_Validate_Quantity() {
        // given
        String input = "티본스테이크-0";

        // when
        // then
        assertThatThrownBy(() -> MenuItem.from(input))
                .isInstanceOf(MenuInputException.class);
    }

    @DisplayName("음료 카테고리 여부 확인 - 참")
    @Test
    public void should_Return_True_For_Drink_Category() {
        // given
        String input = "제로콜라-2";
        MenuItem menuItem = MenuItem.from(input);

        // when
        boolean isOfCategoryDrink = menuItem.isOfCategoryDrink();

        // then
        assertTrue(isOfCategoryDrink);
    }

    @DisplayName("음료 카테고리 여부 확인 - 거짓")
    @Test
    public void should_Return_False_For_Non_Drink_Category() {
        // given
        String input = "해산물파스타-1";
        MenuItem menuItem = MenuItem.from(input);

        // when
        boolean isOfCategoryDrink = menuItem.isOfCategoryDrink();

        // then
        assertFalse(isOfCategoryDrink);
    }
}