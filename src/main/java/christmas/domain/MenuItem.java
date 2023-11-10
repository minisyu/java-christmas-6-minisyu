package christmas.domain;

public class MenuItem {
    private final Menu menu;
    private final int quantity;

    private MenuItem(Menu menu, int quantity) {
        validateQuantity(quantity);
        this.menu = menu;
        this.quantity = quantity;
    }

    public static MenuItem from(Menu menu, int quantity) {
        return new MenuItem(menu, quantity);
    }

    /**
     * 메뉴의 개수를 1이상의 숫자를 입력하는지 검증
     */
    private static void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public int getQuantity() {
        return quantity;
    }
}
