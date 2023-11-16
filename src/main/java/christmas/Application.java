package christmas;

import christmas.controller.ReservationController;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ReservationController reservationController = new ReservationController(inputView, outputView);
        reservationController.run();
    }
}
