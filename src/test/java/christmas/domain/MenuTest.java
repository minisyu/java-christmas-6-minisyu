package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        String menuName1 = "시저샐러드";
        String menuName2 = "해산물파스타";
        String menuName3 = "아이스크림";
        String menuName4 = "레드와인";

        // when
        Menu menu1 = Menu.from(menuName1);
        Menu menu2 = Menu.from(menuName2);
        Menu menu3 = Menu.from(menuName3);
        Menu menu4 = Menu.from(menuName4);

        // then
        assertThat(menu1.getCategory()).isEqualTo(Category.APPETIZER);
        assertThat(menu2.getCategory()).isEqualTo(Category.MAINDISH);
        assertThat(menu3.getCategory()).isEqualTo(Category.DESSERT);
        assertThat(menu4.getCategory()).isEqualTo(Category.DRINK);
    }

    @DisplayName("from 메소드 - 존재하지 않는 메뉴명으로 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"짜장면", "짬뽕", "김밥", "아메리카노", "콘샐러드", "바닐라라떼", "딸기생크림케이크"})
    public void should_Throw_Exception_For_Non_existent_MenuName(String menuName) {
        // when
        // then
        assertThatThrownBy(() -> Menu.from(menuName))
                .isInstanceOf(MenuInputException.class);
    }
}