package christmas.parser;

import christmas.domain.Menu;
import christmas.domain.MenuItem;
import christmas.domain.MenuItems;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StringParser {
    public static LocalDate parseInputToInt(String input) {
        int visitDate = Integer.parseInt(input);
        validateVisitDateRange(visitDate);
        return LocalDate.of(2023, 12, visitDate);
    }

    /**
     * 날짜가 1 이상 31 이하의 숫자가 아니면 예외 발생
     */
    private static void validateVisitDateRange(int visitDate) {
        if (visitDate < 1 || visitDate > 31) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    /**
     * @param input 사용자 입력 값
     * @return 메뉴와 개수를 지닌 Menu의 일급 컬렉션
     * <p>
     * ex. MenuItems { Menu{T_BONE_STEAK, 1}, Menu{ICE_CREAM, 2}}
     */
    public static MenuItems parseInputToMenuItems(String input) {
        String[] orders = input.split(",");
        List<MenuItem> menuItems = createMenuItems(orders);
        return MenuItems.from(menuItems);
    }

    private static List<MenuItem> createMenuItems(String[] orders) {
        List<MenuItem> menuItems = new ArrayList<>();
        for (String order : orders) {
            String[] orderDetail = order.split("-");
            Menu menu = Menu.from(orderDetail[0]);
            int quantity = Integer.parseInt(orderDetail[1]);
            MenuItem menuItem = MenuItem.from(menu, quantity);
            menuItems.add(menuItem);
        }
        return menuItems;
    }
}
