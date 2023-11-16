package christmas.exception;

public class MenuInputException extends IllegalArgumentException {
    private static final String INVALID_MENU_INPUT_MESSAGE = "유효하지 않은 주문입니다. 다시 입력해 주세요.";

    public MenuInputException() {
        super(INVALID_MENU_INPUT_MESSAGE);
    }
}
