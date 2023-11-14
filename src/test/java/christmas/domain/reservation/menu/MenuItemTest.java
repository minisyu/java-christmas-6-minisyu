package christmas.domain.reservation.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import christmas.domain.dto.MenuItemDto;
import christmas.domain.reservation.menu.Category;
import christmas.domain.reservation.menu.Menu;
import christmas.domain.reservation.menu.MenuItem;
import christmas.exception.MenuInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuItemTest {

    @DisplayName("사용자 입력으로부터 MenuItem 생성")
    @ParameterizedTest
    @ValueSource(strings = {"아이스크림-2", "티본스테이크-1", "초코케이크-5", "제로콜라-7"})
    public void should_Create_MenuItem_From_Input(String input) {
        // when
        MenuItem menuItem = MenuItem.from(input);

        // then
        assertNotNull(menuItem);
        assertThat(menuItem).isInstanceOf(MenuItem.class);
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

    @DisplayName("메뉴의 가격과 개수로 주문한 음식 금액을 계산")
    @Test
    void should_Calculate_MenuItem_Price() {
        // given
        String input1 = "양송이수프-3";
        MenuItem menuItem1 = MenuItem.from(input1);

        String input2 = "바비큐립-1";
        MenuItem menuItem2 = MenuItem.from(input2);

        String input3 = "초코케이크-2";
        MenuItem menuItem3 = MenuItem.from(input3);

        String input4 = "레드와인-1";
        MenuItem menuItem4 = MenuItem.from(input4);

        // when
        int menuItemPrice1 = menuItem1.calculateMenuItemPrice();
        int menuItemPrice2 = menuItem2.calculateMenuItemPrice();
        int menuItemPrice3 = menuItem3.calculateMenuItemPrice();
        int menuItemPrice4 = menuItem4.calculateMenuItemPrice();

        // then
        assertThat(menuItemPrice1).isEqualTo(Menu.MUSHROOM_SOUP.getFoodPrice() * 3);
        assertThat(menuItemPrice2).isEqualTo(Menu.BARBECUE_RIBS.getFoodPrice());
        assertThat(menuItemPrice3).isEqualTo(Menu.CHOCOLATE_CAKE.getFoodPrice() * 2);
        assertThat(menuItemPrice4).isEqualTo(Menu.RED_WINE.getFoodPrice());
    }

    @DisplayName("메뉴의 카테고리가 주어진 카테고리랑 동일한지 확인")
    @Test
    void should_Check_If_Same_Category() {
        // given
        String input = "타파스-1";
        MenuItem menuItem = MenuItem.from(input);

        // when
        // then
        assertTrue(menuItem.isSameCategory(Category.APPETIZER));
        assertFalse(menuItem.isSameCategory(Category.MAINDISH));
        assertFalse(menuItem.isSameCategory(Category.DESSERT));
        assertFalse(menuItem.isSameCategory(Category.DRINK));
    }

    @DisplayName("주말/평일 할인 혜택 가격을 메뉴의 개수에 따라 계산하는지 확인")
    @Test
    void should_Calculate_Week_Discount_Price() {
        // given
        String input = "아이스크림-2";
        MenuItem menuItem = MenuItem.from(input);

        // when
        int discountPrice = menuItem.calculateWeekDiscountPrice();

        // then
        assertThat(discountPrice).isEqualTo(2_023 * 2);
    }

    @DisplayName("MenuItemDto를 생성하는지 확인")
    @Test
    void should_Create_MenuItemDto() {
        // given
        String input = "크리스마스파스타-2";
        MenuItem menuItem = MenuItem.from(input);

        // when
        MenuItemDto menuItemDto = menuItem.toMenuItemDto();

        // then
        assertNotNull(menuItemDto);
        assertThat(menuItemDto.menuName()).isEqualTo("크리스마스파스타");
        assertThat(menuItemDto.quantity()).isEqualTo(2);
    }
}