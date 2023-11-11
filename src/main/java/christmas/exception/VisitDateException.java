package christmas.exception;

public class VisitDateException extends IllegalArgumentException {
    public VisitDateException() {
        super("유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }
}
