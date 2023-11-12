package christmas.validator;

import christmas.exception.MenuInputException;
import christmas.exception.VisitDateException;
import java.util.regex.Pattern;

public class InputValidator {
    private static final String NUMBER_REGEX = "\\d+";
    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
    private static final String MENU_ITEM_REGEX = "([가-힣]+-\\d+)(,[가-힣]+-\\d+)*";
    private static final Pattern MENU_ITEM_PATTERN = Pattern.compile(MENU_ITEM_REGEX);

    /**
     * 여러 메뉴-개수 쌍을 쉼표로 구분된 형태로 입력받지 않으면 예외 발생
     */
    public static void validateMenuItemsInputFormat(String input) {
        if (!MENU_ITEM_PATTERN.matcher(input).matches()) {
            throw new MenuInputException();
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
