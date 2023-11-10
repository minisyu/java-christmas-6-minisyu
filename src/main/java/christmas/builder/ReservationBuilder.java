package christmas.builder;

import christmas.domain.MenuItems;
import christmas.domain.Reservation;
import christmas.parser.StringParser;
import java.time.LocalDate;


public class ReservationBuilder {
    private LocalDate visitDate;
    private MenuItems menuItems;
    private boolean isVisitDateNotSet = true;
    private boolean isMenuItemsNotSet = true;

    private ReservationBuilder() {
    }

    public static ReservationBuilder builder() {
        return new ReservationBuilder();
    }

    public ReservationBuilder withMenuItems(String input) {
        MenuItems menuItems = StringParser.parseInputToMenuItems(input);
        this.menuItems = menuItems;
        this.isMenuItemsNotSet = false;
        return this;
    }

    public ReservationBuilder withVisitDate(String input) {
        LocalDate visitDate = StringParser.parseInputToLocalDate(input);
        this.visitDate = visitDate;
        this.isVisitDateNotSet = false;
        return this;
    }

    public Reservation build() {
        verifyAllFieldsAreSet();
        return Reservation.from(menuItems, visitDate);
    }

    private void verifyAllFieldsAreSet() {
        if (isMenuItemsNotSet || isVisitDateNotSet) {
            throw new IllegalArgumentException("필드를 모두 설정해주세요.");
        }
    }
}
