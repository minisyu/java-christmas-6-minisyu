package christmas.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import christmas.exception.MenuInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {

    @DisplayName("메뉴와 개수 입력 형식 유효성 검사 - 성공")
    @Test
    public void should_Validate_MenuItems_Input_Format1() {
        String input = "아이스크림-2";
        InputValidator.validateMenuItemsInputFormat(input);
        assertTrue(true);
    }

    @DisplayName("메뉴와 개수 입력 형식 유효성 검사 - 성공")
    @Test
    public void should_Validate_MenuItems_Input_Format2() {
        String input = "아이스크림-2,티본스테이크-1";
        InputValidator.validateMenuItemsInputFormat(input);
        assertTrue(true);
    }

    @DisplayName("메뉴와 개수 입력 형식 유효성 검사 - 실패")
    @ParameterizedTest
    @ValueSource(strings = {"아이스크림-2,티본스테이크", "아이스크림-2,티본스테이크-1,", ",아이스크림-2"})
    public void should_Throw_Exception_For_Invalid_MenuItems_Input_Format(String input) {
        assertThatThrownBy(() -> InputValidator.validateMenuItemsInputFormat(input))
                .isInstanceOf(MenuInputException.class);
    }
}