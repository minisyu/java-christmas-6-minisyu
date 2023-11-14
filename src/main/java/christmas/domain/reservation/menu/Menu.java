package christmas.domain.reservation.menu;

import christmas.exception.MenuInputException;
import java.util.Arrays;

public enum Menu {
    MUSHROOM_SOUP("양송이수프", 6_000, Category.APPETIZER),
    TAPAS("타파스", 5_500, Category.APPETIZER),
    CAESAR_SALAD("시저샐러드", 8_000, Category.APPETIZER),

    T_BONE_STEAK("티본스테이크", 55_000, Category.MAINDISH),
    BARBECUE_RIBS("바비큐립", 54_000, Category.MAINDISH),
    SEAFOOD_PASTA("해산물파스타", 35_000, Category.MAINDISH),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000, Category.MAINDISH),

    CHOCOLATE_CAKE("초코케이크", 15_000, Category.DESSERT),
    ICE_CREAM("아이스크림", 5_000, Category.DESSERT),

    ZERO_COKE("제로콜라", 3_000, Category.DRINK),
    RED_WINE("레드와인", 60_000, Category.DRINK),
    CHAMPAGNE("샴페인", 25_000, Category.DRINK);

    private final String foodName;
    private final int foodPrice;
    private final Category category;

    Menu(String foodName, int foodPrice, Category category) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.category = category;
    }

    public static Menu from(String menuName) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.foodName.equals(menuName))
                .findFirst()
                .orElseThrow(MenuInputException::new);
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public Category getCategory() {
        return category;
    }
}
