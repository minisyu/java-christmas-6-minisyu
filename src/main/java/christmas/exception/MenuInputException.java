package christmas.exception;

public class MenuInputException extends IllegalArgumentException {
    public MenuInputException() {
        super("유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }
}
