package christmas.domain;

import christmas.exception.MenuInputException;

public class MenuItem {
    private static final int MIN_QUANTITY = 1;
    private final Menu menu;
    private final int quantity;

    private MenuItem(Menu menu, int quantity) {
        validateQuantity(quantity);
        this.menu = menu;
        this.quantity = quantity;
    }

    public static MenuItem from(String input) {
        // "a-1" ["a", "1"
        String[] split = input.split("-");
        String menuName = split[0];

        Menu menu = Menu.from(menuName);
        int quantity = Integer.parseInt(split[1]);
        return new MenuItem(menu, quantity);
    }

    /**
     * 메뉴의 개수를 1이상의 숫자를 입력하는지 검증
     */
    private static void validateQuantity(int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new MenuInputException();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    /**
     * @param category 다른 카테고리
     * @return 동일한 카테고리이면 true, 아니면 false
     */
    public boolean isSameCategory(Category category) {
        return menu.getCategory() == category;
    }

    /**
     * @return 메뉴 가격 * 메뉴 개수
     */
    public int sumMenuPrice() {
        return menu.getFoodPrice() * quantity;
    }

    /**
     * @return 메뉴명 반환
     */
    public String toStringFoodName() {
        return menu.getFoodName();
    }

    /**
     * @return 매뉴의 가격 반환
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return 메뉴의 카테고리가 음료이면 true, 아니면 false 반환
     */
    public boolean isOfCategoryDrink() {
        return menu.getCategory() == Category.DRINK;
    }


    /**
     * @return MenuItemDto(응답) 생성
     */
    public MenuItemDto toMenuItemDto() {
        return new MenuItemDto(menu.getFoodName(), quantity);
    }
}
