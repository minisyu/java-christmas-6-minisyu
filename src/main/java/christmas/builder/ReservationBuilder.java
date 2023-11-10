package christmas.builder;

import christmas.domain.MenuItems;
import christmas.domain.Reservation;
import christmas.parser.StringParser;


public class ReservationBuilder {
    private int visitDate;
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
        int visitDate = StringParser.parseInputToInt(input);
        validateVisitDateRange(visitDate);
        this.visitDate = visitDate;
        this.isVisitDateNotSet = false;
        return this;
    }

    /**
     * 날짜가 1 이상 31 이하의 숫자가 아니면 예외 발생
     */
    private void validateVisitDateRange(int visitDate) {
        if (visitDate < 1 || visitDate > 31) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    public Reservation build() {
        validateAllFieldsAreSet();
        return Reservation.from(menuItems, visitDate);
    }

    private void validateAllFieldsAreSet() {
        if (isMenuItemsNotSet || isVisitDateNotSet) {
            throw new IllegalArgumentException("필드를 모두 설정해주세요.");
        }
    }
}
