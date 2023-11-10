package christmas.domain;

import java.util.List;

public class MenuItems {
    private final List<MenuItem> menuItems;

    private MenuItems(List<MenuItem> menuItems) {
        validateDuplicationMenuName(menuItems);
        validateTotalMenuQuantity(menuItems);
        validateMenuCategory(menuItems);
        this.menuItems = menuItems;
    }

    public static MenuItems from(List<MenuItem> menuItems) {
        return new MenuItems(menuItems);
    }

    private static int getDuplicatedMenuNameCount(List<MenuItem> menuItems) {
        return (int) menuItems.stream()
                .map(menuItem -> menuItem.getMenu().toString())
                .distinct()
                .count();
    }

    /**
     * 중복 메뉴명 입력 시 검증
     */
    private static void validateDuplicationMenuName(List<MenuItem> menuItems) {
        int duplicatedMenuNameCount = getDuplicatedMenuNameCount(menuItems);
        if (duplicatedMenuNameCount != menuItems.size()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    /**
     * 총 메뉴 개수가 20개를 초과하면 예외 발생
     */
    private static void validateTotalMenuQuantity(List<MenuItem> menuItems) {
        int totalQuantity = getTotalQuantity(menuItems);
        if (totalQuantity > 20) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private static int getTotalQuantity(List<MenuItem> menuItems) {
        return menuItems.stream()
                .mapToInt(MenuItem::getQuantity)
                .sum();
    }

    /**
     * 음료만 주문한 경우 예외 발생
     */
    private static void validateMenuCategory(List<MenuItem> menuItems) {
        boolean isAllDrinks = areAllItemsDrink(menuItems);
        if (isAllDrinks) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private static boolean areAllItemsDrink(List<MenuItem> menuItems) {
        return menuItems.stream()
                .map(MenuItem::getMenu)
                .allMatch(menu -> menu.getCategory() == Category.DRINK);
    }
}