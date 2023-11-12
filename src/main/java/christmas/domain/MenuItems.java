package christmas.domain;

import christmas.exception.MenuInputException;
import java.util.Arrays;
import java.util.List;

/**
 * MenuItem 일급 컬렉션
 */
public class MenuItems {
    private final List<MenuItem> menuItems;

    private MenuItems(List<MenuItem> menuItems) {
        validateDuplicateMenuNames(menuItems);
        validateTotalMenuQuantity(menuItems);
        validateMenuCategoryForDrinks(menuItems);
        this.menuItems = menuItems;
    }

    /**
     * @param input 사용자가 입력한 메뉴-개수
     * @return MenuItems
     */
    public static MenuItems from(String input) {
        List<MenuItem> menuItems = Arrays.stream(input.split(","))
                .map(MenuItem::from)
                .toList();
        return new MenuItems(menuItems);
    }

    /**
     * 중복 메뉴명 입력 검증
     */
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

    /**
     * 총 메뉴 개수가 최대 20개인지 검증
     */
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

    /**
     * 음료만 주문한 경우 검증
     */
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
}
