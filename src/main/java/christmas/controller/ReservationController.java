package christmas.controller;

import christmas.domain.Reservation;
import christmas.domain.ReservationDto;
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
        // 1. 날짜와 메뉴 및 개수를 입력 받는다
        Reservation reservation = inputView.inputReservation();
        // 2. 할인 적용
        reservation.discount();
        // 3. outputview에 필요한 데이터 생성
        ReservationDto reservationDto = reservation.toReservationDto();
        // 4. outputview에서 혜택 내역 출력
        outputView.printEvent(reservationDto);
    }
}
