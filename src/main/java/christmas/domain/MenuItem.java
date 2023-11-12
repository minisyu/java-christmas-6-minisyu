package christmas.domain;

import christmas.exception.MenuInputException;

/**
 * 주문 메뉴와 개수
 */
public class MenuItem {
    private static final int MIN_QUANTITY = 1;
    private final Menu menu;
    private final int quantity;

    private MenuItem(Menu menu, int quantity) {
        validateQuantity(quantity);
        this.menu = menu;
        this.quantity = quantity;
    }

    /**
     * @param input 사용자 입력 값 (ex. 티본스테이크-2,아이스크림-1)
     * @return MenuItem
     */
    public static MenuItem from(String input) {
        String[] details = input.split("-");
        String menuName = details[0];
        Menu menu = Menu.from(menuName);

        int quantity = Integer.parseInt(details[1]);
        return new MenuItem(menu, quantity);
    }

    /**
     * 메뉴의 개수가 1이상인지 검증
     */
    private static void validateQuantity(int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new MenuInputException();
        }
    }

    /**
     * @return 메뉴명
     */
    public String getFoodName() {
        return menu.getFoodName();
    }

    /**
     * @return 주문 개수
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
}