package christmas.domain;

import christmas.exception.MenuInputException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuItems {
    private final List<MenuItem> menuItems;

    private MenuItems(List<MenuItem> menuItems) {
        validateDuplicationMenuName(menuItems);
        validateTotalMenuQuantity(menuItems);
        validateMenuCategory(menuItems);
        this.menuItems = menuItems;
    }

    // imp. getter를 꼭 사용해야 하는지 고민, MenuItems에서 할 수 있지 않을까?
    private static int getDuplicatedMenuNameCount(List<MenuItem> menuItems) {
        return (int) menuItems.stream()
                .map(MenuItem::toStringFoodName)
                .distinct()
                .count();
    }

    /**
     * 중복 메뉴명 입력 시 검증
     */
    private static void validateDuplicationMenuName(List<MenuItem> menuItems) {
        int duplicatedMenuNameCount = getDuplicatedMenuNameCount(menuItems);
        if (duplicatedMenuNameCount != menuItems.size()) {
            throw new MenuInputException();
        }
    }

    /**
     * 총 메뉴 개수가 20개를 초과하면 예외 발생
     */
    private static void validateTotalMenuQuantity(List<MenuItem> menuItems) {
        int totalQuantity = getTotalQuantity(menuItems);
        if (totalQuantity > 20) {
            throw new MenuInputException();
        }
    }

    // imp. getter를 꼭 사용해야 하는지 고민, MenuItems에서 할 수 있지 않을까?
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
            throw new MenuInputException();
        }
    }

    // imp. getter를 꼭 사용해야 하는지 고민, MenuItems에서 할 수 있지 않을까?
    private static boolean areAllItemsDrink(List<MenuItem> menuItems) {
        return menuItems.stream()
                .allMatch(MenuItem::isOfCategoryDrink);
//        return menuItems.stream()
//                .map(MenuItem::getMenu)
//                .allMatch(menu -> menu.getCategory() == Category.DRINK);
    }

    public static MenuItems from(String input) {
        // ["icecream-1", "cjdrnrwkd-2", "sss-3"]
        List<MenuItem> menuItems = Arrays.stream(input.split(","))
                .map(MenuItem::from)
                .toList();

        return new MenuItems(menuItems);
    }

    // imp. getter를 꼭 사용해야 하는지 고민, MenuItems에서 할 수 있지 않을까?
    public int getTotalPrice() {
        return menuItems.stream()
                .mapToInt(MenuItem::sumMenuPrice)
                .sum();
//        int totalPrice = 0;
//
//        for (MenuItem menuItem : menuItems) {
//            totalPrice += menuItem.sumMenuPrice();
//        }
//        return totalPrice;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * @return ReservationDto의 <주문 메뉴>에 해당하는 Dto 필드 값 생성
     */
    public List<MenuItemDto> toMenuItemsDto() {
        List<MenuItemDto> menuItemDtos = new ArrayList<>();
        for (MenuItem menuItem : menuItems) {
            MenuItemDto menuItemDto = menuItem.toMenuItemDto();
            menuItemDtos.add(menuItemDto);
        }
        return menuItemDtos;
    }
}