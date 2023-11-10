package christmas.domain;

import java.time.LocalDate;

public class Reservation {
    private final MenuItems menuItems;
    private final LocalDate visitDate;

    private Reservation(MenuItems menuItems, LocalDate visitDate) {
        this.menuItems = menuItems;
        this.visitDate = visitDate;
    }

    public static Reservation from(MenuItems menuItems, LocalDate visitDate) {
        return new Reservation(menuItems, visitDate);
    }
}
