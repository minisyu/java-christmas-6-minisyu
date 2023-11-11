package christmas;

import christmas.controller.ReservationController;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ReservationController reservationController = new ReservationController(inputView, outputView);
        reservationController.run();
    }
}
