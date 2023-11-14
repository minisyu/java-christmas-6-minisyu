package christmas.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.exception.MenuInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {

    @DisplayName("메뉴와 개수 입력 형식 유효성 검사 - 성공")
    @Test
    public void should_Validate_MenuItems_Input_Format1() {
        // given
        String input = "아이스크림-2";

        // when
        // then
        assertDoesNotThrow(() -> InputValidator.validateMenuItemsInputFormat(input));

    }

    @DisplayName("메뉴와 개수 입력 형식 유효성 검사 - 성공")
    @Test
    public void should_Validate_MenuItems_Input_Format2() {
        // given
        String input = "아이스크림-2,티본스테이크-1";

        // when
        // then
        assertDoesNotThrow(() -> InputValidator.validateMenuItemsInputFormat(input));
    }

    @DisplayName("메뉴와 개수 입력 형식 유효성 검사 - 실패")
    @ParameterizedTest
    @ValueSource(strings = {"아이스크림-2,티본스테이크", "아이스크림-2,티본스테이크-1,", ",아이스크림-2"})
    public void should_Throw_Exception_For_Invalid_MenuItems_Input_Format(String input) {
        // when
        // then
        assertThatThrownBy(() -> InputValidator.validateMenuItemsInputFormat(input))
                .isInstanceOf(MenuInputException.class);
    }
}