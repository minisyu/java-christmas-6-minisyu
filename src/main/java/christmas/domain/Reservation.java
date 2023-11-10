package christmas.domain;

public class Reservation {
    private final MenuItems menuItems;
    private final int visitDate;

    public Reservation(MenuItems menuItems, int visitDate) {
        this.menuItems = menuItems;
        this.visitDate = visitDate;
    }
}
