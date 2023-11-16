package christmas.domain.reservation.menu;

import christmas.domain.dto.MenuItemDto;
import christmas.exception.MenuInputException;
import java.util.Arrays;
import java.util.List;

public class MenuItems {
    private final List<MenuItem> menuItems;

    private MenuItems(List<MenuItem> menuItems) {
        validateDuplicateMenuNames(menuItems);
        validateTotalMenuQuantity(menuItems);
        validateMenuCategoryForDrinks(menuItems);
        this.menuItems = menuItems;
    }

    public static MenuItems from(String input) {
        List<MenuItem> menuItems = Arrays.stream(input.split(","))
                .map(MenuItem::from)
                .toList();
        return new MenuItems(menuItems);
    }

    private static void validateDuplicateMenuNames(List<MenuItem> menuItems) {
        int duplicatedMenuNames = countDuplicateMenuNames(menuItems);
        if (duplicatedMenuNames != menuItems.size()) {
            throw new MenuInputException();
        }
    }

    private static int countDuplicateMenuNames(List<MenuItem> menuItems) {
        return (int) menuItems.stream()
                .map(MenuItem::getFoodName)
                .distinct()
                .count();
    }

    private static void validateTotalMenuQuantity(List<MenuItem> menuItems) {
        int totalQuantity = calculateTotalQuantity(menuItems);
        if (totalQuantity > 20) {
            throw new MenuInputException();
        }
    }

    private static int calculateTotalQuantity(List<MenuItem> menuItems) {
        return menuItems.stream()
                .mapToInt(MenuItem::getQuantity)
                .sum();
    }

    private static void validateMenuCategoryForDrinks(List<MenuItem> menuItems) {
        boolean areAllDrinks = isAllMenuItemsDrink(menuItems);
        if (areAllDrinks) {
            throw new MenuInputException();
        }
    }

    private static boolean isAllMenuItemsDrink(List<MenuItem> menuItems) {
        return menuItems.stream()
                .allMatch(MenuItem::isOfCategoryDrink);
    }

    public int calculateMenuItemsTotalPrice() {
        return menuItems.stream()
                .mapToInt(MenuItem::calculateMenuItemPrice)
                .sum();
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public List<MenuItemDto> toMenuItemsDto() {
        return menuItems.stream()
                .map(MenuItem::toMenuItemDto)
                .toList();
    }
}
