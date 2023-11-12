package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import christmas.domain.dto.MenuItemDto;
import christmas.exception.MenuInputException;
import java.util.List;
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

    @DisplayName("주문한 메뉴들의 총 가격을 계산")
    @Test
    void should_Calculate_Total_Price_Of_MenuItems() {
        // given
        String input = "시저샐러드-1,크리스마스파스타-2,아이스크림-1,레드와인-1";
        MenuItems menuItems = MenuItems.from(input);

        // when
        int totalPrice = menuItems.calculateMenuItemsTotalPrice();

        // then
        int expectedPrice = Menu.CAESAR_SALAD.getFoodPrice() +
                Menu.CHRISTMAS_PASTA.getFoodPrice() * 2 +
                Menu.ICE_CREAM.getFoodPrice() +
                Menu.RED_WINE.getFoodPrice();

        assertEquals(expectedPrice, menuItems.calculateMenuItemsTotalPrice());
    }

    @DisplayName("List<MenuItemDto>를 생성하는지 확인")
    @Test
    void should_Create_MenuItemDtoList() {
        // given
        String input = "타파스-1,해산물파스타-2,초코케이크-1,레드와인-1,제로콜라-3";
        MenuItems menuItems = MenuItems.from(input);

        // when
        List<MenuItemDto> menuItemsDto = menuItems.toMenuItemsDto();

        // then
        assertNotNull(menuItemsDto);
        assertEquals(5, menuItemsDto.size());

        assertEquals("타파스", menuItemsDto.get(0).menuName());
        assertEquals(1, menuItemsDto.get(0).quantity());

        assertEquals("해산물파스타", menuItemsDto.get(1).menuName());
        assertEquals(2, menuItemsDto.get(1).quantity());

        assertEquals("초코케이크", menuItemsDto.get(2).menuName());
        assertEquals(1, menuItemsDto.get(2).quantity());

        assertEquals("레드와인", menuItemsDto.get(3).menuName());
        assertEquals(1, menuItemsDto.get(3).quantity());

        assertEquals("제로콜라", menuItemsDto.get(4).menuName());
        assertEquals(3, menuItemsDto.get(4).quantity());
    }
}