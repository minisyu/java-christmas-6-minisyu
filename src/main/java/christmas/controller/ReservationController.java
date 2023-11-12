package christmas.controller;

import christmas.domain.Confirmation;
import christmas.domain.Order;
import christmas.domain.dto.ReservationDto;
import christmas.view.InputView;
import christmas.view.OutputView;

public class ReservationController {
    private final InputView inputView;
    private final OutputView outputView;

    public ReservationController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        // v1
//        // 1. 날짜와 메뉴 및 개수를 입력 받는다
//        Reservation reservation = inputView.inputReservation();
//        // 2. 할인 적용
//        reservation.discount();
//        // 3. outputview에 필요한 데이터 생성
//        ReservationDto reservationDto = reservation.toReservationDto();
//        // 4. outputview에서 혜택 내역 출력
//        outputView.printEvent(reservationDto);

        // v2
        // 1. 날짜와 메뉴 및 개수를 입력 받는다
        Order order = inputView.inputReservation2();

        // 2. 할인 적용
        // eventManager.discount();
        order.discount();

        // 3. 예약 내역을 생성한다
        Confirmation confirmation = order.from();

        // 4. outputview에 필요한 데이터 생성
        ReservationDto reservationDto = confirmation.toReservationDto();

        // 5. outputview에서 혜택 내역 출력
        outputView.printEvent(reservationDto);
    }
}
