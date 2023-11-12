package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.MenuItems;
import christmas.domain.Reservation;
import christmas.domain.date.VisitDate;
import christmas.validator.InputValidator;
import java.util.function.Supplier;

public class InputView {

    private static final String START_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String VISIT_DATE_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String MENU_ITEMS_MESSAGE = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";

    private <T> T retryIfThrow(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.printf("[ERROR] %s\n", e.getMessage());
            }
        }
    }

    /**
     * @return 메뉴와 개수 및 날짜로 Reservation 생성
     */
    public Reservation inputReservation() {
        System.out.println(START_MESSAGE);
        VisitDate visitDate = retryIfThrow(this::inputVisitDate);
        MenuItems menuItems = retryIfThrow(this::inputMenuItems);
        return Reservation.from(visitDate, menuItems);
    }

    /**
     * @return 메뉴와 개수로 MenuItems를 생성
     */
    public MenuItems inputMenuItems() {
        System.out.println(MENU_ITEMS_MESSAGE);
        String input = readLine();
        InputValidator.validateMenuItemsInputFormat(input);
        return MenuItems.from(input);
    }

    /**
     * @return 날짜로 VisitDate를 생성
     */
    public VisitDate inputVisitDate() {
        System.out.println(VISIT_DATE_MESSAGE);
        String input = readLine();
        InputValidator.validateVisitDateString(input);
        return VisitDate.from(input);
    }

    private String readLine() {
        return Console.readLine().trim();
    }
}
