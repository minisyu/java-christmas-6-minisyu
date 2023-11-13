package christmas.controller;

import christmas.domain.Reservation;
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
        // 사용자가 입력한 메뉴와 개수 및 날짜를 입력 받는다
        Reservation reservation = inputView.inputReservation();

        // 이벤트를 적용한다
        reservation.applyEvents();
    }
}
