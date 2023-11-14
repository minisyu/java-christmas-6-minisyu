package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.reservation.Reservation;
import christmas.domain.reservation.date.VisitDate;
import christmas.domain.reservation.menu.MenuItems;
import christmas.util.ViewConstants;
import christmas.validator.InputValidator;
import java.util.function.Supplier;

public class InputView {
    public Reservation inputReservation() {
        System.out.println(ViewConstants.START_EVENT_PLANNER_MESSAGE);
        VisitDate visitDate = retryIfThrow(this::inputVisitDate);
        MenuItems menuItems = retryIfThrow(this::inputMenuItems);
        return Reservation.from(visitDate, menuItems);
    }

    public MenuItems inputMenuItems() {
        System.out.println(ViewConstants.MENU_ITEMS_MESSAGE);
        String input = readLine();
        InputValidator.validateMenuItemsInputFormat(input);
        return MenuItems.from(input);
    }

    public VisitDate inputVisitDate() {
        System.out.println(ViewConstants.VISIT_DATE_MESSAGE);
        String input = readLine();
        InputValidator.validateVisitDateString(input);
        return VisitDate.from(input);
    }

    private <T> T retryIfThrow(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(ViewConstants.ERROR_PREFIX + e.getMessage());
            }
        }
    }

    private String readLine() {
        return Console.readLine().trim();
    }
}
