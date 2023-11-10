package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.builder.ReservationBuilder;
import christmas.domain.Reservation;
import christmas.validator.InputValidator;

public class InputView {

    /**
     * 두 개의 입력을 받아서 Reservation 객체 생성하여 반환
     */
    public Reservation inputReservation() {
        ReservationBuilder builder = ReservationBuilder.builder();
        inputDate(builder);
        inputMenuItems(builder);
        return builder.build();
    }

    /**
     * 날짜 입력 받기
     */
    private ReservationBuilder inputDate(ReservationBuilder builder) {
        try {
            System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.\n"
                    + "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
            String input = readLine();
            InputValidator.validateNonEmptyInput(input);
            InputValidator.validateNumericString(input);
            builder.withVisitDate(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputDate(builder);
        }
        return builder;
    }

    /**
     * 메뉴와 개수 입력 받기
     */
    private ReservationBuilder inputMenuItems(ReservationBuilder builder) {
        try {
            System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
            String input = readLine();
            InputValidator.validateNonEmptyInput(input);
            InputValidator.validateInputFormat(input);
            builder.withMenuItems(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputMenuItems(builder);
        }
        return builder;
    }

    private String readLine() {
        return Console.readLine().trim();
    }
}
