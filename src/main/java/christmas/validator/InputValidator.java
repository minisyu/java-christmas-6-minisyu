package christmas.validator;

import christmas.exception.VisitDateException;
import java.util.regex.Pattern;

public class InputValidator {
    private static final String NUMBER_REGEX = "\\d+";
    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
    //private static final String MENU_ITEM_REGEX = ".*\\w+\\s*-\\s*\\d+(,\\w+\\s*-\\s*\\d+)*";
    private static final String MENU_ITEM_REGEX = "\\w+-\\d+(,\\w+-\\d+)*";
    private static final Pattern MENU_ITEM_PATTERN = Pattern.compile(MENU_ITEM_REGEX);

    /**
     * 여러 메뉴-개수 쌍을 쉼표로 구분된 형태로 입력받지 않으면 예외 발생
     */
    public static void validateMenuItemsInputFormat(String input) {
        if (!MENU_ITEM_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    /**
     * 입력하지 않으면 예외 발생
     */
    public static void validateNonEmptyInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("유효하지 않은 입력입니다. 다시 입력해 주세요.");
        }
    }

    /**
     * 숫자를 입력하지 않으면 예외 발생
     */
    public static void validateVisitDateString(String input) {
        if (!NUMBER_PATTERN.matcher(input).matches()) {
            throw new VisitDateException();
        }
    }

}
