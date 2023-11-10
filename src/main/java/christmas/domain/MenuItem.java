package christmas.domain;

public class MenuItem {
    private final Menu menu;
    private final int quantity;

    public MenuItem(Menu menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }
}
