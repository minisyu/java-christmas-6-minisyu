package christmas.exception;

public class VisitDateException extends IllegalArgumentException {
    private static final String INVALID_VISITDATE_MESSAGE = "유효하지 않은 날짜입니다. 다시 입력해 주세요.";

    public VisitDateException() {
        super(INVALID_VISITDATE_MESSAGE);
    }
}
