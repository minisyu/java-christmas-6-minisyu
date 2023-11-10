package christmas;

import christmas.controller.OrderController;
import christmas.view.InputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        InputView inputView = new InputView();
        OrderController orderController = new OrderController(inputView);
        orderController.run();
    }
}
