package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.exception.MenuInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {

    @DisplayName("from 메소드 - 메뉴명으로 Enum 상수 가져오기")
    @Test
    public void should_Return_Enum_Constant_For_MenuName() {
        // given
        String menuName = "시저샐러드";

        // when
        Menu menu = Menu.from(menuName);

        // then
        assertEquals(menuName, menu.getFoodName());
        assertEquals(menu.getFoodPrice(), 8_000);
        assertEquals(menu.getCategory(), Category.APPETIZER);
    }

    @DisplayName("from 메소드 - 존재하지 않는 메뉴명으로 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"짜장면", "짬뽕", "김밥", "아메리카노", "콘샐러드"})
    public void should_Throw_Exception_For_Non_existent_MenuName(String menuName) {
        // when
        // then
        assertThatThrownBy(() -> Menu.from(menuName))
                .isInstanceOf(MenuInputException.class);
    }
}