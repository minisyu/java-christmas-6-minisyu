package christmas.domain;

import christmas.domain.dto.MenuItemDto;
import christmas.exception.MenuInputException;

public class MenuItem {
    public static final int WEEK_DISCOUNT_PRICE = 2_023;
    private static final int MIN_QUANTITY = 1;
    private final Menu menu;
    private final int quantity;

    private MenuItem(Menu menu, int quantity) {
        validateQuantity(quantity);
        this.menu = menu;
        this.quantity = quantity;
    }

    public static MenuItem from(String input) {
        String[] details = input.split("-");
        String menuName = details[0];
        Menu menu = Menu.from(menuName);

        int quantity = Integer.parseInt(details[1]);
        return new MenuItem(menu, quantity);
    }

    private static void validateQuantity(int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new MenuInputException();
        }
    }

    public String getFoodName() {
        return menu.getFoodName();
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isOfCategoryDrink() {
        return menu.getCategory() == Category.DRINK;
    }

    public int calculateMenuItemPrice() {
        return menu.getFoodPrice() * quantity;
    }

    public boolean isSameCategory(Category category) {
        return menu.getCategory() == category;
    }

    public int calculateWeekDiscountPrice() {
        return WEEK_DISCOUNT_PRICE * quantity;
    }

    public MenuItemDto toMenuItemDto() {
        return new MenuItemDto(menu.getFoodName(), quantity);
    }
}
