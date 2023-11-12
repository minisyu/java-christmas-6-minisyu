package christmas.domain;

import christmas.domain.date.VisitDate;

/**
 * 예약 내역
 */
public class Reservation {
    private final VisitDate visitDate;
    private final MenuItems menuItems;

    private Reservation(VisitDate visitDate, MenuItems menuItems) {
        this.visitDate = visitDate;
        this.menuItems = menuItems;
    }

    public static Reservation from(VisitDate visitDate, MenuItems menuItems) {
        return new Reservation(visitDate, menuItems);
    }
}
