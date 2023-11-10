package christmas.controller;

import christmas.domain.Reservation;
import christmas.view.InputView;

public class OrderController {
    private final InputView inputView;

    public OrderController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        // 날짜와 메뉴/개수를 입력 받는다
        Reservation reservation = inputView.inputReservation();
        // todo
    }
}

