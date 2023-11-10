package christmas.domain;

public class Reservation {
    private final MenuItems menuItems;
    private final int visitDate;

    private Reservation(MenuItems menuItems, int visitDate) {
        this.menuItems = menuItems;
        this.visitDate = visitDate;
    }

    public static Reservation from(MenuItems menuItems, int visitDate) {
        return new Reservation(menuItems, visitDate);
    }
}
